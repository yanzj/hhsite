package com.vilio.plms.controller;

import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.bms.BmsSynchronizationService;
import com.vilio.plms.util.PlmsUtil;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CoreController<br>
 * 功能：核心请求Controller类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Controller
public class CoreController {


    private static final Logger logger = Logger.getLogger(CoreController.class);

    @Resource
    BmsSynchronizationService bmsSynchronizationService;

    /**
     * 核心controller
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/appCore.json")
    public void mobileCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        BufferedReader reader = null;
        try {
            Map<String, Object> params = (Map<String, Object>) request.getAttribute("params");
            Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
            String transCode = String.valueOf(head.get(Fields.PARAM_FUNCTION_NO));
            //全部小写
            transCode = transCode.toLowerCase();
            //调用相应流程
            BaseService bService = (BaseService) SpringContextUtil.getBean(transCode);
            //respMap = ((BaseService) AopContext.currentProxy()).doMain(params);
            respMap = bService.doMain(params);
            PlmsUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            PlmsUtil.returnData(request, response, respMap);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
