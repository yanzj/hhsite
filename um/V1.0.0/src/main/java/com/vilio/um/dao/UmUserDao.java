package com.vilio.um.dao;

import com.vilio.um.pojo.UmUser;

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
public interface UmUserDao {
    //查询用户
    public UmUser queryUser(UmUser User);

    //更改用户登录失败次数
    public int updateLoginErrorByUserId(UmUser User);

    //用户锁定或解锁
    public int updateHashLockByUserId(UmUser User);

    //用户系统查询
    public Map queryUserSystem(Map param);

    //修改密码
    public int updatePassword(Map param);

    //用户解锁
    public int updateUnLock(Map param);

    //查询用户
    public List<UmUser> queryUserList(UmUser user);

}
