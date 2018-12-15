package com.vilio.plms.service.base;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 类名： ReceiptsService<br>
 * 功能：回滚本金和逾期<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月1日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Service
public class RollBackPaymentAndOverdueService {

    private static final Logger logger = Logger.getLogger(RollBackPaymentAndOverdueService.class);

    @Resource
    private PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;

    @Resource
    private PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;

    @Resource
    private OverdueDao overdueDao;

    @Resource
    private OverdueDetailDao overdueDetailDao;

    @Resource
    private PaidInfoDao paidInfoDao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private PlmsRepaymentDetailDao plmsRepaymentDetailDao;

    @Resource
    private ApplyInterestingDao applyInterestingDao;

    @Resource
    private ReceiptsRecordDao receiptsRecordDao;

    @Resource
    private RepaymentDetailTempDao repaymentDetailTempDao;

    @Resource
    private CommonService commonService;


    /**
     * 回滚本金和逾期
     *
     * @param rollBachDate yyyy-MM-DD 回滚到哪一天
     * @param contractCode
     */
    public void mainJob(String rollBachDate, String contractCode, String batchCode) throws ErrorException, ParseException {
        if (rollBachDate.length() != 10) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "日期格式不正确");
        }
        // 需求变更：找到关联到当前借款合同关联的账户明细表的所有还款计划表记录   wangxf by 20170823
        //借款合同关联的账户明细表关联的还款计划表记录找到符合条件的还款计划信息（1.	应还日期大于等于资金到账日期小于当前日期。）
        //根据合同查询还款计划明细表
        Map queryRepaymentScheduleParam = new HashMap();
        queryRepaymentScheduleParam.put("contractCode", contractCode);
        //queryRepaymentScheduleParam.put("rollBachDate", rollBachDate);
        //queryRepaymentScheduleParam.put("nowDate", DateUtil.getCurrentDateTime());
        List<PlmsRepaymentScheduleBean> repaymentSchedules = plmsRepaymentScheduleDao.queryRepaymentScheduleForContractCodeAndReceiptsDate(queryRepaymentScheduleParam);
        if (repaymentSchedules == null || repaymentSchedules.size() <= 0) {
            logger.info("借款合同关联的账户明细表关联的还款计划表记录找到符合条件的还款计划信息（1.应还日期大于等于资金到账日期小于当前日期。），未找到需要回滚的数据");
            return;
        }
        //查询当前还款计划下的所有划款计划明细
        List<PlmsRepaymentScheduleDetail> repaymentScheduleDetails = plmsRepaymentScheduleDetailDao.queryRepaymentScheduleDetailBatch(repaymentSchedules);
        if (repaymentScheduleDetails == null || repaymentScheduleDetails.size() <= 0) {
            //查询还款计划明细为空
            logger.info("还款计划有数据，还款计划明细没有数据，资金入账失败，请检查");
            //如果有还款计划数据，但是没有还款计划明细数据，肯定是生成划款计划信息有问题，这里报错，资金入账更改为失败
            throw new ErrorException(ReturnCode.REPAYMENT_SCHEDULE_DETAIL_FAIL, "");
        }
        //针对每一条划款计划明细
        for (PlmsRepaymentScheduleDetail repaymentScheduleDetail : repaymentScheduleDetails) {
            //回滚罚息
            Map overdueTotal = rollBackOverdue(repaymentScheduleDetail, rollBachDate);
            if (overdueTotal != null) {
                //更新还款计划明细表中的应还金额、应还罚息、应还服务费违约金
                updateRepaymentScheduleDetail(repaymentScheduleDetail, overdueTotal);
                //更新还款计划明细表记录关联的放款信息表记录
                updatePaidInfo(repaymentScheduleDetail, overdueTotal);
                //更新当前还款计划明细记录关联的还款计划表记录
                PlmsRepaymentScheduleBean repaymentScheduleBean = updateRepaymentSchedule(repaymentScheduleDetail, overdueTotal);
                //更新账户明细表
                AccountDetail accountDetail = updateAccountDetail(repaymentScheduleBean, overdueTotal);
                //更新账户汇总表
                updateAccount(accountDetail, overdueTotal);
            }
            //回滚扣款
            Map paymentTotal = rollBackPayment(repaymentScheduleDetail, rollBachDate, batchCode);
            if (paymentTotal != null) {
                //更新当前还款计划明细表记录
                updateRepaymentScheduleDetailByRollBackPayment(repaymentScheduleDetail, paymentTotal, rollBachDate);
                //更新放款信息表记录
                updatePaidInfoByRollBackPayment(repaymentScheduleDetail, paymentTotal);
                //更新还款计划表记录
                PlmsRepaymentScheduleBean repaymentScheduleBean = updateRepaymentScheduleByRollBackPayment(repaymentScheduleDetail, paymentTotal, rollBachDate);
                //更新账户明细表记录
                AccountDetail accountDetail = updateAccountDetailByRollBackPayment(repaymentScheduleBean, paymentTotal, contractCode);
                //更新账户汇总表记录
                updateAccountByRollBackPayment(accountDetail, paymentTotal);
            }
        }
        //回滚完还款计划明细后，所描全部的放款信息，每条更改状态
        updatePaidStatusByRepaymentScheduleDetail(contractCode);
        //回滚资金入账
        rollBackReceipts(rollBachDate, contractCode);


    }

    /**
     * 回滚资金入账
     *
     * @param rollBachDate
     * @param contractCode
     */
    private void rollBackReceipts(String rollBachDate, String contractCode) throws ErrorException {
        //1.	入账日期大于此次拟入账资金的入账日期，且小于等于当前日期；
        Map dateMap = new HashMap();
        dateMap.put("rollBachDate", rollBachDate);
        dateMap.put("nowDate", DateUtil.getCurrentDate());
        dateMap.put("dealStatus", GlobDict.receipts_deal_stauts_succ.getKey());
        dateMap.put("contractCode", contractCode);
        List<ReceiptsRecord> receiptsRecords = receiptsRecordDao.queryReceiptsRecordByReceiptsDate(dateMap);
        if (receiptsRecords == null || receiptsRecords.size() == 0) {
            //查询入账日期大于此次拟入账资金的入账日期，且小于等于当前日期；正常现象，直接返回
            logger.info("没有查询到需要回滚的资金入账流水");
            return;
        }
        String fundSideBalance = "0";
        String honghuoBalance = "0";
        String honghuoBailAccountBalance = "0";
        for (ReceiptsRecord receiptsRecord : receiptsRecords) {
            if (GlobDict.receipts_record_account_type_honghuo.getKey().equals(receiptsRecord.getAccountType())) {
                //宏获还款账户
                honghuoBalance = MathUtil.strAdd(honghuoBalance, receiptsRecord.getReceiptsAmount(), 2);
            } else if (GlobDict.receipts_record_account_type_fund_side.getKey().equals(receiptsRecord.getAccountType())) {
                //资方还款账户
                fundSideBalance = MathUtil.strAdd(fundSideBalance, receiptsRecord.getReceiptsAmount(), 2);
            } else if (GlobDict.receipts_record_account_type_honghuo_bail.getKey().equals(receiptsRecord.getAccountType())) {
                //宏获保证金账户
                honghuoBailAccountBalance = MathUtil.strAdd(honghuoBailAccountBalance, receiptsRecord.getReceiptsAmount(), 2);
            } else {
                throw new ErrorException(ReturnCode.ACCOUNT_TYPE_FAIL, "");
            }
        }
        //根据合同查询当前账户明细信息
        AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(contractCode);
        if (accountDetail == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
        accountDetail.setHonghuoBalance(MathUtil.strSub(accountDetail.getHonghuoBalance(), honghuoBalance, 2));
        accountDetail.setFundSideBalance(MathUtil.strSub(accountDetail.getFundSideBalance(), fundSideBalance, 2));
        accountDetail.setHonghuoBailAccountBalance(MathUtil.strSub(accountDetail.getHonghuoBailAccountBalance(), honghuoBailAccountBalance, 2));
        //修改账户余额
        int ret = accountDetailDao.updateAccountDetailBalanceByCode(accountDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 回滚完还款计划明细后，所描全部的放款信息，每条更改状态
     *
     * @param contractCode
     */
    private void updatePaidStatusByRepaymentScheduleDetail(String contractCode) throws ErrorException {
        List<PaidInfo> paidInfos = paidInfoDao.queryPaidInfoByContractCode(contractCode);
        for (PaidInfo paidInfo : paidInfos) {
            /*If应还本金>=已还本金 || 应还利息>=已还利息 || 应还服务费>=已还服务费 || 应还保证金>=已还保证金，则：
            If 关联到当前放款信息表记录的所有还款计划明细表记录中有状态为“逾期”的，则为“逾期”；
            Else 为“正常还款中”*/
            if (MathUtil.strcompareTo(paidInfo.getPaidAmount(), paidInfo.getRepaymentedPrincipal())
                    || MathUtil.strcompareTo(paidInfo.getInterest(), paidInfo.getRepaymentedInterest())
                    || MathUtil.strcompareTo(paidInfo.getServiceFee(), paidInfo.getRepaymentedServiceFee())
                    || MathUtil.strcompareTo(paidInfo.getBail(), paidInfo.getRepaymentedBail())) {
                //查询当前放款信息下的所有还款计划明细
                List<PlmsRepaymentScheduleDetail> plmsRepaymentScheduleDetails = plmsRepaymentScheduleDetailDao.queryScheduleDetailBeanByPaidCode(paidInfo.getCode());
                //循环查看是否有逾期的还款计划明细
                boolean flag = false;
                for (PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail : plmsRepaymentScheduleDetails) {
                    if (GlobDict.repayment_schedule_detail_status_overdue.getKey().equals(plmsRepaymentScheduleDetail.getStatus())) {
                        //如果有逾期中状态的还款明细
                        flag = true;
                    }
                }
                if (flag) {
                    //如果有逾期中状态的还款计划明细，则修改当前放款信息表状态为逾期中
                    paidInfo.setStatus(GlobDict.paid_info_status_overdue.getKey());
                } else {
                    //如果还款计划明细中没有逾期状态的，则修改当前放款信息表状态为正常还款中
                    paidInfo.setStatus(GlobDict.paid_info_status_paying_and_not_overdue.getKey());
                }
                //根据code修改放款信息表状态
                int ret = paidInfoDao.updatePaidInfoToRepaymentedByCode(paidInfo);
                if (ret < 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
            }
        }
    }

    /**
     * 更新账户汇总表记录（回滚扣款）
     *
     * @param accountDetail
     * @param paymentTotal
     */
    private void updateAccountByRollBackPayment(AccountDetail accountDetail, Map paymentTotal) throws ErrorException {
        Account accountParam = new Account();
        accountParam.setCode(accountDetail.getAccountCode());
        //查询账户信息表
        Account account = accountDao.qryAccount(accountParam);
        if (account == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_INFO_FAIL, "");
        }
        //已贷本金
        String principal = MathUtil.strAdd
                (
                        ObjectUtils.toString(account.getPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //剩余额度
        String remainingQuota = MathUtil.strcompareTo
                (
                        MathUtil.strSub
                                (
                                        account.getTotalQuota(),
                                        principal, 2
                                ),
                        "0"
                ) ?
                MathUtil.strSub
                        (
                                account.getTotalQuota(),
                                principal,
                                2
                        ) : "0";
        //已还本金
        String repaymentedPrincipal = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //已还利息
        String repaymentedInterest = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedInterest()),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //已还服务费
        String repaymentedServiceFee = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedServiceFee()),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //已还罚息
        String repaymentedOverdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(account.getRepaymentedOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //已还服务费违约金
        String repaymentedServiceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //应还利息
        String interest = MathUtil.strAdd
                (
                        account.getInterest(),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //应还服务费
        String serviceFee = MathUtil.strAdd
                (
                        account.getServiceFee(),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //应还罚息
        String overdue = MathUtil.strAdd
                (
                        MathUtil.strAdd
                                (
                                        ObjectUtils.toString(account.getOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strAdd
                (
                        ObjectUtils.toString(account.getServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //需求变更：增加保证金       wangxf by 20170823
        //已还保证金
        String repaymentedBail = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //已还保证金违约金
        String repaymentedBailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getRepaymentedBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );
        //应还保证金
        String bail = MathUtil.strAdd
                (
                        ObjectUtils.toString(account.getBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //应还保证金违约金
        String bailPenalty = MathUtil.strAdd
                (
                        ObjectUtils.toString(account.getBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );

        account.setPrincipal(principal);
        account.setRemainingQuota(remainingQuota);
        account.setRepaymentedPrincipal(repaymentedPrincipal);
        account.setRepaymentedInterest(repaymentedInterest);
        account.setRepaymentedServiceFee(repaymentedServiceFee);
        account.setRepaymentedOverdue(repaymentedOverdue);
        account.setRepaymentedServiceFeePenalty(repaymentedServiceFeePenalty);
        account.setInterest(interest);
        account.setServiceFee(serviceFee);
        account.setOverdue(overdue);
        account.setServiceFeePenalty(serviceFeePenalty);
        account.setRepaymentedBail(repaymentedBail);
        account.setRepaymentedBailPenalty(repaymentedBailPenalty);
        account.setBail(bail);
        account.setBailPenalty(bailPenalty);
        //更新账户明细表记录
        int ret = accountDao.updateAccountRollBackAmountByCode(account);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 更新账户明细表记录（回滚扣款）
     *
     * @param repaymentScheduleBean
     * @param paymentTotal
     * @param contractCode
     * @return
     */
    private AccountDetail updateAccountDetailByRollBackPayment(PlmsRepaymentScheduleBean repaymentScheduleBean, Map paymentTotal, String contractCode) throws ErrorException {
        //查询账户明细表
        AccountDetail accountDetailParam = new AccountDetail();
        accountDetailParam.setCode(repaymentScheduleBean.getDetailCode());
        AccountDetail accountDetail = accountDetailDao.queryAccountDetailByCode(accountDetailParam);
        if (accountDetail == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
        //已贷本金
        String principal = MathUtil.strAdd
                (
                        ObjectUtils.toString(accountDetail.getPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //剩余额度
        String remainingQuota = MathUtil.strcompareTo
                (
                        MathUtil.strSub
                                (
                                        accountDetail.getTotalQuota(),
                                        principal, 2
                                ),
                        "0"
                ) ?
                MathUtil.strSub
                        (
                                accountDetail.getTotalQuota(),
                                principal,
                                2
                        ) : "0";
        //已还本金
        String repaymentedPrincipal = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //已还利息
        String repaymentedInterest = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedInterest()),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //已还服务费
        String repaymentedServiceFee = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedServiceFee()),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //已还罚息
        String repaymentedOverdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(accountDetail.getRepaymentedOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //已还服务费违约金
        String repaymentedServiceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //应还利息
        String interest = MathUtil.strAdd
                (
                        accountDetail.getInterest(),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //应还服务费
        String serviceFee = MathUtil.strAdd
                (
                        accountDetail.getServiceFee(),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //应还罚息
        String overdue = MathUtil.strAdd
                (
                        MathUtil.strAdd
                                (
                                        ObjectUtils.toString(accountDetail.getOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strAdd
                (
                        ObjectUtils.toString(accountDetail.getServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //需求变更：增加保证金       wangxf by 20170823
        //已还保证金
        String repaymentedBail = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //已还保证金违约金
        String repaymentedBailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getRepaymentedBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );
        //应还保证金
        String bail = MathUtil.strAdd
                (
                        ObjectUtils.toString(accountDetail.getBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //应还保证金违约金
        String bailPenalty = MathUtil.strAdd
                (
                        ObjectUtils.toString(accountDetail.getBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );

        accountDetail.setPrincipal(principal);
        accountDetail.setRemainingQuota(remainingQuota);
        accountDetail.setRepaymentedPrincipal(repaymentedPrincipal);
        accountDetail.setRepaymentedInterest(repaymentedInterest);
        accountDetail.setRepaymentedServiceFee(repaymentedServiceFee);
        accountDetail.setRepaymentedOverdue(repaymentedOverdue);
        accountDetail.setRepaymentedServiceFeePenalty(repaymentedServiceFeePenalty);
        accountDetail.setInterest(interest);
        accountDetail.setServiceFee(serviceFee);
        accountDetail.setOverdue(overdue);
        accountDetail.setServiceFeePenalty(serviceFeePenalty);
        accountDetail.setRepaymentedBail(repaymentedBail);
        accountDetail.setRepaymentedBailPenalty(repaymentedBailPenalty);
        accountDetail.setBail(bail);
        accountDetail.setBailPenalty(bailPenalty);
        //查询利息是否代收付
        //根据合同编码查询进件利息表
        ApplyInteresting applyInterestingParam = new ApplyInteresting();
        applyInterestingParam.setContractCode(contractCode);
        ApplyInteresting applyInteresting = applyInterestingDao.qryApplyInteresting(applyInterestingParam);
        if (applyInteresting == null) {
            //进件利息信息表不存在
            throw new ErrorException(ReturnCode.APPLY_INTERESTING_FAIL, "");
        }
        String honghuoBalance = "0";
        String fundSideBalance = "0";
        if (GlobDict.instead.getKey().equals(applyInteresting.getIsInterestInstead())) {
            //代收付
            //宏获账户余额 :原金额 + 以上还款明细表记录中所有科目不是“本金”的记录的金额之和“保证金”
            honghuoBalance = MathUtil.strAdd
                    (
                            MathUtil.strAdd
                                    (
                                            MathUtil.strAdd
                                                    (
                                                            MathUtil.strAdd
                                                                    (
                                                                            MathUtil.strAdd
                                                                                    (
                                                                                            MathUtil.strAdd
                                                                                                    (
                                                                                                            accountDetail.getHonghuoBalance(),
                                                                                                            paymentTotal.get("interest").toString(),
                                                                                                            2
                                                                                                    ),
                                                                                            paymentTotal.get("serviceCharge").toString(),
                                                                                            2
                                                                                    ),
                                                                            paymentTotal.get("principalOverdue").toString(),
                                                                            2
                                                                    ), paymentTotal.get("interestOverdue").toString(),
                                                            2
                                                    ),
                                            paymentTotal.get("serviceChargeOverdue").toString(),
                                            2
                                    ),
                            paymentTotal.get("bailPenalty").toString(),
                            2
                    );
            //资方账户余额:原金额 + 以上还款明细表记录中所有科目为“本金”的记录的金额之和
            fundSideBalance = MathUtil.strAdd
                    (
                            accountDetail.getFundSideBalance(),
                            paymentTotal.get("principal").toString(),
                            2
                    );
        } else if (GlobDict.un_Instead.getKey().equals(applyInteresting.getIsInterestInstead())) {
            //非代收付
            //宏获账户余额 :原金额 + 以上还款明细表记录中所有科目不是“本金”和“利息”和“保证金”的记录的金额之和
            honghuoBalance = MathUtil.strAdd
                    (
                            MathUtil.strAdd
                                    (
                                            MathUtil.strAdd
                                                    (
                                                            MathUtil.strAdd
                                                                    (
                                                                            MathUtil.strAdd
                                                                                    (
                                                                                            accountDetail.getHonghuoBalance(),
                                                                                            paymentTotal.get("serviceCharge").toString(),
                                                                                            2
                                                                                    ),
                                                                            paymentTotal.get("principalOverdue").toString(),
                                                                            2
                                                                    ), paymentTotal.get("interestOverdue").toString(),
                                                            2
                                                    ),
                                            paymentTotal.get("serviceChargeOverdue").toString(),
                                            2
                                    ),
                            paymentTotal.get("serviceChargeOverdue").toString(),
                            2
                    );
            //资方账户余额:=原金额 + 以上还款明细表记录中所有科目为“本金”和“利息”的记录的金额之和
            fundSideBalance = MathUtil.strAdd
                    (
                            MathUtil.strAdd
                                    (
                                            accountDetail.getFundSideBalance(),
                                            paymentTotal.get("principal").toString(),
                                            2
                                    ),
                            paymentTotal.get("interest").toString(),
                            2
                    );
        } else {
            //报错
            throw new ErrorException(ReturnCode.ACCOUNT_TYPE_FAIL, "");
        }
        //宏获保证金账户余额
        String honghuoBailAccountBalance = "0";
        honghuoBailAccountBalance = MathUtil.strAdd(accountDetail.getHonghuoBailAccountBalance(), paymentTotal.get("bail").toString(), 2);
        accountDetail.setHonghuoBalance(honghuoBalance);
        accountDetail.setFundSideBalance(fundSideBalance);
        accountDetail.setHonghuoBailAccountBalance(honghuoBailAccountBalance);
        //更新账户明细表记录
        int ret = accountDetailDao.updateAccountDetailRollBackAmountByCode(accountDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        return accountDetail;
    }


    /**
     * 更新还款计划表记录（回滚扣款）
     *
     * @param repaymentScheduleDetail
     * @param paymentTotal
     * @param rollBachDate
     */
    private PlmsRepaymentScheduleBean updateRepaymentScheduleByRollBackPayment(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map paymentTotal, String rollBachDate) throws ErrorException, ParseException {
        //查询还款计划信息
        PlmsRepaymentScheduleBean repaymentScheduleBeanParam = new PlmsRepaymentScheduleBean();
        repaymentScheduleBeanParam.setCode(repaymentScheduleDetail.getScheduleCode());
        PlmsRepaymentScheduleBean repaymentScheduleBean = plmsRepaymentScheduleDao.queryRepaymentScheduleByCode(repaymentScheduleBeanParam);
        if (repaymentScheduleBean == null) {
            throw new ErrorException(ReturnCode.REPAYMENT_SCHEDULE_FAIL, "");
        }
        //已还本金
        String repaymentedPrincipal = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //已还利息
        String repaymentedInterest = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedInterest()),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //已还服务费
        String repaymentedServiceFee = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedServiceFee()),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //已还罚息
        String repaymentedOverdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //已还服务费违约金
        String repaymentedServiceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //需求变更：增加保证金       wangxf by 20170823
        //已还保证金
        String repaymentedBail = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //已还保证金违约金
        String repaymentedBailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getRepaymentedBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );
        //状态修改需求变更
        /*
        If 应还日期>=资金到账日期，则为“正常还款中”；
        Elseif 应还本金>=已还本金 || 应还利息>=已还利息 || 应还服务费>=已还服务费 || 应还保证金>=已还保证金，则为“逾期”
        Else 则为“已结清”
        */
        if (DateUtil.dateCompare(DateUtil.convert(repaymentScheduleBean.getRepaymentDate(), "yyyy-MM-dd"), rollBachDate, "yyyy-MM-dd") <= 0) {
            //正常还款中
            repaymentScheduleBean.setStatus(GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
        } else if (MathUtil.strcompareTo(repaymentScheduleBean.getPrincipal().toString(), repaymentedPrincipal)
                || MathUtil.strcompareTo(repaymentScheduleBean.getInterest().toString(), repaymentedInterest)
                || MathUtil.strcompareTo(repaymentScheduleBean.getServiceFee().toString(), repaymentedServiceFee)
                || MathUtil.strcompareTo(repaymentScheduleBean.getBail().toString(), repaymentedBail)) {
            //逾期
            repaymentScheduleBean.setStatus(GlobDict.repayment_schedule_status_overdue.getKey());
        } else {
            //已结清
            repaymentScheduleBean.setStatus(GlobDict.repayment_schedule_status_paid.getKey());
        }

        repaymentScheduleBean.setRepaymentedPrincipal(new BigDecimal(repaymentedPrincipal));
        repaymentScheduleBean.setRepaymentedInterest(new BigDecimal(repaymentedInterest));
        repaymentScheduleBean.setRepaymentedServiceFee(new BigDecimal(repaymentedServiceFee));
        repaymentScheduleBean.setRepaymentedOverdue(new BigDecimal(repaymentedOverdue));
        repaymentScheduleBean.setRepaymentedServiceFeePenalty(new BigDecimal(repaymentedServiceFeePenalty));
        repaymentScheduleBean.setRepaymentedBail(new BigDecimal(repaymentedBail));
        repaymentScheduleBean.setRepaymentedBailPenalty(new BigDecimal(repaymentedBailPenalty));
        int ret = plmsRepaymentScheduleDao.updateRepaymentScheduleToRepaymentedByCode(repaymentScheduleBean);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        return repaymentScheduleBean;
    }

    /**
     * 更新放款信息表记录（回滚扣款）
     *
     * @param repaymentScheduleDetail
     * @param paymentTotal
     */
    private void updatePaidInfoByRollBackPayment(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map paymentTotal) throws ErrorException {
        //查询放款信息表
        PaidInfo paidInfoParam = new PaidInfo();
        paidInfoParam.setCode(repaymentScheduleDetail.getPaidCode());
        PaidInfo paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfoParam);
        if (paidInfo == null) {
            throw new ErrorException(ReturnCode.PAID_INFO_FAIL, "");
        }
        //已还本金
        String repaymentedPrincipal = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //已还利息
        String repaymentedInterest = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedInterest()),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //已还服务费
        String repaymentedServiceFee = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedServiceFee()),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //已还罚息
        String repaymentedOverdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(paidInfo.getRepaymentedOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //已还服务费违约金
        String repaymentedServiceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //需求变更：增加保证金       wangxf by 20170823
        //已还保证金
        String repaymentedBail = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //已还保证金违约金
        String repaymentedBailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getRepaymentedBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );
        paidInfo.setRepaymentedPrincipal(repaymentedPrincipal);
        paidInfo.setRepaymentedInterest(repaymentedInterest);
        paidInfo.setRepaymentedServiceFee(repaymentedServiceFee);
        paidInfo.setRepaymentedOverdue(repaymentedOverdue);
        paidInfo.setRepaymentedServiceFeePenalty(repaymentedServiceFeePenalty);
        paidInfo.setRepaymentedBail(repaymentedBail);
        paidInfo.setRepaymentedBailPenalty(repaymentedBailPenalty);
        int ret = paidInfoDao.updatePaidInfoToRepaymentedByCode(paidInfo);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 更新当前还款计划明细表记录（回滚扣款）
     *
     * @param repaymentScheduleDetail
     * @param paymentTotal
     * @param rollBachDate
     */
    private void updateRepaymentScheduleDetailByRollBackPayment(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map paymentTotal, String rollBachDate) throws ErrorException, ParseException {
        //更新当前还款计划明细表记录
        //已还本金
        String repaymentedPrincipal = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedPrincipal()),
                        paymentTotal.get("principal").toString(),
                        2
                );
        //已还利息
        String repaymentedInterest = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedInterest()),
                        paymentTotal.get("interest").toString(),
                        2
                );
        //已还服务费
        String repaymentedServiceFee = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedServiceFee()),
                        paymentTotal.get("serviceCharge").toString(),
                        2
                );
        //已还罚息
        String repaymentedOverdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedOverdue()),
                                        paymentTotal.get("principalOverdue").toString(),
                                        2
                                ),
                        paymentTotal.get("interestOverdue").toString(),
                        2
                );
        //已还服务费违约金
        String repaymentedServiceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedServiceFeePenalty()),
                        paymentTotal.get("serviceChargeOverdue").toString(),
                        2
                );
        //需求变更：增加保证金       wangxf by 20170823
        //已还保证金
        String repaymentedBail = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedBail()),
                        paymentTotal.get("bail").toString(),
                        2
                );
        //已还保证金违约金
        String repaymentedBailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getRepaymentedBailPenalty()),
                        paymentTotal.get("bailPenalty").toString(),
                        2
                );

        //状态修改需求变更
        /*
        If 应还日期>=资金到账日期，则为“正常还款中”；
        Elseif 应还本金>=已还本金 || 应还利息>=已还利息 || 应还服务费>=已还服务费 || 应还保证金>=已还保证金，则为“逾期”
        Else 则为“已结清”
        */
        if (DateUtil.dateCompare(DateUtil.convert(repaymentScheduleDetail.getRepaymentDate(), "yyyy-MM-dd"), rollBachDate, "yyyy-MM-dd") <= 0) {
            //正常还款中
            repaymentScheduleDetail.setStatus(GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getKey());
        } else if (MathUtil.strcompareTo(repaymentScheduleDetail.getPrincipal().toString(), repaymentedPrincipal)
                || MathUtil.strcompareTo(repaymentScheduleDetail.getInterest().toString(), repaymentedInterest)
                || MathUtil.strcompareTo(repaymentScheduleDetail.getServiceFee().toString(), repaymentedServiceFee)
                || MathUtil.strcompareTo(repaymentScheduleDetail.getBail().toString(), repaymentedBail)) {
            //逾期
            repaymentScheduleDetail.setStatus(GlobDict.repayment_schedule_detail_status_overdue.getKey());
        } else {
            //已结清
            repaymentScheduleDetail.setStatus(GlobDict.repayment_schedule_detail_status_paid.getKey());
        }
        //更新还款计划明细表
        repaymentScheduleDetail.setRepaymentedPrincipal(new BigDecimal(repaymentedPrincipal));
        repaymentScheduleDetail.setRepaymentedInterest(new BigDecimal(repaymentedInterest));
        repaymentScheduleDetail.setRepaymentedServiceFee(new BigDecimal(repaymentedServiceFee));
        repaymentScheduleDetail.setRepaymentedOverdue(new BigDecimal(repaymentedOverdue));
        repaymentScheduleDetail.setRepaymentedServiceFeePenalty(new BigDecimal(repaymentedServiceFeePenalty));
        repaymentScheduleDetail.setRepaymentedBail(new BigDecimal(repaymentedBail));
        repaymentScheduleDetail.setRepaymentedBailPenalty(new BigDecimal(repaymentedBailPenalty));
        int ret = plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetailToRepaymentedByCode(repaymentScheduleDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 回滚扣款
     *
     * @param repaymentScheduleDetail
     * @param rollBachDate
     */
    private Map rollBackPayment(PlmsRepaymentScheduleDetail repaymentScheduleDetail, String rollBachDate, String batchCode) throws ErrorException {
        //查询还款明细记录
        Map param = new HashMap();
        param.put("scheduleDetailCode", repaymentScheduleDetail.getCode());
        param.put("rollBachDate", rollBachDate);
        param.put("nowDate", DateUtil.getCurrentDateTime());
        List<PlmsRepaymentDetail> repaymentDetails = plmsRepaymentDetailDao.queryRepaymentDetailByScheduleDetailCodeAndDate(param);
        if (repaymentDetails == null || repaymentDetails.size() == 0) {
            logger.info("当前还款计划明细没有还款明细记录");
            return null;
        }

        List <PlmsRepaymentDetailTemp>plmsRepaymentDetailTempList = new ArrayList<PlmsRepaymentDetailTemp>();

        for (int i = 0; i < repaymentDetails.size();i++){
            PlmsRepaymentDetail plmsRepaymentDetail = repaymentDetails.get(i);
            String code = plmsRepaymentDetail.getCode();
            BigDecimal amount = plmsRepaymentDetail.getAmount();
            Integer subject = plmsRepaymentDetail.getSubject();
            Date timeHappened= plmsRepaymentDetail.getTimeHappened();
            String paymentMethod = plmsRepaymentDetail.getPaymentMethod();
            String scheduleDetailCode = plmsRepaymentDetail.getScheduleDetailCode();

            if (paymentMethod!= null && GlobDict.payment_method_manual.getKey().equals(paymentMethod)) {
                PlmsRepaymentDetailTemp plmsRepaymentDetailTemp = new PlmsRepaymentDetailTemp();
                plmsRepaymentDetailTemp.setCode(commonService.getUUID());
                plmsRepaymentDetailTemp.setDetailCode(code);
                plmsRepaymentDetailTemp.setAmount(amount);
                plmsRepaymentDetailTemp.setTimeHappened(timeHappened);
                plmsRepaymentDetailTemp.setSubject(subject);
                plmsRepaymentDetailTemp.setPaymentMethod(paymentMethod);
                plmsRepaymentDetailTemp.setBatchCode(batchCode);
                plmsRepaymentDetailTemp.setStatus(GlobDict.valid.getKey());
                plmsRepaymentDetailTemp.setScheduleDetailCode(scheduleDetailCode);
                plmsRepaymentDetailTempList.add(plmsRepaymentDetailTemp);
            }
        }

        int ret = repaymentDetailTempDao.insert(plmsRepaymentDetailTempList);

        if (ret != repaymentDetails.size()) {
            //更新数不等于查询出来的条数，则报错
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //将关联的所有罚息信息标记成未结清
        ret = overdueDao.updateOverdueStatusByRepaymentDetailCodeBatch(repaymentDetails);
        if (ret < 0) {
            //等于0是正常现象
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //将还款明细标记为无效
        ret = plmsRepaymentDetailDao.updateRepaymentDetailByCodeBatch(repaymentDetails);
        if (ret != repaymentDetails.size()) {
            //更新数不等于查询出来的条数，则报错
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }


        //定义参数    需求变更，增加保证金和保证金违约金  wangxf by 20170823
        String principal = "0.00";
        String interest = "0.00";
        String serviceCharge = "0.00";
        String bail = "0.00";
        String principalOverdue = "0.00";
        String interestOverdue = "0.00";
        String serviceChargeOverdue = "0.00";
        String bailPenalty = "0.00";
        //按科目进行统计金额
        for (PlmsRepaymentDetail repaymentDetail : repaymentDetails) {
            if (GlobDict.repayment_detail_subject_service_fee_penalty.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                // 服务费罚金
                serviceChargeOverdue = MathUtil.strAdd(serviceChargeOverdue, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_interest_overdue.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //利息罚金
                interestOverdue = MathUtil.strAdd(interestOverdue, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_principal_overdue.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //本金罚金
                principalOverdue = MathUtil.strAdd(principalOverdue, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_service_fee.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //服务费
                serviceCharge = MathUtil.strAdd(serviceCharge, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_interest.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //利息
                interest = MathUtil.strAdd(interest, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_principal.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //本金
                principal = MathUtil.strAdd(principal, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_bail.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //保证金
                bail = MathUtil.strAdd(bail, repaymentDetail.getAmount().toString(), 2);
            } else if (GlobDict.repayment_detail_subject_prepayment_penalty.getKey().equals(String.valueOf(repaymentDetail.getSubject()))) {
                //保证金违约金
                bailPenalty = MathUtil.strAdd(bailPenalty, repaymentDetail.getAmount().toString(), 2);
            }
        }
        //定义返回参数
        Map retParam = new HashMap();
        retParam.put("principal", principal);
        retParam.put("interest", interest);
        retParam.put("serviceCharge", serviceCharge);
        retParam.put("principalOverdue", principalOverdue);
        retParam.put("interestOverdue", interestOverdue);
        retParam.put("serviceChargeOverdue", serviceChargeOverdue);
        retParam.put("bail", bail);
        retParam.put("bailPenalty", bailPenalty);
        return retParam;
    }


    /**
     * 更新账户汇总表（罚息）
     *
     * @param accountDetail
     * @param overdueTotal
     */
    private void updateAccount(AccountDetail accountDetail, Map overdueTotal) throws ErrorException {
        Account accountParam = new Account();
        accountParam.setCode(accountDetail.getAccountCode());
        //查询账户信息表
        Account account = accountDao.qryAccount(accountParam);
        if (account == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_INFO_FAIL, "");
        }
        //应还罚金
        String overdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(account.getOverdue()),
                                        overdueTotal.get("principalTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("interestTotal").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getServiceFeePenalty()),
                        overdueTotal.get("serviceTotal").toString(),
                        2
                );
        //增加应还保证金违约金需求     wangxf by 20170823
        String bailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(account.getBailPenalty()),
                        overdueTotal.get("bailTotal").toString(),
                        0
                );
        account.setOverdue(overdue);
        account.setServiceFeePenalty(serviceFeePenalty);
        account.setBailPenalty(bailPenalty);
        int ret = accountDao.updateOverdueAndServiceFeePenaltyByCode(account);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 更新账户明细表（罚息）
     *
     * @param repaymentScheduleBean
     * @param overdueTotal
     */
    private AccountDetail updateAccountDetail(PlmsRepaymentScheduleBean repaymentScheduleBean, Map overdueTotal) throws ErrorException {
        //查询账户明细表
        AccountDetail accountDetailParam = new AccountDetail();
        accountDetailParam.setCode(repaymentScheduleBean.getDetailCode());
        AccountDetail accountDetail = accountDetailDao.queryAccountDetailByCode(accountDetailParam);
        if (accountDetail == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
        //更新账户明细表金额
        //应还罚金
        String overdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(accountDetail.getOverdue()),
                                        overdueTotal.get("principalTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("interestTotal").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getServiceFeePenalty()),
                        overdueTotal.get("serviceTotal").toString(),
                        2
                );
        //增加应还保证金违约金需求     wangxf by 20170823
        String bailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(accountDetail.getBailPenalty()),
                        overdueTotal.get("bailTotal").toString(),
                        0
                );
        accountDetail.setOverdue(overdue);
        accountDetail.setServiceFeePenalty(serviceFeePenalty);
        accountDetail.setBailPenalty(bailPenalty);
        int ret = accountDetailDao.updateAccountDetailAmountByCode(accountDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        return accountDetail;
    }

    /**
     * 更新当前还款计划明细记录关联的还款计划表记录（罚息）
     *
     * @param repaymentScheduleDetail
     * @param overdueTotal
     */
    private PlmsRepaymentScheduleBean updateRepaymentSchedule(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map overdueTotal) throws ErrorException {
        //查询还款计划信息
        PlmsRepaymentScheduleBean repaymentScheduleBeanParam = new PlmsRepaymentScheduleBean();
        repaymentScheduleBeanParam.setCode(repaymentScheduleDetail.getScheduleCode());
        PlmsRepaymentScheduleBean repaymentScheduleBean = plmsRepaymentScheduleDao.queryRepaymentScheduleByCode(repaymentScheduleBeanParam);
        if (repaymentScheduleBean == null) {
            throw new ErrorException(ReturnCode.REPAYMENT_SCHEDULE_FAIL, "");
        }
        //更新还款计划表
        //应还金额
        String amount = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        MathUtil.strSub
                                                (
                                                        ObjectUtils.toString(repaymentScheduleBean.getAmount()),
                                                        overdueTotal.get("principalTotal").toString(),
                                                        2
                                                ),
                                        overdueTotal.get("interestTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("serviceTotal").toString()
                        ,
                        2
                );
        //应还罚金
        String overdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(repaymentScheduleBean.getOverdue()),
                                        overdueTotal.get("principalTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("interestTotal").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getServiceFeePenalty()),
                        overdueTotal.get("serviceTotal").toString(),
                        2
                );
        //增加应还保证金违约金需求     wangxf by 20170823
        String bailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleBean.getBailPenalty()),
                        overdueTotal.get("bailTotal").toString(),
                        0
                );
        repaymentScheduleBean.setAmount(new BigDecimal(amount));
        repaymentScheduleBean.setOverdue(new BigDecimal(overdue));
        repaymentScheduleBean.setServiceFeePenalty(new BigDecimal(serviceFeePenalty));
        repaymentScheduleBean.setBailPenalty(new BigDecimal(bailPenalty));
        int ret = plmsRepaymentScheduleDao.updateRepaymentScheduleAmountByCode(repaymentScheduleBean);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        return repaymentScheduleBean;
    }

    /**
     * 更新还款计划明细表记录关联的放款信息表记录（罚息）
     *
     * @param repaymentScheduleDetail
     * @param overdueTotal
     */
    private void updatePaidInfo(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map overdueTotal) throws ErrorException {
        //查询放款信息表
        PaidInfo paidInfoParam = new PaidInfo();
        paidInfoParam.setCode(repaymentScheduleDetail.getPaidCode());
        PaidInfo paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfoParam);
        if (paidInfo == null) {
            throw new ErrorException(ReturnCode.PAID_INFO_FAIL, "");
        }
        //更新放款信息表
        String overdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(paidInfo.getOverdue()),
                                        overdueTotal.get("principalTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("interestTotal").toString(),
                        2
                );
        String serviceFeePenalty = MathUtil.strSub
                (
                        paidInfo.getServiceFeePenalty(),
                        overdueTotal.get("serviceTotal").toString(),
                        2
                );
        //增加应还保证金违约金需求     wangxf by 20170823
        String bailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(paidInfo.getBailPenalty()),
                        overdueTotal.get("bailTotal").toString(),
                        0
                );

        paidInfo.setOverdue(overdue);
        paidInfo.setServiceFeePenalty(serviceFeePenalty);
        paidInfo.setBailPenalty(bailPenalty);
        //更新放款信息表
        int ret = paidInfoDao.updatePaidInfoOverdueAndServiceFeePenalty(paidInfo);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 更新还款计划明细表中的应还金额、应还罚息、应还服务费违约金（罚息）
     *
     * @param repaymentScheduleDetail
     * @param overdueTotal
     */
    private void updateRepaymentScheduleDetail(PlmsRepaymentScheduleDetail repaymentScheduleDetail, Map overdueTotal) throws ErrorException {
        //应还金额
        String amount = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        MathUtil.strSub
                                                (
                                                        ObjectUtils.toString(repaymentScheduleDetail.getAmount()),
                                                        overdueTotal.get("principalTotal").toString(),
                                                        2
                                                ),
                                        overdueTotal.get("interestTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("serviceTotal").toString()
                        ,
                        2
                );
        //应还罚金
        String overdue = MathUtil.strSub
                (
                        MathUtil.strSub
                                (
                                        ObjectUtils.toString(repaymentScheduleDetail.getOverdue()),
                                        overdueTotal.get("principalTotal").toString(),
                                        2
                                ),
                        overdueTotal.get("interestTotal").toString(),
                        2
                );
        //应还服务费违约金
        String serviceFeePenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getServiceFeePenalty()),
                        overdueTotal.get("serviceTotal").toString(),
                        2
                );
        String totalOverdueDays = MathUtil.strSub
                (
                        repaymentScheduleDetail.getTotalOverdueDays(),
                        overdueTotal.get("prcpalAndIntstDays").toString(),
                        0
                );
        String serviceFeeTotalOverdueDays = MathUtil.strSub
                (
                        repaymentScheduleDetail.getServiceFeeTotalOverdueDays(),
                        overdueTotal.get("serviceFeeDays").toString(),
                        0
                );
        //增加应还保证金违约金需求     wangxf by 20170823
        String bailPenalty = MathUtil.strSub
                (
                        ObjectUtils.toString(repaymentScheduleDetail.getBailPenalty()),
                        overdueTotal.get("bailTotal").toString(),
                        0
                );
        //修改还款计划明细
        repaymentScheduleDetail.setAmount(new BigDecimal(amount));
        repaymentScheduleDetail.setOverdue(new BigDecimal(overdue));
        repaymentScheduleDetail.setServiceFeePenalty(new BigDecimal(serviceFeePenalty));
        repaymentScheduleDetail.setTotalOverdueDays(totalOverdueDays);
        repaymentScheduleDetail.setServiceFeeTotalOverdueDays(serviceFeeTotalOverdueDays);
        repaymentScheduleDetail.setBailPenalty(new BigDecimal(bailPenalty));
        int ret = plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetailAmountByCode(repaymentScheduleDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 回滚逾期
     *
     * @param repaymentScheduleDetail
     * @param rollBachDate
     */
    private Map rollBackOverdue(PlmsRepaymentScheduleDetail repaymentScheduleDetail, String rollBachDate) throws ErrorException {
        //找到关联到该记录的所有罚息表记录
        List<Overdue> overdues = overdueDao.queryOverdueByScheduleDetail(repaymentScheduleDetail);
        if (overdues == null || overdues.size() <= 0) {
            //没有需要回滚的罚息，属于正常现象
            logger.info("当前还款计划明细没有需要回滚的罚息，继续执行：" + repaymentScheduleDetail.getCode());
            return null;
        }
        Map queryOverdueGroupDateParam = new HashMap();
        queryOverdueGroupDateParam.put("scheduleDetailCode", repaymentScheduleDetail.getCode());
        queryOverdueGroupDateParam.put("rollBachDate", rollBachDate);
        queryOverdueGroupDateParam.put("nowDate", DateUtil.getCurrentDateTime());

        //根据日期和逾期天数进行分组，统计出逾期天数   wangxf by 20170807
        //本息逾期天数计算
        Integer prcpalAndIntstDays = overdueDetailDao.queryPrcpalAndIntstOverdueGroupByScheduleDetailCode(queryOverdueGroupDateParam);
        Integer serviceFeeDays = overdueDetailDao.queryServiceFeeOverdueGroupByScheduleDetailCode(queryOverdueGroupDateParam);
        if ((prcpalAndIntstDays == null || prcpalAndIntstDays == 0) && (serviceFeeDays == null || serviceFeeDays == 0)) {
            //如果两个都没有值，没有需要回滚的逾期明细，正常现象，例如：资金入账时间之后没有跑扣账或者逾期定时任务，一直挂起，资金入账之前有罚息，那么资金入账之后没有生成罚息流水。
            return null;
        }
        //其中一个有值
        Map overdueTotal = new HashMap();
        overdueTotal.put("prcpalAndIntstDays", ObjectUtils.toString(prcpalAndIntstDays) == "" ? "0" : ObjectUtils.toString(prcpalAndIntstDays));
        overdueTotal.put("serviceFeeDays", ObjectUtils.toString(serviceFeeDays) == "" ? "0" : ObjectUtils.toString(serviceFeeDays));

        //根据日期分组，查询罚息明细表（统计出每条还款明细回滚的逾期天数，因为本金、利息和服务费罚息明细日期会重叠）
        //Integer totalDays = overdueDetailDao.queryOverdueGroupDateByScheduleDetailCode(queryOverdueGroupDateParam);
        //if (totalDays == null || totalDays == 0) {
        //    //没有需要回滚的逾期明细，正常现象，例如：资金入账时间之后没有跑扣账或者逾期定时任务，一直挂起，资金入账之前有罚息，那么资金入账之后没有生成罚息流水。
        //    return null;
        //}
        //定义返回参数

        //定义本金、利息和服务费的罚息总金额
        String principalTotal = "0";
        String interestTotal = "0";
        String serviceTotal = "0";
        String bailTotal = "0";

        //根据每条罚息进行回滚
        for (Overdue overdue : overdues) {
            Map rollBackDetailMap = rollBackOverdueSingle(overdue, rollBachDate);
            if (rollBackDetailMap == null) {
                //证明没有回滚的明细，直接跳过
                continue;
            }
            //逾期金额递减
            String amount = MathUtil.strSub(overdue.getAmount(), rollBackDetailMap.get("totalAmount").toString(), 2);
            String overdueDays = MathUtil.strSub(overdue.getOverdueDays(), rollBackDetailMap.get("totalNum").toString(), 0);
            overdue.setAmount(amount);
            overdue.setOverdueDays(overdueDays);
            if (MathUtil.strcompareTo3(amount, "0") && MathUtil.strcompareTo3(overdueDays, "0")) {
                //减去金额和天数，标记为失效
                overdue.setStatus(GlobDict.overdue_status_unvalid.getKey());
            }
            //修改表结构数据
            int ret = overdueDao.updateOverdueByCode(overdue);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
            //判断当前罚金类型
            if (GlobDict.overdue_subject_principal.getKey().equals(overdue.getSubject())) {
                //本金罚息
                principalTotal = MathUtil.strAdd(principalTotal, rollBackDetailMap.get("totalAmount").toString(), 2);
            } else if (GlobDict.overdue_subject_interest.getKey().equals(overdue.getSubject())) {
                //利息罚息
                interestTotal = MathUtil.strAdd(interestTotal, rollBackDetailMap.get("totalAmount").toString(), 2);
            } else if (GlobDict.overdue_subject_fee.getKey().equals(overdue.getSubject())) {
                //服务费违约金
                serviceTotal = MathUtil.strAdd(serviceTotal, rollBackDetailMap.get("totalAmount").toString(), 2);
            } else if (GlobDict.overdue_subject_bail.getKey().equals(overdue.getSubject())) {
                bailTotal = MathUtil.strAdd(bailTotal, rollBackDetailMap.get("totalAmount").toString(), 2);
            }
        }
        overdueTotal.put("principalTotal", principalTotal);
        overdueTotal.put("interestTotal", interestTotal);
        overdueTotal.put("serviceTotal", serviceTotal);
        overdueTotal.put("bailTotal", bailTotal);
        return overdueTotal;
    }

    /**
     * 单条回滚罚息信息
     *
     * @param overdue
     * @param rollBachDate
     */
    private Map rollBackOverdueSingle(Overdue overdue, String rollBachDate) throws ErrorException {
        //查询罚息流水表记录：时间所属日期大于资金到账日期小于等于当前日期。
        Map queryParam = new HashMap();
        queryParam.put("rollBachDate", rollBachDate);
        queryParam.put("nowDate", DateUtil.getCurrentDateTime());
        queryParam.put("overdueCode", overdue.getCode());
        List<OverdueDetail> overdueDetails = overdueDetailDao.queryOverdueDetailByOverdueCodeAndTime(queryParam);
        if (overdueDetails == null || overdueDetails.size() <= 0) {
            //正常现象，例如：资金入账时间之后没有跑扣账或者逾期定时任务，一直挂起，资金入账之前有罚息，那么资金入账之后没有生成罚息流水。
            logger.info("当前罚息信息的罚息流水不存在，正常现象，继续下一步：" + overdue.getCode());
            return null;
        }
        Map overdueDetailTotal = new HashMap();
        //算出需要回滚的总金额
        String totalAmount = "0";
        String totalNum = "0";
        for (OverdueDetail overdueDetail : overdueDetails) {
            totalAmount = MathUtil.strAdd(totalAmount, overdueDetail.getOverdueAmount(), 2);
            totalNum = MathUtil.strAdd(totalNum, overdueDetail.getOverdueDays(), 2);
        }
        //算出总金额后，把查询出来的罚息流水全部标记为无效
        int ret = overdueDetailDao.updateOverdueDetailStatusByCodeBatch(overdueDetails);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        overdueDetailTotal.put("totalAmount", totalAmount);
        overdueDetailTotal.put("totalNum", totalNum);
        return overdueDetailTotal;
    }


}
