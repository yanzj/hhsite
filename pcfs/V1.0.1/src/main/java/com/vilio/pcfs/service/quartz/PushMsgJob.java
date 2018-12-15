package com.vilio.pcfs.service.quartz;

import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 类名： pushMsgJob<br>
 * 功能：定时推送消息<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class PushMsgJob extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(PushMsgJob.class);

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("推送消息定时任务启动");
        BaseService quartz = (BaseService) SpringContextUtil.getBean("pushMsgService");
        try {
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("推送消息定时任务结束");
    }

}
