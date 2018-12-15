package com.vilio.plms.service.base;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.Plms100100;
import com.vilio.plms.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

/**
 * Created by dell on 2017/7/20.
 */
@Service("commonService")
public class CommonService {
    private static final Logger logger = Logger.getLogger(CommonService.class);
    //还款模型 -  利息宏获统一代收
    private static final int repayModel_honghuo_agency_Interest = 1;
    //还款模型 -  利息还给资方，宏获一次性收取服务费
    private static final int repayModel_honghuo_single_service_fee = 2;
    //还款模型 -  利息还给资方，服务费还给宏获(分次给)
    private static final int repayModel_honghuo_period_service_fee = 3;

    @Resource
    PlmsContractInfoDao plmsContractInfoDao;
    @Resource
    PlmsRepaymentDateDao plmsRepaymentDateDao;
    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    private CommDao commDao;
    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;
    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;
    @Resource
    AccountDetailDao accountDetailDao;
    @Resource
    CityDao cityDao;
    @Resource
    UserGovernCityDao userGovernCityDao;
    @Resource
    DistributorDao distributorDao;
    @Resource
    private RemoteUmService remoteUmService;
    @Resource
    UserDistributorDao userDistributorDao;

    @Resource
    PlmsFundSideDao plmsFundSideDao;
    @Resource
    LoginInfoDao loginInfoDao;

    @Resource
    RollBackPaymentAndOverdueService rollBackPaymentAndOverdueService;
    @Resource
    BorrowApplyDao borrowApplyDao;
    @Resource
    IouDao iouDao;
    @Resource
    AccountDao accountDao;
    @Resource
    Plms100100 plms100100;
    @Resource
    private EmailInfoDao emailInfoDao;
    @Resource
    private PaidInfoDao paidInfoDao;


    /**
     * 应还利息、应还服务费和应还保证金计算
     *
     * @param paidAmount    放款金额
     * @param paidDate      放款日期
     * @param borrowEndDate 借款到期日
     * @param contractCode  合同编号
     * @return
     * @throws Exception
     */
    /*
      返回：Map
      1.repayModel  还款模型(int)（1 利息宏获统一代收；2 利息还给资方，宏获一次性收取服务费；3 利息还给资方，服务费还给宏获）
      2.principal  本金(BigDecimal)
      3.totalInterest 应还利息总额(BigDecimal)
      4.dayInterest 日平均应还利息(BigDecimal)
      5.monthAvgInterest 月平均应还利息(BigDecimal)
      6.totalServiceFee 应还服务费总额(BigDecimal)
      7.dayServiceFee 日平均应还服务费(BigDecimal)
      8.monthAvgServiceFee 月平均应还服务费(BigDecimal)
      9.bigBail 应还保证金(BigDecimal)
    */
    public Map calculateInterestForPaidinfo(BigDecimal paidAmount, Date paidDate, Date borrowEndDate, String contractCode) throws Exception {
        Map resultMap = new HashedMap();
        //校验参数
        if (StringUtils.isBlank((contractCode)) || null == borrowEndDate || null == paidDate || null == paidAmount) {
            logger.info("contractCode:"+contractCode+",borrowEndDate:"+borrowEndDate+",paidDate:"+paidDate+",paidAmount:"+paidAmount);
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "传入参数为空！");
        }

        //放款金额
        BigDecimal bigLoanAmount = new BigDecimal(0);
        if (paidAmount != null) {
            bigLoanAmount = paidAmount;
        }
        //放款日期(yyyy-MM-dd)
        String strPaidTime = DateUtil.formatDateTime(paidDate, DateUtil.DATE_PATTERN2);
        //借款到期日(yyyy-MM-dd)
        String strBorrowEndDate = DateUtil.formatDateTime(borrowEndDate, DateUtil.DATE_PATTERN2);

        /** 获取系统参数（年化利率、年模型天数、押息期数） **/
        Map contractMap = plmsContractInfoDao.getProductInfoAndInterest(contractCode);
        if (null == contractMap) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "没有该进件产品信息！");
        }
        if (!GlobDict.product_repayment_methods_first_interest.getKey().equals(contractMap.get("repaymentMethods"))) {
            logger.info("contractCode=" + contractCode + "对应的产品还款方式不是先息后本");
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "不是先息后本");
        }
        //年模型天数
        BigDecimal bigYearPeriod = new BigDecimal((Integer) contractMap.get("yearPeriod"));
        //押息期数
        BigDecimal bigMortgageInterestPeriod = new BigDecimal((Long) contractMap.get("mortgageInterestPeriod"));
        if (null == bigMortgageInterestPeriod) {
            bigMortgageInterestPeriod = new BigDecimal(0);
        }
        //年化利率
        BigDecimal bigAnnualizedInterest = (BigDecimal) contractMap.get("annualizedInterest");        //息差是否一次性收取
        Boolean singleServiceCharge = false;
        if (Integer.parseInt(GlobDict.product_is_spread_one_time_yes.getKey()) == (int) contractMap.get("isSpreadOneTime")) {
            singleServiceCharge = true;
        }
        //计息周期
        String interestCycle = (String) contractMap.get("interestCycle");
        //利息是否代收代付
        Boolean isInterestInstead = false;
        if (Integer.parseInt(GlobDict.apply_interesting_is_interest_Instead_yes.getKey()) == (int) contractMap.get("isInterestInstead")) {
            isInterestInstead = true;
        }

        /** 获取资方信息 **/
        Map fundSideMap = plmsFundSideDao.queryFundSideAndDistribute(contractCode);
        if (null == fundSideMap) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "没有该资方信息！");
        }
        //资方年模型
        BigDecimal bigFundSideYearPeriod = new BigDecimal(0);
        if (fundSideMap.get("yearPeriod") != null) {
            String yearPeriodString = String.valueOf(fundSideMap.get("yearPeriod"));
            bigFundSideYearPeriod = new BigDecimal(yearPeriodString);
        }
        //资方年化利率
        BigDecimal bigFundSideAnnualizedInterest = new BigDecimal(0);
        if (fundSideMap.get("actualInterest") != null) {
            String actualInterestString = String.valueOf(fundSideMap.get("actualInterest"));
            bigFundSideAnnualizedInterest = new BigDecimal(actualInterestString);
        }

        //计算计息天数（实际放款天数）
        int useDays = 0;
        try {
            useDays = DateUtil.dateCompare(strPaidTime, strBorrowEndDate, DateUtil.DATE_PATTERN2) + 1;
        } catch (ParseException e) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "计息天数计算异常！");
        }
        BigDecimal bigUseDays = new BigDecimal(useDays);

        //判断还款模型
        int repayModel = 0;
        if (isInterestInstead) {
            repayModel = CommonService.repayModel_honghuo_agency_Interest;
        } else {
            if (singleServiceCharge) {
                repayModel = CommonService.repayModel_honghuo_single_service_fee;
            } else {
                repayModel = CommonService.repayModel_honghuo_period_service_fee;
            }
        }
        //根据还款模型计算 总利息、日息、月息、服务费、日服务费、月服务费
        //应还利息总额
        BigDecimal bigTotalInterest = new BigDecimal(0);
        //日应还平均利息
        BigDecimal bigDayInterest = new BigDecimal(0);
        //月应还平均利息
        BigDecimal bigMonthAvgInterest = new BigDecimal(0);
        //应还服务费总额
        BigDecimal bigTotalServiceFee = new BigDecimal(0);
        //月应还平均服务费
        BigDecimal bigMonthAvgServiceFee = new BigDecimal(0);
        //日应还平均服务费
        BigDecimal bigDayServiceFee = new BigDecimal(0);

        if (repayModel == CommonService.repayModel_honghuo_agency_Interest) {
            //宏获代收利息
            bigTotalInterest = bigLoanAmount.multiply(bigAnnualizedInterest).multiply(bigUseDays).divide(bigYearPeriod, 6, RoundingMode.HALF_DOWN);
            bigDayInterest = bigTotalInterest.divide(bigUseDays, 6, RoundingMode.HALF_DOWN);
            bigMonthAvgInterest = bigLoanAmount.multiply(bigAnnualizedInterest).divide(new BigDecimal(12), 6, RoundingMode.HALF_DOWN);
        } else if (repayModel == CommonService.repayModel_honghuo_single_service_fee) {
            //利息给资方，宏获一次性收完服务费
            bigTotalInterest = bigLoanAmount.multiply(bigFundSideAnnualizedInterest).multiply(bigUseDays).divide(bigFundSideYearPeriod, 6, RoundingMode.HALF_DOWN);
            bigDayInterest = bigTotalInterest.divide(bigUseDays, 6, RoundingMode.HALF_DOWN);
            bigMonthAvgInterest = bigLoanAmount.multiply(bigFundSideAnnualizedInterest).divide(new BigDecimal(12), 6, RoundingMode.HALF_DOWN);
            bigTotalServiceFee = bigLoanAmount.multiply(bigAnnualizedInterest).multiply(bigUseDays).divide(bigYearPeriod, 6, RoundingMode.HALF_DOWN).subtract(
                    bigLoanAmount.multiply(bigFundSideAnnualizedInterest).multiply(bigUseDays).divide(bigFundSideYearPeriod, 6, RoundingMode.HALF_DOWN)
            );
        } else if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
            //利息给资方，宏获按周期收取服务费
            System.out.print("bigLoanAmount:" + bigLoanAmount + "bigFundSideAnnualizedInterest:" + bigFundSideAnnualizedInterest + "bigUseDays:" + bigUseDays + "bigFundSideYearPeriod:" + bigFundSideYearPeriod);
            bigTotalInterest = bigLoanAmount.multiply(bigFundSideAnnualizedInterest).multiply(bigUseDays).divide(bigFundSideYearPeriod, 6, RoundingMode.HALF_DOWN);
            bigDayInterest = bigTotalInterest.divide(bigUseDays, 6, RoundingMode.HALF_DOWN);
            bigMonthAvgInterest = bigLoanAmount.multiply(bigFundSideAnnualizedInterest).divide(new BigDecimal(12), 6, RoundingMode.HALF_DOWN);
            bigTotalServiceFee = bigLoanAmount.multiply(bigAnnualizedInterest).multiply(bigUseDays).divide(bigYearPeriod, 6, RoundingMode.HALF_DOWN).subtract(
                    bigLoanAmount.multiply(bigFundSideAnnualizedInterest).multiply(bigUseDays).divide(bigFundSideYearPeriod, 6, RoundingMode.HALF_DOWN)
            );
            bigDayServiceFee = bigTotalServiceFee.divide(bigUseDays, 6, RoundingMode.HALF_DOWN);
            bigMonthAvgServiceFee = bigLoanAmount.multiply(bigAnnualizedInterest.subtract(bigFundSideAnnualizedInterest)).divide(new BigDecimal(12), 6, RoundingMode.HALF_DOWN);
        }

        //应还保证金
        BigDecimal bigBail = new BigDecimal(0);
        if (bigMortgageInterestPeriod.compareTo(new BigDecimal("0")) > 0) {
            bigBail = bigLoanAmount.multiply(bigAnnualizedInterest).multiply(bigMortgageInterestPeriod).divide(new BigDecimal(12), 6, RoundingMode.HALF_DOWN);
        }

        resultMap.put("repayModel", repayModel);
        resultMap.put("principal", bigLoanAmount);
        resultMap.put("totalInterest", bigTotalInterest);
        resultMap.put("dayInterest", bigDayInterest);
        resultMap.put("monthAvgInterest", bigMonthAvgInterest);
        resultMap.put("totalServiceFee", bigTotalServiceFee);
        resultMap.put("dayServiceFee", bigDayServiceFee);
        resultMap.put("monthAvgServiceFee", bigMonthAvgServiceFee);
        resultMap.put("bigBail", bigBail);
        return resultMap;
    }

    /**
     * 创建该笔放款的还款计划明细
     *
     * @param borrowEndDate 借款到期日
     * @param paidCode      放款信息表code
     * @return
     * @throws Exception
     */
    public Map buildRepaymentScheduleDetail(Date borrowEndDate, String paidCode) throws Exception {
        Map resultMap = new HashedMap();
        //校验参数
        if (null == borrowEndDate || StringUtils.isBlank(paidCode)) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "传入参数为空！");
        }

        //检验是否已经生成还款计划明细，避免重新生成
        List<PlmsRepaymentScheduleDetail> scheduleDetailList = plmsRepaymentScheduleDetailDao.queryScheduleDetailBeanByPaidCode(paidCode);
        if (null != scheduleDetailList && scheduleDetailList.size() > 0) {
            logger.info("放款paidCode=" + paidCode + "已经生成还款计划明细，不重复生成！");
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "未找到paidCode=" + paidCode + "对应的放款信息！");
        }
        /** 获取放款信息 **/
        Map paidInfoMap = plmsPaidInfoDao.getPaidInfoAndContractByPaidCode(paidCode);
        if (null == paidInfoMap) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "未找到paidCode=" + paidCode + "对应的放款信息！");
        }
        //借款到期日(yyyy-MM-dd)
        String strBorrowEndDate = DateUtil.formatDateTime(borrowEndDate, DateUtil.DATE_PATTERN2);
        //合同信息
        String contractCode = (String) paidInfoMap.get("contractCode");
        //放款金额
        BigDecimal bigLoanAmount = (BigDecimal) paidInfoMap.get("paidAmount");
        //放款日期
        Date paidTime = (Date) paidInfoMap.get("paidTime");
        //放款日期(yyyy-MM-dd)
        String strPaidTime = DateUtil.formatDateTime(paidTime, DateUtil.DATE_PATTERN2);

        /** 获取系统参数（年化利率、年模型天数、押息期数） **/
        Map contractMap = plmsContractInfoDao.getProductInfoAndInterest(contractCode);
        if (null == contractMap) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "没有该进件产品信息！");
        }
        if (!GlobDict.product_repayment_methods_first_interest.getKey().equals(contractMap.get("repaymentMethods"))) {
            logger.info("放款paidCode=" + paidCode + "对应的产品还款方式不是先息后本");
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "不是先息后本");
        }

        //收息方式
        String interestCollectionMethod = (String) contractMap.get("interestCollectionMethod");
        //放款天数计算规则 01、算头算尾;02、算头不算尾
        String paidDaysComputationalRule = (String) contractMap.get("paidDaysComputationalRule");
        //计息周期
        String interestCycle = (String) contractMap.get("interestCycle");

        //本金归还日
        Date repayPrincipalDate = null;
        if (GlobDict.product_paid_days_computational_rule_only_begin.getKey().equals(paidDaysComputationalRule)) {
            //算头不算尾
            String strRepayPrincipalDate = DateUtil.getNextDay(DateUtil.formatDateTime(borrowEndDate, DateUtil.DATE_PATTERN));
            repayPrincipalDate = DateUtil.parseDateTimeForPattern(strRepayPrincipalDate, DateUtil.DATE_PATTERN);
        } else {
            repayPrincipalDate = borrowEndDate;
        }

        /** 获取所有还款日 **/
        Map paramMap = new HashedMap();
        paramMap.put("paidTime", paidTime);
        paramMap.put("contractCode", contractCode);
        paramMap.put("borrowEndDate", repayPrincipalDate);
        List<PlmsRepaymentDate> repaymentDateList = plmsRepaymentDateDao.getProductInfoAndInterest(paramMap);
        /*if(null == repaymentDateList || repaymentDateList.size() < 1){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "未找到该合同contractCode=" + contractCode + "对应的还款日列表！");
        }*/

        boolean principalDateIsFixeRepaymentDate = false;//本金归还日是否是是固定还款日（本金归还日是否在固定还款日表里）

        //查询的最后还款日是否是本金归还日，如果不是加上本金归还日
        if (null == repaymentDateList || repaymentDateList.size() < 1) {
            repaymentDateList = new ArrayList();
            PlmsRepaymentDate repaymentDate = new PlmsRepaymentDate();
            repaymentDate.setRepaymentDate(repayPrincipalDate);
            repaymentDateList.add(repaymentDate);
        } else {
            Date lastRepaymentDate = repaymentDateList.get(repaymentDateList.size() - 1).getRepaymentDate();
            if (!DateUtil.formatDateTime(lastRepaymentDate, DateUtil.DATE_PATTERN).equals(DateUtil.formatDateTime(repayPrincipalDate, DateUtil.DATE_PATTERN))) {
                PlmsRepaymentDate repaymentDate = new PlmsRepaymentDate();
                repaymentDate.setRepaymentDate(repayPrincipalDate);
                repaymentDateList.add(repaymentDate);
            } else {
                principalDateIsFixeRepaymentDate = true;
            }

        }

        //应还利息、应还服务费和应还保证金计算
        Map returnInterestMap = calculateInterestForPaidinfo(bigLoanAmount, paidTime, borrowEndDate, contractCode);
        if (null == returnInterestMap) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "该合同contractCode=" + contractCode + "计算出问题！");
        }
        int repayModel = (int) returnInterestMap.get("repayModel");
        BigDecimal totalInterest = (BigDecimal) returnInterestMap.get("totalInterest");
        BigDecimal dayInterest = (BigDecimal) returnInterestMap.get("dayInterest");
        BigDecimal monthAvgInterest = (BigDecimal) returnInterestMap.get("monthAvgInterest");
        BigDecimal totalServiceFee = (BigDecimal) returnInterestMap.get("totalServiceFee");
        BigDecimal dayServiceFee = (BigDecimal) returnInterestMap.get("dayServiceFee");
        BigDecimal monthAvgServiceFee = (BigDecimal) returnInterestMap.get("monthAvgServiceFee");
        BigDecimal bail = (BigDecimal) returnInterestMap.get("bigBail");

        /* *
        生成还款计划明细
         *  */
        scheduleDetailList = new ArrayList();
        int length = repaymentDateList.size();
        for (int i = 0; i <= length; i++) {
            PlmsRepaymentScheduleDetail detail = new PlmsRepaymentScheduleDetail();
            //应还本金
            BigDecimal principal = new BigDecimal(0);
            if (i == length) {
                //到期日，归还本金
                principal = (BigDecimal) returnInterestMap.get("principal");
            }
            //应还利息
            BigDecimal interest = new BigDecimal(0);

            //应还服务费
            BigDecimal bigServiceFee = new BigDecimal(0);
            //应还保证金
            BigDecimal bigBail = new BigDecimal(0);
            /* 是否前置收息 */
            if (interestCollectionMethod.equals(GlobDict.interest_collection_method_preposition.getKey())) {
                //前置收息
                if (i == 0) {
                    //放款日
                    //判断是否间隔为一个月
                    boolean isOneMonth = false;
                    // 第1期利息
                    Date firstRepaymentDate = repaymentDateList.get(i).getRepaymentDate();
                    String strFirstRepaymentDate = DateUtil.formatDateTime(firstRepaymentDate, DateUtil.DATE_PATTERN2);
                    //计算一个月后的计算时间 yyyyMMdd
                    String nextMonthDate = DateUtil.getDayForSetMonth(paidTime, 1);
                    if (nextMonthDate.equals(DateUtil.formatDateTime(firstRepaymentDate, DateUtil.DATE_PATTERN))) {
                        isOneMonth = true;
                    }

                    //计算计息天数
                    int useDays = 0;
                    if (length == 1) {
                        useDays = DateUtil.dateCompare(strPaidTime, strBorrowEndDate, DateUtil.DATE_PATTERN2) + 1;
                    } else {
                        useDays = DateUtil.dateCompare(strPaidTime, strFirstRepaymentDate, DateUtil.DATE_PATTERN2);
                    }
                    BigDecimal bigUseDays = new BigDecimal(useDays);

                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(bigUseDays);
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && isOneMonth) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(bigUseDays);
                    }
                    //保证金
                    bigBail = bail;
                    if (repayModel == CommonService.repayModel_honghuo_single_service_fee) {
                        //服务费
                        bigServiceFee = totalServiceFee;
                    } else if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第1期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(bigUseDays);
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && isOneMonth) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(bigUseDays);
                        }
                    }
                } else if (i == (length - 1)) {
                    //第N期利息 + 第N期服务费
                    Date startDate = repaymentDateList.get(length - 2).getRepaymentDate();
                    String strStartDate = DateUtil.formatDate2(startDate);
                    Date endDate = borrowEndDate;
                    String strEndDate = DateUtil.formatDate2(endDate);

                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && principalDateIsFixeRepaymentDate) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                    }

                    if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第N期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && principalDateIsFixeRepaymentDate) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                        }
                    }

                } else if (i != length) {
                    //i 期
                    //第i+1期利息 + 第i+1期服务费
                    Date startDate = repaymentDateList.get(i - 1).getRepaymentDate();
                    String strStartDate = DateUtil.formatDate2(startDate);
                    Date endDate = repaymentDateList.get(i).getRepaymentDate();
                    String strEndDate = DateUtil.formatDate2(endDate);

                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey())) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                    }

                    if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第i+1期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey())) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                        }
                    }
                }
            } else {
                //固定日收息
                if (i == 0 && repayModel != CommonService.repayModel_honghuo_single_service_fee && bail.compareTo(new BigDecimal(0)) <= 0) {
                    continue;
                } else if (i == 0) {
                    //放款日
                    //保证金
                    bigBail = bail;
                    if (repayModel == CommonService.repayModel_honghuo_single_service_fee) {
                        //服务费
                        bigServiceFee = totalServiceFee;
                    }
                } else if (i == 1) {
                    //1 期
                    //第1期利息 + 第1期服务费
                    Date firstRepaymentDate = repaymentDateList.get(i - 1).getRepaymentDate();
                    String strFirstRepaymentDate = DateUtil.formatDateTime(firstRepaymentDate, DateUtil.DATE_PATTERN2);
                    //判断是否间隔为一个月
                    boolean isOneMonth = false;
                    //计算一个月后的计算时间 yyyyMMdd
                    String nextMonthDate = DateUtil.getDayForSetMonth(paidTime, 1);
                    if (nextMonthDate.equals(DateUtil.formatDateTime(firstRepaymentDate, DateUtil.DATE_PATTERN))) {
                        isOneMonth = true;
                    }

                    //计算计息天数
                    int useDays = 0;
                    if (length == 1) {
                        useDays = DateUtil.dateCompare(strPaidTime, strBorrowEndDate, DateUtil.DATE_PATTERN2) + 1;
                    } else {
                        useDays = DateUtil.dateCompare(strPaidTime, strFirstRepaymentDate, DateUtil.DATE_PATTERN2);
                    }
                    BigDecimal bigUseDays = new BigDecimal(useDays);
                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(bigUseDays);
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && isOneMonth) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(bigUseDays);
                    }

                    if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第1期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(bigUseDays);
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && isOneMonth) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(bigUseDays);
                        }
                    }
                } else if (i == length) {
                    //本金归还日
                    Date startDate = repaymentDateList.get(length - 2).getRepaymentDate();
                    String strStartDate = DateUtil.formatDate2(startDate);
                    Date endDate = borrowEndDate;
                    String strEndDate = DateUtil.formatDate2(endDate);

                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && principalDateIsFixeRepaymentDate) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                    }

                    if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第N期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey()) && principalDateIsFixeRepaymentDate) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2) + 1));
                        }
                    }

                } else {
                    //i 期
                    //第i期利息 + 第i期服务费
                    Date startDate = repaymentDateList.get(i - 2).getRepaymentDate();
                    String strStartDate = DateUtil.formatDate2(startDate);
                    Date endDate = repaymentDateList.get(i - 1).getRepaymentDate();
                    ;
                    String strEndDate = DateUtil.formatDate2(endDate);

                    if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                    } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey())) {
                        interest = monthAvgInterest;
                    } else {
                        interest = dayInterest.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                    }

                    if (repayModel == CommonService.repayModel_honghuo_period_service_fee) {
                        //第i期服务费
                        if (interestCycle.equals(GlobDict.product_interest_cycle_day.getKey())) {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                        } else if (interestCycle.equals(GlobDict.product_interest_cycle_month.getKey())) {
                            bigServiceFee = monthAvgServiceFee;
                        } else {
                            bigServiceFee = dayServiceFee.multiply(new BigDecimal(DateUtil.dateCompare(strStartDate, strEndDate, DateUtil.DATE_PATTERN2)));
                        }
                    }
                }
            }
            if (principal.add(interest).add(bigServiceFee).add(bigBail).compareTo(new BigDecimal(0)) <= 0) {
                //没有要生成的还款计划明细
                continue;
            }
            //还款日期
            Date repayDate = null;
            if (i == 0) {
                repayDate = DateUtil.parseDateTimeForPattern(strPaidTime, DateUtil.DATE_PATTERN2);
            } else {
                PlmsRepaymentDate repaymentDateBean = repaymentDateList.get(i - 1);
                repayDate = repaymentDateBean.getRepaymentDate();
            }

            detail.setCode(getSeq(GlobParam.SEQUENCE_SERIAL_NO, 6));
            detail.setRepaymentDate(repayDate);
            detail.setAmount(principal.add(interest).add(bigServiceFee).add(bigBail));
            detail.setPrincipal(principal);
            detail.setInterest(interest);
            detail.setServiceFee(bigServiceFee);
            detail.setBail(bigBail);
            detail.setServiceFeePenalty(new BigDecimal(0));
            detail.setOverdue(new BigDecimal(0));
            detail.setRepaymentedPrincipal(new BigDecimal(0));
            detail.setRepaymentedInterest(new BigDecimal(0));
            detail.setRepaymentedOverdue(new BigDecimal(0));
            detail.setRepaymentedServiceFee(new BigDecimal(0));
            detail.setRepaymentedServiceFeePenalty(new BigDecimal(0));
            detail.setCreateDate(new Date());
            detail.setModifyDate(new Date());
            detail.setStatus(GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getKey());
            //放款信息表code
            detail.setPaidCode(paidCode);

            scheduleDetailList.add(detail);
        }

        //入库

        PaidInfo paidInfo = new PaidInfo();
        paidInfo.setCode(paidCode);
        paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfo);
        AccountDetail accountDetail = accountDetailDao.queryAccountDetailByContractCode(contractCode);
        String accountCode = accountDetail.getAccountCode();
        Account account = new Account();
        account.setCode(accountCode);
        account = accountDao.qryAccount(account);

        BigDecimal paidInfoInterestAmount = new BigDecimal(paidInfo.getInterest());
        BigDecimal paidInfoServiceFeeAmount = new BigDecimal(paidInfo.getServiceFee());

        BigDecimal accountDetailInterestAmount = new BigDecimal(accountDetail.getInterest());
        BigDecimal accountDetailServiceFeeAmount = new BigDecimal(accountDetail.getServiceFee());

        BigDecimal accountInterestAmount = new BigDecimal(account.getInterest());
        BigDecimal accountServiceFeeAmount = new BigDecimal(account.getServiceFee());

        for (int i = 0; i < scheduleDetailList.size(); i++) {
            PlmsRepaymentScheduleDetail detail = scheduleDetailList.get(i);
            //还款计划表code
            Map map = new HashMap();
            map.put("contractCode", contractCode);
            map.put("repaymentDate", detail.getRepaymentDate());
            map.put("overDue", new BigDecimal(0));
            map.put("serviceFee", detail.getServiceFee());
            map.put("principal", detail.getPrincipal());
            map.put("interest", detail.getInterest());
            map.put("bail", detail.getBail());
            map.put("bailPenalty", new BigDecimal(0));
            map.put("serviceFeePenalty", new BigDecimal(0));
            map.put("repaymentedPrincipal", new BigDecimal(0));
            map.put("repaymentedInterest", new BigDecimal(0));
            map.put("repaymentedOverdue", new BigDecimal(0));
            map.put("repaymentedServiceFee", new BigDecimal(0));
            map.put("repaymentedServiceFeePenalty", new BigDecimal(0));
            map.put("repaymentedBail", new BigDecimal(0));
            map.put("repaymentedBailPenalty", new BigDecimal(0));
            map.put("totalOverdueDays", 0);
            map.put("serviceFeeTotalOverdueDays", 0);
            map = updateSchedule(map);
            BigDecimal interest = detail.getInterest();
            BigDecimal serviceFee = detail.getServiceFee();

            paidInfoInterestAmount =  paidInfoInterestAmount.add(interest);
            paidInfoServiceFeeAmount = paidInfoServiceFeeAmount.add(serviceFee);

            accountDetailInterestAmount =  accountDetailInterestAmount.add(interest);
            accountDetailServiceFeeAmount = accountDetailServiceFeeAmount.add(serviceFee);

            accountInterestAmount =  accountInterestAmount.add(interest);
            accountServiceFeeAmount = accountServiceFeeAmount.add(serviceFee);

            detail.setScheduleCode((String) map.get("code"));
            detail.setTotalPeriod(scheduleDetailList.size());
            detail.setCurrentPeriod(i + 1);
            plmsRepaymentScheduleDetailDao.saveRepaymentScheduleDetail(detail);
        }
        paidInfo.setInterest(paidInfoInterestAmount.toString());
        paidInfo.setServiceFee(paidInfoServiceFeeAmount.toString());
        accountDetail.setInterest(accountDetailInterestAmount.toString());
        accountDetail.setServiceFee(accountDetailServiceFeeAmount.toString());
        account.setInterest(accountInterestAmount.toString());
        account.setServiceFee(accountServiceFeeAmount.toString());

        plmsPaidInfoDao.updatePaidInfoByCodeOriginalData(paidInfo);
        accountDetailDao.updateAccountDetailByCodeOriginalData(accountDetail);
        accountDao.updateAccountByCodeOriginalData(account);

        //重新排序该合同下还款计划
        Map scheduleMap = new HashedMap();
        scheduleMap.put("contractCode", contractCode);
        List<PlmsRepaymentScheduleBean> listScheduleBean = plmsRepaymentScheduleDao.getScheduleInfoByContractCode(scheduleMap);
        int totalPeriod = listScheduleBean.size();
        for (int i = 0; i < totalPeriod; i++) {
            PlmsRepaymentScheduleBean bean = listScheduleBean.get(i);
            bean.setTotalPeriod(totalPeriod);
            bean.setCurrentPeriod(i + 1);
        }
        plmsRepaymentScheduleDao.updateScheduleList(listScheduleBean);

        return resultMap;
    }

    /**
     * 更新还款计划
     *
     * @param requestMap
     * @return
     * @throws Exception
     */
    public Map updateSchedule(Map requestMap) throws Exception {
        if (null == requestMap) {
            return null;
        }
        BigDecimal amount = new BigDecimal("0");
        //合同编号
        String contractCode = (String) requestMap.get("contractCode");
        //应还日期
        Date repaymentDate = (Date) requestMap.get("repaymentDate");
        //应还本金
        BigDecimal principal = (BigDecimal) requestMap.get("principal");
        if (null != principal) {
            amount = amount.add(principal);
        }
        //应还利息
        BigDecimal interest = (BigDecimal) requestMap.get("interest");
        if (null != interest) {
            amount = amount.add(interest);
        }
        //应还罚息
        BigDecimal overDue = (BigDecimal) requestMap.get("overDue");
        if (null != overDue) {
            amount = amount.add(overDue);
        }
        //应还服务费
        BigDecimal serviceFee = (BigDecimal) requestMap.get("serviceFee");
        if (null != serviceFee) {
            amount = amount.add(serviceFee);
        }
        //应还服务费违约金
        BigDecimal serviceFeePenalty = (BigDecimal) requestMap.get("serviceFeePenalty");
        if (null != serviceFeePenalty) {
            amount = amount.add(serviceFeePenalty);
        }
        //应还保证金
        BigDecimal bail = (BigDecimal) requestMap.get("bail");
        if (null != bail) {
            amount = amount.add(bail);
        }
        requestMap.put("amount", amount);
        //已还本金
        BigDecimal repaymentedPrincipal = (BigDecimal) requestMap.get("repaymentedPrincipal");
        //已还利息
        BigDecimal repaymentedInterest = (BigDecimal) requestMap.get("repaymentedInterest");
        //已还罚息
        BigDecimal repaymentedOverdue = (BigDecimal) requestMap.get("repaymentedOverdue");
        //已还服务费
        BigDecimal repaymentedServiceFee = (BigDecimal) requestMap.get("repaymentedServiceFee");
        //已还服务费违约金
        BigDecimal repaymentedServiceFeePenalty = (BigDecimal) requestMap.get("repaymentedServiceFeePenalty");
        if (StringUtils.isBlank(contractCode) || null == repaymentDate || null == principal || null == interest || null == overDue || null == serviceFee) {
            return null;
        }

        String scheduleCode = null;
        List<PlmsRepaymentScheduleBean> scheduleBeanList = plmsRepaymentScheduleDao.getScheduleInfoByContractCode(requestMap);
        if (null == scheduleBeanList || scheduleBeanList.size() < 1) {
            scheduleCode = getSeq(GlobParam.SEQUENCE_SERIAL_NO, 6);
            requestMap.put("code", scheduleCode);
            requestMap.put("status", GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
            requestMap.put("createDate", new Date());
            requestMap.put("modifyDate", new Date());
            //账户明细表code
            Map accountDetail = accountDetailDao.findAccountDetailByCode(contractCode);
            requestMap.put("detailCode", accountDetail.get("code"));
            //用户信息表code
            requestMap.put("userCode", accountDetail.get("userCode"));
            //新增还款计划
            int flag = plmsRepaymentScheduleDao.saveScheduleInfo(requestMap);
        } else {
            PlmsRepaymentScheduleBean scheduleBean = scheduleBeanList.get(0);
            scheduleCode = scheduleBean.getCode();
            requestMap.put("code", scheduleCode);
            requestMap.put("modifyDate", new Date());
            //更新还款计划
            plmsRepaymentScheduleDao.updateScheduleAmount(requestMap);
        }

        return requestMap;
    }

    /**
     * 获取序列号方法
     *
     * @param seqName
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public String getSeq(String seqName, int num) throws ErrorException {
        //先更改序列表，序列增加
        int ret = commDao.nextval(seqName);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //查询当前序列值
        Long seq = commDao.currval(seqName);
        if (seq.toString().length() > num) {
            //如果大于所传num，则进行截取
            seq = Long.parseLong(seq.toString().substring(0, num));
        }
        return String.format("%0" + num + "d", seq);
    }

    /**
     * 查询城市所有有效城市列表
     *
     * @return
     * @throws Exception
     */
    public List<City> queryAllCity() throws Exception {
        return cityDao.queryAllCity();
    }

    /**
     * 获取用户所辖城市列表
     *
     * @param userId
     * @return cityList
     * @throws Exception
     */
    public List queryCityListByUserId(String userId) throws Exception {
        List<Map> cityList = new ArrayList<Map>();

        //获取城市列表
        List<UserGovernCity> userGovernCityList = userGovernCityDao.queryCityByUserNo(userId);
        if (userGovernCityList != null && userGovernCityList.size() > 0) {
            for (UserGovernCity nugc : userGovernCityList) {
                //如果存在全国的业务管辖，则直接查找全部城市即可
                if (GlobParam.CITY_CODE_QUANGUO.equals(nugc.getCityCode())) {
                    List<City> nlbsCityList = this.queryAllCity();
                    cityList.clear();
                    if (nlbsCityList != null && nlbsCityList.size() > 0) {
                        for (City nc : nlbsCityList) {
                            if (GlobParam.CITY_CODE_QUANGUO.equals(nc.getCode())) {
                                continue;
                            }
                            Map cityMap = new HashMap();
                            cityMap.put(Fields.PARAM_CITY_CODE, nc.getCode());
                            cityMap.put(Fields.PARAM_CITY_NAME, nc.getAbbrName());
                            cityList.add(cityMap);
                        }
                    }
                    break;
                }
                Map cityMap = new HashMap();
                cityMap.put(Fields.PARAM_CITY_CODE, nugc.getCityCode());
                cityMap.put(Fields.PARAM_CITY_NAME, nugc.getCityName());
                cityList.add(cityMap);
            }
        }

        return cityList;
    }

    /**
     * 根据传入的渠道号查询其子孙渠道(不含自身)
     *
     * @param distributorCode
     * @return
     * @throws Exception
     */
    public List<Map> queryBmsDistributors(String distributorCode) throws Exception {
        if (StringUtils.isBlank(distributorCode)) {
            return new ArrayList<>();
        }
        List<Map> returnDistributorList = new ArrayList<>();
        List<Map> childrenDistributors = distributorDao.queryChildrenDistributors(distributorCode);
        if (childrenDistributors != null && childrenDistributors.size() > 0) {
            for (Map distributor : childrenDistributors) {
                Object childDistributorCode = distributor.get(Fields.PARAM_DISTRIBUTRO_CODE);
                if (childDistributorCode != null) {
                    returnDistributorList.addAll(queryBmsDistributors(childDistributorCode.toString()));
                }
            }
            returnDistributorList.addAll(childrenDistributors);
        }
        //去除重复项
        Set distributorSet = new LinkedHashSet();
        distributorSet.addAll(returnDistributorList);
        returnDistributorList.clear();
        returnDistributorList.addAll(distributorSet);
        return returnDistributorList;
    }


    /**
     * 根据传入的用户查找子孙用户，不包含自身
     *
     * @param userCode
     * @return
     * @throws Exception
     */
    public List<Map> queryAllChildUsers(String userCode) throws Exception {
        if (StringUtils.isBlank(userCode)) {
            return new ArrayList<>();
        }
        List<Map> returnUserList = new ArrayList<>();
        List<Map> childrenUsers = loginInfoDao.queryUserInfoByParentUserCode(userCode);
        if (childrenUsers != null && childrenUsers.size() > 0) {
            for (Map user : childrenUsers) {
                Object childUserCode = user.get("code");
                if (childUserCode != null) {
                    returnUserList.addAll(queryAllChildUsers(childUserCode.toString()));
                }
            }
            returnUserList.addAll(childrenUsers);
        }
        //去除重复项
        Set userSet = new LinkedHashSet();
        userSet.addAll(returnUserList);
        returnUserList.clear();
        returnUserList.addAll(userSet);
        return returnUserList;
    }

    /**
     * 根据传入的业务员查找子孙业务员，不包含自身
     *
     * @param agentId
     * @return
     * @throws Exception
     */
    public List<Map> queryAllChildAgents(String agentId) throws Exception {
        if (StringUtils.isBlank(agentId)) {
            return new ArrayList<>();
        }
        List<Map> returnAgentList = new ArrayList<>();
        List<Map> childrenAgents = loginInfoDao.queryAgentInfoByParentAgentId(agentId);
        if (childrenAgents != null && childrenAgents.size() > 0) {
            for (Map user : childrenAgents) {
                Object childAgentId = user.get("code");
                if (childAgentId != null) {
                    returnAgentList.addAll(queryAllChildAgents(childAgentId.toString()));
                }
            }
            returnAgentList.addAll(childrenAgents);
        }
        //去除重复项
        Set userSet = new LinkedHashSet();
        userSet.addAll(returnAgentList);
        returnAgentList.clear();
        returnAgentList.addAll(userSet);
        return returnAgentList;
    }

    /**
     * @Description: 根据传入的业务员编码（本系统的code），仅查找父级业务员的业务员编码（bms系统的code）
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/14/0014
     */
    public List<Map> queryParentAgentByAgentCode(String agentCode) throws Exception{
        if(StringUtils.isBlank(agentCode)){
            return new ArrayList<>();
        }
        List<Map> parentAgentList = commDao.getParentAgentByAgentCode(agentCode);
        if(parentAgentList == null){
            parentAgentList = new ArrayList<>();
        }
        return parentAgentList;
    }

    /**
     * 根据传入的业务员编号在BMS查询其子孙业务员(含自身)
     *
     * @param userCode
     * @return
     * @throws Exception
     */
    public List<Map> queryAllChildUsersIncludeItself(String userCode) throws Exception {
        List<Map> returnUserList = new ArrayList<Map>();
        //添加自身所在渠道
        Map bmsAgent = loginInfoDao.queryUserInfoByCode(userCode);
        if (bmsAgent != null) {
            returnUserList.add(bmsAgent);
        }
        returnUserList.addAll(queryAllChildUsers(userCode));
        return returnUserList;
    }

    /**
     * 根据传入的渠道号查询其子孙渠道(含自身)
     *
     * @param distributorCode
     * @return
     * @throws Exception
     */
    public List<Map> queryDistributorsIncludeItself(String distributorCode) throws Exception {
        List<Map> bmsDistributorList = new ArrayList<Map>();
        //添加自身所在渠道
        Map bmsChannel = distributorDao.queryDistributorByCode(distributorCode);
        if (bmsChannel != null) {
            bmsDistributorList.add(bmsChannel);
        }
        bmsDistributorList.addAll(queryBmsDistributors(distributorCode));
        return bmsDistributorList;
    }

    private final int MANAGER_FLAG = 1;
    private final int RECORD_FLAG = 3;

    /**
     * 返回当前用户可见渠道列表
     *
     * @param userId 当前用户
     * @return
     */
    public List getAvaliableDistributorListByUserId(String userId) throws Exception {

        int role = MANAGER_FLAG;

        boolean beAdminFlag = false;
        List<String> roleList = new ArrayList<String>();
        roleList.add(GlobParam.ROLE_SUPPER_MANAGER);
        beAdminFlag = hasRoles(userId, roleList);

        //查询用户渠道表（<业务员渠道表><录单员渠道表>，即用户可以录入哪些渠道的单子）
        List<UserDistributor> userDistributorList = userDistributorDao.selectUserDistributorByUserId(userId);
        if (!beAdminFlag) {
            role = RECORD_FLAG;
        }

        List<Map> returnDistributorList = new ArrayList<Map>();
        if (MANAGER_FLAG == role) {
//            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "Y");
            List<Map> distributorList = distributorDao.queryAllDistributors();
            if (distributorList != null && distributorList.size() > 0) {
                for (Map nb : distributorList) {
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                    returnDistributorList.add(standNb);
                }
            }
        } else if (RECORD_FLAG == role) {
            List<Map> nlbsDistributorList = new ArrayList<Map>();
            //Step 2.3.1先查找关联表的数据
            if (userDistributorList != null && userDistributorList.size() > 0) {
                for (UserDistributor nrd : userDistributorList) {
                    nlbsDistributorList.addAll(queryDistributorsIncludeItself(nrd.getDistributorCode()));
                }
                //去除重复项
                HashSet hs = new HashSet(nlbsDistributorList);
                nlbsDistributorList.clear();
                nlbsDistributorList.addAll(hs);
            }
            //Step 2.3.3 整理distributerList的参数
            if (nlbsDistributorList != null && nlbsDistributorList.size() > 0) {
                //整理为标准的distributorCode distributorName的List
                for (Map map : nlbsDistributorList) {
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, map.get(Fields.PARAM_DISTRIBUTRO_CODE));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, map.get(Fields.PARAM_DISTRIBUTRO_NAME));
                    standNb.put(Fields.PARAM_GROUP_CITY, map.get(Fields.PARAM_GROUP_CITY));
                    returnDistributorList.add(standNb);
                }
            }
        }

        return returnDistributorList;
    }

    /**
     * 判断用户是拥有roleList对应角色
     *
     * @param userId--需判断的用户
     * @param roleList--管理员的角色代码列表
     * @return
     * @throws Exception
     */
    public boolean hasRoles(String userId, List<String> roleList) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return false;
        }
        //获取当前用户所拥有的所有角色
        Map queryRoleMap = new HashMap();
        queryRoleMap.put(Fields.PARAM_USER_NO, userId);
        queryRoleMap.put(Fields.PARAM_ROLE_STATUS, GlobParam.ROLE_STATUS_VALID);
        List<Map> userRoleList = queryUserRoleList(queryRoleMap);

        //判断当前用户的角色是否在传入的角色列表中
        if (userRoleList != null && userRoleList.size() > 0 && roleList != null && roleList.size() > 0) {
            for (String roleStr : roleList) {
                for (Map roleMap : userRoleList) {
                    if (roleStr.equals(roleMap.get(Fields.PARAM_ROLE_ID))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 调用UM获取用户的角色列表
     * 角色状态：1-正常 0-停用 2-已删除（不会返回）
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public List<Map> queryUserRoleList(Map paramMap) throws Exception {
        //Step1 入参检查
        if (paramMap == null) {
            return new ArrayList<Map>();
        }
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String roleId = paramMap.get(Fields.PARAM_ROLE_ID) == null ? "" : paramMap.get(Fields.PARAM_ROLE_ID).toString();
        String roleStatus = paramMap.get(Fields.PARAM_ROLE_STATUS) == null ? "" : paramMap.get(Fields.PARAM_ROLE_STATUS).toString();

        List<Map> returnRoleList = new ArrayList<Map>();
        Map resultMap = new HashMap();

        //Step2 整理调用UM参数
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_USER_ID, userNo);
        remoteParamMap.put(Fields.PARAM_ROLE_ID, roleId);
        remoteParamMap.put(Fields.PARAM_ROLE_STATUS, roleStatus);
        remoteParamMap.put(Fields.PARAM_SYSTEM_ID, GlobParam.SYSTEM_ID_PLMS);
        resultMap = remoteUmService.callService(remoteParamMap, GlobParam.UM_FUNCTION_GET_ROLES);
        //Step3 判断返回参数并整理出参
        if (resultMap != null) {
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if (ReturnCode.SUCCESS_CODE.equals(errCode)) {
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                Object roles = bodyMap.get(Fields.PARAM_ROLES);

                if (roles != null) {
                    returnRoleList = (ArrayList<Map>) roles;
                }
            }
        }


        return returnRoleList;
    }


    /**
     * 获取贷款状态列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryLoanStatusList() throws Exception {
        List<Map> loanStatusList = new ArrayList<Map>();
        //目前还没有基表，暂时写死

        Map statusMap = new HashMap();
        statusMap.put("loanStatusCode", GlobDict.repayment_schedule_detail_status_paying.getKey());
        statusMap.put("loanStatusName", GlobDict.repayment_schedule_detail_status_paying.getDesc());
        loanStatusList.add(statusMap);

        statusMap = new HashMap();
        statusMap.put("loanStatusCode", GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getKey());
        statusMap.put("loanStatusName", GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getDesc());
        loanStatusList.add(statusMap);

        statusMap = new HashMap();
        statusMap.put("loanStatusCode", GlobDict.repayment_schedule_detail_status_overdue.getKey());
        statusMap.put("loanStatusName", GlobDict.repayment_schedule_detail_status_overdue.getDesc());
        loanStatusList.add(statusMap);

        statusMap = new HashMap();
        statusMap.put("loanStatusCode", GlobDict.repayment_schedule_detail_status_paid.getKey());
        statusMap.put("loanStatusName", GlobDict.repayment_schedule_detail_status_paid.getDesc());
        loanStatusList.add(statusMap);


        return loanStatusList;
    }

    /**
     * 获取收款方列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryPayeeList() throws Exception {
        List<Map> payeeList = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map payeeMap = new HashMap();
        payeeMap.put("payeeCode", GlobDict.account_type_vilio.getKey());
        payeeMap.put("payeeName", GlobDict.account_type_vilio.getDesc());
        payeeList.add(payeeMap);

        payeeMap = new HashMap();
        payeeMap.put("payeeCode", GlobDict.account_type_fund_side.getKey());
        payeeMap.put("payeeName", GlobDict.account_type_fund_side.getDesc());
        payeeList.add(payeeMap);

        return payeeList;
    }

    /**
     * 获取收款方列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryAccountTypeList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("accountTypeCode", GlobDict.receipts_record_account_type_honghuo.getKey());
        map.put("accountTypeName", GlobDict.receipts_record_account_type_honghuo.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("accountTypeCode", GlobDict.receipts_record_account_type_fund_side.getKey());
        map.put("accountTypeName", GlobDict.receipts_record_account_type_fund_side.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("accountTypeCode", GlobDict.receipts_record_account_type_honghuo_bail.getKey());
        map.put("accountTypeName", GlobDict.receipts_record_account_type_honghuo_bail.getDesc());
        list.add(map);

        return list;
    }

    /**
     * 获取资金来源方列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> querFundSourceList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("fundSourceCode", GlobDict.receipts_record_fund_source_customer.getKey());
        map.put("fundSourceName", GlobDict.receipts_record_fund_source_customer.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("fundSourceCode", GlobDict.receipts_record_fund_source_honghuo.getKey());
        map.put("fundSourceName", GlobDict.receipts_record_fund_source_honghuo.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("fundSourceCode", GlobDict.receipts_record_fund_source_danbao.getKey());
        map.put("fundSourceName", GlobDict.receipts_record_fund_source_danbao.getDesc());
        list.add(map);

        return list;
    }

    /**
     * 获取还款计划状态列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryRepaymentScheduleStatusList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("repaymentScheduleStatusCode", GlobDict.repayment_schedule_status_paid.getKey());
        map.put("repaymentScheduleStatusName", GlobDict.repayment_schedule_status_paid.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("repaymentScheduleStatusCode", GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
        map.put("repaymentScheduleStatusName", GlobDict.repayment_schedule_status_paying_and_not_overdue.getDesc());
        list.add(map);

        return list;
    }

    /**
     * 获取资金入账处理状态列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryReceiptsRecordStatusList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("receiptsRecordStatusCode", GlobDict.receipts_deal_stauts_succ.getKey());
        map.put("receiptsRecordStatusName", GlobDict.receipts_deal_stauts_succ.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("receiptsRecordStatusCode", GlobDict.receipts_deal_stauts_init.getKey());
        map.put("receiptsRecordStatusName", GlobDict.receipts_deal_stauts_init.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("receiptsRecordStatusCode", GlobDict.receipts_deal_stauts_ing.getKey());
        map.put("receiptsRecordStatusName", GlobDict.receipts_deal_stauts_ing.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("receiptsRecordStatusCode", GlobDict.receipts_deal_stauts_fail.getKey());
        map.put("receiptsRecordStatusName", GlobDict.receipts_deal_stauts_fail.getDesc());
        list.add(map);

        return list;
    }

    /**
     * 获取科目列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> querySubjectList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_principal.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_principal.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_interest.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_interest.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_service_fee.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_service_fee.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_bail.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_bail.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_overdue.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_overdue.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_service_fee_penalty.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_service_fee_penalty.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_prepayment_penalty.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_prepayment_penalty.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_prepayment_interest_penalty.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_prepayment_interest_penalty.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("subjectCode", GlobDict.repayment_detail_subject_prepayment_service_fee_penalty.getKey());
        map.put("subjectName", GlobDict.repayment_detail_subject_prepayment_service_fee_penalty.getDesc());
        list.add(map);

        return list;
    }


    /**
     * 获取资金入账批量上载状态列表
     *
     * @return
     * @throws Exception
     */
    public List<Map> queryReceiptsRecordUploadStatusList() throws Exception {
        List<Map> list = new ArrayList<Map>();
        //目前还没有基表，暂时写死
        Map map = new HashMap();
        map.put("statusCode", GlobDict.receipts_record_upload_status_checking.getKey());
        map.put("statusName", GlobDict.receipts_record_upload_status_checking.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("statusCode", GlobDict.receipts_record_upload_status_success.getKey());
        map.put("statusName", GlobDict.receipts_record_upload_status_success.getDesc());
        list.add(map);

        map = new HashMap();
        map.put("statusCode", GlobDict.receipts_record_upload_status_fail.getKey());
        map.put("statusName", GlobDict.receipts_record_upload_status_fail.getDesc());
        list.add(map);

        return list;
    }


    public String getUUID() {
        return UUID.randomUUID().toString();
    }


    /**
     * 获取系统参数日期
     *
     * @return
     */
    public String getItemTime(String itemId) throws ErrorException {
        SysParam sysParam = commDao.getSysParam(itemId);
        if (sysParam == null) {
            throw new ErrorException(ReturnCode.SYS_PARAM_FAIL, "");
        }
        return sysParam.getExecuteTime();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public int setItemVal(String itemId, String itemCval, String itemIval) throws ErrorException {
        SysParam sysParam = new SysParam();
        sysParam.setItemId(itemId);
        sysParam.setItemCval(itemCval);
        sysParam.setItemIval(itemIval);
        int ret = commDao.setItemVal(sysParam);
        return ret;
    }


    /**
     * 监控邮件
     *
     * @param jobName 工作名称
     * @param errorMsg     发生的异常
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void monitorEmail(String jobName, String errorMsg) {
        try {
            if (GlobDict.email_switch_monitor_no.getKey().equals(GlobParam.monitorSwitch)) {
                Map matchParam = new HashMap();
                matchParam.put("jobName", jobName);
                matchParam.put("errorMsg", errorMsg);
                matchParam.put("dateTime", DateUtil.getCurrentDateTime2());
                //入库邮件流水表参数
                EmailInfo emailInfo = new EmailInfo();
                emailInfo.setCode(UUID.randomUUID().toString());
                emailInfo.setSystemNo(DateUtil.getCurrentDateTime() + getSeq("SERNO", 8));
                emailInfo.setDisplayName(GlobParam.monitorDisplayName);
                emailInfo.setUserName(GlobParam.monitorUserName);
                emailInfo.setToUserList(GlobParam.monitorToUserList);
                emailInfo.setToCcList(GlobParam.monitorToCcList);
                String subject = GlobParam.monitorSubject;
                if (JudgeUtil.isNull(subject)) {
                    subject = MatchUtil.matchValue(subject, matchParam);
                }
                emailInfo.setSubject(subject);
                String content = GlobParam.monitorContent;
                if (JudgeUtil.isNull(subject)) {
                    content = MatchUtil.matchValue(content, matchParam);
                }
                emailInfo.setContent(content);
                emailInfo.setEmailType(GlobDict.email_type_monitor.getKey());
                emailInfo.setSendMethod(GlobDict.send_method_timely.getKey());
                emailInfo.setSendStatus(GlobDict.send_init.getKey());
                //初始化入库
                int ret = emailInfoDao.insertEmailInfoLog(emailInfo);
                if (ret <= 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
                //定义发送到mps的参数
                Map sendMap = new HashMap();
                Map head = new HashMap();
                Map body = BeanUtil.transBean2Map(emailInfo);
                sendMap.put("head", head);
                sendMap.put("body", body);
                body.put("toUserList", JsonUtil.jsonToList(emailInfo.getToUserList()));
                body.put("toCcList", JsonUtil.jsonToList(emailInfo.getToCcList()));
                body.put("type", GlobParam.MPS_EMAIL_TYPE);
                body.put("requestNo", emailInfo.getSystemNo());
                body.put("senderIdentityId", GlobParam.mpsSmsSenderIdentityId);
                body.put("senderName", emailInfo.getDisplayName());
                body.put("password", GlobParam.monitorPassword);
                body.put("senderSystem", GlobParam.SYSTEM_ID_PLMS);

                head.put("functionNo", GlobParam.MPS_EMAIL_FUNCTION_NO);
                try {
                    //发送mps系统
                    String resultStr = HttpUtil.sendHttp(GlobParam.mpsUrl, JsonUtil.objectToJson(sendMap));
                    Map resultMap = JsonUtil.jsonToMap(resultStr);
                    Map resultHead = (Map) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
                    String returnCode = resultHead.get("returnCode").toString();
                    String returnMessage = resultHead.get("returnMessage").toString();
                    if (!"0000".equals(returnCode)) {
                        //发送失败，更改状态为发送失败
                        logger.error("发送失败：" + returnMessage);
                        emailInfo.setSendStatus(GlobDict.send_fail.getKey());
                    } else {
                        //发送成功
                        emailInfo.setSendStatus(GlobDict.send_succ.getKey());
                    }
                    emailInfo.setRetCode(returnCode);
                    emailInfo.setRetMsg(returnMessage.length() > 500 ? returnMessage.substring(0, 500) : returnMessage);
                } catch (ErrorException e2) {
                    e2.printStackTrace();
                    e2.printStackTrace();
                    if (ReturnCode.TIME_OUT.equals(e2.getErroCode())) {
                        //超时，未知状态
                        logger.error("发送超时：" + e2.getMessage());
                        emailInfo.setSendStatus(GlobDict.send_unknown.getKey());
                    } else {
                        //发送失败
                        logger.error("发送失败：" + e2.getMessage());
                        emailInfo.setSendStatus(GlobDict.send_fail.getKey());
                    }
                    emailInfo.setRetCode(e2.getErroCode());
                    emailInfo.setRetMsg(e2.getMessage().length() > 500 ? e2.getMessage().substring(0, 500) : e2.getMessage());
                }
                emailInfo.setSendTime(DateUtil.getCurrentDateTime());
                ret = emailInfoDao.updateEmailInfoSendStatus(emailInfo);
                if (ret <= 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


}
