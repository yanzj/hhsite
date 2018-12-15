package com.vilio.pcfs.service.quartz;

import com.vilio.pcfs.service.LoginComn;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： ClearLoginNum<br>
 * 功能：定时清空session（暂时废弃）<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class ClearLoginNum extends QuartzJobBean {
    private static Logger logger = Logger.getLogger(ClearLoginNum.class);

    private LoginComn loginComn;

    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            //获取JobExecutionContext中的service对象
            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            //获取SchedulerContext中的service
            //这里的service就是通过配置文件配置的
//            loginComn = (LoginComn) skedCtx.get("loginComn");
            loginComn = (LoginComn) SpringContextUtil.getBean("loginComn");
            //获取清空session阈值
            String clearLoginTimestamp = skedCtx.get("clearLoginTimestamp").toString();
            Map<String, Object> deleteParam = new HashMap<String, Object>();
            deleteParam.put("systemTimestamp", String.valueOf(System.currentTimeMillis()));
            deleteParam.put("clearLoginTimestamp", clearLoginTimestamp);
            int ret = loginComn.deleteSession(deleteParam);
            if (ret == 0) {
                logger.error("删除数为0");
            } else if (ret <= 0) {
                logger.error("删除失败");
            }
            logger.info("清理登录信息定时任务执行一次");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("清理登录信息定时任务执行出现异常:" + ex.getMessage());
        }
    }

}
