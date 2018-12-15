package com.vilio.plms.service;

import com.vilio.plms.dao.SysInfoParamDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.MessageService;
import com.vilio.plms.service.quartz.impl.OverdueServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlElementDecl;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by martin on 2017/7/30.
 */
@Service
public class Plms100100 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms100100.class);

    @Resource
    MessageService messageService;
    @Resource
    SysInfoParamDao sysInfoParamDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程空实现
     *
     * @param contractCode
     * @param executeDate
     */

    public void overdue(String contractCode,String executeDate) throws ErrorException {

        Map sysInfoParam = new HashMap();
        List<HashMap> accountLockList = (List)sysInfoParamDao.qryAccountLockList();
        Map accountLock = new HashMap();
        if (accountLockList!=null&&accountLockList.size()>0){
            accountLock = accountLockList.get(0);
            String itemCval = (String)accountLock.get("itemCval");
            if (itemCval!=null && "Y".equals(itemCval)){
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
        }

//        String executeDate = (String)body.get("executeDate");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date executeTime = new Date();
//        try {
//            executeTime = sdf.parse(calculationDate);
//        }catch(ParseException e){
//            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//        }
//        List<HashMap> calculateOverdueInterestJobList = (List)sysInfoParamDao.qryCurrentDayOverdueJobList();
//        if (calculateOverdueInterestJobList!=null&&calculateOverdueInterestJobList.size()>0){
//            for (int i = 0;i<calculateOverdueInterestJobList.size();i++){
//                HashMap calculateOverdueInterestJob = calculateOverdueInterestJobList.get(0);
//                executeTime = (Date)calculateOverdueInterestJob.get("executeTime");
//            }
//        }
//        Calendar calendar = Calendar.getInstance();
//        if (executeTime!=null) {
//            calendar.setTime(executeTime);
//            calendar.add(Calendar.DATE,1);
//        }
//        Date currentExecuteTime = calendar.getTime();

        //计算calculationDate当天的罚息
//        String calculationDate = (String)sdf.format(executeTime);
        //定时任务应该执行的日期
//        String currentExecuteDate = (String)sdf.format(currentExecuteTime);
//        System.out.println(currentExecuteTime);
        List<HashMap> payScheduleJobList = (List)sysInfoParamDao.qryCurrentDayPayScheduleJobList(executeDate);
        if (payScheduleJobList==null||payScheduleJobList.size()==0){
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }

        //更改系统表账户类操作锁定
        setItemVal("ACCOUNT_LOCK", GlobDict.account_lock.getKey(), "");

        try {
            ((Plms100100) AopContext.currentProxy()).calculateOverdue(executeDate,contractCode);
        } catch (Exception e) {
            setItemVal("ACCOUNT_LOCK", GlobDict.account_unlock.getKey(), "");
            e.printStackTrace();
        }
        setItemVal("ACCOUNT_LOCK", GlobDict.account_unlock.getKey(), "");

    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void calculateOverdue(String currentExecuteDate,String contractCode){
        Map map = new HashMap();
        map.put("currentExecuteDate",currentExecuteDate);
        map.put("contractCode",contractCode);
        List<HashMap> overdueFirstDayRepaymentScheduleDetailList = (List)sysInfoParamDao.qryOverdueRepaymentScheduleDetailListByContractNo(map);
        System.out.println(overdueFirstDayRepaymentScheduleDetailList);
        if (overdueFirstDayRepaymentScheduleDetailList.size()>0){
            for (int i = 0;i < overdueFirstDayRepaymentScheduleDetailList.size();i++){
                HashMap overdueRepaymentScheduleDetail = overdueFirstDayRepaymentScheduleDetailList.get(i);

                Long totalOverDays = (Long)overdueRepaymentScheduleDetail.get("totalOverDays");
                Long serviceFeeTotalOverDays = (Long)overdueRepaymentScheduleDetail.get("serviceFeeTotalOverdueDays");
                Long graceDays = (Long)overdueRepaymentScheduleDetail.get("graceDays");
                Long newOverDueDays = 1L;
                Long newServiceFeeOverDueDays = 1L;

                if (totalOverDays != null && totalOverDays.equals(0)){
                    newOverDueDays = graceDays + 1L;
                }else if (totalOverDays != null && !totalOverDays.equals(0)){
                    newOverDueDays = 1L;
                }

                if (serviceFeeTotalOverDays != null && serviceFeeTotalOverDays.equals(0)){
                    newServiceFeeOverDueDays = graceDays + 1L;
                }else if (serviceFeeTotalOverDays != null && !serviceFeeTotalOverDays.equals(0)){
                    newServiceFeeOverDueDays = 1L;
                }

                BigDecimal principalAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("principal");
                BigDecimal interestAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("interest");
                BigDecimal serviceFeeAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("serviceFee");
                BigDecimal bailAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("bail");

                String overdueMethod = (String)overdueRepaymentScheduleDetail.get("overdueMethod");
                String scheduleDetailCode = (String)overdueRepaymentScheduleDetail.get("code");

                BigDecimal principalOverInterestRate = (BigDecimal)overdueRepaymentScheduleDetail.get("principalOverInterest");
                BigDecimal interestOverInterestRate = (BigDecimal)overdueRepaymentScheduleDetail.get("interestOverInterest");
                BigDecimal serviceFeeInterestRate = (BigDecimal)overdueRepaymentScheduleDetail.get("serviceFeeInterestRate");

                BigDecimal repaymentedPrincipalAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("repaymentedPrincipal");
                BigDecimal repaymentedInterestAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("repaymentedInterest");
                BigDecimal repaymentedServiceFeeAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("repaymentedServiceFee");
                BigDecimal repaymentedBailAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("repaymentedBail");

                int yearPeriod = (int)overdueRepaymentScheduleDetail.get("yearPeriod");

                //逾期金额
                //BigDecimal overdueAmount = new BigDecimal(0);

                BigDecimal serviceFeeOverdueTotal = new BigDecimal(0);
                BigDecimal interestOverdueTotal = new BigDecimal(0);
                BigDecimal principalOverdueTotal = new BigDecimal(0);
                BigDecimal bailOverdueTotal = new BigDecimal(0);


                if (overdueMethod!= null && GlobDict.actual_overdue_simple_interest.getKey().equals(overdueMethod)){
                    if (bailAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedBailAmount.compareTo(new BigDecimal(0))==0){
                        bailOverdueTotal = (bailAmount.multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    if (serviceFeeAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedServiceFeeAmount.compareTo(new BigDecimal(0))==0){
                        serviceFeeOverdueTotal = (serviceFeeAmount.multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }

                    if (interestAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedInterestAmount.compareTo(new BigDecimal(0))==0){
                        interestOverdueTotal = (interestAmount.multiply(interestOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }

                    if (principalAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedPrincipalAmount.compareTo(new BigDecimal(0))==0){
                        principalOverdueTotal = (principalAmount.multiply(principalOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    //逾期金额
                    //overdueAmount = principalAmount;
                    //罚息总额
                    BigDecimal amount = principalOverdueTotal.add(interestOverdueTotal).add(serviceFeeOverdueTotal);
                }
                else if (overdueMethod!= null && GlobDict.actual_overdue_compound_interest.getKey().equals(overdueMethod)){
                    BigDecimal principalOverdue = (BigDecimal)overdueRepaymentScheduleDetail.get("principalOverdue");
                    BigDecimal interestOverdue = (BigDecimal)overdueRepaymentScheduleDetail.get("interestOverdue");
                    BigDecimal serviceFeeInterestOverdue = (BigDecimal)overdueRepaymentScheduleDetail.get("serviceFeeInterestOverdue");
                    BigDecimal bailOverdue = (BigDecimal)overdueRepaymentScheduleDetail.get("bailOverdue");

                    if (bailAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedBailAmount.compareTo(new BigDecimal(0))==0){
                        bailOverdueTotal = (bailAmount.add(bailOverdue).multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    if (serviceFeeAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedServiceFeeAmount.compareTo(new BigDecimal(0))==0){
                        serviceFeeOverdueTotal = ((serviceFeeAmount.add(serviceFeeInterestOverdue)).multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }

                    if (interestAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedInterestAmount.compareTo(new BigDecimal(0))==0){
                        interestOverdueTotal = ((interestAmount.add(interestOverdue)).multiply(interestOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }

                    if (principalAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedPrincipalAmount.compareTo(new BigDecimal(0))==0){
                        principalOverdueTotal = ((principalAmount.add(principalOverdue)).multiply(principalOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    //逾期金额
                    //overdueAmount = principalAmount;
                    //罚息总额
                    BigDecimal amount = principalOverdueTotal.add(interestOverdueTotal).add(serviceFeeOverdueTotal);
                }

                else if (overdueMethod!= null && GlobDict.principal_simple_interest.getKey().equals(overdueMethod)){
                    BigDecimal paidAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("paidAmount");

                    if (bailAmount.compareTo(new BigDecimal(0))==0
                            &&serviceFeeAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedServiceFeeAmount.compareTo(new BigDecimal(0))==0){
                        serviceFeeOverdueTotal = (paidAmount.multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }else if(bailAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedBailAmount.compareTo(new BigDecimal(0))==0){
                        bailOverdueTotal = (paidAmount.multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);

                    }

                    if (principalAmount.compareTo(new BigDecimal(0))==0
                            &&interestAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedInterestAmount.compareTo(new BigDecimal(0))==0){
                        interestOverdueTotal = (paidAmount.multiply(interestOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }else if (principalAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedPrincipalAmount.compareTo(new BigDecimal(0))==0){
                        principalOverdueTotal = (paidAmount.multiply(principalOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    //逾期金额
                    // overdueAmount = principalAmount.add(interestAmount).add(serviceFeeAmount);
                    //罚息总额
                    BigDecimal amount = principalOverdueTotal.add(interestOverdueTotal).add(serviceFeeOverdueTotal);
                }

                else if (overdueMethod!= null && GlobDict.principal_interest_simple_interest.getKey().equals(overdueMethod)){
                    BigDecimal paidAmount = (BigDecimal)overdueRepaymentScheduleDetail.get("paidAmount");

                    if (bailAmount.compareTo(new BigDecimal(0))==0
                            &&serviceFeeAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedServiceFeeAmount.compareTo(new BigDecimal(0))==0){
                        serviceFeeOverdueTotal = ((paidAmount.add(interestAmount)).multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }else if(bailAmount.compareTo(new BigDecimal(0))>0
                        &&repaymentedBailAmount.compareTo(new BigDecimal(0))==0){
                        bailOverdueTotal = ((paidAmount.add(interestAmount).add(bailAmount)).multiply(serviceFeeInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    if (principalAmount.compareTo(new BigDecimal(0))==0
                            &&interestAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedInterestAmount.compareTo(new BigDecimal(0))==0){
                        interestOverdueTotal = ((paidAmount.add(interestAmount)).multiply(interestOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }else if (principalAmount.compareTo(new BigDecimal(0))>0
                            &&repaymentedPrincipalAmount.compareTo(new BigDecimal(0))==0){
                        principalOverdueTotal = ((paidAmount.add(interestAmount)).multiply(principalOverInterestRate)).divide(new BigDecimal(yearPeriod),BigDecimal.ROUND_HALF_UP);
                    }
                    //逾期金额
                    //overdueAmount = principalAmount.add(interestAmount).add(serviceFeeAmount);
                    //罚息总额
                    BigDecimal amount = principalOverdueTotal.add(interestOverdueTotal).add(serviceFeeOverdueTotal);
                }

                //本次逾期金额=单日逾期金额*新增逾期天数
                principalOverdueTotal = principalOverdueTotal.multiply(new BigDecimal(newOverDueDays));
                interestOverdueTotal = interestOverdueTotal.multiply(new BigDecimal(newOverDueDays));
                serviceFeeOverdueTotal = serviceFeeOverdueTotal.multiply(new BigDecimal(newServiceFeeOverDueDays));
                bailOverdueTotal = bailOverdueTotal.multiply(new BigDecimal(newServiceFeeOverDueDays));


                if (principalOverdueTotal.compareTo(new BigDecimal(0))>0){
                    //计算逾期金额
                    BigDecimal overdueAmount = new BigDecimal(0);
                    if (overdueMethod!=null && (GlobDict.actual_overdue_simple_interest.getKey().equals(overdueMethod)||
                            GlobDict.actual_overdue_compound_interest.getKey().equals(overdueMethod))){
                        overdueAmount = principalAmount;
                    }else if (overdueMethod!=null && (GlobDict.principal_simple_interest.getKey().equals(overdueMethod)||
                            GlobDict.principal_interest_simple_interest.getKey().equals(overdueMethod))) {
                        overdueAmount = principalAmount.add(interestAmount);
                    }

                    Map paramMap = new HashMap();
                    paramMap.put("scheduleDetailCode",scheduleDetailCode);
                    paramMap.put("scheduleDetailCode",GlobDict.overdue_subject_principal.getKey());
                    List overdueList = sysInfoParamDao.qryOverdueByScheduleDetailCodeAndSubject(paramMap);
                    String code;
                    if (overdueList!=null&&overdueList.size()>0){
                        Map overdue = (HashMap)overdueList.get(0);
                        code = (String)overdue.get("code");

                        long overdueDays = (int)overdue.get("overdueDays");
                        overdueDays = overdueDays + newOverDueDays;
                        overdue.put("overdueDays",overdueDays);

                        overdue.put("overdueAmount",overdueAmount);

                        overdue.put("amount",principalOverdueTotal);

                        sysInfoParamDao.updateOverdue(overdue);
                    }else{
                        Map overdue = new HashMap();
                        code = getUUID();

                        overdue.put("overdueDays",newOverDueDays);
                        overdue.put("overdueAmount",overdueAmount);
                        overdue.put("amount",principalOverdueTotal);
                        overdue.put("status",GlobDict.valid.getKey());
                        overdue.put("subject",GlobDict.overdue_subject_principal.getKey());
                        overdue.put("scheduleDetailCode",scheduleDetailCode);
                        overdue.put("code",code);

                        sysInfoParamDao.insertOverdue(overdue);
                    }

                    Map overdueDetail = new HashMap();
                    Date now = new Date();

                    overdueDetail.put("overdueAmount",principalOverdueTotal);
                    overdueDetail.put("time",now);
                    overdueDetail.put("overdueCode",code);
                    overdueDetail.put("overdueDays",newOverDueDays);
                    overdueDetail.put("status",GlobDict.valid.getKey());

                    sysInfoParamDao.insertOverdueDetail(overdueDetail);
                }
                if (interestOverdueTotal.compareTo(new BigDecimal(0))>0){
                    //计算逾期金额
                    BigDecimal overdueAmount = interestAmount;
                    Map paramMap = new HashMap();
                    paramMap.put("scheduleDetailCode",scheduleDetailCode);
                    paramMap.put("scheduleDetailCode",GlobDict.overdue_subject_principal.getKey());
                    List overdueList = sysInfoParamDao.qryOverdueByScheduleDetailCodeAndSubject(paramMap);
                    String code;
                    if (overdueList!=null&&overdueList.size()>0){
                        Map overdue = (HashMap)overdueList.get(0);
                        code = (String)overdue.get("code");

                        long overdueDays = (int)overdue.get("overdueDays");
                        overdueDays = overdueDays + newOverDueDays;
                        overdue.put("overdueDays",overdueDays);

                        overdue.put("overdueAmount",overdueAmount);

                        overdue.put("amount",interestOverdueTotal);

                        sysInfoParamDao.updateOverdue(overdue);
                    }else{
                        Map overdue = new HashMap();
                        code = getUUID();

                        overdue.put("overdueDays",newOverDueDays);
                        overdue.put("overdueAmount",overdueAmount);
                        overdue.put("amount",interestOverdueTotal);
                        overdue.put("status",GlobDict.valid.getKey());
                        overdue.put("subject",GlobDict.overdue_subject_interest.getKey());
                        overdue.put("scheduleDetailCode",scheduleDetailCode);
                        overdue.put("code",code);

                        sysInfoParamDao.insertOverdue(overdue);
                    }

                    Map overdueDetail = new HashMap();
                    Date now = new Date();

                    overdueDetail.put("overdueAmount",interestOverdueTotal);
                    overdueDetail.put("time",now);
                    overdueDetail.put("overdueCode",code);
                    overdueDetail.put("overdueDays",newOverDueDays);
                    overdueDetail.put("status",GlobDict.valid.getKey());

                    sysInfoParamDao.insertOverdueDetail(overdueDetail);
                }
                if (serviceFeeOverdueTotal.compareTo(new BigDecimal(0))>0){
                    //计算逾期金额
                    BigDecimal overdueAmount = new BigDecimal(0);
                    overdueAmount = serviceFeeAmount;

                    Map paramMap = new HashMap();
                    paramMap.put("scheduleDetailCode",scheduleDetailCode);
                    paramMap.put("scheduleDetailCode",GlobDict.overdue_subject_principal.getKey());
                    List overdueList = sysInfoParamDao.qryOverdueByScheduleDetailCodeAndSubject(paramMap);
                    String code;
                    if (overdueList!=null&&overdueList.size()>0){
                        Map overdue = (HashMap)overdueList.get(0);
                        code = (String)overdue.get("code");

                        long overdueDays = (int)overdue.get("overdueDays");
                        overdueDays = overdueDays + newOverDueDays;
                        overdue.put("overdueDays",overdueDays);

                        overdue.put("overdueAmount",overdueAmount);

                        overdue.put("amount",serviceFeeOverdueTotal);

                        sysInfoParamDao.updateOverdue(overdue);
                    }else{
                        Map overdue = new HashMap();
                        code = getUUID();

                        overdue.put("overdueDays",newOverDueDays);
                        overdue.put("overdueAmount",overdueAmount);
                        overdue.put("amount",serviceFeeOverdueTotal);
                        overdue.put("status",GlobDict.valid.getKey());
                        overdue.put("subject",GlobDict.overdue_subject_fee.getKey());
                        overdue.put("scheduleDetailCode",scheduleDetailCode);
                        overdue.put("code",code);

                        sysInfoParamDao.insertOverdue(overdue);
                    }

                    Map overdueDetail = new HashMap();
                    Date now = new Date();

                    overdueDetail.put("overdueAmount",serviceFeeOverdueTotal);
                    overdueDetail.put("time",now);
                    overdueDetail.put("overdueCode",code);
                    overdueDetail.put("overdueDays",newServiceFeeOverDueDays);
                    overdueDetail.put("status",GlobDict.valid.getKey());

                    sysInfoParamDao.insertOverdueDetail(overdueDetail);
                }

                if (bailOverdueTotal.compareTo(new BigDecimal(0))>0){
                    //计算逾期金额
                    BigDecimal overdueAmount = new BigDecimal(0);
                    if (overdueMethod!=null && (GlobDict.actual_overdue_simple_interest.getKey().equals(overdueMethod)||
                            GlobDict.actual_overdue_compound_interest.getKey().equals(overdueMethod))){
                        overdueAmount = bailAmount;
                    }else if (overdueMethod!=null && (GlobDict.principal_simple_interest.getKey().equals(overdueMethod)||
                            GlobDict.principal_interest_simple_interest.getKey().equals(overdueMethod))) {
                        overdueAmount = bailAmount.add(serviceFeeAmount);
                    }

                    Map paramMap = new HashMap();
                    paramMap.put("scheduleDetailCode",scheduleDetailCode);
                    paramMap.put("scheduleDetailCode",GlobDict.overdue_subject_principal.getKey());
                    List overdueList = sysInfoParamDao.qryOverdueByScheduleDetailCodeAndSubject(paramMap);
                    String code;
                    if (overdueList!=null&&overdueList.size()>0){
                        Map overdue = (HashMap)overdueList.get(0);
                        code = (String)overdue.get("code");

                        long overdueDays = (int)overdue.get("overdueDays");
                        overdueDays = overdueDays + newOverDueDays;
                        overdue.put("overdueDays",overdueDays);

                        overdue.put("overdueAmount",overdueAmount);

                        overdue.put("amount",serviceFeeOverdueTotal);

                        sysInfoParamDao.updateOverdue(overdue);
                    }else{
                        Map overdue = new HashMap();
                        code = getUUID();

                        overdue.put("overdueDays",newOverDueDays);
                        overdue.put("overdueAmount",overdueAmount);
                        overdue.put("amount",serviceFeeOverdueTotal);
                        overdue.put("status",GlobDict.valid.getKey());
                        overdue.put("subject",GlobDict.overdue_subject_fee.getKey());
                        overdue.put("scheduleDetailCode",scheduleDetailCode);
                        overdue.put("code",code);

                        sysInfoParamDao.insertOverdue(overdue);
                    }

                    Map overdueDetail = new HashMap();
                    Date now = new Date();

                    overdueDetail.put("overdueAmount",serviceFeeOverdueTotal);
                    overdueDetail.put("time",now);
                    overdueDetail.put("overdueCode",code);
                    overdueDetail.put("overdueDays",newServiceFeeOverDueDays);
                    overdueDetail.put("status",GlobDict.valid.getKey());

                    sysInfoParamDao.insertOverdueDetail(overdueDetail);
                }

                Map repaymentScheduleDetail = new HashMap();
                Map paidInfo = new HashMap();
                Map repaymentSchedule = new HashMap();
                Map accountDetail = new HashMap();
                Map account = new HashMap();

                repaymentScheduleDetail = (Map)sysInfoParamDao.qryRepaymentScheduleDetail(scheduleDetailCode);
                System.out.println("repaymentScheduleDetail："+repaymentScheduleDetail);
                String paidCode = (String)repaymentScheduleDetail.get("paidCode");
                paidInfo = (Map)sysInfoParamDao.qryPaidInfo(paidCode);
                String scheduleCode = (String)repaymentScheduleDetail.get("scheduleCode");
                System.out.println("scheduleCode："+scheduleCode);
                repaymentSchedule = (Map)sysInfoParamDao.qryRepaymentSchedule(scheduleCode);
                System.out.println("repaymentSchedule:"+repaymentSchedule);
                String detailCode = (String)repaymentSchedule.get("detailCode");
                System.out.println("detailCode:"+detailCode);
                accountDetail = (Map)sysInfoParamDao.qryAccountDetail(detailCode);
                System.out.println("accountDetail:"+accountDetail);
                String accountCode = (String)accountDetail.get("accountCode");
                account = (Map)sysInfoParamDao.qryAccount(accountCode);

                //应还金额
                //应还金额=原应还金额+应还本金罚息+应还利息罚息+应还服务费违约金+应还保证金违约金
                BigDecimal amount = (BigDecimal)repaymentScheduleDetail.get("amount");
                amount = amount.add(principalOverdueTotal).add(interestOverdueTotal).add(serviceFeeOverdueTotal).add(bailOverdueTotal);
                repaymentScheduleDetail.put("amount",amount);

                //应还罚息
                BigDecimal overdue = (BigDecimal)repaymentScheduleDetail.get("overdue");
                overdue = overdue.add(principalOverdueTotal).add(interestOverdueTotal);
                repaymentScheduleDetail.put("overdue",overdue);

                //应还服务费违约金
                BigDecimal serviceFeePenalty = (BigDecimal)repaymentScheduleDetail.get("serviceFeePenalty");
                serviceFeePenalty = serviceFeePenalty.add(serviceFeeOverdueTotal);
                repaymentScheduleDetail.put("serviceFeePenalty",serviceFeePenalty);

                //应还保证金违约金
                BigDecimal bailPenalty = (BigDecimal)repaymentScheduleDetail.get("bailPenalty");
                bailPenalty = bailPenalty.add(bailOverdueTotal);
                repaymentScheduleDetail.put("bailPenalty",bailPenalty);

                //本息累计逾期天数
                long totalOverdueDays = (long)repaymentScheduleDetail.get("totalOverdueDays");
                totalOverdueDays = totalOverdueDays + newOverDueDays;
                repaymentScheduleDetail.put("totalOverdueDays",totalOverdueDays);

                //服务费累计逾期天数
                long serviceFeeTotalOverdueDays = (long)repaymentScheduleDetail.get("serviceFeeTotalOverdueDays");
                serviceFeeTotalOverdueDays = serviceFeeTotalOverdueDays + newServiceFeeOverDueDays;
                repaymentScheduleDetail.put("serviceFeeTotalOverdueDays",serviceFeeTotalOverdueDays);

                //状态“逾期”
                repaymentScheduleDetail.put("status",GlobDict.repayment_schedule_detail_status_overdue.getKey());

                sysInfoParamDao.updateRepaymentScheduleDetail(repaymentScheduleDetail);

                //更新放款信息表
                //应还罚息
                //应还罚息 = 原应还罚息 + 应还本金罚息+应还利息罚息
                BigDecimal paidInfoOverdue = (BigDecimal)paidInfo.get("overdue");
                paidInfoOverdue = paidInfoOverdue.add(principalOverdueTotal).add(interestOverdueTotal);
                paidInfo.put("overdue",paidInfoOverdue);

                //应还服务费违约金
                //应还服务费违约金 = 原应还服务费违约金 + 应还服务费违约金
                BigDecimal paidInfoServiceFeePenalty = (BigDecimal)paidInfo.get("serviceFeePenalty");
                paidInfoServiceFeePenalty = paidInfoServiceFeePenalty.add(serviceFeePenalty);
                paidInfo.put("serviceFeePenalty",paidInfoServiceFeePenalty);

                //应还保证金违约金
                //应还保证金违约金 = 原应还保证金违约金  + 应还保证金违约金
                BigDecimal paidInfoBailPenalty = (BigDecimal)paidInfo.get("paidInfoBailPenalty");
                paidInfoBailPenalty = paidInfoBailPenalty.add(bailPenalty);
                paidInfo.put("paidInfoBailPenalty",paidInfoBailPenalty);

                //状态“逾期”
                paidInfo.put("status",GlobDict.paid_info_status_paying_and_not_overdue.getKey());

                sysInfoParamDao.updatePaidInfo(paidInfo);

                //更新还款计划表
                //应还金额
                //应还金额=原应还金额+应还本金罚息+应还利息罚息+应还服务费违约金+应还保证金违约金
                BigDecimal repaymentScheduleAmount = (BigDecimal)repaymentSchedule.get("amount");
                repaymentScheduleAmount = repaymentScheduleAmount.add(principalOverdueTotal).add(interestOverdueTotal).add(serviceFeeOverdueTotal).add(bailOverdueTotal);
                repaymentSchedule.put("amount",repaymentScheduleAmount);

                //应还罚息
                BigDecimal repaymentScheduleOverdue = (BigDecimal)repaymentSchedule.get("overdue");
                repaymentScheduleOverdue = repaymentScheduleOverdue.add(principalOverdueTotal).add(interestOverdueTotal);
                repaymentSchedule.put("overdue",repaymentScheduleOverdue);

                //应还服务费违约金
                BigDecimal repaymentScheduleServiceFeePenalty = (BigDecimal)repaymentScheduleDetail.get("serviceFeePenalty");
                repaymentScheduleServiceFeePenalty = repaymentScheduleServiceFeePenalty.add(serviceFeeOverdueTotal);
                repaymentSchedule.put("serviceFeePenalty",repaymentScheduleServiceFeePenalty);

                //应还保证金违约金
                BigDecimal repaymentBailPenalty = (BigDecimal)repaymentScheduleDetail.get("bailPenalty");
                repaymentBailPenalty = repaymentBailPenalty.add(bailOverdueTotal);
                repaymentSchedule.put("bailPenalty",repaymentBailPenalty);

                //状态“逾期”
                repaymentSchedule.put("status", GlobDict.repayment_schedule_status_overdue.getKey());

                sysInfoParamDao.updateRepaymentSchedule(repaymentSchedule);

                //更新账户明细表
                //应还罚息
                //应还罚息 = 原应还罚息 + 应还本金罚息+应还利息罚息
                BigDecimal accountDetailOverdue = (BigDecimal)accountDetail.get("overdue");
                accountDetailOverdue = accountDetailOverdue.add(principalOverdueTotal).add(interestOverdueTotal);
                accountDetail.put("overdue",accountDetailOverdue);


                //应还服务费违约金
                //应还服务费违约金 = 原应还服务费违约金 + 应还服务费违约金
                BigDecimal accountDetailServiceFeePenalty = (BigDecimal)accountDetail.get("serviceFeePenalty");
                accountDetailServiceFeePenalty = accountDetailServiceFeePenalty.add(serviceFeeOverdueTotal);
                accountDetail.put("serviceFeePenalty",accountDetailServiceFeePenalty);

                //应还保证金违约金
                //应还保证金违约金 = 原应还保证金违约金 + 应还保证金违约金
                BigDecimal accountDetailBailPenalty = (BigDecimal)accountDetail.get("bailPenalty");
                accountDetailBailPenalty = accountDetailBailPenalty.add(bailOverdueTotal);
                accountDetail.put("bailPenalty",accountDetailBailPenalty);

                //状态“逾期”
                //accountDetail.put("status",GlobDict.account_detail_status_overdue.getKey());

                sysInfoParamDao.updateAccountDetail(accountDetail);

                //更新账户汇总表
                //应还罚息
                //应还罚息 = 原应还罚息 + 应还本金罚息+应还利息罚息
                BigDecimal accountOverdue = (BigDecimal)account.get("overdue");
                accountOverdue = accountOverdue.add(principalOverdueTotal).add(interestOverdueTotal);
                account.put("overdue",accountOverdue);


                //应还服务费违约金
                //应还服务费违约金 = 原应还服务费违约金 + 应还服务费违约金
                BigDecimal accountServiceFeePenalty = (BigDecimal)account.get("serviceFeePenalty");
                accountServiceFeePenalty = accountServiceFeePenalty.add(serviceFeeOverdueTotal);
                account.put("serviceFeePenalty",accountServiceFeePenalty);

                //应还保证金违约金
                //应还保证金违约金 = 原应还保证金违约金 + 应还保证金违约金
                BigDecimal accountBailPenalty = (BigDecimal)account.get("bailPenalty");
                accountBailPenalty = accountBailPenalty.add(bailOverdueTotal);
                account.put("bailPenalty",accountBailPenalty);

                //状态“逾期”
                //account.put("status",GlobDict.account_status_overdue.getKey());

                sysInfoParamDao.updateAccount(account);
            }
        }
    }
}
