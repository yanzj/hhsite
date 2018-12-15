package com.vilio.plms.service.base;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 类名： RollbackAndRecomputationProxy<br>
 * 功能：回滚和重新计算切面类<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月28日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Component
public class RollbackAndRecomputationProxy {

    private static Logger logger = Logger.getLogger(RollbackAndRecomputationProxy.class);

    @Resource
    private CommonService commonService;

    @Resource
    private RollBackPaymentAndOverdueService rollBackPaymentAndOverdueService;

    @Resource
    private RecomputationPaymentAndOverdueService recomputationPaymentAndOverdueService;


    /**
     * 修改账务类标识（加锁）
     *
     * @throws Throwable
     */
    public void updateAccountLockNoCheck() throws Throwable {
        //先进行账务类操作锁定操作
        //更改系统表账户类操作锁定
        int ret = commonService.setItemVal("ACCOUNT_LOCK", GlobDict.account_lock.getKey(), "");
        if (ret == 0) {
            //其他进程在做账务类操作
            throw new ErrorException(ReturnCode.ACCOUNT_LOCK, "");
        }
        if (ret < 0) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "系统异常");
        }
        logger.info("执行修改账务类标识完成");
    }


    /**
     * 修改账务类标识（加锁）
     *
     * @throws Throwable
     */
    public void updateAccountLock() throws Throwable {
        //获取当前扣款和逾期系统参数，判断执行的日期是否等于当前日期，不等于，不能进行回滚重新计算操作
        String payScheduleJob = commonService.getItemTime("PAY_SCHEDULE_JOB");
        String calculateOverdueInterestJob = commonService.getItemTime("CALCULATE_OVERDUE_INTEREST_JOB");
        //判断是否和当前日期相等
        if (DateUtil.dateCompareNow(payScheduleJob) != 0) {
            //扣款日期不等于当前日期
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "扣款执行到的日期不等于当前日期，不能进行账务类操作");
        }
        if (DateUtil.dateCompareNow(calculateOverdueInterestJob) != 0) {
            //扣款日期不等于当前日期
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "逾期执行到的日期不等于当前日期，不能进行账务类操作");
        }
        //先进行账务类操作锁定操作
        //更改系统表账户类操作锁定
        int ret = commonService.setItemVal("ACCOUNT_LOCK", GlobDict.account_lock.getKey(), "");
        if (ret == 0) {
            //其他进程在做账务类操作
            throw new ErrorException(ReturnCode.ACCOUNT_LOCK, "");
        }
        if (ret < 0) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "系统异常");
        }
        logger.info("执行修改账务类标识完成");
    }

    /**
     * 修改账务类标识（解锁）
     *
     * @throws Throwable
     */
    public void updateAccountUnLock() throws Throwable {
        //更改系统表账户类操作锁定
        int ret = commonService.setItemVal("ACCOUNT_LOCK", GlobDict.account_unlock.getKey(), "");
        if (ret == 0) {
            //其他进程在做账务类操作
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "不能重复解锁");
        }
        if (ret < 0) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "系统异常");
        }
        logger.info("执行修改账务类标识完成");
    }

    /**
     * 修改账务类标识（解锁）
     *
     * @throws Throwable
     */
    public void updateAccountUnLockForParam(String rollBachDate, String contractCode, String batchCode) throws Throwable {
        updateAccountUnLock();
    }


    /**
     * 回滚扣款和逾期（需要传两个参数，yyyy-MM-DD 回滚到哪一天，合同编码）
     *
     * @throws Throwable
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void rollBackPaymentAndOverdue(String rollBachDate, String contractCode, String batchCode) throws Throwable {
        if (rollBachDate.length() != 10) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "日期格式不正确");
        }
        rollBackPaymentAndOverdueService.mainJob(rollBachDate, contractCode, batchCode);
        logger.info("回滚扣款和逾期完成");
    }


    /**
     * 锁定标识、回滚扣款和逾期（需要传两个参数，yyyy-MM-DD 回滚到哪一天，合同编码）
     *
     * @throws Throwable
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void accountLockRollBackPaymentAndOverdue(String rollBachDate, String contractCode, String batchCode) throws Throwable {
        updateAccountLock();
        rollBackPaymentAndOverdue(rollBachDate, contractCode, batchCode);
    }

    /**
     * 解锁、重新计算扣款和逾期（需要传两个参数，yyyy-MM-DD 回滚到哪一天就传哪一天，方法里面自动加一天，合同编码）
     *
     * @throws Throwable
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void accountUnLockRecomputationPaymentAndOverdue(String rollBachDate, String contractCode, String batchCode) throws Throwable {
        recomputationPaymentAndOverdue(rollBachDate, contractCode, batchCode);
        updateAccountUnLock();
    }


    /**
     * 重新计算扣款和逾期（需要传两个参数，yyyy-MM-DD 回滚到哪一天就传哪一天，方法里面自动加一天，合同编码）
     *
     * @throws Throwable
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void recomputationPaymentAndOverdue(String rollBachDate, String contractCode, String batchCode) throws Throwable {
        if (rollBachDate.length() != 10) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "日期格式不正确");
        }
        String paymentDate = DateUtil.getDaysDate2(rollBachDate, 1);
        recomputationPaymentAndOverdueService.mainJob(paymentDate, contractCode, batchCode);
        logger.info("重新计算扣款和逾期完成");
    }

}
