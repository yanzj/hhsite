package com.vilio.custom.dao;

import com.vilio.custom.pojo.CustomUser;

import java.util.List;
import java.util.Map;

/**
 * 类名： UserDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CustomUserDao {

    //查询用户
    public CustomUser queryUser(CustomUser User);

    //更改用户登录失败次数
    public int updateLoginErrorByUserId(CustomUser User);

    //用户锁定或解锁
    public int updateHashLockByUserId(CustomUser User);

    //用户解锁
    public int updateUnLock(Map param);

    //修改密码
    public int updatePassword(Map param);

    //插入用户表
    public int insertUser(CustomUser user);

    //用户批量查询
    public List<CustomUser> batchQryUser(CustomUser user);

    //修改用户信息
    public int updateUserInfo(CustomUser user);

    //修改用户交易密码错误次数
    public int updateTransErrorByUserId(CustomUser user);


    //用户交易密码锁定或解锁
    public int updateTransHashLockByUserId(CustomUser User);

    //用户交易解锁
    public int updateTransUnLock(Map param);

    //修改交易密码
    public int updateTransPassword(Map param);

    //重置交易密码
    public int updateResetTransPassword(Map param);


    //修改首次登录标识
    public int updateFirstLogin(Map param);


    //忘记密码，直接修改登录密码
    public int forgetUpdatePassword(CustomUser customUser);


    //通过userId直接修改
    public int updatePasswordByUserId(CustomUser customUser);

    //校验手机号是否存在
    public int checkMobile(CustomUser customUser);

}
