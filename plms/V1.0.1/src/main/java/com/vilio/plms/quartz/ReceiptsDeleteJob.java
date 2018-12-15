package com.vilio.plms.quartz;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 类名： ReceiptsJob<br>
 * 功能：资金入账删除定时任务<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月8日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class ReceiptsDeleteJob extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(ReceiptsDeleteJob.class);

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("资金入账删除定时任务启动！");
        BaseService quartz = (BaseService) SpringContextUtil.getBean("receiptsDeleteService");
        try {
            quartz.execute();
        } catch (ErrorException e) {
            e.printStackTrace();
            if (ReturnCode.ACCOUNT_LOCK.equals(e.getErroCode())) {
                return;
            }
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("资金入账删除定时任务", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("资金入账删除定时任务", e.getMessage());
        }
        logger.info("资金入账删除定时任务结束！");
    }

}
