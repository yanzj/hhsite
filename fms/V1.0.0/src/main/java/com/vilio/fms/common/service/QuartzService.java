package com.vilio.fms.common.service;

import org.apache.log4j.Logger;

/**
 * 所有服务的父类
 */
public class QuartzService {

    private static Logger logger = Logger.getLogger(QuartzService.class);

    public void excute() throws Exception {
        logger.info("\n\n\n-----------------------------定时任务==》》" + getQuartzDescription() + "==》开始执行...............");
        subExcute();
        logger.info("-----------------------------定时任务==》》" + getQuartzDescription() + "==》》执行完毕！！！！！！！！\n\n\n");
        return ;
    }

    /**
     * 各个接口自己的实现
     * @return
     * @throws Exception
     */
    public void subExcute() throws Exception {
    }
    /**
     * 接口描述
     * @return
     */
    public String getQuartzDescription() {
        return "";
    }

}
