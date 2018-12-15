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
public class Plms900004 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900004.class);

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        logger.info("信息收集定时任务启动！");

        logger.info("收集还款提醒信息开始");
        try {
            BaseService quartz = (BaseService) SpringContextUtil.getBean("repaymentCollectService");
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("收集还款提醒信息结束");


        logger.info("收集逾期提醒信息开始");
        try {
            BaseService quartz = (BaseService) SpringContextUtil.getBean("overdueCollectService");
            quartz.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("收集逾期提醒信息结束");


        logger.info("信息收集定时任务结束！");
    }
}
