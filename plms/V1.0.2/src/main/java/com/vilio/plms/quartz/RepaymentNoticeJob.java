package com.vilio.plms.quartz;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import com.vilio.plms.service.quartz.RepaymentNoticeService;
import com.vilio.plms.service.quartz.RepaymentOverdueNoticeService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by dell on 2017/9/18.
 */
public class RepaymentNoticeJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(RepaymentNoticeJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{

            RepaymentNoticeService repaymentNoticeService = (RepaymentNoticeService)  SpringContextUtil.getBean("repaymentNoticeService");
            repaymentNoticeService.execute();

            logger.info("RepaymentNoticeJob还款提醒定时任务执行一次");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
