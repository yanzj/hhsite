package com.vilio.plms.service.bms.impl;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.BmsGlobDict;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RemoteFmsService;
import com.vilio.plms.service.bms.BmsRepaymentScheduleService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.PlmsUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by zx on 2017/8/23.
 */
@Service
public class BmsRepaymentScheduleServiceImpl implements BmsRepaymentScheduleService {
    private static final Logger logger = Logger.getLogger(BmsRepaymentScheduleServiceImpl.class);

    @Resource
    CommonService commonService;
    @Resource
    ContractDao contractDao;
    @Resource
    ApplyInfoDao applyInfoDao;
    @Resource
    AccountDao accountDao;
    @Resource
    AccountDetailDao accountDetailDao;
    @Resource
    BorrowVoucherDao borrowVoucherDao;
    @Resource
    RemoteFmsService remoteFmsService;
    @Resource
    PlmsRepaymentDateDao plmsRepaymentDateDao;
    @Resource
    PlmsContractInfoDao plmsContractInfoDao;
    @Resource
    PaidInfoDao paidInfoDao;

    /**
     * 还款计划相关
     * @param requestMap  核心同步数据请求参数
     * @throws Exception
     */
    @Override
    public void calculateRepaymentScheduleDependencyInfo(Map requestMap) throws Exception {
        logger.debug("calculateRepaymentScheduleDependencyInfo入参：requestMap=【" + requestMap + "】");
        //核心系统code
        String bmsCode = (String) requestMap.get("bmsLoanCode");

        //运营信息
        Map operationsInfo = (Map) requestMap.get("operationsInfo");
        //运营登记信息
        Map operationsRegisterInfo = (Map) operationsInfo.get("operationsRegisterInfo");
        //放款信息
        Map lendingRegistration = (Map) operationsRegisterInfo.get("lendingRegistration");
        //合同信息记录
        List<Map> contractInfoList = (List<Map>)operationsInfo.get("contractInfo");
        //运营材料信息
        List<Map> operationsMaterialInfoList = (List<Map>) operationsInfo.get("operationsMaterialInfo");
        //抵押物列表（合同）
        List<Map> collateralList = null;

        //进件信息
        Map applyInfo = (Map) requestMap.get("applyInfo");
        //抵押物信息列表（进件）
        List<Map> collateralInfoList = (List<Map>)applyInfo.get("collateralInfo");
        Map collateralInfoMap = new HashedMap();
        if (collateralInfoList != null && collateralInfoList.size() > 0) {
            for (Map collateralInfo : collateralInfoList) {
                collateralInfoMap.put(collateralInfo.get("id"), collateralInfo);
            }
        }

        //前端产品信息
        Map distributeProductInfo = (Map) requestMap.get("distributeProductInfo");

        //放款日
        String creditTime = null;
        //借款合同信息
        Map contractInfoMap = null;
        //借款合同信息记录
        String contractCode = null;
        if (contractInfoList != null) {
            for (Map contractInfo : contractInfoList) {
                Boolean isLoanContract = (Boolean) contractInfo.get("isLoanContract");
                if (!isLoanContract) {
                    continue;
                }
                contractInfoMap = contractDao.qryContractByContractNo((String) contractInfo.get("contractNo"));
                if (contractInfoMap != null) {
                    System.out.print(contractInfo.get("contractNo"));
                    contractCode = (String) contractInfoMap.get("code");
                }
                collateralList = (List<Map>) contractInfo.get("collateralList");

                creditTime = (String) lendingRegistration.get("creditTime");
                break;
            }
        }

        //抵押物批复总额
        BigDecimal approvalAmountTotal = new BigDecimal(0);
        //批复期限最小值
        Integer approvalLimit = null;
        //抵押物列表
        if (collateralList != null && collateralList.size() >0) {
            for (Map collateral : collateralList) {
//                Map collateralInfo = (Map) collateralInfoMap.get(collateral.get("id"));
//                if (collateralInfo != null) {
                int limit = Integer.parseInt(null == collateral.get("approvalLimit") ? "0" : collateral.get("approvalLimit").toString());
                if (null == approvalLimit) {
                    approvalLimit = limit;
                } else {
                    if (approvalLimit > limit) {
                        approvalLimit = limit;
                    }
                }
                String amount = (null == collateral.get("approvalAmount") ? "0" : collateral.get("approvalAmount").toString());
                approvalAmountTotal = approvalAmountTotal.add(new BigDecimal(amount));
//                }
            }
        }

        //应还利息总额
        BigDecimal bigTotalInterest = new BigDecimal(0);
        //应还服务费总额
        BigDecimal bigTotalServiceFee = new BigDecimal(0);
        //应还保证金
        BigDecimal bigBail = new BigDecimal(0);
        //放款金额
        BigDecimal paidAmount = new BigDecimal(null == lendingRegistration.get("creditAmount") ? "0" : lendingRegistration.get("creditAmount").toString());

        //放款到期日
        Date borrowEndDate = null;
        //首笔放款金额是否大于0，如果大于0，计算应还利息，应还服务费，应还保证金
        if(paidAmount.compareTo(new BigDecimal(0)) > 0){
            Date paidDate = DateUtil.parseDateTimeForPattern(creditTime, DateUtil.DATE_PATTERN2);
            //归还本金周期
            String strPrincipalDate = (String) distributeProductInfo.get("principalDate");
            int principalDate = Integer.parseInt(strPrincipalDate);

            //放款期数 = Min（授信期限，本金归还周期）
            int lendingPeriod = principalDate;
            if (approvalLimit != null) {
                lendingPeriod = lendingPeriod > approvalLimit ? approvalLimit : lendingPeriod;
            }

            //放款到期日De = Ds + 放款期数月数– 1天（若计算所得月份没有该日期，则取该月最后一天
            String strDate = DateUtil.formatDate(paidDate);
            int year = Integer.parseInt(strDate.substring(0, 4));
            int month = Integer.parseInt(strDate.substring(4, 6));
            int day = Integer.parseInt(strDate.substring(6, 8));
            String lastDay = DateUtil.getLastDayForSetMonth(paidDate, lendingPeriod);
            if(day > Integer.parseInt(lastDay.substring(6, 8))){
                //传入天大于要获取月份的最后一天，取最后一天
                borrowEndDate =  DateUtil.parseDateTimeForPattern(lastDay, DateUtil.DATE_PATTERN);
            }else{
                month = month + lendingPeriod;
                Calendar calendar = Calendar.getInstance(Locale.CHINESE);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.DATE, day - 1);
                borrowEndDate = calendar.getTime();
            }

            Map interestMap = commonService.calculateInterestForPaidinfo(paidAmount, paidDate ,borrowEndDate ,contractCode);
            bigTotalInterest = (BigDecimal)interestMap.get("totalInterest");
            bigTotalServiceFee = (BigDecimal)interestMap.get("totalServiceFee");
            bigBail = (BigDecimal)interestMap.get("bigBail");
        }

        //账户汇总表 （是否有记录，如果没有新建）
        System.out.print("contractInfoMap:"+contractInfoMap);
        ApplyInfo applyInfoBean = new ApplyInfo();
        applyInfoBean.setCode((String) contractInfoMap.get("applyCode"));
        applyInfoBean = applyInfoDao.qryApplyInfo(applyInfoBean);
        System.out.print("applyInfoBean:"+applyInfoBean);
        String userCode = applyInfoBean.getUserCode();
        System.out.print("userCode:"+userCode);
        Account account = new Account();
        account.setUserCode(userCode);
        account = accountDao.qryAccount(account);
        String accountCode = null;
        if(null == account){
            //新建
            account = new Account();
            accountCode = commonService.getUUID();
            account.setCode(accountCode);
            account.setUserCode(userCode);
            account.setTotalQuota(approvalAmountTotal.toString());
            account.setPrincipal(paidAmount.toString());
            account.setRemainingQuota(approvalAmountTotal.subtract(paidAmount).toString());
            //还款计划表生成后再将利息和服务费加总
//            account.setInterest(bigTotalInterest.toString());
//            account.setServiceFee(bigTotalServiceFee.toString());
            account.setInterest("0");
            account.setServiceFee("0");
            account.setBail(bigBail.toString());
            account.setOverdue("0");
            account.setServiceFeePenalty("0");
            account.setBailPenalty("0");
            account.setCreditEndDate((String) contractInfoMap.get("creditEndDate"));
            account.setCreateDate(DateUtil.getCurrentDateTime2());
            account.setModifyDate(DateUtil.getCurrentDateTime2());
            account.setRepaymentedPrincipal("0");
            account.setRepaymentedInterest("0");
            account.setRepaymentedServiceFee("0");
            account.setRepaymentedBail("0");
            account.setRepaymentedOverdue("0");
            account.setRepaymentedBailPenalty("0");
            account.setRepaymentedServiceFeePenalty("0");
            account.setRepaymentedAheadInterestPenalty("0");
            account.setRepaymentedAheadServiceFeePenalty("0");
            account.setStatus(GlobDict.account_status_avaliable.getKey());
            accountDao.saveAccount(account);
        }else{
            Account account_1 = new Account();
            accountCode = account.getCode();
            //授信有效期
            String creditEndDate_1 = (String) contractInfoMap.get("creditEndDate");
            String creditEndDate_2 = account.getCreditEndDate();
            if(DateUtil.dateCompare(creditEndDate_1.substring(0, 8), creditEndDate_2, DateUtil.DATE_PATTERN) < 0){
                account_1.setCreditEndDate(creditEndDate_1);
            }
            account_1.setTotalQuota(null == approvalAmountTotal ? "0" :approvalAmountTotal.toString());
            account_1.setPrincipal(null ==  paidAmount? "0" :paidAmount.toString());
            account_1.setRemainingQuota(null ==  approvalAmountTotal? "0" :approvalAmountTotal.subtract(paidAmount).toString());
            //还款计划表生成后再将利息和服务费加总
//            account_1.setInterest(null ==  bigTotalInterest? "0" :bigTotalInterest.toString());
//            account_1.setServiceFee(null ==  bigTotalServiceFee? "0" :bigTotalServiceFee.toString());
            account_1.setBail(null ==  bigBail? "0" :bigBail.toString());
            account.setModifyDate(DateUtil.getCurrentDateTime2());
            accountDao.updateAccountByCode(account_1);
        }
        //创建账户明细表
        AccountDetail accountDetail = new AccountDetail();
        String accountDetailCode = commonService.getUUID();
        accountDetail.setCode(accountDetailCode);
        accountDetail.setCreditEndDate((String) contractInfoMap.get("creditEndDate"));
        accountDetail.setTotalQuota(null == approvalAmountTotal ? "0" :approvalAmountTotal.toString());
        accountDetail.setPrincipal(null ==  paidAmount? "0" :paidAmount.toString());
        accountDetail.setRemainingQuota(null ==  approvalAmountTotal? "0" :approvalAmountTotal.subtract(paidAmount).toString());
        //还款计划表生成后再将利息和服务费加总
//        accountDetail.setInterest(null ==  bigTotalInterest? "0" :bigTotalInterest.toString());
//        accountDetail.setServiceFee(null ==  bigTotalServiceFee? "0" :bigTotalServiceFee.toString());
        accountDetail.setInterest("0");
        accountDetail.setServiceFee("0");
        accountDetail.setBail(null ==  bigBail? "0" :bigBail.toString());
        accountDetail.setOverdue("0");
        accountDetail.setServiceFeePenalty("0");
        accountDetail.setBailPenalty("0");
        accountDetail.setHonghuoBailAccountBalance("0");
        accountDetail.setHonghuoBalance("0");
        accountDetail.setFundSideBalance("0");
        accountDetail.setRepaymentedPrincipal("0");
        accountDetail.setRepaymentedInterest("0");
        accountDetail.setRepaymentedServiceFee("0");
        accountDetail.setRepaymentedServiceFeePenalty("0");
        accountDetail.setRepaymentedBail("0");
        accountDetail.setRepaymentedBailPenalty("0");
        accountDetail.setRepaymentedOverdue("0");
        accountDetail.setRepaymentedAheadInterestPenalty("0");
        accountDetail.setRepaymentedAheadServiceFeePenalty("0");
        accountDetail.setModifyDate(DateUtil.getCurrentDateTime2());
        accountDetail.setCreateDate(DateUtil.getCurrentDateTime2());
        accountDetail.setConfirmed(GlobDict.account_detail_confirmed_unconfirmed.getKey());
        accountDetail.setFirstAmount(null ==  paidAmount? "0" :paidAmount.toString());
        accountDetail.setContractCode(contractCode);
        accountDetail.setStatus(GlobDict.account_detail_status_avaliable.getKey());
        account.setModifyDate(DateUtil.getCurrentDateTime2());
        accountDetail.setAccountCode(accountCode);
        accountDetailDao.saveAccountDetail(accountDetail);
        //创建放款记录
        String paidInfoCode = null;
        if(paidAmount.compareTo(new BigDecimal(0) ) > 0){
            PaidInfo paidInfo = new PaidInfo();
            paidInfoCode = commonService.getUUID();
            paidInfo.setCode(paidInfoCode);
            paidInfo.setAccountCode(applyInfoBean.getAccountCode());
            paidInfo.setUserCode(userCode);
            paidInfo.setContractCode(contractCode);
            paidInfo.setPaidTime(creditTime);
            paidInfo.setPaidAmount(null ==  paidAmount? "0" :paidAmount.toString());
            paidInfo.setRepaymentedPrincipal("0");
            paidInfo.setRepaymentedInterest("0");
            paidInfo.setRepaymentedServiceFee("0");
            paidInfo.setRepaymentedOverdue("0");
            paidInfo.setRepaymentedBail("0");
            paidInfo.setRepaymentedServiceFeePenalty("0");
            paidInfo.setRepaymentedBailPenalty("0");
            //还款计划表生成后再将利息和服务费加总
//            paidInfo.setInterest(null ==  bigTotalInterest? "0" :bigTotalInterest.toString());
//            paidInfo.setServiceFee(null ==  bigTotalServiceFee? "0" :bigTotalServiceFee.toString());
            paidInfo.setInterest("0");
            paidInfo.setServiceFee("0");
            paidInfo.setBail(null ==  bigBail? "0" :bigBail.toString());
            paidInfo.setOverdue("0");
            paidInfo.setServiceFeePenalty("0");
            paidInfo.setBailPenalty("0");
            paidInfo.setRepaymentedAheadInterestPenalty("0");
            paidInfo.setRepaymentedAheadServiceFeePenalty("0");
            paidInfo.setRemark((String) lendingRegistration.get("creditRemark"));
            paidInfo.setStatus(GlobDict.paid_info_status_paying_and_not_overdue.getKey());
            paidInfo.setCreateDate(DateUtil.getCurrentDateTime2());
            paidInfo.setModifyDate(DateUtil.getCurrentDateTime2());
            paidInfoDao.savePaidInfo(paidInfo);

            //创建放款凭证表记录
            createBorrowVoucher(operationsMaterialInfoList, contractCode, paidInfoCode);
        }

        //授信开始日期 yyyy-MM-dd HH:mm:ss
        String strCreditStartDate = (String) contractInfoMap.get("creditStartDate");
        Date creditStartDate = DateUtil.parseDateTimeForPattern(strCreditStartDate, DateUtil.DATE_TIME_PATTERN);
        //授信截至日期 yyyy-MM-dd HH:mm:ss
        String strCreditEndDate = (String) contractInfoMap.get("creditEndDate");
        Date creditEndDate = DateUtil.parseDateTimeForPattern(strCreditEndDate, DateUtil.DATE_TIME_PATTERN);
        //授信期限
        Integer creditPeriod = (Integer) contractInfoMap.get("creditPeriod");

        /** 获取系统参数（年化利率、年模型天数、押息期数） **/
        Map contractMap = plmsContractInfoDao.getProductInfoAndInterest(contractCode);
        if(null == contractMap){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "没有该进件产品信息！");
        }
        //固定还息日
        Integer interestRepaymentDay = (null == contractMap.get("interestRepaymentDay") ? null : Integer.parseInt((String) contractMap.get("interestRepaymentDay")));
        //收息方式
        String interestCollectionMethod = (String) contractMap.get("interestCollectionMethod");
        //放款天数计算规则
        String paidDaysComputationalRule = (String) contractMap.get("paidDaysComputationalRule");

        //计算当前合同的固定还款日
        createRepaymenyDate(accountDetailCode, creditStartDate, creditEndDate, creditPeriod, interestRepaymentDay, interestCollectionMethod, paidDaysComputationalRule);

        if(paidAmount.compareTo(new BigDecimal(0) ) > 0){
            //还款计划
            commonService.buildRepaymentScheduleDetail(borrowEndDate, paidInfoCode);
        }
    }

    /**
     * 创建固定还款日表记录
     * @param accountDetailCode 账户明细表Code
     * @param creditStartDate 授信开始日期
     * @param creditEndDate 授信截止日期
     * @param creditPeriod 授信期限
     * @param interestRepaymentDay 固定还息日
     * @param interestCollectionMethod 收息方式（01、前置；02、后置；03、固定日）
     * @param paidDaysComputationalRule 放款天数计算规则（01、算头算尾;02、算头不算尾）
     */
    public void createRepaymenyDate(String accountDetailCode ,Date creditStartDate,Date creditEndDate, Integer creditPeriod,Integer interestRepaymentDay, String interestCollectionMethod, String paidDaysComputationalRule) throws ParseException {
        if(null == accountDetailCode || null == interestCollectionMethod || null == paidDaysComputationalRule || null == creditStartDate || null == creditEndDate || null == creditPeriod){
            logger.info("createRepaymenyDate传入必填字段缺失");
            return;
        }else if(GlobDict.interest_collection_method_fixed_day.getKey().equals(interestCollectionMethod) && null == interestRepaymentDay){
            logger.info("createRepaymenyDate缺失固定还息日！");
            return;
        }

        Set<Date> repaymentDateList = new HashSet<>();
        if(GlobDict.interest_collection_method_fixed_day.getKey().equals(interestCollectionMethod)){
            //固定日还息
            List<Date> dateList = DateUtil.getMonthsBetweenTwoDate(creditStartDate, creditEndDate);
            for(int i = 0; i < dateList.size(); i++){
                Date date = dateList.get(i);
                String strDate = null;
                if(DateUtil.getDaysByYearMonth(date) < interestRepaymentDay){
                    strDate = DateUtil.getLastDayForSetMonth(date, 0);
                }else{
                    strDate = DateUtil.getFixedDay(date, interestRepaymentDay);
                }
                date = DateUtil.parseDate(strDate);

                if(i == 0){
                    if(DateUtil.dateCompare(DateUtil.formatDate(creditStartDate), strDate) < 0) continue;
                }else if(i == dateList.size() - 1){
                    if(DateUtil.dateCompare(strDate, DateUtil.formatDate(creditEndDate)) < 0) continue;
                }
                repaymentDateList.add(date);
            }

            //本金归还日
            if(GlobDict.product_paid_days_computational_rule_both.getKey().equals(paidDaysComputationalRule)){
                //算头算尾
                Date date = PlmsUtil.getDueDate(creditStartDate,creditPeriod);
                repaymentDateList.add(date);
            }else{
                //算头不算尾
                String strDate = DateUtil.getDayForSetMonth(creditStartDate, creditPeriod);
                repaymentDateList.add(DateUtil.parseDate(strDate));
            }
        }else{
            //前置收息或后置收息
            for(int i = 1; i < creditPeriod; i++){
                String strDate = null;
                strDate = DateUtil.getDayForSetMonth(creditStartDate, i);
                Date date = DateUtil.parseDate(strDate);
                repaymentDateList.add(date);
            }
            //本金归还日
            if(GlobDict.product_paid_days_computational_rule_both.getKey().equals(paidDaysComputationalRule)){
                //算头算尾
                Date date = PlmsUtil.getDueDate(creditStartDate,creditPeriod);
                repaymentDateList.add(date);
            }else{
                //算头不算尾
                String strDate = DateUtil.getDayForSetMonth(creditStartDate, creditPeriod);
                repaymentDateList.add(DateUtil.parseDate(strDate));
            }

        }

        for(Date repaymentDate: repaymentDateList){
            PlmsRepaymentDate plmsRepaymentDate = new PlmsRepaymentDate();
            plmsRepaymentDate.setRepaymentDate(repaymentDate);
            plmsRepaymentDate.setDetailCode(accountDetailCode);
            plmsRepaymentDate.setStatus(GlobDict.repayment_date_status_valid.getKey());

            List list = plmsRepaymentDateDao.selectRepaymentDateByParams(plmsRepaymentDate);
            if(null == list || list.size() < 1){
                //新增一条
                String code = commonService.getUUID();
                plmsRepaymentDate.setCode(code);
                plmsRepaymentDate.setCreateDate(new Date());
                plmsRepaymentDate.setModifyDate(new Date());
                plmsRepaymentDateDao.saveRepaymentDate(plmsRepaymentDate);
            }
        }

    }


    //创建放款凭证表记录
    public void createBorrowVoucher(List<Map> operationsMaterialInfoList, String contractCode ,String paidCode){
        if(null == operationsMaterialInfoList || operationsMaterialInfoList.size() < 1 || null == contractCode){
            return;
        }
        List<BorrowVoucher> borrowVoucherList = new ArrayList();
        for(Map operationsMaterialInfo: operationsMaterialInfoList){
            String type = (String) operationsMaterialInfo.get("fileType");
            if(!BmsGlobDict.pigeonhole_info_type_loan_voucher.getKey().equals(type)){
                continue;
            }
            BorrowVoucher borrowVoucher = new BorrowVoucher();
            String code = commonService.getUUID();
            borrowVoucher.setCode(code);
//            borrowVoucher.setFileCode((String) operationsMaterialInfo.get("fileId"));
            borrowVoucher.setFileName((String) operationsMaterialInfo.get("fileName"));
            //去文件系统获取上载时间
            List fileList = new ArrayList();
            Map fileMap = new HashMap();
            fileMap.put("serialNo",(String) operationsMaterialInfo.get("fileId"));
            fileList.add(fileMap);
            Map map = new HashMap();
            map.put("serialNoList",fileList);
            Map result = null;
            try {
                result = remoteFmsService.getUrlList(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //获取返回结果
            Map body = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
            List fileMaps = (List)body.get("fileMaps");
            if(fileMaps!=null && fileMaps.size()>0) {
                fileMap = (Map) fileMaps.get(0);
                String uploadTime = (String) fileMap.get("uploadTime");
                String fileSuffix = (String) fileMap.get("fileSuffix");
                String fileCode = (String) fileMap.get("serialNo");
                borrowVoucher.setUploadTime(uploadTime);
                borrowVoucher.setFileSuffix(fileSuffix);
                borrowVoucher.setFileCode(fileCode);

            }
            borrowVoucher.setPaidInfoCode(paidCode);
            borrowVoucher.setStatus(GlobDict.valid.getKey());
            borrowVoucher.setCreateDate(DateUtil.getCurrentDateTime2());
            borrowVoucher.setModifyDate(DateUtil.getCurrentDateTime2());
            borrowVoucherList.add(borrowVoucher);
        }

        if(null != borrowVoucherList && borrowVoucherList.size() >0){
            borrowVoucherDao.saveBorrowVoucher(borrowVoucherList);
        }
    }
}
