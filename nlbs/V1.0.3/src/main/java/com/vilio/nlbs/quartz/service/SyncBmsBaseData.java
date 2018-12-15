package com.vilio.nlbs.quartz.service;

import com.vilio.nlbs.bms.service.BmsSyncDataService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by dell on 2017/6/2.
 */
public class SyncBmsBaseData extends QuartzJobBean{
    private static Logger logger = Logger.getLogger(SyncBmsBaseData.class);
    private BmsSyncDataService bmsSyncDataService;

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            logger.info("同步核心系统基础数据定时任务执行开始");
            //获取JobExecutionContext中的service对象
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            //获取SchedulerContext中的service
            bmsSyncDataService = (BmsSyncDataService) skedCtx.get("bmsSyncDataService");
            //具体业务实现


            //这里的service就是通过配置文件配置的
            logger.info("同步核心系统基础数据定时任务执行一次");
        }catch(Exception ex){
            logger.info("同步核心系统基础数据定时任务执行出现异常:" + ex.getMessage());
        }

    }
}
