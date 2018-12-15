package com.vilio.mps.common.dao;

import java.sql.Date;

/**
 * Created by xiezhilei on 2017/1/15.
 */
public interface CommonDao {

    public String getUUID();

    public Date getCurrentDateTime();

    //查询序列
    public Long querySequence(String seqName);

    //序列增加
    public int nextval(String seqName);

    //查询序列
    public Long currval(String seqName);

}
