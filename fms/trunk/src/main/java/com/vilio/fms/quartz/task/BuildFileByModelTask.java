package com.vilio.fms.quartz.task;

import com.vilio.fms.common.service.QuartzService;
import com.vilio.fms.quartz.service.BuildFileByModelService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by dell on 2017/7/6/0006.
 */
public class BuildFileByModelTask extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(BuildFileByModelTask.class);
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            QuartzService quartzService = (QuartzService) skedCtx.get("buildFileByModelService");
            quartzService.excute();

        } catch (Exception ex) {
            logger.info("\n\n\n通过模板生成文件--定时任务执行出现异常:" + ex + "\n\n\n");
        }
    }
}
