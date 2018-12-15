package com.vilio.plms.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/7/30.
 */
public interface SysInfoParamDao {

    //查询正在运行的定时任务。
    public List<Map> qryAccountLockList();
    //更新正在运营的定时任务
    public void updateAccountLock(Map accountLock);

    //查询扣款操作定时任务。
    public List<Map> qryCurrentDayPayScheduleJobList(String executeTime);

    //查询更新授信截止日
    public List<Map> qryCreditEndDateLockList();

    //查询逾期的还款计划明细表
    public List<Map> qryOverdueRepaymentScheduleDetailList(String executeTime);

    //查询放款信息
    public Map qryPaidInfo(String paidCode);

    //查询申请信息
    public Map qryProduct(String contractCode);

    //通过还款计划明细表code和科目查询逾期信息
    public List<Map> qryOverdueByScheduleDetailCodeAndSubject(Map paramMap);

    //查询本金逾期信息
    //public List<Map> qryInterestOverdue(String scheduleDetailCode);

    //查询本金逾期信息
    //public List<Map> qryServiceFeeOverdue(String scheduleDetailCode);

    //更新逾期信息表
    public void updateOverdue(Map overdue);

    //新增逾期信息表
    public void insertOverdue(Map overdue);

    //新增逾期明细表
    public void insertOverdueDetail(Map overdueDetail);

    //查询还款计划明细表
    public Map qryRepaymentScheduleDetail(String scheduleDetailCode);

    //查询还款计划表
    public Map qryRepaymentSchedule(String scheduleCode);

    //查询账户明细表
    public Map qryAccountDetail(String detailCode);

    //查询账户汇总表
    public Map qryAccount(String accountCode);

    //更新还款计划明细表
    public void updateRepaymentScheduleDetail(Map repaymentScheduleDetail);

    //更新放款信息表
    public void updatePaidInfo(Map paidInfo);

    //更新还款计划表
    public void updateRepaymentSchedule(Map repaymentSchedule);

    //查询账户明细表
    public void updateAccountDetail(Map accountDetail);

    //查询账户汇总表
    public void updateAccount(Map account);

    //查询逾期的定时任务信息。
    public List<HashMap> qryCurrentDayOverdueJobList();

    //查询第一天逾期的记录
    public List<HashMap> qryOverdueFirstDayRepaymentScheduleDetailList(String calculationDate);

    //通过合同号查询逾期还款计划明细信息
    public List<HashMap> qryOverdueRepaymentScheduleDetailListByContractNo(Map map);
    //通过授信截止日期查询账户明细信息
    public List<HashMap> qryAccountDetailByCreditEndDate(Date creditEndDate);
    //查找同一个账户下账户明细表里最小的授信截止时间
    public Date qryMinCreditEndDateByAccountCode(String accountCode);
    //更新账户信息表的授信总额、剩余额度和授信截止日期
    public Date updateAccountByRemainingQuotaAndCreditEndDate(Map paramMap);

    int updateSysparamInfoExecuteTime(Map paramMap);

    List<Map> querySysparamInfoByItemId(Map map);


}
