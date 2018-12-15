package com.vilio.plms.service.base.impl;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.Account;
import com.vilio.plms.pojo.BorrowApply;
import com.vilio.plms.pojo.PaidInfo;
import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;
import com.vilio.plms.service.Plms100100;
import com.vilio.plms.service.base.*;
import com.vilio.plms.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/8/30.
 */
@Service
public class PaidInfoServiceImpl implements PaidInfoService {
    Logger logger = Logger.getLogger(PaidInfoServiceImpl.class);

    @Resource
    PlmsContractInfoDao plmsContractInfoDao;
    @Resource
    PlmsRepaymentDateDao plmsRepaymentDateDao;
    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
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
    private PayScheduleDetailForContract payScheduleDetailForContract;
    @Resource
    private RecomputationPaymentAndOverdueService recomputationPaymentAndOverdueService;
    @Resource
    CommonService commonService;

    /**
     * 放款删除
     * @param paidCode
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void cancelPaidInfo(String paidCode) throws Exception {
        if(null == paidCode){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "传入参数不能为空！");
        }
        /** 获取放款信息 **/
        Map paidInfoMap = plmsPaidInfoDao.getPaidInfoAndContractByPaidCode(paidCode);
        if(null == paidInfoMap){
            logger.info("未找到paidCode=" + paidCode + "对应的放款信息！");
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "未找到paidCode=" + paidCode + "对应的放款信息！");
        }
        //合同信息
        String contractCode = (String) paidInfoMap.get("contractCode");
        //放款日期
        Date paidTime = (Date) paidInfoMap.get("paidTime");
        //放款日期(yyyy-MM-dd)
        String strPaidTime =  DateUtil.formatDateTime(paidTime,DateUtil.DATE_PATTERN2);
        //借款表Code
        String borrowCode = (String) paidInfoMap.get("borrowCode");
        //借款借据表
        String iouCode = (String) paidInfoMap.get("iouCode");
        //放款金额
        BigDecimal paidAmount = (BigDecimal) paidInfoMap.get("paidAmount");
        Map sumMap = plmsRepaymentScheduleDetailDao.sumScheduleDetailByPaidCode(paidCode);
        //应还利息
        BigDecimal totalInterest = (BigDecimal) sumMap.get("interest");
        //应还服务费
        BigDecimal totalServiceFee = (BigDecimal) sumMap.get("serviceFee");
        //应还保证金
        BigDecimal totalBail = (BigDecimal) sumMap.get("bail");
        /**
         回滚逾期，回滚扣款
         */
        String batchCode = commonService.getUUID();
        rollBackPaymentAndOverdueService.mainJob(strPaidTime, contractCode,batchCode);
        /**
         删除放款
         */
        //1.更新借款信息为放款中
        BorrowApply borrowApply = new BorrowApply();
        borrowApply.setCode(borrowCode);
        borrowApply.setModifyDate(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_PATTERN3));
        borrowApply.setApplyStatus(GlobDict.order_status_loan_ing.getKey());
        borrowApplyDao.updateBorrowApplyForStatus(borrowApply);
        //2.更新放款信息为无效
        PaidInfo paidInfo = new PaidInfo();
        paidInfo.setCode(paidCode);
        paidInfo.setStatus(GlobDict.paid_info_status_unvalid.getKey());
        plmsPaidInfoDao.updatePaidInfoByCode(paidInfo);
        //3.更新借款借据表为无效
        Map iouMap = new HashedMap();
        iouMap.put("status", GlobDict.iou_valid.getKey());
        iouMap.put("code", iouCode);
        iouDao.updateIouStats(iouMap);
        //4.更新与该条放款信息表记录关联的所有还款计划明细表记录  无效
        Map detailMap = new HashedMap();
        detailMap.put("paidCode", paidCode);
        detailMap.put("status",GlobDict.repayment_schedule_detail_status_unvalid.getKey());
        plmsRepaymentScheduleDetailDao.updateRepaymentScheduleDetailStatusByPaidCode(detailMap);
        //5.更新还款计划表
        List<Map> detailList = plmsRepaymentScheduleDetailDao.queryScheduleDetailByPaidCode(paidCode);
        for(Map detail: detailList){
            //每笔还款计划明细对应的还款计划减去所有应还总额、应还本金、应还利息、应还服务费，应还保证金 若总额=0则置为无效
            Map scheduleMap = new HashedMap();
            scheduleMap.put("code", detail.get("scheduleCode"));
            scheduleMap.put("principal", ((BigDecimal)detail.get("principal")).multiply(new BigDecimal(-1)));
            scheduleMap.put("interest", ((BigDecimal)detail.get("interest")).multiply(new BigDecimal(-1)));
            scheduleMap.put("serviceFee", ((BigDecimal)detail.get("serviceFee")).multiply(new BigDecimal(-1)));
            scheduleMap.put("bail", ((BigDecimal)detail.get("bail")).multiply(new BigDecimal(-1)));
            plmsRepaymentScheduleDetailDao.updateSecheduleAmountByDetail(scheduleMap);
            scheduleMap = null;
        }
        //重新排序该合同下还款计划
        Map scheduleMap = new HashedMap();
        scheduleMap.put("contractCode", contractCode);
        List<PlmsRepaymentScheduleBean> listScheduleBean = plmsRepaymentScheduleDao.getScheduleInfoByContractCode(scheduleMap);
        int totalPeriod = listScheduleBean.size();
        for(int i = 0; i < totalPeriod; i++){
            PlmsRepaymentScheduleBean bean = listScheduleBean.get(i);
            bean.setTotalPeriod(totalPeriod);
            bean.setCurrentPeriod(i + 1);
        }
        plmsRepaymentScheduleDao.updateScheduleList(listScheduleBean);
        //6.更新账户汇总表和账户明细表
        //已贷本金 = 原本金 - 放款额度
        //应还利息
        //应还服务费
        Map accountDetailMap = new HashedMap();
        accountDetailMap.put("principal", paidAmount);
        accountDetailMap.put("interest", totalInterest);
        accountDetailMap.put("serviceFee", totalServiceFee);
        accountDetailMap.put("bail",totalBail);
        accountDetailDao.updatelAccountDetailByContractCode(accountDetailMap);
        Account account = accountDao.getAccountByContractCode(contractCode);
        Account requAccount = new Account();
        requAccount.setCode(account.getCode());
        requAccount.setPrincipal("-" + paidAmount.toString());
        requAccount.setInterest("-" + totalInterest.toString());
        requAccount.setServiceFee("-" + totalServiceFee.toString());
        requAccount.setBail("-"+totalBail.toString());
        int upAccountFlag = accountDao.updateAccountByCode(requAccount);
        //重新计算预期和放款
        //今天扣昨天的款，今天生成昨天的逾期，所以入账日期加一天
        String paymentDate = DateUtil.getDaysDate(DateUtil.formatDateTime(paidTime, DateUtil.DATE_PATTERN), "1");
        //yyyy-MM-dd
        recomputationPaymentAndOverdueService.mainJob(DateUtil.formatDateTime(DateUtil.parseDate(paymentDate), DateUtil.DATE_PATTERN2), contractCode,batchCode);
    }


}
