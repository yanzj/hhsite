package com.vilio.nlbs.commonMapper.pojo;

public class NlbsRoleMenu {
    private Integer id;

    private String code;

    private String roleCode;

    private String menuCode;

    public NlbsRoleMenu(Integer id, String code, String roleCode, String menuCode) {
        this.id = id;
        this.code = code;
        this.roleCode = roleCode;
        this.menuCode = menuCode;
    }

    public NlbsRoleMenu() {
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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }
}