package com.vilio.bps.quartz.service;

import com.vilio.bps.inquiry.service.AutoGetCompanyPriceService;
import com.vilio.bps.inquiry.service.CheckApplyStatusService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by dell on 2017/6/17.
 */
public class CheckApplyStatusTask extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(CheckApplyStatusTask.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            CheckApplyStatusService checkApplyStatusService = (CheckApplyStatusService) skedCtx.get("checkApplyStatusService");
            checkApplyStatusService.execute();

            logger.info("检查过期的询价并更新状态，同步外系统相关数据定时任务执行一次");
        }catch(Exception ex){
            logger.info("检查过期的询价并更新状态，同步外系统相关数据定时任务执行出现异常:" + ex);
        }
    }
}
