package com.vilio.plms.dao;

import com.vilio.plms.pojo.RepaymentApply;

import java.util.List;
import java.util.Map;

/**
 * 还款申请
 */
public interface PlmsRepaymentApplyDao {
    //查询还款记录查询接口sql
    public List<Map> queryRepaymentApplyInfo(Map map);

    public int saveRepaymentApplyInfo(RepaymentApply repaymentApply) throws Exception;


}
