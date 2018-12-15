package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.quartz.PaidInfoDeleteService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by wangxf on 2017/9/1
 */
@Service
public class Plms900008 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900008.class);

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        logger.info("放款删除定时任务启动！");
        PaidInfoDeleteService paidInfoDeleteService = (PaidInfoDeleteService) SpringContextUtil.getBean("paidInfoDeleteService");
        try {
            paidInfoDeleteService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("放款删除定时任务结束！");
    }
}
