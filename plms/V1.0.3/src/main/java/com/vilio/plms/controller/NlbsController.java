package com.vilio.plms.controller;

import com.vilio.plms.dao.CommDao;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * nlbs内部调用plms
 */
@Controller
public class NlbsController {
    private static final Logger logger = Logger.getLogger(NlbsController.class);

    @Resource
    CommDao commDao;

    /**
     * 核心controller
     */
    @RequestMapping(value = "/nlbsBackendapi",method = RequestMethod.POST)
    @ResponseBody
    public Map doRequest(@RequestBody Map paramMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        BufferedReader reader = null;
        try {
            Map<String, Object> params = paramMap;
            Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
            String transCode = String.valueOf(head.get(Fields.PARAM_FUNCTION_NO));
            //全部小写
            transCode = transCode.toLowerCase();
            //调用相应流程
            BaseService bService = (BaseService) SpringContextUtil.getBean(transCode);
            respMap = bService.doMain(params);
//            PlmsUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
//            PlmsUtil.returnData(request, response, respMap);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return respMap;

    }
}
