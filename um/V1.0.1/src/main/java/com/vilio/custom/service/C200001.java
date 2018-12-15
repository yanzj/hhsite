package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.comm.util.UmUtil;
import com.vilio.custom.dao.CustomMenuDao;
import com.vilio.custom.dao.CustomRoleDao;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomRole;
import com.vilio.custom.pojo.CustomUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： U200001<br>
 * 功能：对外用户登录业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200001 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200001.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private CustomRoleDao customRoleDao;

    @Resource
    private CustomMenuDao customMenuDao;

    @Resource
    private LoginComm loginComm;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //转调登录公共业务流程
        loginComm.checkParam(body);
    }

    /**
     * 登录业务主流程处理
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //根据用户名或者手机号查询用户信息(加上系统编号，对外用户区分系统)
        CustomUser queryUser = new CustomUser();
        queryUser.setUserName(body.get("userName") == null ? null : body.get("userName").toString());
        queryUser.setMobile(body.get("mobile") == null ? null : body.get("mobile").toString());
        queryUser.setEmail(body.get("email") == null ? null : body.get("email").toString());
        queryUser.setSystemId(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        CustomUser user = customUserDao.queryUser(queryUser);
        //用户是否存在
        if (null == user) {
            throw new ErrorException("9004", "");
        }
        //用户是否有效(状态是否存在，用户状态是否有效)
        loginComm.checkUserStatus(user.getStatus());
        //用户是否被锁定
        if (GlobDict.hash_lock.getKey().equals(user.getHashLock())) {
            throw new ErrorException("9001", "");
        }
        //判断密码是否正确
        if (!body.get("password").equals(user.getPassword())) {
            //错误次数加1，并判断如果错误次数达到阈值，则账号锁定
            dealLoginNum(user);
        }
        //如果登录成功，登录错误次数重置
        user.setLoginError("0");
        //清空登录次数
        int ret = customUserDao.updateLoginErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //登录成功后，设置返回参数
        resultMap.put("userInfo", user);
        //查询角色信息
        List<CustomRole> roles = getRoleInfo(user, head);
        resultMap.put("roles", roles);
        //查询菜单信息
        List<Map<String,Object>> menus = getMenuInfo(roles, head);
        resultMap.put("menus", menus);
    }

    /**
     * 查询菜单信息
     *
     * @param roles
     */
    private List<Map<String,Object>> getMenuInfo(List<CustomRole> roles, Map head) {
        if (roles == null || roles.size() == 0) {
            return null;
        }
        Map param = new HashMap();
        //菜单有效
        param.put("menuStatus", GlobDict.menu_status_valid.getKey());
        //角色菜单关系有效
        param.put("menuRoleStatus", GlobDict.role_menu_status_valid.getKey());
        param.put("systemId", head.get(GlobParam.PARAM_SYSTEM_ID));
        param.put("roles", roles);
        List<Map<String,Object>> menusTmp = customMenuDao.queryMenuByRoles(param);
        //开始进行菜单转换，转换成树形结构
        List<Map<String,Object>> menus = UmUtil.menuListToTree(menusTmp);
        return menus;
    }



    /**
     * 查询角色信息
     *
     * @param user
     */
    private List<CustomRole> getRoleInfo(CustomUser user, Map head) {
        //查询信息
        Map param = new HashMap();
        param.put("userId", user.getUserId());
        param.put("systemId", head.get(GlobParam.PARAM_SYSTEM_ID));
        //角色状态为有效
        param.put("roleStatus", GlobDict.role_status_valid.getKey());
        //用户角色状态为有效
        param.put("userRoleStatus", GlobDict.user_role_status_valid.getKey());
        List<CustomRole> roles = customRoleDao.queryRoleByUserAndSystem(param);
        return roles;
    }


    /**
     * 对密码错误次数进行处理
     *
     * @param user
     */
    private void dealLoginNum(CustomUser user) throws ErrorException {
        //登录次数加1
        String loginError = user.getLoginError();
        user.setLoginError(loginError == null || loginError == "" ? "1" : String.valueOf(Integer.parseInt(loginError) + 1));
        int ret = customUserDao.updateLoginErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        if (Integer.parseInt(user.getLoginError()) >= GlobParam.customLoginError) {
            //用户锁定
            user.setHashLock(GlobDict.hash_lock.getKey());
            user.setLockTime(String.valueOf(System.currentTimeMillis()));
            ret = customUserDao.updateHashLockByUserId(user);
            if (ret <= 0) {
                throw new ErrorException("9997", "");
            }
            throw new ErrorException("9002", "用户名或密码错误，超过" + GlobParam.customLoginError + "次锁定");
        } else {
            throw new ErrorException("9003", "用户名或密码错误，还剩" + (GlobParam.customLoginError - Integer.parseInt(user.getLoginError())) + "次锁定");
        }
    }



}
