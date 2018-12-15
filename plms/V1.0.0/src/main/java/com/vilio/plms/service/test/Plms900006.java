package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by martin on 2017/8/18
 */
@Service
public class Plms900006 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900006.class);

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        logger.info("短信推送定时任务启动！");
        try {
            BaseService quartz = (BaseService) SpringContextUtil.getBean("pushSmsService");
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("短信推送定时任务结束！");
    }
}
