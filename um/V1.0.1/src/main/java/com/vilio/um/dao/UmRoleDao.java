package com.vilio.um.dao;

import com.vilio.um.pojo.UmRole;

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
public interface UmRoleDao {

    //查询用户所在系统的角色
    public List<UmRole> queryRoleByUserAndSystem(Map param);

    //根据条件查询角色信息
    public List<UmRole> queryRole(Map param);


}
