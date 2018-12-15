package com.vilio.bps.quartz.service;

import com.vilio.bps.inquiry.worldunion.service.impl.WULoginImpl;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;

/**
 * Created by dell on 2017/6/20.
 */
public class RegularCatchWUTokenTask  extends QuartzJobBean {
    Logger logger = Logger.getLogger(RegularCatchWUTokenTask.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            WULoginImpl wULoginImpl = (WULoginImpl) skedCtx.get("wULoginImpl");
            Map paramMap = wULoginImpl.login();
            logger.info("获取世联token定时任务执行一次");
        }catch(Exception ex){
            logger.info("获取世联token定时任务执行出现异常:" + ex);
        }
    }
}
