package com.vilio.plms.quartz;

import com.vilio.plms.service.quartz.RepaymentNoticeService;
import com.vilio.plms.service.quartz.RepaymentOverdueNoticeService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by dell on 2017/9/20.
 */
public class RepaymentOverdueNoticeJob extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(RepaymentOverdueNoticeJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{

            RepaymentOverdueNoticeService repaymentOverdueNoticeService = (RepaymentOverdueNoticeService)  SpringContextUtil.getBean("repaymentOverdueNoticeService");
            repaymentOverdueNoticeService.execute();

            logger.info("RepaymentOverdueNoticeJob逾期提醒定时任务执行一次");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
