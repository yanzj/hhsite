package com.vilio.plms.quartz;

import com.vilio.plms.service.quartz.OverdueService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 入账批量上载任务
 */
public class ReceiptsRecordUploadJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(ReceiptsRecordUploadJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            OverdueService overdueService = (OverdueService) skedCtx.get("fundManagerService");
            overdueService.execute();

            logger.info("入账批量上载任务执行一次");
        }catch(Exception ex){
            logger.info("入账批量上载任务执行出现异常:" + ex);
        }
    }
}
