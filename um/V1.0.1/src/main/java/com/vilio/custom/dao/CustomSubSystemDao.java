package com.vilio.custom.dao;

import com.vilio.custom.pojo.CustomSubSystem;

/**
 * 类名： CustomSubSystemDao<br>
 * 功能：Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CustomSubSystemDao {

    //根据系统编码查询子系统信息
    public CustomSubSystem querySubSystem(CustomSubSystem subSystem);

}
