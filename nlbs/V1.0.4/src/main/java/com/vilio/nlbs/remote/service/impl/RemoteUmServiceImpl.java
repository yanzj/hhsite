package com.vilio.nlbs.remote.service.impl;

import com.vilio.nlbs.remote.service.RemoteUmService;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
@Service
public class RemoteUmServiceImpl implements RemoteUmService {

    @Resource
    ConfigInfo configInfo;


    private static Logger logger = Logger.getLogger(RemoteUmServiceImpl.class);
    /**
     * 调用UM服务，此处统一处理head部分。
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(Map paramMap, String functionNo) throws Exception {

        Map returnMap = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap = paramMap;
        headMap.put(Fields.PARAM_FUNCTION_NO, functionNo);
        headMap.put(Fields.PARAM_FUNCTION_TYPE, Constants.UM_FUNCTION_TYPE);
        headMap.put(Fields.PARAM_SYSTEM_ID, Constants.SYSTEM_ID_NLBS);
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

        JSONObject result = null;
        try {
            String url = configInfo.getUmUrl();

            JSONObject jsonParam = JSONObject.fromObject(remoteParamMap);
            result = HttpRequestUtils.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);
            if(returnMap == null){
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "调用用户管理系统出现异常");
            } else {
                String errCode = ((Map)returnMap.get(Fields.PARAM_MESSAGE_HEAD)).get(Fields.PARAM_MESSAGE_ERR_CODE).toString();
                if(!ReturnCode.SUCCESS_CODE.equals(errCode)){
                    throw new HHBizException(errCode, ((Map)returnMap.get(Fields.PARAM_MESSAGE_HEAD)).get(Fields.PARAM_MESSAGE_ERR_MESG).toString());
                }
            }
        } catch (HHBizException hhbE){
            throw hhbE;
        } catch (Exception e){
            logger.error("调用用户管理系统异常：",e);
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求用户管理系统失败！");
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "调用用户管理系统出现异常");
        }

        return returnMap;
    }
}
