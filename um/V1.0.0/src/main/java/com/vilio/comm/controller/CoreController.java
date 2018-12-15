package com.vilio.comm.controller;

import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.SpringContextUtil;
import com.vilio.comm.util.UmUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CoreController<br>
 * 功能：核心请求Controller类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Controller
public class CoreController {
    private static final Logger logger = Logger.getLogger(CoreController.class);

    /**
     * 接受请求服务并处理返回
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/umCore")
    public void userCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        //获取传过来的参数
        Map<String, Object> params = (Map<String, Object>) request.getAttribute("params");
        Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
        Map<String, Object> body = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_BODY);
        try {
            //获取交易码
            String transCode = head.get(GlobParam.PARAM_FUNCTION_NO) == null ? null : head.get(GlobParam.PARAM_FUNCTION_NO).toString();
            String functionType = head.get(GlobParam.PARAM_FUNCTION_NO) == null ? null : head.get(GlobParam.PARAM_FUNCTION_TYPE).toString();
            BaseService bService = null;
            if (GlobParam.customFunctionType.equals(functionType)) {
                bService = (BaseService) SpringContextUtil.getBean("c" + transCode);
            } else if (GlobParam.umFunctionType.equals(functionType)) {
                bService = (BaseService) SpringContextUtil.getBean("u" + transCode);
            } else {
                throw new NoSuchBeanDefinitionException("业务类型错误");
            }
            //调用相应流程
            //调用流程
            respMap = bService.doMain(params);
            //返回信息
            UmUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            //将传过来的参数返回，并定义错误码和错误信息
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9999");
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, "交易码不存在");
            respMap.put(GlobParam.PARAM_MESSAGE_HEAD, head);
            respMap.put(GlobParam.PARAM_MESSAGE_BODY, body);
            UmUtil.returnData(request, response, respMap);
        }

    }

}
