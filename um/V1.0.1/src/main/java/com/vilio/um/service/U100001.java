package com.vilio.um.service;

import com.vilio.comm.dao.CommDao;
import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.pojo.City;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.comm.util.UmUtil;
import com.vilio.um.dao.UmGroupDao;
import com.vilio.um.dao.UmMenuDao;
import com.vilio.um.dao.UmRoleDao;
import com.vilio.um.dao.UmUserDao;
import com.vilio.um.pojo.UmGroup;
import com.vilio.um.pojo.UmRole;
import com.vilio.um.pojo.UmUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100001<br>
 * 功能：登录业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100001 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100001.class);

    @Resource
    private UmUserDao umUserDao;

    @Resource
    private UmGroupDao umGroupDao;

    @Resource
    private UmRoleDao umRoleDao;

    @Resource
    private UmMenuDao umMenuDao;

    @Resource
    private LoginComm loginComm;

    @Resource
    private CommDao commDao;

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
        //根据用户名或者手机号查询用户信息
        UmUser queryUser = new UmUser();
        queryUser.setUserName(body.get("userName") == null ? null : body.get("userName").toString());
        queryUser.setMobile(body.get("mobile") == null ? null : body.get("mobile").toString());
        queryUser.setEmail(body.get("email") == null ? null : body.get("email").toString());
        UmUser user = umUserDao.queryUser(queryUser);
        //用户是否存在
        if (null == user) {
            throw new ErrorException("9004", "");
        }
        //查看当前用户有无权限登录此系统
        Map userSystemParam = new HashMap();
        userSystemParam.put("userId", user.getUserId());
        userSystemParam.put("systemId", head.get(GlobParam.PARAM_SYSTEM_ID));
        Map userSystem = umUserDao.queryUserSystem(userSystemParam);
        if (userSystem == null || userSystem.size() == 0
                || !GlobDict.user_system_status_valid.getKey().equals(userSystem.get("status"))) {
            //当前系统没有登录权限
            throw new ErrorException("0004", "");
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
        int ret = umUserDao.updateLoginErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //给用户设置城市信息
        setCityInfo(user);
        //登录成功后，设置返回参数
        resultMap.put("userInfo", user);
        //查询上级用户信息
        UmUser superUser = getSuperUser(user);
        //给上级用户设置城市信息
        setCityInfo(user);
        resultMap.put("superUser", superUser);
        //查询机构信息
        UmGroup groupInfo = getGroupInfo(user);
        resultMap.put("groupInfo", groupInfo);
        //查询角色信息
        List<UmRole> roles = getRoleInfo(user, head);
        resultMap.put("roles", roles);
        //查询菜单信息
        List<Map<String, Object>> menus = getMenuInfo(roles, head);
        resultMap.put("menus", menus);
    }

    /**
     * 查询菜单信息
     *
     * @param roles
     */
    private List<Map<String, Object>> getMenuInfo(List<UmRole> roles, Map head) {
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
        List<Map<String, Object>> menusTmp = umMenuDao.queryMenuByRoles(param);
        //开始进行菜单转换，转换成树形结构
        List<Map<String, Object>> menus = UmUtil.menuListToTree(menusTmp);
        return menus;
    }

    /**
     * 查询角色信息
     *
     * @param user
     */
    private List<UmRole> getRoleInfo(UmUser user, Map head) {
        //查询信息
        Map param = new HashMap();
        param.put("userId", user.getUserId());
        param.put("systemId", head.get(GlobParam.PARAM_SYSTEM_ID));
        //角色状态为有效
        param.put("roleStatus", GlobDict.role_status_valid.getKey());
        //用户角色状态为有效
        param.put("userRoleStatus", GlobDict.user_role_status_valid.getKey());
        List<UmRole> roles = umRoleDao.queryRoleByUserAndSystem(param);
        return roles;
    }

    /**
     * 获取机构信息
     *
     * @param user
     */
    private UmGroup getGroupInfo(UmUser user) throws ErrorException {
        if (user.getGroupId() == null || "".equals(user.getGroupId())) {
            return null;
        }
        UmGroup groupParam = new UmGroup();
        groupParam.setGroupId(user.getGroupId());
        UmGroup group = umGroupDao.queryGroup(groupParam);
        //机构为已删除状态，则不返回机构信息
        if (group != null && GlobDict.group_status_delete.getKey().equals(group.getStatus())) {
            return null;
        }
        //机构所在城市信息
        if (group.getGroupCity()!=null&&!"".equals(group.getGroupCity())){
            City city = commDao.queryCity(group.getGroupCity());
            if (null == city) {
                throw new ErrorException("0024", "");
            }
            group.setGroupCityFullName(city.getFullName());
            group.setGroupCityAbbrName(city.getAbbrName());
            group.setGroupCityLevel(city.getLevel());
        }
        return group;
    }

    /**
     * 获取上级用户信息
     *
     * @param user
     */
    private UmUser getSuperUser(UmUser user) {
        if (user.getSuperUserId() == null || "".equals(user.getSuperUserId())) {
            return null;
        }
        UmUser superUserQry = new UmUser();
        superUserQry.setUserId(user.getSuperUserId());
        UmUser superUser = umUserDao.queryUser(superUserQry);
        //如果上级用户是已删除状态，则返回null
        if (superUser != null && GlobDict.user_status_delete.getKey().equals(superUser.getStatus())) {
            return null;
        }
        return superUser;
    }


    /**
     * 对密码错误次数进行处理
     *
     * @param user
     */
    private void dealLoginNum(UmUser user) throws ErrorException {
        //登录次数加1
        String loginError = user.getLoginError();
        user.setLoginError(loginError == null || loginError == "" ? "1" : String.valueOf(Integer.parseInt(loginError) + 1));
        int ret = umUserDao.updateLoginErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        if (Integer.parseInt(user.getLoginError()) >= GlobParam.umLoginError) {
            //用户锁定
            user.setHashLock(GlobDict.hash_lock.getKey());
            user.setLockTime(String.valueOf(System.currentTimeMillis()));
            ret = umUserDao.updateHashLockByUserId(user);
            if (ret <= 0) {
                throw new ErrorException("9997", "");
            }
            throw new ErrorException("9002", "用户名或密码错误，超过" + GlobParam.umLoginError + "次锁定");
        } else {
            throw new ErrorException("9003", "用户名或密码错误，还剩" + (GlobParam.umLoginError - Integer.parseInt(user.getLoginError())) + "次锁定");
        }
    }


    /**
     * 给用户设置工作城市字段
     *
     * @param user
     */
    private void setCityInfo(UmUser user) throws ErrorException {
        if (user.getCityCode() == null || "".equals(user.getCityCode())) {
            //如果不存在城市编码，则直接返回
            return;
        }
        City city = commDao.queryCity(user.getCityCode());
        if (null == city) {
            throw new ErrorException("0024", "");
        }
        user.setCityFullName(city.getFullName());
        user.setCityAbbrName(city.getAbbrName());
        user.setCityLevel(city.getLevel());
    }
}
