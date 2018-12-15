package com.vilio.pcfs.util;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxf on 2017/6/15.
 */
public class PcfsUtil {

    private static final Logger logger = Logger.getLogger(PcfsUtil.class);


    /**
     * 发送手机推送方法
     * @param sendSmsBody
     * @return
     * @throws ErrorException
     */
    public static Map pushMsg(Map sendSmsBody) throws ErrorException {
        Map sendSms = new HashMap();
        Map sendSmsHead = new HashMap();
        sendSms.put("body", sendSmsBody);
        sendSms.put("head", sendSmsHead);
        sendSmsHead.put("functionNo", GlobParam.pushFunctionNo);
        sendSmsBody.put("type", GlobParam.pushType);
        sendSmsBody.put("senderIdentityId", sendSmsBody.get(GlobParam.PARAM_USER_ID) == null|| "".equals(sendSmsBody.get(GlobParam.PARAM_USER_ID))? GlobParam.pushSenderIdentityId : sendSmsBody.get(GlobParam.PARAM_USER_ID));
        sendSmsBody.put("senderSystem", GlobParam.SYSTEM_NO);
        String resultStr = HttpUtil.sendHttp(GlobParam.mpsUrl, JsonUtil.objectToJson(sendSms));
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        Map resultHead = (Map) resultMap.get("head");
        String returnCode = resultHead.get("returnCode").toString();
        String returnMessage = resultHead.get("returnMessage").toString();
        if (!"0000".equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        return resultMap;
    }


    /**
     * 发送短信方法
     *
     * @param head
     * @param sendSmsBody
     * @return
     * @throws ErrorException
     */
    public static Map sendSms(Map head, Map sendSmsBody) throws ErrorException {
        if (GlobParam.baffleSwitch.equals(GlobDict.baffle_switch_valid.getKey())) {
            return null;
        }
        Map sendSms = new HashMap();
        Map sendSmsHead = new HashMap();
        sendSms.put("body", sendSmsBody);
        sendSms.put("head", sendSmsHead);
        sendSmsHead.put("functionNo", GlobParam.smsFunctionNo);
        sendSmsBody.put("type", GlobParam.smsType);
        sendSmsBody.put("senderIdentityId", head.get(GlobParam.PARAM_USER_ID) == null|| "".equals(head.get(GlobParam.PARAM_USER_ID))? GlobParam.smsSenderIdentityId : head.get(GlobParam.PARAM_USER_ID));
        sendSmsBody.put("senderSystem", GlobParam.SYSTEM_NO);
        String resultStr = HttpUtil.sendHttp(GlobParam.mpsUrl, JsonUtil.objectToJson(sendSms));
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        Map resultHead = (Map) resultMap.get("head");
        String returnCode = resultHead.get("returnCode").toString();
        String returnMessage = resultHead.get("returnMessage").toString();
        if (!"0000".equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        return resultMap;
    }

    /**
     * 发送UM系统并且判断返回值，如果返回值不是0000则报错
     *
     * @param sendBody
     * @return
     * @throws ErrorException
     */
    public static Map sendUMRetJudge(Map sendBody) throws ErrorException {
        Map resultMap = sendUM(sendBody);
        Map resultHead = (Map) resultMap.get("head");
        String returnCode = resultHead.get("returnCode").toString();
        String returnMessage = resultHead.get("returnMessage").toString();
        logger.info("um返回参数："+returnMessage);
        if (!"0000".equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        return resultMap;
    }


    /**
     * 发送UM方法
     *
     * @param sendBody
     * @return
     * @throws ErrorException
     */
    public static Map sendUM(Map sendBody) throws ErrorException {
        //定义发送结构结构体
        Map sendMap = new HashMap();
        Map sendHead = new HashMap();
        sendMap.put("head", sendHead);
        sendMap.put("body", sendBody);
        sendHead.put("systemId", GlobParam.SYSTEM_NO);
        sendHead.put("functionType", GlobParam.umFunctionType);
        sendHead.put("functionNo", sendBody.get(GlobParam.PARAM_FUNCTION_NO));
        String resultStr = HttpUtil.sendHttp(GlobParam.umUrl, JsonUtil.objectToJson(sendMap));
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        return resultMap;
    }


    /**
     * 发送贷后系统并且判断返回值，如果返回值不是0000则报错
     *
     * @param head
     * @param sendBody
     * @return
     * @throws ErrorException
     */
    public static Map sendPLMSRetJudge(Map head, Map sendBody) throws ErrorException {
        Map resultMap = sendPLMS(head, sendBody);
        Map resultHead = (Map) resultMap.get("head");
        String returnCode = resultHead.get("returnCode").toString();
        String returnMessage = resultHead.get("returnMessage").toString();
        if (!"0000".equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        Map plmsRetBody = (Map) resultMap.get(GlobParam.PARAM_MESSAGE_BODY);
        return plmsRetBody;
    }


    /**
     * 发送贷后方法
     *
     * @param head
     * @param sendBody
     * @return
     * @throws ErrorException
     */
    public static Map sendPLMS(Map head, Map sendBody) throws ErrorException {
        //定义发送结构结构体
        Map sendMap = new HashMap();
        Map sendHead = new HashMap();
        sendMap.put("head", sendHead);
        sendMap.put("body", sendBody);
        sendHead.put("sourceSystem", GlobParam.SYSTEM_NO);
        sendHead.put("userId", head.get(GlobParam.PARAM_USER_ID));
        sendHead.put("functionNo", sendBody.get(GlobParam.PARAM_FUNCTION_NO));
        String resultStr = HttpUtil.sendHttp(GlobParam.plmsUrl, JsonUtil.objectToJson(sendMap));
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        return resultMap;
    }

    /**
     * 通用返回方法
     *
     * @param request
     * @param response
     * @param result
     */
    public static void returnData(HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> result) {
        PrintWriter pw = null;
        String respMessage = null;
        Map<String, Object> head = (Map<String, Object>) result.get(GlobParam.PARAM_MESSAGE_HEAD);
        respMessage = JsonUtil.objectToJson(result);
        logger.info(respMessage);
        try {
            if (respMessage != null) {
                pw = response.getWriter();
                pw.print(respMessage);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("返回信息失败！");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

    }
}
