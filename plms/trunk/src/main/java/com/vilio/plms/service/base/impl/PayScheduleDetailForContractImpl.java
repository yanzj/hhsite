package com.vilio.plms.service.base.impl;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.base.PayScheduleDetailForContract;
import com.vilio.plms.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2017/8/3.
 */
@Service("payScheduleDetailForContract")
public class PayScheduleDetailForContractImpl implements PayScheduleDetailForContract {
    private static Logger logger = Logger.getLogger(PayScheduleDetailForContractImpl.class);

    //保证金违约金
    private static final int subject_bail_penalty = 1;
    //服务费违约金
    private static final int subject_fee_penalty = 2;
    //利息罚息
    private static final int subject_interest_overdue = 3;
    //本金罚息
    private static final int subject_principal_overdue = 4;
    //保证金
    private static final int subject_bail = 5;
    //服务费
    private static final int subject_service_fee = 6;
    //利息
    private static final int subject_interest = 7;
    //本金
    private static final int subject_principal = 8;

    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;
    @Resource
    PlmsOverdueDao plmsOverdueDao;
    @Resource
    PlmsRepaymentDetailDao plmsRepaymentDetailDao;
    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;
    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    AccountDetailDao accountDetailDao;
    @Resource
    AccountDao accountDao;
    @Resource
    CommDao commDao;
    @Resource
    SysInfoParamDao sysInfoParamDao;

    /**
     * 根据合同和业务发生日期扣款（T日扣T-1的款）
     * @param contractCode
     * @param happenTime
     * @return
     * @throws Exception
     */
    public boolean payContract(String contractCode, Date happenTime) throws Exception {
        if(null == contractCode || null == happenTime){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "合同号或者发生时间为空");
        }
        String strHappenTime = DateUtil.formatDateTime(happenTime, DateUtil.DATE_PATTERN2);
        happenTime = DateUtil.parseDate2(strHappenTime);
        // 获取需要扣款的还款计划明细
        Map requestMap = new HashedMap();
        requestMap.put("today",happenTime);
        requestMap.put("contractCode",contractCode);
        List<Map> scheduleDetailList = plmsRepaymentScheduleDetailDao.queryAllNeedPayScheduleDetail(requestMap);
        if(null == scheduleDetailList || scheduleDetailList.size() < 1){
            logger.info("合同contract=" + contractCode + "没有要扣款的还款计划明细");
            return false;
        }
        //将统一个合同下的还款明细统计到一起，将还款计划明细中的 服务费违约金、利息罚息、本金罚息、服务费、利息、本金分别抽取出来并按续排列
        ArrayList<Map> detailList = new ArrayList();
        for(Map detail: scheduleDetailList){
            contractCode = (String) detail.get("contractCode");
            String detailCode = (String) detail.get("detailCode");
            //放款日
            Date paidTime = (Date) detail.get("paidTime");
            //还款日
            Date repaymentDate = (Date) detail.get("repaymentDate");

            //服务费违约金利率
            BigDecimal serviceFeeInterestRate = (BigDecimal) detail.get("serviceFeeInterestRate");
            //利息逾期年化利率
            BigDecimal interestOverInterestRate = (BigDecimal) detail.get("interestOverInterest");
            //本金逾期年化利率
            BigDecimal principalOverInterestRate = (BigDecimal) detail.get("principalOverInterest");
            //产品年化利率
            BigDecimal annualizedInterest = (BigDecimal) detail.get("annualizedInterest");
            //利息是否代收代付
            String isInterestInstead = GlobDict.apply_interesting_is_interest_Instead_no.getKey();
            if(Integer.parseInt(GlobDict.apply_interesting_is_interest_Instead_yes.getKey()) == Integer.parseInt(detail.get("isInterestInstead").toString())) {
                isInterestInstead = GlobDict.apply_interesting_is_interest_Instead_yes.getKey();
            }

            //获取罚息表中信息
            requestMap.put("scheduleDetailCode", detailCode);
            requestMap.put("status", GlobDict.overdue_status_paying.getKey());
            List<PlmsOverdue> overdueList = plmsOverdueDao.queryOverdueByDetailCodeAndStatus(requestMap);
            if(null == overdueList || overdueList.size() == 0){
                logger.debug("detailCode=" + detailCode + "没有要处理的逾期！");
            }else{
                for(PlmsOverdue overdue: overdueList){
                    String subject = overdue.getSubject();
                    if(GlobDict.overdue_subject_fee.getKey().equals(subject)){
                        //应还服务费罚息
                        Map feeOverdueMap = new HashedMap();
                        feeOverdueMap.put("subject",subject_fee_penalty);
                        feeOverdueMap.put("detailCode",detailCode);
                        feeOverdueMap.put("code",overdue.getCode());
                        feeOverdueMap.put("amount",overdue.getAmount());
                        feeOverdueMap.put("paidTime",paidTime);
                        feeOverdueMap.put("repaymentDate",repaymentDate);
                        feeOverdueMap.put("rate",serviceFeeInterestRate);
                        feeOverdueMap.put("isInterestInstead",isInterestInstead);
                        detailList.add(feeOverdueMap);

                    } else if(GlobDict.overdue_subject_interest.getKey().equals(subject)){
                        //应还利息罚息
                        Map interestOverdueMap = new HashedMap();
                        interestOverdueMap.put("subject",subject_interest_overdue);
                        interestOverdueMap.put("detailCode",detailCode);
                        interestOverdueMap.put("code",overdue.getCode());
                        interestOverdueMap.put("amount",overdue.getAmount());
                        interestOverdueMap.put("paidTime",paidTime);
                        interestOverdueMap.put("repaymentDate",repaymentDate);
                        interestOverdueMap.put("rate",interestOverInterestRate);
                        interestOverdueMap.put("isInterestInstead",isInterestInstead);
                        detailList.add(interestOverdueMap);
                    }else if(GlobDict.overdue_subject_principal.getKey().equals(subject)){
                        //应还本金罚息
                        Map principalOverdueMap = new HashedMap();
                        principalOverdueMap.put("subject",subject_principal_overdue);
                        principalOverdueMap.put("detailCode",detailCode);
                        principalOverdueMap.put("code",overdue.getCode());
                        principalOverdueMap.put("amount",overdue.getAmount());
                        principalOverdueMap.put("paidTime",paidTime);
                        principalOverdueMap.put("repaymentDate",repaymentDate);
                        principalOverdueMap.put("rate",principalOverInterestRate);
                        principalOverdueMap.put("isInterestInstead",isInterestInstead);
                        detailList.add(principalOverdueMap);
                    }else if(GlobDict.overdue_subject_bail.getKey().equals(subject)){
                        //应还保证金罚息
                        Map bailOverdueMap = new HashedMap();
                        bailOverdueMap.put("subject",subject_bail_penalty);
                        bailOverdueMap.put("detailCode",detailCode);
                        bailOverdueMap.put("code",overdue.getCode());
                        bailOverdueMap.put("amount",overdue.getAmount());
                        bailOverdueMap.put("paidTime",paidTime);
                        bailOverdueMap.put("repaymentDate",repaymentDate);
                        bailOverdueMap.put("rate",serviceFeeInterestRate);
                        bailOverdueMap.put("isInterestInstead",isInterestInstead);
                        detailList.add(bailOverdueMap);
                    }
                }
            }

            if(isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_no.getKey())){
                //应还服务费
                BigDecimal bigServiceFee = (BigDecimal) detail.get("serviceFee");
                BigDecimal bigRepaymentedServiceFee = (BigDecimal) detail.get("repaymentedServiceFee");
                //判断是否已还
                if(bigServiceFee.compareTo(bigRepaymentedServiceFee) != 0){
                    Map serviceFeeMap = new HashedMap();
                    serviceFeeMap.put("subject",subject_service_fee);
                    serviceFeeMap.put("detailCode",detailCode);
                    serviceFeeMap.put("amount",bigServiceFee);
                    serviceFeeMap.put("paidTime",paidTime);
                    serviceFeeMap.put("repaymentDate",repaymentDate);
                    serviceFeeMap.put("rate",annualizedInterest);
                    serviceFeeMap.put("isInterestInstead",isInterestInstead);
                    detailList.add(serviceFeeMap);
                }
            }
            //应还利息
            BigDecimal bigInterest = (BigDecimal) detail.get("interest");
            BigDecimal bigRepaymentedInterest = (BigDecimal) detail.get("repaymentedInterest");
            //判断是否已还
            if(bigInterest.compareTo(bigRepaymentedInterest) != 0){
                Map interestMap = new HashedMap();
                interestMap.put("subject",subject_interest);
                interestMap.put("detailCode",detailCode);
                interestMap.put("amount",bigInterest);
                interestMap.put("paidTime",paidTime);
                interestMap.put("repaymentDate",repaymentDate);
                interestMap.put("rate",annualizedInterest);
                interestMap.put("isInterestInstead",isInterestInstead);
                detailList.add(interestMap);
            }
            //应还保证金
            BigDecimal bigBail = (BigDecimal) detail.get("bail");
            BigDecimal bigRepaymentedBail = (BigDecimal) detail.get("repaymentedBail");
            //判断是否已还
            if(bigBail.compareTo(bigRepaymentedBail) != 0){
                Map interestMap = new HashedMap();
                interestMap.put("subject",subject_bail);
                interestMap.put("detailCode",detailCode);
                interestMap.put("amount",bigBail);
                interestMap.put("paidTime",paidTime);
                interestMap.put("repaymentDate",repaymentDate);
                interestMap.put("rate",annualizedInterest);
                interestMap.put("isInterestInstead",isInterestInstead);
                detailList.add(interestMap);
            }
            //应还本金
            BigDecimal bigPrincipal = (BigDecimal) detail.get("principal");
            BigDecimal bigRepaymentedPrincipal = (BigDecimal) detail.get("repaymentedPrincipal");
            //判断是否已还
            if(bigPrincipal.compareTo(bigRepaymentedPrincipal) != 0){
                Map principalMap = new HashedMap();
                principalMap.put("subject",subject_principal);
                principalMap.put("detailCode",detailCode);;
                principalMap.put("amount",detail.get("principal"));
                principalMap.put("paidTime",paidTime);
                principalMap.put("repaymentDate",repaymentDate);
                principalMap.put("rate",annualizedInterest);
                principalMap.put("isInterestInstead",isInterestInstead);
                detailList.add(principalMap);
            }
        }
        //扣款
        if(detailList.size() > 0){
            //宏获扣款账户
            BigDecimal honghuoBalance = null;
            //资方扣款账户
            BigDecimal fundSideBalance = null;
            //宏获保证金账户
            BigDecimal honghuoBailAccountBalance = null;
            //账户明细表code
            String accountDetailCode = null;
            //账户汇总表code
            String accountCode = null;
            //排序
            sortPay(detailList);
            //宏获账户扣款标识
            boolean honghuoFlag = true;
            //资方账户扣款标识
            boolean fundSideFlag = true;
            //宏获保证金账户扣款标识
            boolean honghuoBailAccountFlag = true;
            //开始扣款
            for(int i = 0; i < detailList.size(); i++){
                Map payBean = detailList.get(i);
                //还款计划明细表code
                String detailCode = (String) payBean.get("detailCode");
                int subject = (int) payBean.get("subject");
                //是否代收代付
                String isInterestInstead = GlobDict.apply_interesting_is_interest_Instead_no.getKey() ;
                if(Integer.parseInt(GlobDict.apply_interesting_is_interest_Instead_yes.getKey()) == Integer.parseInt(payBean.get("isInterestInstead").toString())) {
                    isInterestInstead = GlobDict.apply_interesting_is_interest_Instead_yes.getKey();
                }
                //放款信息表code
                String paidCode = null;
                //还款计划表code
                String scheduleCode = null;

                if(!honghuoFlag && (subject == subject_fee_penalty || subject == subject_interest_overdue || subject == subject_principal_overdue || subject == subject_service_fee || subject == subject_bail_penalty || (subject == subject_interest && isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_yes.getKey())) )){
                    continue;
                }
                if(!fundSideFlag && (subject == subject_principal || (subject == subject_interest && isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_no.getKey())) )){
                    continue;
                }
                if(!honghuoBailAccountFlag && subject == subject_bail){
                    continue;
                }

                Map payInfoMap = plmsRepaymentScheduleDetailDao.getPayInfoForOneScheduleDetail(detailCode);
                honghuoBalance = (BigDecimal) payInfoMap.get("honghuoBalance");
                fundSideBalance = (BigDecimal) payInfoMap.get("fundSideBalance");
                honghuoBailAccountBalance = (BigDecimal) payInfoMap.get("honghuoBailAccountBalance");
                accountDetailCode = (String) payInfoMap.get("accountDetailCode");
                accountCode = (String) payInfoMap.get("accountCode");
                paidCode = (String) payInfoMap.get("paidCode");
                scheduleCode = (String) payInfoMap.get("scheduleCode");

                String code = (String) payBean.get("code");
                BigDecimal amount = (BigDecimal) payBean.get("amount");
                if( (subject == subject_fee_penalty || subject == subject_interest_overdue || subject == subject_principal_overdue || subject == subject_service_fee || subject == subject_bail_penalty || (subject == subject_interest && isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_yes.getKey()))) && honghuoBalance.compareTo(amount) == -1){
                    if(i == detailList.size() - 1){
                        break;
                    }else{
                        honghuoFlag = false;
                        continue;
                    }
                }
                if( (subject == subject_principal || (subject == subject_interest && isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_no.getKey()))) &&  fundSideBalance.compareTo(amount) == -1){
                    if(i == detailList.size() - 1){
                        break;
                    }else{
                        fundSideFlag = false;
                        continue;
                    }
                }
                if(subject == subject_bail && honghuoBailAccountBalance.compareTo(amount) == -1){
                    if(i == detailList.size() - 1){
                        break;
                    }else{
                        honghuoBailAccountFlag = false;
                        continue;
                    }
                }
                PlmsOverdue overdue = null;
                //更新罚息表
                if(subject == subject_fee_penalty || subject == subject_interest_overdue || subject == subject_principal_overdue||subject == subject_bail_penalty){
                    overdue = new PlmsOverdue();
                    overdue.setCode(code);
                    overdue.setStatus(GlobDict.overdue_status_paid.getKey());
                    plmsOverdueDao.updateOverdueStatusByCode(overdue);
                }
                //创建还款明细表记录
                PlmsRepaymentDetail plmsRepaymentDetail = new PlmsRepaymentDetail();
                String repaymentDetailCode = getUUID();
                plmsRepaymentDetail.setCode(repaymentDetailCode);
                plmsRepaymentDetail.setAmount(amount);
                plmsRepaymentDetail.setSubject(subject);
                plmsRepaymentDetail.setScheduleDetailCode(detailCode);
                plmsRepaymentDetail.setStatus(GlobDict.repayment_detail_status_avaliable.getKey());
                plmsRepaymentDetail.setTimeHappened(happenTime);
                plmsRepaymentDetail.setCreateDate(happenTime);
                plmsRepaymentDetail.setModifyDate(happenTime);
                int upRepaymentDetailFlag = plmsRepaymentDetailDao.saveRepaymentDetail(plmsRepaymentDetail);
                //更新罚息表的还款明细code字段
                if(subject == subject_fee_penalty || subject == subject_interest_overdue || subject == subject_principal_overdue||subject == subject_bail_penalty){
                    overdue.setRepaymentDetailCode(repaymentDetailCode);
                    plmsOverdueDao.updateOverdueStatusByCode(overdue);
                }

                    //更新还款计划明细表记录
                PlmsRepaymentScheduleDetail repaymentScheduleDetail = new PlmsRepaymentScheduleDetail();
                repaymentScheduleDetail.setCode(detailCode);
                repaymentScheduleDetail.setModifyDate(happenTime);
                if(subject == subject_bail_penalty){
                    repaymentScheduleDetail.setRepaymentedBailPenalty(amount);
                }else if(subject == subject_fee_penalty){
                    repaymentScheduleDetail.setRepaymentedServiceFeePenalty(amount);
                }else if(subject == subject_interest_overdue || subject == subject_principal_overdue){
                    repaymentScheduleDetail.setRepaymentedOverdue(amount);
                }else if (subject == subject_bail){
                    repaymentScheduleDetail.setRepaymentedBail(amount);
                }else if(subject == subject_service_fee){
                    repaymentScheduleDetail.setRepaymentedServiceFee(amount);
                }else if(subject == subject_interest){
                    repaymentScheduleDetail.setRepaymentedInterest(amount);
                }else if(subject == subject_principal){
                    repaymentScheduleDetail.setRepaymentedPrincipal(amount);
                    repaymentScheduleDetail.setStatus(GlobDict.repayment_schedule_detail_status_paid.getKey());
                }
                int upRepaymentScheduleDetailFlag = plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetailRepaymentAmountByCode(repaymentScheduleDetail);
                //是否结清
                Map scheduleDetailMap = new HashedMap();
                scheduleDetailMap.put("code", detailCode);
                scheduleDetailMap.put("status", GlobDict.repayment_schedule_detail_status_paid.getKey());
                plmsRepaymentScheduleDetailDao.updateFinishedScheduleDetailStatusByCodeAndStatus(scheduleDetailMap);

                //更新还款计划明细表记录关联的放款信息表记录
                PaidInfo paidInfo = new PaidInfo();
                paidInfo.setCode(paidCode);
                paidInfo.setModifyDate(DateUtil.convert(happenTime, DateUtil.DATE_TIME_PATTERN3));
                if (subject == subject_bail_penalty){
                    paidInfo.setRepaymentedBailPenalty(amount.toString());
                }else if(subject == subject_fee_penalty){
                    paidInfo.setRepaymentedServiceFeePenalty(amount.toString());
                }else if(subject == subject_interest_overdue || subject == subject_principal_overdue){
                    paidInfo.setRepaymentedOverdue(amount.toString());
                }else if(subject == subject_service_fee){
                    paidInfo.setRepaymentedServiceFee(amount.toString());
                }else if(subject == subject_interest){
                    paidInfo.setRepaymentedInterest(amount.toString());
                }else if(subject == subject_principal){
                    paidInfo.setRepaymentedPrincipal(amount.toString());
                }else if (subject == subject_bail){
                    paidInfo.setRepaymentedBail(amount.toString());
                }
                plmsPaidInfoDao.updatePaidInfoByCode(paidInfo);
                if(subject == subject_principal){
                    //是否结清
                    paidInfo.setStatus(GlobDict.paid_info_status_cleared.getKey());
                    int upPaidInfoStatusFlag = plmsPaidInfoDao.updateFinishedPaidInfoStatusByCodeAndStatus(paidInfo);
                }
                //更新还款计划表记录
                PlmsRepaymentScheduleBean scheduleBean = new PlmsRepaymentScheduleBean();
                scheduleBean.setCode(scheduleCode);
                scheduleBean.setModifyDate(happenTime);
                if(subject == subject_bail_penalty){
                    scheduleBean.setRepaymentedBailPenalty(amount);
                }else if(subject == subject_fee_penalty){
                    scheduleBean.setRepaymentedServiceFeePenalty(amount);
                }else if(subject == subject_interest_overdue || subject == subject_principal_overdue){
                    scheduleBean.setRepaymentedOverdue(amount);
                }else if(subject == subject_bail){
                    scheduleBean.setRepaymentedBail(amount);
                }else if(subject == subject_service_fee){
                    scheduleBean.setRepaymentedServiceFee(amount);
                }else if(subject == subject_interest){
                    scheduleBean.setRepaymentedInterest(amount);
                }else if(subject == subject_principal){
                    scheduleBean.setRepaymentedPrincipal(amount);
                }
                plmsRepaymentScheduleDao.updateRepaymentScheduleRepaymentAmountByCode(scheduleBean);
                //是否结清
                scheduleBean.setStatus(GlobDict.repayment_schedule_status_paid.getKey());
                int upScheduleStatusFlag = plmsRepaymentScheduleDao.updateFinishedScheduleStatusByCodeAndStatus(scheduleBean);
                //更新账户明细表记录
                AccountDetail accountDetail = new AccountDetail();
                accountDetail.setCode(accountDetailCode);
                accountDetail.setModifyDate(DateUtil.convert(happenTime, DateUtil.DATE_TIME_PATTERN3));
                if (subject == subject_bail_penalty){
                    accountDetail.setRepaymentedBailPenalty(amount.toString());
                    accountDetail.setHonghuoBalance(amount.toString());
                }else if(subject == subject_fee_penalty){
                    accountDetail.setRepaymentedServiceFeePenalty(amount.toString());
                    accountDetail.setHonghuoBalance(amount.toString());
                }else if(subject == subject_interest_overdue || subject == subject_principal_overdue){
                    accountDetail.setRepaymentedOverdue(amount.toString());
                    accountDetail.setHonghuoBalance(amount.toString());
                }else if(subject == subject_bail){
                    accountDetail.setRepaymentedBail(amount.toString());
                    accountDetail.setHonghuoBailAccountBalance(amount.toString());
                }else if(subject == subject_service_fee){
                    accountDetail.setRepaymentedServiceFee(amount.toString());
                    accountDetail.setHonghuoBalance(amount.toString());
                }else if(subject == subject_interest){
                    accountDetail.setRepaymentedInterest(amount.toString());
                    if(isInterestInstead.equals(GlobDict.apply_interesting_is_interest_Instead_yes.getKey())){
                        accountDetail.setHonghuoBalance(amount.toString());
                    }else{
                        accountDetail.setFundSideBalance(amount.toString());
                    }
                }else if(subject == subject_principal){
                    accountDetail.setRepaymentedPrincipal(amount.toString());
                    accountDetail.setFundSideBalance(amount.toString());
                }
                int upAccountDetailFlag = accountDetailDao.updateAccountDetailByCode(accountDetail);
                //更新账户汇总表记录
                Account account = new Account();
                account.setCode(accountCode);
                account.setModifyDate(DateUtil.convert(happenTime, DateUtil.DATE_TIME_PATTERN3));
                if(subject == subject_bail_penalty){
                    account.setRepaymentedBailPenalty(amount.toString());
                    account.setBailPenalty("-" + amount.toString());
                }else if(subject == subject_fee_penalty){
                    account.setRepaymentedServiceFeePenalty(amount.toString());
                    account.setServiceFeePenalty("-" + amount.toString());
                }else if(subject == subject_interest_overdue || subject == subject_principal_overdue){
                    account.setRepaymentedOverdue(amount.toString());
                    account.setOverdue("-" + amount.toString());
                }else if(subject == subject_bail){
                    account.setRepaymentedBail(amount.toString());
                    account.setBail("-" + amount.toString());
                }else if(subject == subject_service_fee){
                    account.setRepaymentedServiceFee(amount.toString());
                    account.setServiceFee("-" + amount.toString());
                }else if(subject == subject_interest){
                    account.setRepaymentedInterest(amount.toString());
                    account.setInterest("-" + amount.toString());
                }else if(subject == subject_principal){
                    account.setRepaymentedPrincipal(amount.toString());
                    account.setPrincipal("-" + amount.toString());
                    account.setRemainingQuota(amount.toString());
                }
                int upAccountFlag = accountDao.updateAccountByCode(account);
            }
        }

        return true;
    }

    /**
     * 批量处理合同扣款
     * @param contractList
     * @param happenTime
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public boolean payContractList(List<Map> contractList, Date happenTime) throws Exception {
        //更新扣款操作定时任务数据库时间
        Map sysMap = new HashedMap();
        sysMap.put("itemId",GlobDict.pay_schedule_job_item_id.getKey());
        sysMap.put("executeTime",DateUtil.getCurrentDateTime());
        sysInfoParamDao.updateSysparamInfoExecuteTime(sysMap);
        for(Map map : contractList){
            payContract((String) map.get("contractCode"), happenTime);
        }
        return true;
    }

    /**
     * 扣款排序:
     * 1级.  科目： 保证金违约金 > 服务费违约金 > 利息罚息 > 本金罚息 > 保证金 > 服务费 > 利息 >本金
     * 2级.  还款日顺序由大到小
     * 3级    逾期利率小的在前   ， 若是本金，则放款日早的先
     */
    public static void sortPay(List<Map> pays) throws Exception{
        Collections.sort(pays, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                Map p1 = (Map)o1;
                Map p2 = (Map)o2;
                //科目
                int subject1 = (int) p1.get("subject");
                int subject2 = (int) p2.get("subject");
                if( (subject1 - subject2) > 0){
                    return 1;
                }else if((subject1 - subject2) < 0){
                    return -1;
                }else{
                    //还款日
                    Date repaymentDate1 = (Date) p1.get("repaymentDate");
                    Date repaymentDate2 = (Date) p2.get("repaymentDate");
                    double repaymentDateDifferHours = 0;
                    try {
                        repaymentDateDifferHours = DateUtil.differHours(repaymentDate2,repaymentDate1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("还款日计算出错！paidTime1=" + repaymentDate1 + ",paidTime2=" + repaymentDate2);
                    }
                    if( (subject1 == subject_interest || subject1 == subject_principal) && repaymentDateDifferHours > 0){
                        return -1;
                    }else if( (subject1 == subject_interest || subject1 == subject_principal) && repaymentDateDifferHours < 0){
                        return 1;
                    }else if( (subject1 == subject_interest || subject1 == subject_principal) && repaymentDateDifferHours == 0){
                        //利息或本金 放款日
                        Date paidTime1 = (Date) p1.get("paidTime");
                        Date paidTime2 = (Date) p2.get("paidTime");
                        double differHours = 0;
                        try {
                            differHours = DateUtil.differHours(paidTime2,paidTime1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("放款日计算出错！paidTime1=" + paidTime1 + ",paidTime2=" + paidTime2);
                        }
                        if(differHours > 0){
                            return -1;
                        }else if(differHours < 0){
                            return 1;
                        }else{
                            return 0;
                        }
                    }else if(repaymentDateDifferHours > 0){
                        return 1;
                    }else if(repaymentDateDifferHours < 0){
                        return -1;
                    }else{
                        //罚息
                        BigDecimal rate1 = (BigDecimal) p1.get("rate");
                        BigDecimal rate2 = (BigDecimal) p2.get("rate");
                        if(rate1.compareTo(rate2) < 0){
                            return -1;
                        }else if(rate1.compareTo(rate2) > 0){
                            return 1;
                        }else{
                            return 0;
                        }
                    }

                }

            }

        });
    }

    /**
     * 生成uuid
     *
     * @return
     * @throws ErrorException
     */
    public String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) throws Exception {

        List<Map> pays = new ArrayList();
        Map m1 = new HashedMap();
        m1.put("subject",null);


        m1.put("repaymentDate",DateUtil.parseDate2("2017-02-02"));
        m1.put("paidTime",DateUtil.parseDate2("2017-02-03"));
        m1.put("rate",new BigDecimal("0.2"));
        pays.add(m1);
        Map m2 = new HashedMap();
        m2.put("subject",5);
        m2.put("repaymentDate",DateUtil.parseDate2("2017-02-02"));
        m2.put("paidTime",DateUtil.parseDate2("2017-02-02"));
        m2.put("rate",new BigDecimal("0.1"));
        pays.add(m2);
    }


}
