package com.vilio.custom.quartz;

import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.custom.dao.CustomUserDao;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CustomUnLockJob<br>
 * 功能：定时解锁<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class CustomUnLockJob extends QuartzJobBean {

    private static Logger logger = Logger.getLogger(CustomUnLockJob.class);

    /**
     *定时任务主方法
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("custom解锁定时任务开始");
        //获取JobExecutionContext中的service对象
        try {
            SchedulerContext skedCtx =  jobExecutionContext.getScheduler().getContext();
            //获取SchedulerContext中的service
            //这里的service就是通过配置文件配置的
            CustomUserDao customUserDao = (CustomUserDao) skedCtx.get("customUserDao");
            String unLockTime = GlobParam.customHashLockTime;
            Map updateParam = new HashMap();
            updateParam.put("unHashLock", GlobDict.un_hash_lock.getKey());
            updateParam.put("hashLock", GlobDict.hash_lock.getKey());
            updateParam.put("systemTimestamp", String.valueOf(System.currentTimeMillis()));
            updateParam.put("unLockTime", unLockTime);
            updateParam.put("loginError", 0);
            //锁定的用户超过阈值解锁
            int ret = customUserDao.updateUnLock(updateParam);
            if (ret>0){
                logger.info("解锁用户数："+ret);
            }else if (ret == 0 ){
                logger.info("无锁定用户"+ret);
            }else{
                logger.error("解锁报错："+ret);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        logger.info("custom解锁定时任务结束");
    }

}
