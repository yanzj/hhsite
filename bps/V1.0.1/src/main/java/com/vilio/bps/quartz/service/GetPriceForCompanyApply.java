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
 * 轮巡评估中的公司询价请求获取评估值
 */
public class GetPriceForCompanyApply  extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(GetPriceForCompanyApply.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            AutoGetCompanyPriceService autoGetCompanyPriceService = (AutoGetCompanyPriceService) skedCtx.get("autoGetCompanyPriceService");
            autoGetCompanyPriceService.execute();

            logger.info("去询价公司获取评估中的订单的结果值定时任务执行一次");
        }catch(Exception ex){
            logger.info("去询价公司获取评估中的订单的结果值定时任务执行出现异常:" + ex);
        }
    }
}
