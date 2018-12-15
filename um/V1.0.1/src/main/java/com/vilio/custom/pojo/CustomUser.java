package com.vilio.custom.pojo;

import com.vilio.comm.pojo.User;

/**
 * 类名： User<br>
 * 功能：用户对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class CustomUser extends User{

    private String systemId;
    private String transPassword;
    private String transHashLock;
    private String transError;
    private String transLockTime;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getTransPassword() {
        return transPassword;
    }

    public void setTransPassword(String transPassword) {
        this.transPassword = transPassword;
    }

    public String getTransHashLock() {
        return transHashLock;
    }

    public void setTransHashLock(String transHashLock) {
        this.transHashLock = transHashLock;
    }

    public String getTransError() {
        return transError;
    }

    public void setTransError(String transError) {
        this.transError = transError;
    }

    public String getTransLockTime() {
        return transLockTime;
    }

    public void setTransLockTime(String transLockTime) {
        this.transLockTime = transLockTime;
    }

    public CustomUser() {
    }

    public CustomUser(Integer id, String userId, String userName, String mobile, String password, String email, String status, String firstLogin, String fullName, String sex, String birthday, String birthAddr, String nick, String headImg, String registerChl, String loginError, String hashLock, String createTime, String lockTime, String systemId, String transPassword, String transHashLock, String transError, String transLockTime) {
        super(id, userId, userName, mobile, password, email, status, firstLogin, fullName, sex, birthday, birthAddr, nick, headImg, registerChl, loginError, hashLock, createTime, lockTime);
        this.systemId = systemId;
        this.transPassword = transPassword;
        this.transHashLock = transHashLock;
        this.transError = transError;
        this.transLockTime = transLockTime;
    }
}
