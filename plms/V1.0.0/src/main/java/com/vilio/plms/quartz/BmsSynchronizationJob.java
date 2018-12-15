package com.vilio.plms.quartz;

import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.BmsSynchronizationBaseDataService;
import com.vilio.plms.service.quartz.BmsSynchronizeService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * Created by martin on 2017/9/6.
 */
public class BmsSynchronizationJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(BmsSynchronizationJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            logger.info("开始同步bms数据定时任务");
//            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            BmsSynchronizeService bmsSynchronizeService = (BmsSynchronizeService) SpringContextUtil.getBean("bmsSynchronizeService");
            bmsSynchronizeService.execute();
            logger.info("完成同步bms数据定时任务");
        }catch(Exception ex){
            logger.info("同步bms数据定时任务执行出现异常:" + ex);
            ex.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("同步bms数据定时任务", ex.getMessage());
        }
    }
}
