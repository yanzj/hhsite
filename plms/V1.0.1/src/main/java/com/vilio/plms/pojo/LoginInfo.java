package com.vilio.plms.pojo;

/**
 * Created by xiezhilei on 2017/7/19.
 */
public class LoginInfo {
    private Integer id;

    private String code;

    private String userNo;

    private String systemTimestamp;

    private String clientTimestamp;

    private String userName;
    private String fullName;
    private String email;
    private String mobile;
    private String cityCode;
    private String cityName;
    private String groupId;
    private String groupName;


    public LoginInfo(Integer id, String code, String userNo, String systemTimestamp, String clientTimestamp) {
        this.id = id;
        this.code = code;
        this.userNo = userNo;
        this.systemTimestamp = systemTimestamp;
        this.clientTimestamp = clientTimestamp;
    }

    public LoginInfo() {
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

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getSystemTimestamp() {
        return systemTimestamp;
    }

    public void setSystemTimestamp(String systemTimestamp) {
        this.systemTimestamp = systemTimestamp == null ? null : systemTimestamp.trim();
    }

    public String getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(String clientTimestamp) {
        this.clientTimestamp = clientTimestamp == null ? null : clientTimestamp.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
