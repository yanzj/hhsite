package com.vilio.comm.pojo;

/**
 * 类名： User<br>
 * 功能：用户对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class User {

    public Integer id;
    public String userId;
    public String userName;
    public String mobile;
    public String password;
    public String email;
    public String status;
    public String firstLogin;
    public String fullName;
    public String sex;
    public String birthday;
    public String birthAddr;
    public String nick;
    public String headImg;
    public String registerChl;
    public String loginError;
    public String hashLock;
    public String createTime;
    public String lockTime;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
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

    public String getLockTime() {
        return lockTime;
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

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public User() {
    }

    public User(Integer id, String userId, String userName, String mobile, String password, String email, String status, String firstLogin, String fullName, String sex, String birthday, String birthAddr, String nick, String headImg, String registerChl, String loginError, String hashLock, String createTime, String lockTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
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
        this.lockTime = lockTime;
    }
}
