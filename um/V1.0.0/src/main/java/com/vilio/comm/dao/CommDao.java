package com.vilio.comm.dao;

import com.vilio.comm.pojo.City;

/**
 * 类名： CommDao<br>
 * 功能：公共Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CommDao {

    //查询序列
    public Long querySequence(String seqName);

    //查询城市
    public City queryCity(String code);


}
