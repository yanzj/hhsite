package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/9/15.
 */
public interface RepaymentDetailTempDao {
    //新增还款计划明细表记录
    public int insert(List list);
    //通过批次号、发生时间和还款操作方式查询还款计划明细信息
    public List qryScheduleDetailByBatchCodeHappenTimeAndPayMethod(Map map);
    //通过批次号、发生时间、还款计划明细编号和还款操作方式查询还款计划明细信息
    public List findRepayDtlTmpByBatchCodeHappenTimeAndPayMethod(Map map);
}
