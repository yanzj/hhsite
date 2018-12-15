package com.vilio.pcfs.service.quartz;

import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 类名： FilterCreateMsgJob<br>
 * 功能：创建和过滤待发送的信息<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class FilterCreateMsgJob extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(FilterCreateMsgJob.class);

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("待发送消息过滤定时任务启动");
        BaseService quartz = (BaseService) SpringContextUtil.getBean("filterCreateMsgService");
        try {
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("待发送消息过滤定时任务结束");
    }

}
