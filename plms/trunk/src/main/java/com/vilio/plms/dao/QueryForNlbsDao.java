package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * @Description:贷后系统查询(NBLS专用)
 * @param:
 * @return:
 * @Author: liuzhu.feng
 * @Date: 2017/9/8/0008
 */
public interface QueryForNlbsDao {


    //贷款业务列表查询
    public List qryLoanList(Map map);

    //还款计划列表查询
    public List queryRepaymentScheduleList(Map map);

    public List queryOverDueRepaymentScheduleList(Map paramMap);
}
