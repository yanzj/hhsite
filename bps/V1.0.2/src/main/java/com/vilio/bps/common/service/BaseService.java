package com.vilio.bps.common.service;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 所有服务的父类
 */
public class BaseService {

    private static Logger logger = Logger.getLogger(BaseService.class);

    public Map excute(Map paramMap) throws Exception {
        logger.info("********************************************《" + getInterfaceDescription() + "》开始调用服务********************************************");
        Map subExcuteResultMap = subExcute(paramMap);
        logger.info("********************************************《" + getInterfaceDescription() + "》结束调用服务********************************************");
        return subExcuteResultMap;
    }

    /**
     * 各个接口自己的实现
     * @return
     * @throws Exception
     */
    public Map subExcute(Map paramMap) throws Exception {
        return null;
    }
    /**
     * 接口描述
     * @return
     */
    public String getInterfaceDescription() {
        return "";
    }

}
