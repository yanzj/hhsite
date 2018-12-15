package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/19.
 */
public class NlbsUser {
    private Integer id;

    private String userNo;

    private String parentUserNo;

    private String mobile;

    private String password;

    private String userName;

    private String email;

    private String fullName;

    //业务员id(核心系统)
    private String agentId;

    private String beRecordClerk;

    private String status;

    private String firstLogin;

    public NlbsUser(Integer id, String userNo, String parentUserNo, String mobile, String password, String userName, String email, String fullName, String agentId,String status, String firstLogin) {
        this.id = id;
        this.userNo = userNo;
        this.parentUserNo = parentUserNo;
        this.mobile = mobile;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.agentId = agentId;
        this.status = status;
        this.firstLogin = firstLogin;
    }

    public NlbsUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }
    public String getParentUserNo() {
        return parentUserNo;
    }

    public void setParentUserNo(String parentUserNo) {
        this.parentUserNo = parentUserNo == null ? null : parentUserNo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getBeRecordClerk() {
        return beRecordClerk;
    }

    public void setBeRecordClerk(String beRecordClerk) {
        this.beRecordClerk = beRecordClerk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }


}
