package com.vilio.nlbs.remote.service.impl;

import com.vilio.nlbs.remote.service.RemoteBpsService;
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
public class RemoteBpsServiceImpl implements RemoteBpsService {

    @Resource
    ConfigInfo configInfo;


    private static Logger logger = Logger.getLogger(RemoteBpsServiceImpl.class);
    /**
     * 调用BPS服务，直接透传
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = configInfo.getBpsUrl();
            JSONObject jsonParam = JSONObject.fromObject(paramMap);
            result = HttpRequestUtils.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);
        }catch (Exception e){
            logger.error("调用询价系统异常：",e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求询价系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }
        return returnMap;
    }
}
