package com.vilio.plms.quartz;

import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.BmsSynchronizationBaseDataService;
import com.vilio.plms.service.quartz.OverdueService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * Created by martin on 2017/8/29.
 */
public class BmsSynchronizationBaseDataJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(BmsSynchronizationBaseDataJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            logger.info("获取bms基础数据定时任务执行开始");
//            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            BmsSynchronizationBaseDataService bmsSynchronizationBaseDataService = (BmsSynchronizationBaseDataService) SpringContextUtil.getBean("bmsSynchronizationBaseDataService");
            bmsSynchronizationBaseDataService.execute();

            logger.info("获取bms基础数据定时任务执行完成");
        }catch(Exception ex){
            logger.info("获取bms基础数据定时任务执行出现异常:" + ex);
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("获取bms基础数据定时任务执行", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
