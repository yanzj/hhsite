package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.dao.CommDao;
import com.vilio.plms.dao.PlmsRepaymentScheduleDetailDao;
import com.vilio.plms.dao.SysInfoParamDao;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.pojo.SysParam;
import com.vilio.plms.service.base.PayScheduleDetailForContract;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import com.vilio.plms.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 扣款业务
 */
@Service("payRepaymentScheduleDetailService")
public class PayRepaymentScheduleDetailServiceImpl implements PayRepaymentScheduleDetailService {

    private static Logger logger = Logger.getLogger(PayRepaymentScheduleDetailService.class);

    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;
    @Resource
    PayScheduleDetailForContract payScheduleDetailForContract;
    @Resource
    CommDao commDao;

    @Resource
    SysInfoParamDao sysInfoParamDao;

    public void execute() throws Exception {
        //当前时间
        Date currnetDate = new Date();
        String beforeHappenDate = DateUtil.getDateBefore(currnetDate, 24, DateUtil.DATE_PATTERN2);
        //获取逾期定时任务的执行信息  if (执行时间不是昨天)  则告警昨天的逾期未正常执行  不执行扣款操作
        SysParam sysParam_overdue = commDao.getSysParam(GlobDict.calculate_overdue_interest_item_id.getKey());
        if (null == sysParam_overdue) {
            logger.error("未获取到逾期定时任务信息，扣款任务暂不执行！");
            return;
        }
        if (!beforeHappenDate.equals(sysParam_overdue.getExecuteTime())) {
            logger.error("未获取到扣款执行日" + beforeHappenDate + "日的逾期定时任务信息，扣款任务暂不执行！");
            return;
        }
        //开始进行扣款操作
        //获取需要扣款的合同
        Map requestMap = new HashedMap();
        requestMap.put("today", DateUtil.getCurrentDatePattern2());
        List<Map> contractList = plmsRepaymentScheduleDetailDao.queryAllNeedPayContract(requestMap);
        if (null == contractList || contractList.size() < 1) {
            //更新扣款操作定时任务数据库时间
            Map sysMap = new HashedMap();
            sysMap.put("itemId",GlobDict.pay_schedule_job_item_id.getKey());
            sysMap.put("executeTime",DateUtil.getCurrentDateTime());
            sysInfoParamDao.updateSysparamInfoExecuteTime(sysMap);
            logger.info("PayRepaymentScheduleDetailService没有要扣款的还款计划明细");
            return;
        }
        //获取账户类操作锁 if(被锁定){  不执行扣款操作，等待下次执行  }   else{ 上锁，同时更新扣款定时任务执行时间 }   wangxf by 20170829 修改成切面
        //扣款
        payScheduleDetailForContract.payContractList(contractList, currnetDate);
    }

}
