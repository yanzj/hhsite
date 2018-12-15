package com.vilio.plms.dao;

import java.util.Map;

/**
 * 类名： RepaymentScheduleDao<br>
 * 功能：还款计划Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface RepaymentScheduleDao {

    //查询账户信息汇总表
    public String qryLoanCount(Map param);

}
