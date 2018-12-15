package com.vilio.plms.service.opertaion;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.Plms100100;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.PayScheduleDetailForContract;
import com.vilio.plms.service.base.RollBackPaymentAndOverdueService;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xiezhilei on GlobParam.SCALE_2017/8/1.
 */
@Service
public class OperationManagerService {

    @Resource
    OperationManagerDao operationManagerDao;
    @Resource
    PaidInfoDao paidInfoDao;
    @Resource
    RollBackPaymentAndOverdueService rollBackPaymentAndOverdueService;
    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    CommonService commonService;
    @Resource
    AccountDetailDao accountDetailDao;
    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;
    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;
    @Resource
    AccountDao accountDao;
    @Resource
    PayScheduleDetailForContract payScheduleDetailForContract;
    @Resource
    Plms100100 plms100100;

    /**
     * 根据合同编码，创建对应的还款计划明细表临时数据,以及控制表数据
     * @param paramMap
     * @return
     * @throws Exception
     */
    public void createRepaymentScheduleDetailTmpAndControlData(Map paramMap) throws Exception {
        String contractCode = paramMap.get("contractCode").toString();
        String umId = paramMap.get("userNo").toString();

        //根据合同编码，作废对应的还款计划明细表临时数据
        operationManagerDao.nullifyRepaymentScheduleDetailTmpByContractCode(contractCode);

        //根据合同编码，作废一个合同的还款计划明细数据修改控制表数据
        operationManagerDao.nullifyRepaymentScheduleDetailChangeControlByContractCode(contractCode);

        //根据合同编码，创建对应的还款计划明细表临时数据
        operationManagerDao.createRepaymentScheduleDetailTmpByContractCode(contractCode);

        //创建一个合同的还款计划明细数据修改控制表数据
        Map param = new HashMap();
        param.put("contractCode",contractCode);
        param.put("changeStatus","00");
        param.put("changeUmId",umId);
        operationManagerDao.createRepaymentScheduleDetailChangeControl(param);
    }

    /**
     * 判断还款时间是否小于放款时间
     * @param repaymentDate
     * @param paidInfo
     * @return
     * @throws Exception
     */
    public boolean isRepaymentLessThanPaidTime(String repaymentDate, PaidInfo paidInfo) throws ErrorException {

        String paidTime = paidInfo.getPaidTime();

        //转日期类型
        Date repaymentDateAfterFormat = new Date();
        Date paidTimeAfterFormat = new Date();
        try{
            repaymentDateAfterFormat = CommonUtil.formatString2Date(repaymentDate,"yyyy-MM-dd");
            paidTimeAfterFormat = CommonUtil.formatString2Date(paidTime,"yyyy-MM-dd");
        }catch (Exception e){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"日期转换错误");
        }

        return  repaymentDateAfterFormat.before(paidTimeAfterFormat);
    }

    /**
     * 判断还款时间是否大于授信到期日
     * @param repaymentDate
     * @param contract
     * @return
     * @throws Exception
     */
    public boolean isRepaymentGreaterThanCreditEndDate(String repaymentDate, Contract contract) throws ErrorException {

        String creditEndDate = contract.getCreditEndDate();

        //转日期类型
        Date repaymentDateAfterFormat = new Date();
        Date creditEndDateAfterFormat = new Date();
        try{
            repaymentDateAfterFormat = CommonUtil.formatString2Date(repaymentDate,"yyyy-MM-dd");
            creditEndDateAfterFormat = CommonUtil.formatString2Date(creditEndDate,"yyyyMMddHHmmss");
        }catch (Exception e){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"日期转换错误");
        }

        return  repaymentDateAfterFormat.after(creditEndDateAfterFormat);
    }

    /**
     * 判断还款时间是否已存在
     * @param repaymentDate
     * @param paidCode
     * @return
     * @throws Exception
     */
    public boolean isRepaymentExists(String repaymentDate, String paidCode) throws ErrorException {

        Map param = new HashMap();
        param.put("paidCode",paidCode);
        param.put("repaymentDate",repaymentDate);
        int result = operationManagerDao.countRepaymentScheduleDetailTmpByPaidCodeAndRepaymentDate(param);

        return  result > 0;
    }

    /**
     * 判断应还本金是否大于放款金额
     * @param principal
     * @param paidInfo
     * @return
     * @throws Exception
     */
    public boolean isPrincipalGreaterThanPaidAmount(String principal, PaidInfo paidInfo) throws ErrorException {

        String paidAmount = paidInfo.getPaidAmount();

        return MathUtil.strcompareTo(principal,paidAmount);
    }

    /**
     * 判断应还利息是否大于放款信息表记录的应还利息
     * @param interest
     * @param paidInfo
     * @return
     * @throws Exception
     */
    public boolean isInterestGreaterThanTotalInterest(String interest, PaidInfo paidInfo) throws ErrorException {

        String totalInterest = paidInfo.getInterest();

        return MathUtil.strcompareTo(interest,totalInterest);
    }

    /**
     * 判断应还服务费是否大于放款信息表记录的应还服务费
     * @param serviceFee
     * @param paidInfo
     * @return
     * @throws Exception
     */
    public boolean isServiceFeeGreaterThanTotalServiceFee(String serviceFee, PaidInfo paidInfo) throws ErrorException {

        String totalServiceFee = paidInfo.getServiceFee();

        return MathUtil.strcompareTo(serviceFee,totalServiceFee);
    }

    /**
     * 判断应还保证金是否大于放款信息表记录的应还保证金
     * @param bail
     * @param paidInfo
     * @return
     * @throws Exception
     */
    public boolean isBailGreaterThanTotalServiceFee(String bail, PaidInfo paidInfo) throws ErrorException {

        String totalServiceFee = paidInfo.getBail();

        return MathUtil.strcompareTo(bail,totalServiceFee);
    }

    /**
     * 还款计划修改
     * @param paramMap
     * @return
     * @throws Exception
     */
    public void modifyScheduleDetail(Map paramMap) throws Exception {
        String contractCode = paramMap.get("contractCode").toString();

        //查找删除记录
        List deletedList = operationManagerDao.queryDeletedRepaymentScheduleDetail(contractCode);
        if(null == deletedList) {
            deletedList = new ArrayList();
        }

        //查找修改记录
        List updatedList = operationManagerDao.queryUpdatedRepaymentScheduleDetail(contractCode);
        if(null == updatedList) {
            updatedList = new ArrayList();
        }

        //查找新增记录
        List insertedList = operationManagerDao.queryInsertedRepaymentScheduleDetail(contractCode);
        if(null == updatedList) {
            updatedList = new ArrayList();
        }

        //找出数据变更的最早日期(还款计划日期)
        String earliestDate = getEarliestRepaymentDate(deletedList,updatedList,insertedList);
        if(earliestDate == null){
            return;//直接返回
        }
        String rollBachDate = earliestDate;
        ((OperationManagerService) AopContext.currentProxy()).updateRepaymentDetailList(rollBachDate,contractCode,insertedList,updatedList,deletedList);
    }

    /**
     * 修改还款计划明细表（切面变成，前后增加账务锁定解锁、回滚扣款和逾期、重新计算扣款和逾期） wangxf by 20170829
     * @param rollBachDate
     * @param contractCode
     * @param insertedList
     * @param updatedList
     * @param deletedList
     * @throws Exception
     */
    public void updateRepaymentDetailList(String rollBachDate, String contractCode, List insertedList, List updatedList, List deletedList) throws Exception {
        //修改还款计划表
        dealInsertedRepaymentDetailList(insertedList);
        dealUpdatedRepaymentDetailList(updatedList);
        dealDeletedRepaymentDetailList(deletedList);

        //重新排序放款记录下的还款计划
        List paidInfoList = paidInfoDao.queryPaidInfoByContractCode(contractCode);
        for(int i = 0; null!=paidInfoList && i < paidInfoList.size(); i++){
            PaidInfo paidInfo = (PaidInfo) paidInfoList.get(i);
            List tmpDataList = operationManagerDao.queryRepaymentScheduleDetailTmpListByPaidCode(paidInfo.getCode());
            for(int p=0; null!=tmpDataList && p < tmpDataList.size(); p++){
                Map result = (Map)tmpDataList.get(i);
                Map tmpMap = new HashMap();
                tmpMap.put("code",result.get("scheduleDetailTmpCode"));
                tmpMap.put("totalPeriod",tmpDataList.size());
                tmpMap.put("currentPeriod",i+1);
                operationManagerDao.updatePeriodForRepaymentScheduleDetailTmp(tmpMap);
            }
        }

        //重新排序该合同下还款计划
        Map scheduleMap = new HashedMap();
        scheduleMap.put("contractCode", contractCode);
        List<PlmsRepaymentScheduleBean> listScheduleBean = plmsRepaymentScheduleDao.getScheduleInfoByContractCode(scheduleMap);
        for(int i = 0; null!=listScheduleBean && i < listScheduleBean.size(); i++){
            PlmsRepaymentScheduleBean bean = listScheduleBean.get(i);
            bean.setTotalPeriod(listScheduleBean.size());
            bean.setCurrentPeriod(i + 1);
        }
        plmsRepaymentScheduleDao.updateScheduleList(listScheduleBean);
    }

    /**
     * 回滚逾期和扣款
     * @param rollBachDate
     * @param contractCode
     * @throws Exception
     */
    public void rollBackPaymentAndOverdueService(String rollBachDate, String contractCode) throws Exception {

        rollBackPaymentAndOverdueService.mainJob(rollBachDate,contractCode);

    }

    /**
     * 从数据列表中获取最早日期，依赖于数据列表是排序过的
     * @param deletedList
     * @param updatedList
     * @param insertedList
     * @return
     * @throws Exception
     */
    public String getEarliestRepaymentDate(List deletedList, List updatedList, List insertedList) throws Exception {

        String deletedDate = null;
        if(!deletedList.isEmpty()){
            Map map = (Map)deletedList.get(0);
            deletedDate = (String)map.get("repaymentDate");
        }

        String updatedDate = null;
        if(!updatedList.isEmpty()){
            Map map = (Map)updatedList.get(0);
            updatedDate = (String)map.get("repaymentDate");
        }

        String insertedDate = null;
        if(!insertedList.isEmpty()){
            Map map = (Map)insertedList.get(0);
            insertedDate = (String)map.get("repaymentDate");
        }

        if(null == deletedDate && null == updatedDate && null == insertedDate){
            return null;
        }

        if(null == deletedDate && null == updatedDate){
            return insertedDate;
        }

        if(null == deletedDate && null == insertedDate){
            return updatedDate;
        }

        if(null == insertedDate && null == updatedDate){
            return deletedDate;
        }

        if(null == deletedDate){
            if(DateUtil.dateCompare(insertedDate, updatedDate, "yyyy-MM-dd") >= 0){
                return updatedDate;
            }else {
                return insertedDate;
            }
        }

        if(null == insertedDate){
            if(DateUtil.dateCompare(deletedDate, updatedDate, "yyyy-MM-dd") >= 0){
                return updatedDate;
            }else {
                return deletedDate;
            }
        }

        if(null == updatedDate){
            if(DateUtil.dateCompare(insertedDate, deletedDate, "yyyy-MM-dd") >= 0){
                return deletedDate;
            }else {
                return insertedDate;
            }
        }

        String earliestDate = deletedDate;
        if(DateUtil.dateCompare(earliestDate, updatedDate, "yyyy-MM-dd") >= 0){
            earliestDate = updatedDate;
        }
        if(DateUtil.dateCompare(earliestDate, insertedDate, "yyyy-MM-dd") >= 0){
            earliestDate = insertedDate;
        }

        return earliestDate;

    }

    /**
     * 处理新增记录
     * @param list
     */
    public void dealInsertedRepaymentDetailList(List list) throws Exception {
        for(int i=0; i<list.size(); i++){
            Map repaymentDetailMap = (Map)list.get(i);
            String repaymentDate = (String)repaymentDetailMap.get("repaymentDate");
            String paidCode = (String)repaymentDetailMap.get("paidCode");
            BigDecimal principal = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("principal"));
            BigDecimal interest = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("interest"));
            BigDecimal serviceFee = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("serviceFee"));
            BigDecimal bail = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("bail"));

            //取放款信息
            PaidInfo paidInfo = new PaidInfo();
            paidInfo.setCode(paidCode);
            paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfo);

            //取账户明细信息
            AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(paidInfo.getContractCode());

            //是否存在当前日期的还款计划表记录
            Map paramMap = new HashMap();
            paramMap.put("repaymentDate",repaymentDate);
            paramMap.put("paidCode",paidCode);
            Map scheduleMap = operationManagerDao.queryRepaymentScheduleByPaidCodeAndRepaymentDate(paramMap);
            String scheduleCode = null;

            if(null != scheduleMap){
                paramMap = new HashMap();
                //应还金额 = 原金额 + 录入的应还本金 + 应还利息 + 应还服务费（利息代收代付时为0.00）+ 应还保证金
                BigDecimal scheduleAmount = GlobParam.ZERO;
                scheduleAmount = MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("amount")), principal, GlobParam.SCALE_2);
                scheduleAmount = MathUtil.add(scheduleAmount, interest, GlobParam.SCALE_2);
                scheduleAmount = MathUtil.add(scheduleAmount, serviceFee, GlobParam.SCALE_2);
                scheduleAmount = MathUtil.add(scheduleAmount, bail, GlobParam.SCALE_2);
                paramMap.put("amount",scheduleAmount);

                //应还本金 = 原金额 + 录入的应还本金
                BigDecimal schedulePrincipal = GlobParam.ZERO;
                schedulePrincipal = MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("principal")), principal, GlobParam.SCALE_2);
                paramMap.put("principal",schedulePrincipal);

                //应还利息 = 原金额 + 录入的应还利息
                BigDecimal scheduleInterest = GlobParam.ZERO;
                scheduleInterest = MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("interest")), interest, GlobParam.SCALE_2);
                paramMap.put("interest",scheduleInterest);

                //应还服务费 = 原金额 + 录入的应还本服务费
                BigDecimal scheduleServiceFee = GlobParam.ZERO;
                scheduleServiceFee = MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("serviceFee")), serviceFee, GlobParam.SCALE_2);
                paramMap.put("serviceFee",scheduleServiceFee);

                //应还保证金 = 原金额 + 录入的应还保证金
                BigDecimal scheduleBail = GlobParam.ZERO;
                scheduleBail = MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("bail")), serviceFee, GlobParam.SCALE_2);
                paramMap.put("bail",scheduleBail);

                scheduleCode = scheduleMap.get("code").toString();
                paramMap.put("code",scheduleCode);
                plmsRepaymentScheduleDao.updateRepaymentSchedule(paramMap);
            }else{
                paramMap = new HashMap();
                scheduleCode = commonService.getUUID();
                paramMap.put("code",commonService.getUUID());
                paramMap.put("currentPeriod",null);//后面会重排序
                paramMap.put("totalPeriod",null);//后面会重排序
                paramMap.put("repaymentDate",repaymentDate);
                paramMap.put("amount",MathUtil.add(MathUtil.add(MathUtil.add(principal,interest,GlobParam.SCALE_2),serviceFee,GlobParam.SCALE_2),bail,GlobParam.SCALE_2));
                paramMap.put("principal",principal);
                paramMap.put("interest",interest);
                paramMap.put("overdue",GlobParam.ZERO);
                paramMap.put("status", GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getKey());
                paramMap.put("detailCode",accountDetail.getCode());
                paramMap.put("userCode",paidInfo.getUserCode());
                paramMap.put("createDate",DateUtil.getCurrentDate());
                paramMap.put("modifyDate",DateUtil.getCurrentDate());
                paramMap.put("repaymentedPrincipal",GlobParam.ZERO);
                paramMap.put("repaymentedInterest",GlobParam.ZERO);
                paramMap.put("repaymentedOverdue",GlobParam.ZERO);
                paramMap.put("serviceFee",serviceFee);
                paramMap.put("serviceFeePenalty",GlobParam.ZERO);
                paramMap.put("bail",bail);
                paramMap.put("bailPenalty",GlobParam.ZERO);
                paramMap.put("repaymentedBail",serviceFee);
                paramMap.put("repaymentedBailPenalty",GlobParam.ZERO);
                paramMap.put("repaymentedServiceFee",GlobParam.ZERO);
                paramMap.put("repaymentedServiceFeePenalty",GlobParam.ZERO);

                plmsRepaymentScheduleDao.saveScheduleInfo(paramMap);
            }

            //新增一条借款明细,从临时表copy
            PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail = new PlmsRepaymentScheduleDetail();
            plmsRepaymentScheduleDetail.setCode(commonService.getUUID());
            plmsRepaymentScheduleDetail.setCurrentPeriod(new Integer(repaymentDetailMap.get("currentPeriod").toString()));
            plmsRepaymentScheduleDetail.setTotalPeriod(new Integer(repaymentDetailMap.get("totalPeriod").toString()));
            plmsRepaymentScheduleDetail.setRepaymentDate(DateUtil.parseDate(repaymentDate));
            plmsRepaymentScheduleDetail.setAmount(new BigDecimal(repaymentDetailMap.get("amount").toString()));
            plmsRepaymentScheduleDetail.setPrincipal(principal);
            plmsRepaymentScheduleDetail.setInterest(interest);
            plmsRepaymentScheduleDetail.setOverdue(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedPrincipal(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedInterest(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedOverdue(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setStatus(GlobDict.repayment_schedule_detail_status_paying_and_not_overdue.getKey());
            plmsRepaymentScheduleDetail.setPaidCode(paidCode);
            plmsRepaymentScheduleDetail.setCreateDate(new Date());
            plmsRepaymentScheduleDetail.setModifyDate(new Date());
            plmsRepaymentScheduleDetail.setScheduleCode(scheduleCode);
            plmsRepaymentScheduleDetail.setPrincipalAccountCode(repaymentDetailMap.get("principalAccountCode").toString());
            plmsRepaymentScheduleDetail.setInterestAccountCode(repaymentDetailMap.get("interestAccountCode").toString());
            plmsRepaymentScheduleDetail.setTotalOverdueDays("0");
            plmsRepaymentScheduleDetail.setServiceFeeTotalOverdueDays("0");
            plmsRepaymentScheduleDetail.setServiceFee(serviceFee);
            plmsRepaymentScheduleDetail.setServiceFeePenalty(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedServiceFee(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedServiceFeePenalty(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setBail(bail);
            plmsRepaymentScheduleDetail.setBailPenalty(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedBail(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedBailPenalty(GlobParam.ZERO);
            plmsRepaymentScheduleDetail.setRepaymentedBailPenalty(GlobParam.ZERO);
            plmsRepaymentScheduleDetailDao.saveRepaymentScheduleDetail(plmsRepaymentScheduleDetail);

            //更新当前放款信息表记录
            paidInfo.setPaidAmount(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getPaidAmount()),principal,GlobParam.SCALE_2).toString());//原金额 + 录入的应还本金
            paidInfo.setInterest(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 + 录入的应还利息
            paidInfo.setServiceFee(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 + 录入的应还服务费（利息代收代付时为0.00）
            paidInfo.setBail(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 + 录入的应还保证金
            plmsPaidInfoDao.updatePaidInfoByCodeOriginalData(paidInfo);

            //更新账户明细表记录
            accountDetail.setPrincipal(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getPrincipal()),principal,GlobParam.SCALE_2).toString());//原金额 + 录入的应还本金
            accountDetail.setRemainingQuota(MathUtil.strSub(accountDetail.getRemainingQuota(),accountDetail.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            accountDetail.setInterest(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 + 录入的应还利息
            accountDetail.setServiceFee(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 + 录入的应还利息
            accountDetail.setBail(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 + 录入的应还保证金
            accountDetailDao.updateAccountDetailByCodeOriginalData(accountDetail);

            //更新账户汇总表记录
            Account account = new Account();
            account.setCode(accountDetail.getAccountCode());
            account.setPrincipal(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(account.getPrincipal()),principal,GlobParam.SCALE_2).toString());//原金额 + 录入的应还本金
            account.setRemainingQuota(MathUtil.strSub(account.getRemainingQuota(),account.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            account.setInterest(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(account.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 + 录入的应还利息
            account.setServiceFee(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(account.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 + 录入的应还利息
            account.setBail(MathUtil.add(CommonUtil.dealBigDecimalNull2Zero(account.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 + 录入的应还保证金
            accountDao.updateAccountByCodeOriginalData(account);
        }
    }

    /**
     * 处理修改记录
     * @param list
     */
    public void dealUpdatedRepaymentDetailList(List list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            Map repaymentDetailMap = (Map)list.get(i);
            String repaymentDate = (String)repaymentDetailMap.get("repaymentDate");
            String paidCode = (String)repaymentDetailMap.get("paidCode");
            String code = (String)repaymentDetailMap.get("code");
            BigDecimal amount = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("amount"));
            BigDecimal principal = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("principal"));
            BigDecimal interest = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("interest"));
            BigDecimal serviceFee = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("serviceFee"));
            BigDecimal bail = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("bail"));

            //获取差额
            PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail = plmsRepaymentScheduleDetailDao.queryScheduleDetailBeanByCode(code);
            //应还金额变化值 = 原金额 - 录入的（应还本金 + 应还利息 + 应还服务费 + 应还保证金）
            BigDecimal amountSub = MathUtil.sub(plmsRepaymentScheduleDetail.getAmount(),amount,GlobParam.SCALE_2);
            //应还本金变化值 = 原金额 - 录入的应还本金
            BigDecimal principalSub = MathUtil.sub(plmsRepaymentScheduleDetail.getPrincipal(),principal,GlobParam.SCALE_2);
            //应还利息变化值 = 原金额 - 录入的应还利息
            BigDecimal interestSub = MathUtil.sub(plmsRepaymentScheduleDetail.getInterest(),interest,GlobParam.SCALE_2);
            //应还服务费变化值 = 原金额 - 录入的应还服务费
            BigDecimal serviceFeeSub = MathUtil.sub(plmsRepaymentScheduleDetail.getServiceFee(),serviceFee,GlobParam.SCALE_2);
            //应还保证金变化值 = 原金额 - 录入的应还保证金
            BigDecimal bailSub = MathUtil.sub(plmsRepaymentScheduleDetail.getBail(),bail,GlobParam.SCALE_2);

            //取放款信息
            PaidInfo paidInfo = new PaidInfo();
            paidInfo.setCode(paidCode);
            paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfo);

            //取账户明细信息
            AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(paidInfo.getContractCode());

            //更新指定的还款计划明细表记录
            plmsRepaymentScheduleDetail.setAmount(amount);
            plmsRepaymentScheduleDetail.setPrincipal(principal);
            plmsRepaymentScheduleDetail.setInterest(interest);
            plmsRepaymentScheduleDetail.setServiceFee(serviceFee);
            plmsRepaymentScheduleDetail.setBail(bail);
            plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetail(plmsRepaymentScheduleDetail);

            //更新关联的还款计划表记录
            Map paramMap = new HashMap();
            paramMap.put("repaymentDate",repaymentDate);
            paramMap.put("paidCode",paidCode);
            Map scheduleMap = operationManagerDao.queryRepaymentScheduleByPaidCodeAndRepaymentDate(paramMap);//按数据逻辑，应该存在当前数据
            paramMap = new HashMap();
            //应还金额 = 原金额 - 应还金额变化值
            paramMap.put("amount",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("amount")),amountSub,GlobParam.SCALE_2));
            //应还本金 = 原金额 - 应还本金变化值
            paramMap.put("principal",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("principal")), principalSub, GlobParam.SCALE_2));
            //应还利息 = 原金额 - 应还利息变化值
            paramMap.put("interest",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("interest")), interestSub, GlobParam.SCALE_2));
            //应还服务费 = 原金额 - 应还服务费变化值
            paramMap.put("serviceFee",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("serviceFee")), serviceFeeSub, GlobParam.SCALE_2));
            //应还应还保证金 = 原金额 - 应还应还保证金变化值
            paramMap.put("bail",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("bail")), bailSub, GlobParam.SCALE_2));
            paramMap.put("code",scheduleMap.get("code"));
            plmsRepaymentScheduleDao.updateRepaymentSchedule(paramMap);

            //更新当前放款信息表记录
            paidInfo.setPaidAmount(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getPaidAmount()),principalSub,GlobParam.SCALE_2).toString());//原金额 - 应还本金变化值
            paidInfo.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getInterest()),interestSub,GlobParam.SCALE_2).toString());//原金额 - 应还利息变化值
            paidInfo.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getServiceFee()),serviceFeeSub,GlobParam.SCALE_2).toString());//原金额 - 应还服务费变化值
            paidInfo.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getBail()),bailSub,GlobParam.SCALE_2).toString());//原金额 - 应还保证金变化值
            plmsPaidInfoDao.updatePaidInfoByCodeOriginalData(paidInfo);

            //更新账户明细表记录
            accountDetail.setPrincipal(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getPrincipal()),principalSub,GlobParam.SCALE_2).toString());//原金额 - 应还本金变化值
            accountDetail.setRemainingQuota(MathUtil.strSub(accountDetail.getRemainingQuota(),accountDetail.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            accountDetail.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getInterest()),interestSub,GlobParam.SCALE_2).toString());//原金额 - 应还利息变化值
            accountDetail.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getServiceFee()),serviceFeeSub,GlobParam.SCALE_2).toString());//原金额 - 应还服务费变化值
            accountDetail.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getBail()),bailSub,GlobParam.SCALE_2).toString());//原金额 - 应还保证金变化值
            accountDetailDao.updateAccountDetailByCodeOriginalData(accountDetail);

            //更新账户汇总表记录
            Account account = new Account();
            account.setCode(accountDetail.getAccountCode());
            account.setPrincipal(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getPrincipal()),principalSub,GlobParam.SCALE_2).toString());//原金额 - 应还本金变化值
            account.setRemainingQuota(MathUtil.strSub(account.getRemainingQuota(),account.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            account.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getInterest()),interestSub,GlobParam.SCALE_2).toString());//原金额 - 应还利息变化值
            account.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getServiceFee()),serviceFeeSub,GlobParam.SCALE_2).toString());//原金额 - 应还服务费变化值
            account.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getBail()),bailSub,GlobParam.SCALE_2).toString());//原金额 - 应还保证金变化值
            accountDao.updateAccountByCodeOriginalData(account);
        }
    }

    /**
     * 处理删除记录
     * @param list
     */
    public void dealDeletedRepaymentDetailList(List list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            Map repaymentDetailMap = (Map)list.get(i);
            String repaymentDate = (String)repaymentDetailMap.get("repaymentDate");
            String paidCode = (String)repaymentDetailMap.get("paidCode");
            String code = (String)repaymentDetailMap.get("code");
            BigDecimal amount = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("amount"));
            BigDecimal principal = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("principal"));
            BigDecimal interest = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("interest"));
            BigDecimal serviceFee = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("serviceFee"));
            BigDecimal bail = CommonUtil.dealBigDecimalNull2Zero(repaymentDetailMap.get("bail"));

            PlmsRepaymentScheduleDetail plmsRepaymentScheduleDetail = plmsRepaymentScheduleDetailDao.queryScheduleDetailBeanByCode(code);

            //取放款信息
            PaidInfo paidInfo = new PaidInfo();
            paidInfo.setCode(paidCode);
            paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfo);

            //取账户明细信息
            AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(paidInfo.getContractCode());

            //更新指定的还款计划明细表记录 - 无效
            PlmsRepaymentScheduleDetail invalidPlmsRepaymentScheduleDetail = new PlmsRepaymentScheduleDetail();
            invalidPlmsRepaymentScheduleDetail.setCode(plmsRepaymentScheduleDetail.getCode());
            invalidPlmsRepaymentScheduleDetail.setStatus(GlobDict.paid_info_status_unvalid.getKey());
            plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetail(invalidPlmsRepaymentScheduleDetail);

            //更新关联的还款计划表记录
            Map paramMap = new HashMap();
            paramMap.put("repaymentDate",repaymentDate);
            paramMap.put("paidCode",paidCode);
            Map scheduleMap = operationManagerDao.queryRepaymentScheduleByPaidCodeAndRepaymentDate(paramMap);//按数据逻辑，应该存在当前数据
            paramMap = new HashMap();
            //应还金额 = 原金额 – 删除的还款计划明细表记录的应还金额
            paramMap.put("amount",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("amount")),amount,GlobParam.SCALE_2));
            //应还本金 = 原金额 – 删除的还款计划明细表记录的应还本金
            paramMap.put("principal",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("principal")), principal, GlobParam.SCALE_2));
            //应还利息 = 原金额 – 删除的还款计划明细表记录的应还利息
            paramMap.put("interest",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("interest")), interest, GlobParam.SCALE_2));
            //应还服务费 = 原金额 – 删除的还款计划明细表记录的应还服务费
            paramMap.put("serviceFee",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("serviceFee")), serviceFee, GlobParam.SCALE_2));
            //应还保证金 = 原金额 – 删除的还款计划明细表记录的应还保证金
            paramMap.put("bail",MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("bail")), bail, GlobParam.SCALE_2));
            paramMap.put("code",scheduleMap.get("code"));
            if(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(scheduleMap.get("amount")),amount,GlobParam.SCALE_2).compareTo(GlobParam.ZERO) == 0){
                paramMap.put("status",GlobDict.paid_info_status_unvalid.getKey());
            }
            plmsRepaymentScheduleDao.updateRepaymentSchedule(paramMap);

            //更新当前放款信息表记录
            paidInfo.setPaidAmount(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getPaidAmount()),principal,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还本金
            paidInfo.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还利息
            paidInfo.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还服务费
            paidInfo.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(paidInfo.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还保证金
            plmsPaidInfoDao.updatePaidInfoByCodeOriginalData(paidInfo);

            //更新账户明细表记录
            accountDetail.setPrincipal(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getPrincipal()),principal,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还本金
            accountDetail.setRemainingQuota(MathUtil.strSub(accountDetail.getRemainingQuota(),accountDetail.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            accountDetail.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还利息
            accountDetail.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还服务费
            accountDetail.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(accountDetail.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还保证金
            accountDetailDao.updateAccountDetailByCodeOriginalData(accountDetail);

            //更新账户汇总表记录
            Account account = new Account();
            account.setCode(accountDetail.getAccountCode());
            account.setPrincipal(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getPrincipal()),principal,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还本金
            account.setRemainingQuota(MathUtil.strSub(account.getRemainingQuota(),account.getPrincipal(),GlobParam.SCALE_2).toString());//授信金额 – 已贷本金
            account.setInterest(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getInterest()),interest,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还利息
            account.setServiceFee(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getServiceFee()),serviceFee,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还服务费
            account.setBail(MathUtil.sub(CommonUtil.dealBigDecimalNull2Zero(account.getBail()),bail,GlobParam.SCALE_2).toString());//原金额 - 删除的还款计划明细表记录的应还保证金
            accountDao.updateAccountByCodeOriginalData(account);
        }
    }


}
