package com.vilio.plms.quartz;

import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 类名： PushSmsJob<br>
 * 功能：短信推送定时任务<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class PushSmsJob extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(PushSmsJob.class);

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("短信推送定时任务启动！");
        try {
            BaseService quartz = (BaseService) SpringContextUtil.getBean("pushSmsService");
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("短信推送定时任务结束！");
    }

}
