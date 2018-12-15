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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public CustomUser() {
    }

    public CustomUser(Integer id, String userId, String userName, String mobile, String password, String email, String status, String firstLogin, String fullName, String sex, String birthday, String birthAddr, String nick, String headImg, String registerChl, String loginError, String hashLock, String createTime, String lockTime, String systemId) {
        super(id, userId, userName, mobile, password, email, status, firstLogin, fullName, sex, birthday, birthAddr, nick, headImg, registerChl, loginError, hashLock, createTime, lockTime);
        this.systemId = systemId;
    }
}
