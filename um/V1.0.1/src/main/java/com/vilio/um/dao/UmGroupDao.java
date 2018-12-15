package com.vilio.um.dao;

import com.vilio.um.pojo.UmGroup;

import java.util.List;

/**
 * 类名： UmGroupDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface UmGroupDao {
    //查询机构信息
    public UmGroup queryGroup(UmGroup group);

    public List<UmGroup> queryGroupList();

}
