package com.vilio.bps.util;

import java.text.ParseException;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by we on 2017/2/28.
 */
public class QuartzUtil {
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "group1";
    private static String TRIGGER_GROUP_NAME = "trigger1";


//    /** *//**
//     *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
//     * @param jobName 任务名
//     * @param jobClass  任务类
//     * @param time    时间设置，参考quartz说明文档
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void addJob(Class jobClass,String jobName, String time)
//            throws SchedulerException, ParseException {
//        Scheduler sch = sf.getScheduler();
//        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,JOB_GROUP_NAME).build();
//        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1",TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).s
//    }

    /** *//**
     * 添加一个定时任务
     * @param jobClass 任务类
     * @param jobName 任务名
     * @param triggerName  触发器名
     * @param time    时间设置，参考quartz说明文档
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void addJob(Class jobClass,String jobName,
                              String triggerName,String time,JobDataMap data)
            throws SchedulerException, ParseException{
        Scheduler sched = sf.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName,JOB_GROUP_NAME).setJobData(data).build();//任务名，任务组，任务执行类
        Trigger trigger;
        //触发器
        if(null != time&& !"".equals(time)) {
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).startNow().build();//触发器名,触发器组
        }else{
            trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).startNow().build();//触发器名,触发器组
        }
        System.out.println(jobDetail);
        sched.scheduleJob(jobDetail,trigger);
        if(!sched.isShutdown())
            sched.start();
    }

//    /** *//**
//     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
//     * @param jobName
//     * @param time
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void modifyJobTime(String jobName,String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
//        if(trigger != null){
//            trigger.getScheduleBuilder();
//            sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);
//        }
//    }
//
//    /** *//**
//     * 修改一个任务的触发时间
//     * @param triggerName
//     * @param triggerGroupName
//     * @param time
//     * @throws SchedulerException
//     * @throws ParseException
//     */
//    public static void modifyJobTime(String triggerName,String triggerGroupName,
//                                     String time)
//            throws SchedulerException, ParseException{
//        Scheduler sched = sf.getScheduler();
//        Trigger trigger =  sched.getTrigger(triggerName,triggerGroupName);
//        if(trigger != null){
//            CronTrigger  ct = (CronTrigger)trigger;
//            //修改时间
//            ct.setCronExpression(time);
//            //重启触发器
//            sched.resumeTrigger(triggerName,triggerGroupName);
//        }
//    }
//
//    /** *//**
//     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
//     * @param jobName
//     * @throws SchedulerException
//     */
//    public static void removeJob(String jobName)
//            throws SchedulerException{
//        Scheduler sched = sf.getScheduler();
//        sched.pauseTrigger(jobName,TRIGGER_GROUP_NAME);//停止触发器
//        sched.unscheduleJob(jobName,TRIGGER_GROUP_NAME);//移除触发器
//        sched.deleteJob(jobName,JOB_GROUP_NAME);//删除任务
//    }
//
//    /** *//**
//     * 移除一个任务
//     * @param jobName
//     * @param jobGroupName
//     * @param triggerName
//     * @param triggerGroupName
//     * @throws SchedulerException
//     */
//    public static void removeJob(String jobName,String jobGroupName,
//                                 String triggerName,String triggerGroupName)
//            throws SchedulerException{
//        Scheduler sched = sf.getScheduler();
//        sched.pauseTrigger(triggerName,triggerGroupName);//停止触发器
//        sched.unscheduleJob(triggerName,triggerGroupName);//移除触发器
//        sched.deleteJob(jobName,jobGroupName);//删除任务
//    }
}
