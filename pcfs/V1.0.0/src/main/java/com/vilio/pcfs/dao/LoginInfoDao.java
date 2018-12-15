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

}
