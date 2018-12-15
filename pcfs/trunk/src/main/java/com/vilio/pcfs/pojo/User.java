package com.vilio.pcfs.pojo;

/**
 * Created by dell on 2017/6/14.
 */
public class User {

    private Integer id;
    private String userId;
    private String userName;
    private String cityCoe;
    private String mobile;
    private String password;
    private String email;
    private String status;
    private String firstLogin;
    private String fullName;
    private String sex;
    private String birthday;
    private String birthAddr;
    private String nick;
    private String headImg;
    private String registerChl;
    private String loginError;
    private String hashLock;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getCityCoe() {
        return cityCoe;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthAddr() {
        return birthAddr;
    }

    public String getNick() {
        return nick;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getRegisterChl() {
        return registerChl;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getHashLock() {
        return hashLock;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCityCoe(String cityCoe) {
        this.cityCoe = cityCoe;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthAddr(String birthAddr) {
        this.birthAddr = birthAddr;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setRegisterChl(String registerChl) {
        this.registerChl = registerChl;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public void setHashLock(String hashLock) {
        this.hashLock = hashLock;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public User() {
    }

    public User(Integer id, String userId, String userName, String cityCoe,
                String mobile, String password, String email, String status,
                String firstLogin, String fullName, String sex, String birthday,
                String birthAddr, String nick, String headImg, String registerChl,
                String loginError, String hashLock, String createTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.cityCoe = cityCoe;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.status = status;
        this.firstLogin = firstLogin;
        this.fullName = fullName;
        this.sex = sex;
        this.birthday = birthday;
        this.birthAddr = birthAddr;
        this.nick = nick;
        this.headImg = headImg;
        this.registerChl = registerChl;
        this.loginError = loginError;
        this.hashLock = hashLock;
        this.createTime = createTime;
    }
}
