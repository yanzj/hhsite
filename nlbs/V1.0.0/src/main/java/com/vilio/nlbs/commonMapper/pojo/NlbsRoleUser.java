package com.vilio.nlbs.commonMapper.pojo;

public class NlbsRoleUser {
    private Integer id;

    private String code;

    private String roleCode;

    private String userNo;

    public NlbsRoleUser(Integer id, String code, String roleCode, String userNo) {
        this.id = id;
        this.code = code;
        this.roleCode = roleCode;
        this.userNo = userNo;
    }

    public NlbsRoleUser() {
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

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }
}