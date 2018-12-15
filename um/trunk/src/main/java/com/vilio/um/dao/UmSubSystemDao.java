package com.vilio.um.dao;

import com.vilio.um.pojo.UmSubSystem;

/**
 * 类名： UmSubSystemDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface UmSubSystemDao {

    //根据系统编码查询子系统信息
    public UmSubSystem querySubSystem(UmSubSystem subSystem);



}
