package com.vilio.plms.controller;

import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.bms.BmsSynchronizationService;
import com.vilio.plms.util.PlmsUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin on 2017/8/24.
 */
@Controller
public class BmsController {
    private static final Logger logger = Logger.getLogger(BmsController.class);

    @Resource
    BmsSynchronizationService bmsSynchronizationService;

    @RequestMapping(value = "/bmsSynchronization.json",method = RequestMethod.POST)
    @ResponseBody
    public void BmsSynchronization (HttpServletRequest request,HttpServletResponse response,@RequestBody Map map){
        Map<String, Object> respMap = new HashMap<String, Object>();
        try{
            logger.info("bms数据入库开始。");
            bmsSynchronizationService.synchronizeApplyInfo(map);
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
//            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            PlmsUtil.returnData(request, response, respMap);
            logger.info("bms数据入库完成，即将开始解析。");
        }catch (Exception e){
            logger.error("核心系统同步数据出错:"+e);
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            PlmsUtil.returnData(request, response, respMap);
            e.printStackTrace();
        }
    }
}
