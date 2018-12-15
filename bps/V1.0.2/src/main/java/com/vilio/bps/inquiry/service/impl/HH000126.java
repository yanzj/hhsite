package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.inquiry.service.CheckApplyStatusService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步外系统相关数据定时任务
 */
@Service
public class HH000126 extends BaseService {
    Logger logger = Logger.getLogger(HH000126.class);

    @Resource
    CheckApplyStatusService checkApplyStatusService;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        try {
            checkApplyStatusService.execute();
            logger.info("重跑同步外系统相关数据job" );
        } catch (Exception e) {
            logger.info("重跑同步外系统相关数据job异常：" + e);
        }

        return returnMap;
    }


    @Override
    public String getInterfaceDescription() {
        return "重跑同步外系统相关数据定时任务";
    }
}
