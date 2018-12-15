package com.vilio.mps.common.dao;

import com.vilio.mps.common.pojo.App;

/**
 * 类名： AppDao<br>
 * 功能：应用Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月10日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface AppDao {

    //根据code查询应用信息
    public App queryAppInfoByCode(String appCode);



}
