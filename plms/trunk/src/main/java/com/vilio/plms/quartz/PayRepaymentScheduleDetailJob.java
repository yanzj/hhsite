package com.vilio.plms.quartz;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 扣款定时任务
 */
public class PayRepaymentScheduleDetailJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(PayRepaymentScheduleDetailJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            PayRepaymentScheduleDetailService payRepaymentScheduleDetailService = (PayRepaymentScheduleDetailService) skedCtx.get("payRepaymentScheduleDetailService");
            payRepaymentScheduleDetailService.execute();

            logger.info("PayRepaymentScheduleDetailJob扣款定时任务执行一次");
        } catch (ErrorException e) {
            e.printStackTrace();
            if (ReturnCode.ACCOUNT_LOCK.equals(e.getErroCode())) {
                return;
            }
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("扣款定时任务", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("扣款定时任务", e.getMessage());
        }
    }
}
