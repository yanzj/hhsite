package com.vilio.nlbs.remote.service.impl;

import com.vilio.nlbs.remote.service.RemoteBmsService;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
@Service
public class RemoteBmsServiceImpl implements RemoteBmsService {

    @Resource
    ConfigInfo configInfo;


    private static Logger logger = Logger.getLogger(RemoteBmsServiceImpl.class);

    /**
     * 调用BMS服务
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callOnlineloanMasterService(Map paramMap, String httpMethod) throws Exception {
        Map returnMap = new HashMap();
        Map resultMap = null;
        try {
            String url = configInfo.getBmsUrl();
            url = url + "/v1/onlineloanMaster";
            JSONObject jsonParam = JSONObject.fromObject(paramMap);
            logger.info("----进件系统【NLBS】请求核心系统【bms】，参数为：\n" + jsonParam.toString());
            if ("PUT".equalsIgnoreCase(httpMethod)) {
                resultMap = HttpRequestUtilsForBms.httpPut(url, jsonParam);
            } else if ("POST".equalsIgnoreCase(httpMethod)){
                resultMap = HttpRequestUtilsForBms.httpPost(url, jsonParam);
            } else if ("GET".equalsIgnoreCase(httpMethod)){
                Iterator it = jsonParam.keys();
                StringBuffer sb = new StringBuffer();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsonParam.getString(key);
                    if (StringUtils.isBlank(sb.toString())) {
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                }
                url += sb.toString();
                resultMap = HttpRequestUtilsForBms.httpGet(url);
            }
            logger.info("----进件系统【NLBS】请求核心系统【bms】，应答为：\n" + (resultMap == null ? "空" : JSONObject.fromObject(resultMap).toString()));
            if (resultMap != null) {
                Object bmsReturn = resultMap.get("success");
                if (bmsReturn != null && Boolean.valueOf(bmsReturn.toString())) {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
                    if ("PUT".equalsIgnoreCase(httpMethod)) {
                        returnMap.put(Fields.PARAM_LOAN_SERIAL_NO, paramMap.get(Fields.PARAM_LOAN_ID));
                    } else if ("POST".equalsIgnoreCase(httpMethod)) {
                        List<Map> dataList = (List<Map>) resultMap.get("data");
                        if (dataList != null && dataList.size() > 0) {
                            Object loanId = dataList.get(0).get(Fields.PARAM_LOAN_ID);
                            returnMap.put(Fields.PARAM_LOAN_SERIAL_NO, loanId);
                        } else {
                            logger.info("获取进件编号失败。");
                            throw new Exception();
                        }
                    }else if ("GET".equalsIgnoreCase(httpMethod)){
                        Map dataMap = (Map) resultMap.get("data");
                        if (dataMap != null) {
                            returnMap.putAll(dataMap);
                        } else {
                            logger.info("获取进件详情失败。");
                            throw new Exception();
                        }
                    }
                } else {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                }
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, resultMap.get("message") == null ? "保存或更新失败！" : resultMap.get("message").toString());
            } else {
                returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "调用核心系统（BMS）出现异常！");
            }

        } catch (Exception e) {
            logger.error("调用核心系统异常：", e);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求核心系统失败！");
        }
        return returnMap;
    }

    @Override
    public Map callAddOnlineloanAttachService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        Map resultMap = new HashMap();
        JSONObject result = null;
        try {
            String url = configInfo.getBmsUrl();
            url = url + "/v1/addOnlineloanAttach";
            JSONObject jsonParam = JSONObject.fromObject(paramMap);
            logger.info("----进件系统【NLBS】请求核心系统【bms】，参数为：\n" + jsonParam.toString());
            result = HttpRequestUtilsForBms.httpPost(url, jsonParam);
            logger.info("----进件系统【NLBS】请求核心系统【bms】，应答为：\n" + (result == null ? "空" : result.toString()));
            resultMap = CommonUtil.toMap(result);
            if (resultMap != null) {
                Object bmsReturn = resultMap.get("success");
                if (bmsReturn != null && Boolean.valueOf(bmsReturn.toString())) {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
                } else {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                }
            } else {
                returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            }
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, resultMap.get("message"));
        } catch (Exception e) {
            logger.error("调用核心系统异常：", e);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求核心系统失败！");
        }
        return returnMap;
    }
}
