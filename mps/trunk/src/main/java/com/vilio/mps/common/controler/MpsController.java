package com.vilio.mps.common.controler;

import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.common.service.PretreatmentService;
import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.util.CommonUtil;
import com.vilio.mps.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class MpsController {

    private static Logger logger = Logger.getLogger(MpsController.class);

    @Resource
    private PretreatmentService pretreatmentService;


    @RequestMapping(value = "/backendapi", method = RequestMethod.POST)
    @ResponseBody
    public Map generalController(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        logger.info("*********************************************************************************************************************************************");
        //获取传过来的参数
        Map<String, Object> paramMap = (Map<String, Object>) request.getAttribute("params");
        Map<String, Object> head = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> body = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
        logger.info("接收到请求(MpsController.generalController),入参：" + paramMap);
        //定义返回值的map

        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            //调用服务，分发请求
            result = pretreatmentService.dispatchServices(paramMap);
        } catch (Exception e) {
            logger.error("系统异常：", e);
        }
        logger.info("请求处理完成(MpsController.generalController),输出结果：" + result);
        logger.info("*********************************************************************************************************************************************");
        return result;

    }


    /**
     * 接受请求服务并处理返回（单笔实时）
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/singMps")
    public void userCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        //获取传过来的参数
        Map<String, Object> params = (Map<String, Object>) request.getAttribute("params");
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        try {
            //获取交易码
            String transCode = head.get(Fields.PARAM_FUNCTION_NO) == null ? null : head.get(Fields.PARAM_FUNCTION_NO).toString();
            //获取消息类型
            String msgType = body.get("type") == null ? null : body.get("type").toString();
            BaseService bService = null;
            //根据消息内容执行不同的流程
            if (Constants.PUSH_TYPE_SMS.equals(msgType)) {
                //单笔短信发送
                bService = (BaseService) SpringContextUtil.getBean("sms" + transCode);
            } else if (Constants.PUSH_TYPE_INSTATION.equals(msgType)) {
                //站内信
                bService = (BaseService) SpringContextUtil.getBean("recv" + transCode);
            } else if (Constants.PUSH_TYPE_UMENG.equals(msgType)) {
                //友盟推送
                bService = (BaseService) SpringContextUtil.getBean("um" + transCode);
            } else if (Constants.PUSH_TYPE_EMAIL.equals(msgType)) {
                bService = (BaseService) SpringContextUtil.getBean("mail" + transCode);
            } else {
                logger.info("推送类型错误");
                head.put(Fields.PARAM_MESSAGE_ERR_CODE, "0023");
                head.put(Fields.PARAM_MESSAGE_ERR_MESG, ConfigInfo.ERROR_CODE.get("0023"));
                respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
                respMap.put(Fields.PARAM_MESSAGE_BODY, body);
                CommonUtil.returnData(request, response, respMap);
                return;
            }
            //调用相应流程
            //调用流程
            respMap = bService.doMain(params);
            //返回信息
            CommonUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            //将传过来的参数返回，并定义错误码和错误信息
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, "9999");
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, "交易码不存在");
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            CommonUtil.returnData(request, response, respMap);
        }


    }


}
