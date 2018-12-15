package com.vilio.pcfs.dao;


import com.vilio.pcfs.pojo.LoginInfo;

import java.util.Map;

public interface LoginInfoDao {

    //查询登录信息表
    public LoginInfo selectLoginInfo(LoginInfo loginInfo);

    //更新登录信息表
    public int updateLoginInfo(LoginInfo loginInfo);

    //插入登录信息表
    public int insert(LoginInfo loginInfo);

    //清空超过阈值的登录信息
    public int deleteInvalidInfo(Map param);

    //退出登录
    public int deleteInvalidInfoById(Map param);

    public int updateUserMessageSet(Map parm);

    //退出登录
    public int updateLoginStatusInvalidById(Map param);

    //修改不是当前用户并且device_token相同的用户的登录状态为登出
    public int updateUserLoginStatusByDeviceTokenAndUserId(Map param);

}
