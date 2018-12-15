package com.vilio.bps.inquiry.controller;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.service.GetHouseInfo;
import com.vilio.bps.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 获取房子相关信息的Controller
 * 包括模糊获取小区，楼栋，室号等信息
 */
//@Controller
public class GetHouseInfoController {

    private static Logger logger = Logger.getLogger(GetHouseInfoController.class);

    @Resource
    CommonService commonService;

    @Resource
    private GetHouseInfo getHouseInfo;


    @RequestMapping(value = "HH000006",method = RequestMethod.POST)
    @ResponseBody
    public Map getDisplayPlotsList(@RequestBody Map map) throws Exception{
        logger.info("获取小区列表接口(GetHouseInfoController.getDisplayPlotsList)被调用开始,入参：" + map );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        List<Map<String, String>> displayPlotsList = new ArrayList<Map<String, String>>();
        try {
            CommonUtil.dealEmpty2Null(map);
            headMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_BODY);
            //调用后台服务获取小区列表
            displayPlotsList = getHouseInfo.getDisplayPlots(bodyMap);
            //整理出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_PLOTS_LIST, displayPlotsList);
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
        logger.info("获取小区列表接口(GetHouseInfoController.getDisplayPlotsList)被调用结束,输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "HH000007",method = RequestMethod.POST)
    @ResponseBody
    public Map getDisplayUnitsList(@RequestBody Map map) throws Exception{
        logger.info("获取楼栋列表接口(GetHouseInfoController.getDisplayUnitsList)被调用开始,入参：" + map );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        List<Map<String, String>> displayUnitsList = new ArrayList<Map<String, String>>();
        try {
            CommonUtil.dealEmpty2Null(map);
            headMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_BODY);
            //调用后台服务获取小区列表
            displayUnitsList = getHouseInfo.getDisplayUnits(bodyMap);
            //整理出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_UNIT_LIST, displayUnitsList);
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
        logger.info("获取楼栋列表接口(GetHouseInfoController.getDisplayUnitsList)被调用结束,输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "HH000008",method = RequestMethod.POST)
    @ResponseBody
    public Map getDisplayRoomsList(@RequestBody Map map) throws Exception{
        logger.info("获取房号列表接口(GetHouseInfoController.getDisplayRoomsList)被调用开始,入参：" + map );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        List<Map<String, String>> displayRoomsList = new ArrayList<Map<String, String>>();
        try {
            CommonUtil.dealEmpty2Null(map);
            headMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_BODY);
            //调用后台服务获取小区列表
            displayRoomsList = getHouseInfo.getDisplayRooms(bodyMap);
            //整理出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_UNIT_LIST, displayRoomsList);
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
        logger.info("获取房号列表接口(GetHouseInfoController.getDisplayRoomsList)被调用结束,输出结果：" + result);
        return result;
    }

}
