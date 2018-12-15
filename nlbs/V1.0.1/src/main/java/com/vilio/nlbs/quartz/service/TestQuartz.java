package com.vilio.nlbs.quartz.service;

import com.vilio.nlbs.apply.controller.ApplyController;
import com.vilio.nlbs.apply.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/25.
 */
@Component
public class TestQuartz {
    protected static final Logger logger = LoggerFactory.getLogger(TestQuartz.class);

    @Resource
    ApplyService applyService;

    public void execute() throws Exception {
        logger.info("__________测试定时任务11111111111");
        Map paramMap = new HashMap();
        applyService.initApplyInfo(paramMap);
        logger.info("__________测试定时任务22222211111");
    }

}
