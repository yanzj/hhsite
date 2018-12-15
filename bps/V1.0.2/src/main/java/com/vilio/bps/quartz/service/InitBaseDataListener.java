package com.vilio.bps.quartz.service;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.worldunion.service.impl.WULoginImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * spring初始化结束后,执行onApplicationEvent方法
 * 此处初始化避免了初始化static时 bean还没注入的问题
 * @author
 */
@Component
public class InitBaseDataListener implements  ApplicationListener<ContextRefreshedEvent>   {
    Logger logger = Logger.getLogger(InitBaseDataListener.class);
    @Resource
    CommonService commonService;
    @Resource
    WULoginImpl wULoginImpl;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null)
        {
            logger.info("初始化世联token......");
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            try {
                Map paramMap = wULoginImpl.login();
            } catch (Exception e) {
                logger.info("初始化世联token异常：" + e);
            }
            logger.info("初始化世联token结束");
        }
    }
}
