package com.vilio.custom.dao;

import com.vilio.custom.pojo.CustomRole;

import java.util.List;
import java.util.Map;

/**
 * 类名： UmRoleDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CustomRoleDao {

    //查询用户所在系统的角色
    public List<CustomRole> queryRoleByUserAndSystem(Map param);

}
