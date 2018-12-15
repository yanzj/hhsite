package com.vilio.pcfs.dao;


import com.vilio.pcfs.pojo.User;


public interface UserDao {
    //查询用户
    public User queryUser(User User);

    //更改用户登录失败次数
    public int updateLoginErrorByUserName(User User);

    //更改用户锁定状态
    public int updateHashLockByPrimaryKey(User User);

}
