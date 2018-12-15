package com.vilio.nlbs.login.service.impl;

import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.NlbsLoginInfoMapper;
import com.vilio.nlbs.commonMapper.pojo.NlbsLoginInfo;
import com.vilio.nlbs.login.service.LoginService;
import com.vilio.nlbs.remote.service.RemoteUmService;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/30.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private NlbsLoginInfoMapper nlbsLoginInfoMapper;

    @Resource
    private CommonService commonService;

    @Resource
    private RemoteUmService remoteUmService;


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
        //Step 1 初始化参数及入参整理，整理调用um的参数
        Map resultMap = new HashMap();
        Map returnMap = new HashMap();
        String clientTimestamp = (String) paramMap.get(Fields.PARAM_CLIENTTIMESTAMP);
        paramMap.put(Fields.PARAM_LOGIN_TYPE, "1");//登录方式，0-用户名密码登录 1-手机密码登录 2-邮箱密码登陆
        paramMap.put(Fields.PARAM_MOBILE, paramMap.get(Fields.PARAM_USER_NAME));//登录方式，0-用户名密码登录 1-手机密码登录 2-邮箱密码登陆
        paramMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        paramMap.remove(Fields.PARAM_USER_NAME);
        resultMap = remoteUmService.callService(paramMap, Constants.UM_FUNCTION_LOGIN);
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                Map userInfo = (Map<String, Object>) bodyMap.get(Fields.PARAM_USER_INFO);
                Map groupInfo = (Map<String, Object>) bodyMap.get(Fields.PARAM_GROUP_INFO);

                //Step 2 更新用户登录session
                Map sessionMap = new HashMap();
                if(userInfo != null){
                    sessionMap.put(Fields.PARAM_USER_NO, userInfo.get(Fields.PARAM_USER_ID));
                    sessionMap.put("systemTimestamp", System.currentTimeMillis() + "");
                    sessionMap.put(Fields.PARAM_CLIENTTIMESTAMP, clientTimestamp);
                    sessionMap.put(Fields.PARAM_USER_NAME, userInfo.get(Fields.PARAM_USER_NAME));
                    sessionMap.put(Fields.PARAM_FULL_NAME, userInfo.get(Fields.PARAM_FULL_NAME));
                    sessionMap.put(Fields.PARAM_E_MAIL, userInfo.get(Fields.PARAM_E_MAIL));
                    sessionMap.put(Fields.PARAM_MOBILE, userInfo.get(Fields.PARAM_MOBILE));
                    sessionMap.put(Fields.PARAM_AGENT_ID, userInfo.get(Fields.PARAM_AGENT_ID));
                    //Step 3 整理出参，按照原接口定义整理
                    returnMap.put(Fields.PARAM_FIRSTLOGIN, userInfo.get(Fields.PARAM_FIRSTLOGIN));
                    returnMap.put(Fields.PARAM_USER_NO, userInfo.get(Fields.PARAM_USER_ID));
                    returnMap.put(Fields.PARAM_MOBILE, userInfo.get(Fields.PARAM_MOBILE));
                    returnMap.put(Fields.PARAM_USER_NAME, userInfo.get(Fields.PARAM_USER_NAME));
                    returnMap.put(Fields.PARAM_E_MAIL, userInfo.get(Fields.PARAM_E_MAIL));
                    returnMap.put(Fields.PARAM_FULL_NAME, userInfo.get(Fields.PARAM_FULL_NAME));
                    returnMap.put(Fields.PARAM_STATUS, userInfo.get(Fields.PARAM_STATUS));
                }
                if(groupInfo != null){
                    sessionMap.put(Fields.PARAM_CITY_CODE, groupInfo.get(Fields.PARAM_GROUP_CITY));
                    sessionMap.put(Fields.PARAM_CITY_NAME, groupInfo.get("groupCityAbbrName"));
                    sessionMap.put(Fields.PARAM_DISTRIBUTRO_CODE, groupInfo.get(Fields.PARAM_GROUP_ID));
                    sessionMap.put(Fields.PARAM_DISTRIBUTRO_NAME, groupInfo.get(Fields.PARAM_GROUP_NAME));

                    returnMap.put(Fields.PARAM_DISTRIBUTRO_CODE, groupInfo.get(Fields.PARAM_GROUP_ID));
                    returnMap.put(Fields.PARAM_DISTRIBUTRO_NAME, groupInfo.get(Fields.PARAM_GROUP_NAME));
                    returnMap.put(Fields.PARAM_CITY_CODE, groupInfo.get(Fields.PARAM_GROUP_CITY));
                    returnMap.put(Fields.PARAM_CITY_NAME, groupInfo.get("groupCityAbbrName"));
                }
                setSession(sessionMap);
                returnMap.put(Fields.PARAM_MENU_LIST, bodyMap.get(Fields.PARAM_MENUS));

            }
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, headMap.get(Fields.PARAM_MESSAGE_ERR_CODE));
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, headMap.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }

        return returnMap;
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
        //Step 1 初始化参数及入参整理，整理调用um的参数
        Map resultMap = new HashMap();
        Map returnMap = new HashMap();
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_NEW_PASSWORD, paramMap.get(Fields.PARAM_NEW_PASSWORD));
        remoteParamMap.put(Fields.PARAM_RE_NEW_PASSWORD, paramMap.get(Fields.PARAM_NEW_PASSWORD_AGAIN));
        remoteParamMap.put(Fields.PARAM_USER_ID, paramMap.get(Fields.PARAM_USER_NO));
        remoteParamMap.put(Fields.PARAM_OLD_PASSWORD, paramMap.get(Fields.PARAM_PASSWORD));
        resultMap = remoteUmService.callService(remoteParamMap, Constants.UM_FUNCTION_FIRST_CHANGEPWD);
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, headMap.get(Fields.PARAM_MESSAGE_ERR_CODE));
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, headMap.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }

        return returnMap;
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
        return nlbsLoginInfoMapper.selectLoginInfoByUserNo(userNo);
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

        String userNo = map.get(Fields.PARAM_USER_NO) == null ? null : map.get(Fields.PARAM_USER_NO).toString();
        String userName = map.get(Fields.PARAM_USER_NAME) == null ? null : map.get(Fields.PARAM_USER_NAME).toString();
        String fullName = map.get(Fields.PARAM_FULL_NAME) == null ? null : map.get(Fields.PARAM_FULL_NAME).toString();
        String email = map.get(Fields.PARAM_E_MAIL) == null ? null : map.get(Fields.PARAM_E_MAIL).toString();
        String mobile = map.get(Fields.PARAM_MOBILE) == null ? null : map.get(Fields.PARAM_MOBILE).toString();
        String cityCode = map.get(Fields.PARAM_CITY_CODE) == null ? null : map.get(Fields.PARAM_CITY_CODE).toString();
        String cityName = map.get(Fields.PARAM_CITY_NAME) == null ? null : map.get(Fields.PARAM_CITY_NAME).toString();
        String distributorCode = map.get(Fields.PARAM_DISTRIBUTRO_CODE) == null ? null : map.get(Fields.PARAM_DISTRIBUTRO_CODE).toString();
        String distributorName = map.get(Fields.PARAM_DISTRIBUTRO_NAME) == null ? null : map.get(Fields.PARAM_DISTRIBUTRO_NAME).toString();
        String agentId = map.get(Fields.PARAM_AGENT_ID) == null ? null : map.get(Fields.PARAM_AGENT_ID).toString();

        Map loginInfoMap = nlbsLoginInfoMapper.selectLoginInfoByUserNo(userNo);
        if (null != loginInfoMap && !loginInfoMap.isEmpty()) {
            loginInfoMap.put("systemTimestamp", map.get("systemTimestamp"));
            loginInfoMap.put("clientTimestamp", map.get("clientTimestamp"));
            loginInfoMap.put(Fields.PARAM_USER_NAME, userName);
            loginInfoMap.put(Fields.PARAM_FULL_NAME, fullName);
            loginInfoMap.put(Fields.PARAM_E_MAIL, email);
            loginInfoMap.put(Fields.PARAM_MOBILE, mobile);
            loginInfoMap.put(Fields.PARAM_CITY_CODE, cityCode);
            loginInfoMap.put(Fields.PARAM_CITY_NAME, cityName);
            loginInfoMap.put(Fields.PARAM_DISTRIBUTRO_CODE, distributorCode);
            loginInfoMap.put(Fields.PARAM_DISTRIBUTRO_NAME, distributorName);
            loginInfoMap.put(Fields.PARAM_AGENT_ID, agentId);
            nlbsLoginInfoMapper.updateLoginInfoByUserNo(loginInfoMap);
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
            nlbsLoginInfo.setUserName(userName);
            nlbsLoginInfo.setFullName(fullName);
            nlbsLoginInfo.setEmail(email);
            nlbsLoginInfo.setMobile(mobile);
            nlbsLoginInfo.setCityCode(cityCode);
            nlbsLoginInfo.setCityName(cityName);
            nlbsLoginInfo.setDistributorCode(distributorCode);
            nlbsLoginInfo.setDistributorName(distributorName);
            nlbsLoginInfo.setAgentId(agentId);
            nlbsLoginInfoMapper.insertSelective(nlbsLoginInfo);
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

        nlbsLoginInfoMapper.deleteLoginInfoByUserNo(userNo);

        Map data = new HashMap();
        result.put("data", data);
        result.put("returnCode", ReturnCode.SUCCESS_CODE);

        return result;
    }

}
