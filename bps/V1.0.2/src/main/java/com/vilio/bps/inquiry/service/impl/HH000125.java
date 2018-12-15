package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.inquiry.service.AutoGetCompanyPriceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 重跑获取各公司询价结果的job
 */
@Service
public class HH000125 extends BaseService {
    Logger logger = Logger.getLogger(HH000125.class);

    @Resource
    AutoGetCompanyPriceService autoGetCompanyPriceService;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        try {
            autoGetCompanyPriceService.execute();
            logger.info("重跑获取各公司询价结果的job" );
        } catch (Exception e) {
            logger.info("重跑获取各公司询价结果job异常：" + e);
        }

        return returnMap;
    }


    @Override
    public String getInterfaceDescription() {
        return "重跑获取各公司询价结果";
    }

}
