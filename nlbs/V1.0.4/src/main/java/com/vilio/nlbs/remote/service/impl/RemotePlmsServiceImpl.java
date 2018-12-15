package com.vilio.nlbs.remote.service.impl;

import com.vilio.nlbs.remote.service.RemotePlmsService;
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
public class RemotePlmsServiceImpl implements RemotePlmsService {

    @Resource
    ConfigInfo configInfo;


    private static Logger logger = Logger.getLogger(RemotePlmsServiceImpl.class);
    /**
     * 调用PLMS服务
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(String functionNo, Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {

            Map forPlmsParamMap = new HashMap();
            Map forPlmsHeadMap = new HashMap();
            forPlmsParamMap.put(Fields.PARAM_MESSAGE_HEAD, forPlmsHeadMap);
            forPlmsParamMap.put(Fields.PARAM_MESSAGE_BODY, paramMap);
            forPlmsHeadMap.put("functionNo", functionNo);
            forPlmsHeadMap.put(Fields.PARAM_USER_NO, paramMap.get(Fields.PARAM_USER_NO));
            String url = configInfo.getPlmsUrl();
            JSONObject jsonParam = JSONObject.fromObject(forPlmsParamMap);
            logger.info("进件系统（NLBS）调用贷后系统（PLMS）参数为：" + jsonParam);
            result = HttpRequestUtils.httpPost(url, jsonParam);
            logger.info("贷后系统（PLMS）返回进件系统（NLBS）应答为：" + result);
            returnMap = CommonUtil.toMap(result);
        }catch (Exception e){
            logger.error("调用贷后系统异常：",e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求贷后系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }
        return returnMap;
    }
}
