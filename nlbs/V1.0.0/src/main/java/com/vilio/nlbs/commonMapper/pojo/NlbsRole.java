package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/19.
 */
public class NlbsRole {
    private Integer id;

    private String code;

    private String roleName;

    private String roleType;

    public NlbsRole(Integer id, String code, String roleName, String roleType) {
        this.id = id;
        this.code = code;
        this.roleName = roleName;
        this.roleType = roleType;
    }

    public NlbsRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }
}
