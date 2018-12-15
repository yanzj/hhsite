package com.vilio.pcfs.controller;

import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.util.PcfsUtil;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： InnerController<br>
 * 功能：对内请求Controller类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Controller
public class InnerController {
    private static final Logger logger = Logger.getLogger(InnerController.class);


    /**
     * 接受内部请求请求服务并处理返回
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/innerCore.json")
    public void innerCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        BufferedReader reader = null;
        try {
            Map<String, Object> params = (Map<String, Object>) request.getAttribute("params");
            Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
            String transCode = String.valueOf(head.get(GlobParam.PARAM_FUNCTION_NO));
            //全部小写
            transCode = transCode.toLowerCase();
            //调用相应流程
            BaseService bService = (BaseService) SpringContextUtil.getBean(transCode);
            respMap = bService.doMain(params);
            PcfsUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(GlobParam.PARAM_MESSAGE_HEAD, head);
            respMap.put(GlobParam.PARAM_MESSAGE_BODY, body);
            PcfsUtil.returnData(request, response, respMap);
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
