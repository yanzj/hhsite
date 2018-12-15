package com.vilio.plms.quartz;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.PaidInfoDeleteService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 类名： PaidInfoDeleteJob<br>
 * 功能： 放款删除定时任务<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月30日<br>
 * 作者： zx<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class PaidInfoDeleteJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(PaidInfoDeleteJob.class);

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("放款删除定时任务启动！");
        PaidInfoDeleteService paidInfoDeleteService = (PaidInfoDeleteService) SpringContextUtil.getBean("paidInfoDeleteService");
        try {
            paidInfoDeleteService.execute();
        } catch (ErrorException e) {
            e.printStackTrace();
            if (ReturnCode.ACCOUNT_LOCK.equals(e.getErroCode())) {
                return;
            }
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("放款删除定时任务", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("放款删除定时任务", e.getMessage());
        }
        logger.info("放款删除定时任务结束！");
    }
}
