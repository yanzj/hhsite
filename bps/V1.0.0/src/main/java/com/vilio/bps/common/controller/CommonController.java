package com.vilio.bps.common.controller;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.temptest.bean.BaffleData;
import com.vilio.bps.temptest.bean.SetSystemConfig;
import com.vilio.bps.temptest.bean.Threshold;
import com.vilio.bps.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/8.
 */
@Controller
public class CommonController {

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    private static Logger logger = Logger.getLogger(CommonController.class);

    @RequestMapping(value = {"/api/system/setSystemConfig", "HH00001"}, method = RequestMethod.POST)
    @ResponseBody
    public Map setSystemConfig(@RequestBody Map map) throws Exception{
        logger.info("更新系统配置(CommonController.setSystemConfig)被调用开始,入参：" + map);

        Map result = new HashMap();
        Map returnBodyMap = new HashMap();
        Map headMap = (Map<String, Object>)map.get(Fields.PARAM_MESSAGE_HEAD);
        Map bodyMap = (Map<String, Object>)map.get(Fields.PARAM_MESSAGE_BODY);
        try {
            CommonUtil.dealEmpty2Null(map);
            returnBodyMap = commonService.updateConfig(bodyMap);

        } catch (Exception e) {
            logger.error("系统异常：", e);
            returnBodyMap.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }

        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, returnBodyMap);
        logger.info("更新系统配置(CommonController.setSystemConfig)被调用结束,输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "/api/system/getAllSystemConfig", method = RequestMethod.POST)
    @ResponseBody
    public Map getAllSystemConfig(@RequestBody Map map) throws Exception {
        logger.info("获取系统配置(CommonController.getAllSystemConfig)被调用开始,入参：" + map);

        Map result = new HashMap();

        try {
            CommonUtil.dealEmpty2Null(map);
            result = commonService.getAllSystemConfig();
        } catch (Exception e) {
            logger.error("系统异常：", e);
            result.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }
        logger.info("获取系统配置(CommonController.getAllSystemConfig)被调用结束,输出结果：" + result);

        return result;
    }

    @RequestMapping(value = "HH0000001", method = RequestMethod.POST)
    @ResponseBody
    public Map setSystemConfig(SetSystemConfig setSystemConfig) throws Exception {
        Map map = CommonUtil.objectToMap(setSystemConfig);
        logger.info("更新系统配置(CommonController.setSystemConfig)被调用开始,入参：" + map);

        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(map);
            result = commonService.updateConfig(map);

        } catch (Exception e) {
            logger.error("系统异常：", e);
            result.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }

        logger.info("更新系统配置(CommonController.setSystemConfig)被调用结束,输出结果：" + result);
        return result;
    }


    @RequestMapping(value = "HH0000003", method = RequestMethod.POST)
    @ResponseBody
    public Map setThresholdConfig(Threshold threshold) throws Exception {
        Map map = CommonUtil.objectToMap(threshold);
        logger.info("更新时间阈值配置(CommonController.setThresholdConfig)被调用开始,入参：" + map);

        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(map);
            result = commonService.updateCityThreshold(map);

        } catch (Exception e) {
            logger.error("系统异常：", e);
            result.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }

        logger.info("更新时间阈值配置(CommonController.setThresholdConfig)被调用结束,输出结果：" + result);
        return result;
    }

}
