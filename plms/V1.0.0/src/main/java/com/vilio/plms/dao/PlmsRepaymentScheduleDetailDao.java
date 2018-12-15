package com.vilio.plms.dao;


import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;
import com.vilio.plms.pojo.PlmsRepaymentScheduleDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/25.
 */
public interface PlmsRepaymentScheduleDetailDao {
    int saveRepaymentScheduleDetail(PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail) throws Exception;

    List<Map> getUnpaidDetailForContractCode(String contractCode) throws Exception;

    Map statisticsOverDueAmontAndCountForContract(Map map) throws Exception;

    //查询当前还款计划下的所有划款计划明细
    public List<PlmsRepaymentScheduleDetail> queryRepaymentScheduleDetailBatch(List<PlmsRepaymentScheduleBean> repaymentSchedules);

    //获取所有需要扣款的还款计划明细
    List<Map> queryAllNeedPayScheduleDetail(Map map) throws Exception;

    Map getPayInfoForOneScheduleDetail(String detailCode) throws Exception;

    //更新还款计划明细表中的应还金额、应还罚息、应还服务费违约金
    public int updateRepaymentScheduleDetailAmountByCode(PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail);

    int updateRepaymentScheduleDetailRepaymentAmountByCode(PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail);

    int updateFinishedScheduleDetailStatusByCodeAndStatus(Map map);

    //更新还款计划明细表中已还本金、已还利息、已还服务费、已还罚息、已还服务费违约金、状态
    public int updateRepaymentScheduleDetailToRepaymentedByCode(PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail);
    //获取所有需要扣款的合同
    List<Map> queryAllNeedPayContract(Map map) throws Exception;

    int updateRepaymentScheduleDetailStatusByPaidCode(Map map);

    List<Map> queryScheduleDetailByPaidCode(String paidCode);

    int updateSecheduleAmountByDetail(Map map);

    Map sumScheduleDetailByPaidCode(String paidCode);

    List<Map> queryOverdueRecordByStatus(Map map);

    List<PlmsRepaymentScheduleDetail> queryScheduleDetailBeanByPaidCode(String paidCode);

    PlmsRepaymentScheduleDetail queryScheduleDetailBeanByCode(String code);

    int updateRepaymentScheduleDetail(PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail);
}
