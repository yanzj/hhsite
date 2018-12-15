package com.vilio.fms.common.service;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 所有服务的父类
 */
public class GenerateService {

    private static Logger logger = Logger.getLogger(GenerateService.class);

    public Map excute(Map paramMap, Map rootMap) throws Exception {
        logger.info("\n-----------------正式生成文件==》》" + getGenerateDescription() + "开始...............");
        Map subReturnMap = subExcute(paramMap, rootMap);
        logger.info("\t-----------------正式生成文件==》》" + getGenerateDescription() + "结束！！！！！！！！\n");
        return subReturnMap;
    }

    /**
     * 各个接口自己的实现
     * @return
     * @throws Exception
     */
    public Map subExcute(Map paramMap, Map rootMap) throws Exception {
        return null;
    }
    /**
     * 接口描述
     * @return
     */
    public String getGenerateDescription() {
        return "";
    }

}
