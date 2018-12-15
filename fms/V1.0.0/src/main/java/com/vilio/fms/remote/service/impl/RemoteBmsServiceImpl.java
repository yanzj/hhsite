package com.vilio.fms.remote.service.impl;

import com.vilio.fms.remote.service.RemoteBmsService;
import com.vilio.fms.util.*;
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
     * 同步合同模板
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map callAddOnlinenstitutionsService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        Map resultMap = new HashMap();
        JSONObject result = null;
        try {
            String url = configInfo.getBmsModelUrl();
            url = url + "/v1/addOnlinenstitutions";
            JSONObject jsonParam = JSONObject.fromObject(paramMap);
            logger.info("----文件系统【FMS】请求核心系统【bms】，参数为：\n" + jsonParam.toString());
            result = HttpRequestUtils.httpPost(url, jsonParam, new HashMap<String, String>());
            logger.info("----文件系统【FMS】请求核心系统【bms】，应答为：\n" + result == null ? "空" : result.toString());
            resultMap = CommonUtil.toMap(result);
            if (resultMap != null) {
                Object bmsReturn = resultMap.get("success");
                if (bmsReturn != null && Boolean.valueOf(bmsReturn.toString())) {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
                } else {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                }
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, resultMap.get("message"));
            } else {
                returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "调用核心系统（BMS）出现异常！");
            }
        } catch (Exception e) {
            logger.error("调用核心系统异常：", e);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求核心系统（BMS）失败！");
        }
        return returnMap;
    }


    /**
     * 查询进件详情
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callOnlineloanMasterService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        Map resultMap = new HashMap();
        Object result = null;
        try {
            String url = configInfo.getBmsApplyUrl();
            url = url + "/v1/onlineloanMaster";
            JSONObject jsonParam = JSONObject.fromObject(paramMap);
            logger.info("----进件系统【NLBS】请求核心系统【bms】，参数为：\n" + jsonParam.toString());
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
            result = HttpRequestUtils.httpGet(url, new HashMap<>());
            logger.info("----进件系统【NLBS】请求核心系统【bms】，应答为：\n" + result == null ? "空" : result.toString());
            resultMap = CommonUtil.toMap(JSONObject.fromObject(result));
            if (resultMap != null) {
                Object bmsReturn = resultMap.get("success");
                if (bmsReturn != null && Boolean.valueOf(bmsReturn.toString())) {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);

                    Map dataMap = (Map) resultMap.get("data");
                    if (dataMap != null) {
                        returnMap.putAll(dataMap);
                    } else {
                        logger.info("获取进件详情失败。");
                        throw new Exception();
                    }
                } else {
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                }
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, resultMap.get("message"));
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
}
