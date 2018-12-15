package com.vilio.um.pojo;

import com.vilio.comm.pojo.User;

import java.util.List;

/**
 * 类名： User<br>
 * 功能：用户对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UmUser extends User {

    private String cityCode;
    private String groupId;
    private String superUserId;
    private String cityAbbrName;
    private String cityFullName;
    private String cityLevel;
    private String post;
    private String agentId;
    public List<UmUser> childUsers;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSuperUserId() {
        return superUserId;
    }

    public void setSuperUserId(String superUserId) {
        this.superUserId = superUserId;
    }

    public String getCityAbbrName() {
        return cityAbbrName;
    }

    public void setCityAbbrName(String cityAbbrName) {
        this.cityAbbrName = cityAbbrName;
    }

    public String getCityFullName() {
        return cityFullName;
    }

    public void setCityFullName(String cityFullName) {
        this.cityFullName = cityFullName;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<UmUser> getChildUsers() {
        return childUsers;
    }

    public void setChildUsers(List<UmUser> childUsers) {
        this.childUsers = childUsers;
    }

    public UmUser() {
    }

    public UmUser(Integer id, String userId, String userName, String mobile, String password, String email, String status, String firstLogin, String fullName, String sex, String birthday, String birthAddr, String nick, String headImg, String registerChl, String loginError, String hashLock, String createTime, String lockTime, String cityCode, String groupId, String superUserId, String cityAbbrName, String cityFullName, String cityLevel, String post, String agentId, List<UmUser> childUsers) {
        super(id, userId, userName, mobile, password, email, status, firstLogin, fullName, sex, birthday, birthAddr, nick, headImg, registerChl, loginError, hashLock, createTime, lockTime);
        this.cityCode = cityCode;
        this.groupId = groupId;
        this.superUserId = superUserId;
        this.cityAbbrName = cityAbbrName;
        this.cityFullName = cityFullName;
        this.cityLevel = cityLevel;
        this.post = post;
        this.agentId = agentId;
        this.childUsers = childUsers;
    }
}
