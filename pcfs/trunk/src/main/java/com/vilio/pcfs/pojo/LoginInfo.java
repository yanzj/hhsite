package com.vilio.pcfs.pojo;

/**
 * Created by wangxf on 2017/6/14.
 */
public class LoginInfo {

    private Integer id;
    private String userId;
    private String userName;
    private String systemTimestamp;
    private String createTime;
    private String mobile;
    private String email;
    private String firstLogin;
    private String fullName;
    private String sex;
    private String birthday;
    private String birthAddr;
    private String token;
    private String nick;
    private String headImg;
    private String transPwdFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystemTimestamp() {
        return systemTimestamp;
    }

    public void setSystemTimestamp(String systemTimestamp) {
        this.systemTimestamp = systemTimestamp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthAddr() {
        return birthAddr;
    }

    public void setBirthAddr(String birthAddr) {
        this.birthAddr = birthAddr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTransPwdFlag() {
        return transPwdFlag;
    }

    public void setTransPwdFlag(String transPwdFlag) {
        this.transPwdFlag = transPwdFlag;
    }

    public LoginInfo() {
    }

    public LoginInfo(Integer id, String userId, String userName, String systemTimestamp, String createTime, String mobile, String email, String firstLogin, String fullName, String sex, String birthday, String birthAddr, String token, String nick, String headImg, String transPwdFlag) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.systemTimestamp = systemTimestamp;
        this.createTime = createTime;
        this.mobile = mobile;
        this.email = email;
        this.firstLogin = firstLogin;
        this.fullName = fullName;
        this.sex = sex;
        this.birthday = birthday;
        this.birthAddr = birthAddr;
        this.token = token;
        this.nick = nick;
        this.headImg = headImg;
        this.transPwdFlag = transPwdFlag;
    }
}
