package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.JudgeUtil;
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
 * 类名： U200004<br>
 * 功能：对外用户明细查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200004 extends BaseService{

    private static final Logger logger = Logger.getLogger(C200004.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private CustomRoleDao customRoleDao;

    @Resource
    private CustomMenuDao customMenuDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //根据用户编码、手机号、email至少一个条件
        if (!JudgeUtil.isNull(body.get("userId"))&&!JudgeUtil.isNull(body.get("mobile"))&&!JudgeUtil.isNull(body.get("email"))&&!JudgeUtil.isNull(body.get("userName"))){
            throw new ErrorException("9998", "");
        }
        //系统编号非必填字段
        if (JudgeUtil.isNull(body.get("systemId")) && body.get("systemId").toString().length() > 40) {
            throw new ErrorException("9998", "");
        }
    }

    /**
     * 用户明细业务主流程处理
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //查询用户
        CustomUser qryUserParam = new CustomUser();
        qryUserParam.setUserId(body.get("userId")==null?null:body.get("userId").toString());
        qryUserParam.setMobile(body.get("mobile")==null?null:body.get("mobile").toString());
        qryUserParam.setEmail(body.get("email")==null?null:body.get("email").toString());
        qryUserParam.setUserName(body.get("userName")==null?null:body.get("userName").toString());
        qryUserParam.setUserName(body.get("systemId")==null?null:body.get("systemId").toString());
        CustomUser user = customUserDao.queryUser(qryUserParam);
        //用户不存在
        if (null==user){
            throw new ErrorException("9004", "");
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

}
