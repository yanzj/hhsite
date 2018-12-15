package com.vilio.pcfs.service.test;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by martin on 2017/8/18
 */
@Service
public class Pcfs990001 extends BaseService {
    private static final Logger logger = Logger.getLogger(Pcfs990001.class);

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        logger.info("待发送消息过滤定时任务启动");
        BaseService quartz = (BaseService) SpringContextUtil.getBean("filterCreateMsgService");
        try {
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("待发送消息过滤定时任务结束");
    }
}
