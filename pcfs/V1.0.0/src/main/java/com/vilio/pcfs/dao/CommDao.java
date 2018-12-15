package com.vilio.pcfs.dao;

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

    //序列增加
    public int nextval(String seqName);

    //查询序列
    public Long currval(String seqName);

}
