package com.vilio.plms.dao;

import com.vilio.plms.pojo.OperatorRecord;

/**
 * 类名： OperatorRecordDao<br>
 * 功能：操作历史表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface OperatorRecordDao {

    //插入历史操作表
    public int insertOperatorRecord(OperatorRecord operatorRecord);

}
