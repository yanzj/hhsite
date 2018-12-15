package com.vilio.custom.pojo;

import com.vilio.comm.pojo.Role;

/**
 * 类名： CustomRole<br>
 * 功能：角色对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class CustomRole extends Role{
    public CustomRole() {
    }

    public CustomRole(Integer id, String roleId, String roleName, String status, String roleDesc, String systemId, String createTime) {
        super(id, roleId, roleName, status, roleDesc, systemId, createTime);
    }
}
