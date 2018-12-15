package com.vilio.plms.quartz;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.BmsSynchronizeService;
import com.vilio.plms.service.quartz.OverdueService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * 逾期定时任务
 */
public class OverdueJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(PayRepaymentScheduleDetailJob.class);

    @Resource
    CommonService commonService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
//            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            OverdueService overdueService = (OverdueService) SpringContextUtil.getBean("overdueService");
            overdueService.execute();

            logger.info("逾期定时任务执行一次");
        } catch (ErrorException e) {
            e.printStackTrace();
            if (ReturnCode.ACCOUNT_LOCK.equals(e.getErroCode())) {
                return;
            }
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("逾期定时任务", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("逾期定时任务", e.getMessage());
        }
    }
}
