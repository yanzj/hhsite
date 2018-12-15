package com.vilio.nlbs.quartz.service;

import com.vilio.nlbs.login.service.LoginService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by xiezhilei on 2017/1/9.
 */
public class ClearLoginNum extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(ClearLoginNum.class);

    private LoginService loginService;

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            //获取JobExecutionContext中的service对象
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            //获取SchedulerContext中的service
            //这里的service就是通过配置文件配置的
            loginService = (LoginService)skedCtx.get("loginService");
            //loginService.deleteNlbsLoginAll();
            logger.info("清理登录信息定时任务执行一次");
        }catch(Exception ex){
            logger.info("清理登录信息定时任务执行出现异常:" + ex.getMessage());
        }
    }

}
