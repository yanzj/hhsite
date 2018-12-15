package com.vilio.plms.dao;

import java.util.Map;

/**
 * 类名： RepaymentDao<br>
 * 功能：固定日还款日Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月23日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface RepaymentDao {

    //查询最高期数
    public int qryBorrowPeriod(Map param);

}
