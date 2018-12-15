package com.vilio.comm.pojo;

/**
 * 类名： Role<br>
 * 功能：角色对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Role {

    public Integer id;
    public String roleId;
    public String roleName;
    public String status;
    public String roleDesc;
    public String systemId;
    public String createTime;

    public Integer getId() {
        return id;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getStatus() {
        return status;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Role() {
    }

    public Role(Integer id, String roleId, String roleName, String status, String roleDesc, String systemId, String createTime) {
        this.id = id;
        this.roleId = roleId;
        this.roleName = roleName;
        this.status = status;
        this.roleDesc = roleDesc;
        this.systemId = systemId;
        this.createTime = createTime;
    }
}
