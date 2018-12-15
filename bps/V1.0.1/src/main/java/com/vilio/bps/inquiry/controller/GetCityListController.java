package com.vilio.bps.inquiry.controller;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.pojo.BpsCity;
import com.vilio.bps.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取城市列表
 * 按照宏获本地库的数据，取有效的对接城市即可；
 */
//@Controller
public class GetCityListController {

    private static Logger logger = Logger.getLogger(GetCityListController.class);

    @Resource
    CommonService commonService;

    @RequestMapping(value = "HH000005",method = RequestMethod.POST)
    @ResponseBody
    public Map getCityList(@RequestBody Map map) throws Exception{
        logger.info("获取城市列表接口(GetCityListController.getCityList)被调用开始,入参：" + map );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        List<BpsCity> bpsCityList = new ArrayList<BpsCity>();
        try {
            CommonUtil.dealEmpty2Null(map);
            headMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_BODY);
            //调用获取城市的service
            bpsCityList = commonService.getValidCitys();
            //整理出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_CITY_LIST, bpsCityList);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("获取城市列表接口(GetCityListController.getCityList)被调用结束,输出结果：" + result);
        return result;
    }

}
