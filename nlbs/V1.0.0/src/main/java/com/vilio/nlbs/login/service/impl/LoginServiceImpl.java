package com.vilio.nlbs.login.service.impl;

import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.NlbsDistributorMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsLoginInfoMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsLoginNumMapper;
import com.vilio.nlbs.commonMapper.pojo.NlbsDistributor;
import com.vilio.nlbs.commonMapper.pojo.NlbsLoginInfo;
import com.vilio.nlbs.commonMapper.pojo.NlbsLoginNum;
import com.vilio.nlbs.commonMapper.pojo.NlbsUser;
import com.vilio.nlbs.login.service.LoginService;
import com.vilio.nlbs.user.dao.UserDao;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/30.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    @Resource
    private NlbsLoginNumMapper nlbsLoginNumMapper;

    @Resource
    private NlbsLoginInfoMapper nlbsLoginInfoMapper;

    @Resource
    private CommonService commonService;

    @Resource
    private NlbsDistributorMapper nlbsDistributorMapper;


    /**
     * 用户登录
     *
     * @param paramMap
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map login(Map paramMap) throws Exception {
        Map resultMap = new HashMap();
        String userName = (String) paramMap.get(Fields.PARAM_USER_NAME);
        String password = (String) paramMap.get(Fields.PARAM_PASSWORD);
        String clientTimestamp = (String) paramMap.get(Fields.PARAM_CLIENTTIMESTAMP);

        //Step 1 用户登录
        //  Step 1.1如果用户信息表中不存在，判断错误次数并处理
        NlbsUser nlbsUser = userDao.queryNlbsUserByUserName(userName);
        if (null == nlbsUser) {
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_WRONG_USER_NO);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "用户不存在！");

            return resultMap;
        }
        //Step 1.2如果在登录信息表中，且锁定
        NlbsLoginNum nlbsLoginNum = userDao.queryNlbsLoginNumByUserName(userName);
        if (null != nlbsLoginNum && nlbsLoginNum.getHaslock()) {
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_HAVE_LOCKED);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "该用户已被锁定，请联系管理员解锁");

            return resultMap;
        }
        //Step 1.3密码不正确，对登陆错误次数处理
        NlbsUser queryParam = new NlbsUser();
        queryParam.setUserName(userName);
        queryParam.setPassword(password);
        NlbsUser userPsw = userDao.queryNlbsUserByUserNameOrUserNoAndPsw(queryParam);
        if (null == userPsw) {
            return dealLoginNum(nlbsUser.getUserNo(), nlbsLoginNum);//对登录次数的处理
        }
        //Step 1.4删除登录错误次数表信息
        userDao.deleteNlbsLoginNumByUserNo(nlbsUser.getUserNo());

        //Step 1.5更新用户登录session
        Map sessionMap = new HashMap();
        sessionMap.put(Fields.PARAM_USER_NO, nlbsUser.getUserNo());
        sessionMap.put("systemTimestamp", System.currentTimeMillis() + "");
        sessionMap.put(Fields.PARAM_CLIENTTIMESTAMP, clientTimestamp);
        setSession(sessionMap);

        //Step 1.6 登录成功，整理登录成功相关出参
        resultMap.put(Fields.PARAM_USER_NO, nlbsUser.getUserNo());
        resultMap.put(Fields.PARAM_USER_NAME, nlbsUser.getUserName());
        resultMap.put(Fields.PARAM_MOBILE, nlbsUser.getMobile());
        resultMap.put(Fields.PARAM_E_MAIL, nlbsUser.getEmail());
        resultMap.put(Fields.PARAM_FULL_NAME, nlbsUser.getFullName());
        resultMap.put(Fields.PARAM_STATUS, nlbsUser.getStatus());
        resultMap.put(Fields.PARAM_FIRSTLOGIN, nlbsUser.getFirstLogin());
        //Step 1.6.1 根据用户业务员编码，查询渠道信息
        NlbsDistributor nlbsDistributor = nlbsDistributorMapper.selectDistributorByUserNo(nlbsUser.getUserNo());
        if(nlbsDistributor != null) {
            resultMap.put(Fields.PARAM_DISTRIBUTRO_CODE, nlbsDistributor.getCode());
            resultMap.put(Fields.PARAM_DISTRIBUTRO_NAME, nlbsDistributor.getFullName());
            resultMap.put(Fields.PARAM_CITY_CODE, nlbsDistributor.getCityCode());
        } else {
            resultMap.put(Fields.PARAM_DISTRIBUTRO_CODE, "");
            resultMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "");
        }

        //Step 2 获取菜单列表
        paramMap.put(Fields.PARAM_USER_NO, nlbsUser.getUserNo());
        Map menuMap = getMenu(paramMap);
        //Step 2.1 整理菜单出参
        resultMap.putAll(menuMap);


        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "登录成功！");

        return resultMap;
    }

    /**
     * 用户第一次登录后，修改密码
     *
     * @param paramMap
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map changePsw4FirstLogin(Map paramMap) throws Exception {
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);
        String password = (String) paramMap.get(Fields.PARAM_PASSWORD);
        String newPassword = (String) paramMap.get(Fields.PARAM_NEW_PASSWORD);
        String newPasswordAgain = (String) paramMap.get(Fields.PARAM_NEW_PASSWORD_AGAIN);

        Map result = new HashMap();


        //Step 1如果在登录信息表中，且锁定
        NlbsLoginNum nlbsLoginNum = userDao.queryNlbsLoginNumByUserNo(userNo);
        if (null != nlbsLoginNum && nlbsLoginNum.getHaslock()) {
            result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_HAVE_LOCKED);
            result.put(Fields.PARAM_MESSAGE_ERR_MESG, "该用户已被锁定，请联系管理员解锁");
            return result;
        }

        //Step 2密码不正确
        NlbsUser queryParam = new NlbsUser();
        queryParam.setUserNo(userNo);
        queryParam.setPassword(password);
        NlbsUser userPsw = userDao.queryNlbsUserByUserNameOrUserNoAndPsw(queryParam);
        if (null == userPsw) {
            return dealLoginNum(userNo, nlbsLoginNum);//对登录次数的处理;
        }

        //Step 3两次密码输入不一致
        if (!newPassword.equals(newPasswordAgain)) {
            result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_DIFF_PASSWORDRESET);
            result.put(Fields.PARAM_MESSAGE_ERR_MESG, "两次密码输入不一致");
            return result;
        }
        //Step 4删除登录错误次数信息表信息
        userDao.deleteNlbsLoginNumByUserNo(userNo);

        //Step 5更新用户密码
        userDao.updatePswByUserName(paramMap);

        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "初次登录密码修改成功！");

        return result;
    }

    /**
     * 用户修改个人信息（当前只修改密码）
     *
     * @param paramMap
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map changeInfo(Map paramMap) throws Exception {
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);
        String password = (String) paramMap.get(Fields.PARAM_PASSWORD);
        String newPassword = (String) paramMap.get(Fields.PARAM_NEW_PASSWORD);
        String newPasswordAgain = (String) paramMap.get(Fields.PARAM_NEW_PASSWORD_AGAIN);

        Map result = new HashMap();

        //Step 1如果在登录信息表中，且锁定
        NlbsLoginNum nlbsLoginNum = userDao.queryNlbsLoginNumByUserNo(userNo);
        if (null != nlbsLoginNum && nlbsLoginNum.getHaslock()) {
            result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_HAVE_LOCKED);
            result.put(Fields.PARAM_MESSAGE_ERR_MESG, "该用户已被锁定，请联系管理员解锁");
            return result;
        }

        //Step 2验证原始密码是否正确
        NlbsUser queryParam = new NlbsUser();
        queryParam.setUserNo(userNo);
        queryParam.setPassword(password);
        NlbsUser userPsw = userDao.queryNlbsUserByUserNameOrUserNoAndPsw(queryParam);
        if (null == userPsw) {
            return dealLoginNum(userNo, nlbsLoginNum);//对登录次数的处理;
        }

        //Step 3两次密码输入不一致
        if (!newPassword.equals(newPasswordAgain)) {
            result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_DIFF_PASSWORDRESET);
            result.put(Fields.PARAM_MESSAGE_ERR_MESG, "两次密码输入不一致");
            return result;
        }

        //Step 4删除登录错误次数信息表信息
        userDao.deleteNlbsLoginNumByUserNo(userNo);

        //Step 5更新用户信息
        userDao.updatePswByUserName(paramMap);

        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "用户修改个人信息（密码）成功！");

        return result;
    }

    public void deleteNlbsLoginAll() throws Exception {
        userDao.deleteNlbsLoginAll();
    }

    /**
     * 获取菜单
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map getMenu(Map paramMap) throws Exception {
        Map result = new HashMap();

        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);
        //Step 1 获取用户所能查询的一级菜单
        Map param = new HashMap();
        param.put(Fields.PARAM_USER_NO, userNo);
        param.put(Fields.PARAM_MENU_LEVEL, 1);
        List firstMenuList = userDao.getMenu(param);

        //Step 2 遍历所有一级菜单，并查询出对应的二级菜单
        for (int i = 0; i < firstMenuList.size(); i++) {
            Map menu = (HashMap) firstMenuList.get(i);
            param = new HashMap();
            param.put(Fields.PARAM_USER_NO, userNo);
            param.put(Fields.PARAM_MENU_LEVEL, 2);

            List secondMenuList = userDao.getMenu(param);
            menu.put(Fields.PARAM_SECONDMENULIST, secondMenuList);
        }

        result.put(Fields.PARAM_FIRSTMENULIST, firstMenuList);
        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取菜单列表成功");

        return result;
    }

    /**
     * 登出
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map logout(Map paramMap) throws Exception {
        Map result = new HashMap();

        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);

        Map sessionMap = new HashMap();
        sessionMap.put(Fields.PARAM_USER_NO, userNo);
        removeSession(sessionMap);

        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "登出成功");

        return result;
    }

    /**
     * 获取当前用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map getSession(Map map) throws Exception {
        String userNo = (String) map.get("userNo");
        return userDao.selectLoginInfoByUserNo(userNo);
    }

    /**
     * 设置当前用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map setSession(Map map) throws Exception {
        Map result = new HashMap();

        String userNo = (String) map.get("userNo");

        Map loginInfoMap = userDao.selectLoginInfoByUserNo(userNo);
        if (null != loginInfoMap && !loginInfoMap.isEmpty()) {
            loginInfoMap.put("systemTimestamp", map.get("systemTimestamp"));
            loginInfoMap.put("clientTimestamp", map.get("clientTimestamp"));
            userDao.updateLoginInfoByUserNo(loginInfoMap);
        } else {
            NlbsLoginInfo nlbsLoginInfo = new NlbsLoginInfo();
            nlbsLoginInfo.setCode(commonService.getUUID());
            nlbsLoginInfo.setUserNo(userNo);
            if (null != map.get("systemTimestamp")) {
                nlbsLoginInfo.setSystemTimestamp((String) map.get("systemTimestamp"));
            }
            if (null != map.get("clientTimestamp")) {
                nlbsLoginInfo.setClientTimestamp((String) map.get("clientTimestamp"));
            }
            nlbsLoginInfoMapper.insert(nlbsLoginInfo);
        }

        Map data = new HashMap();
        result.put("data", data);
        result.put("returnCode", ReturnCode.SUCCESS_CODE);

        return result;
    }

    /**
     * 删除某用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map removeSession(Map map) throws Exception {
        Map result = new HashMap();

        String userNo = (String) map.get(Fields.PARAM_USER_NO);

        userDao.deleteLoginInfoByUserNo(userNo);

        Map data = new HashMap();
        result.put("data", data);
        result.put("returnCode", ReturnCode.SUCCESS_CODE);

        return result;
    }

    /**
     * 登录次数加1，并返错误次数
     *
     * @param userNo
     * @param nlbsLoginNum
     * @return
     */
    private int addLoginNum(String userNo, NlbsLoginNum nlbsLoginNum) {
        if (null == nlbsLoginNum) {//无则新增
            nlbsLoginNum = new NlbsLoginNum();
            nlbsLoginNum.setUserNo(userNo);
            nlbsLoginNum.setErrorNum(1);
            nlbsLoginNum.setHaslock(false);
            nlbsLoginNumMapper.insert(nlbsLoginNum);
        } else {//有则更新
            nlbsLoginNum.setErrorNum(nlbsLoginNum.getErrorNum() + 1);
            nlbsLoginNumMapper.updateByPrimaryKey(nlbsLoginNum);
        }
        return nlbsLoginNum.getErrorNum();
    }

    /**
     * 处理登录次数逻辑
     *
     * @param userNo
     * @param nlbsLoginNum
     * @return
     */
    private Map dealLoginNum(String userNo, NlbsLoginNum nlbsLoginNum) {
        Map result = new HashMap();
        //登录次数加1
        int errorTime = addLoginNum(userNo, nlbsLoginNum);

        //达到最大错误次数
        if (errorTime >= Constants.USER_VALID_LOGINTIME) {
            //锁定用户
            nlbsLoginNum.setHaslock(true);
            nlbsLoginNumMapper.updateByPrimaryKey(nlbsLoginNum);
            result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_WRONG_PSD_AND_LOCKED);
            result.put(Fields.PARAM_MESSAGE_ERR_MESG, "用户名或密码错误，超过5次锁定");
            return result;
        }

        //未达到最大登录次数
        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_WRONG_PSD);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "用户名/密码错误，还剩" + (Constants.USER_VALID_LOGINTIME - errorTime) + "次机会");
        return result;
    }
}
