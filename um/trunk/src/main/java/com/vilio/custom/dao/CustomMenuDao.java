package com.vilio.custom.dao;

import java.util.List;
import java.util.Map;

/**
 * 类名： CustomMenuDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CustomMenuDao {

    //根据角色查询出所有的菜单信息
    public List<Map<String,Object>> queryMenuByRoles(Map param);

}
