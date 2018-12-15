package com.vilio.plms.service.base;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/7/19.
 */
@Service
public class RemoteUmService {

    private static Logger logger = Logger.getLogger(RemoteUmService.class);
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
        headMap.put(Fields.PARAM_FUNCTION_TYPE, GlobParam.UM_FUNCTION_TYPE);
        headMap.put(Fields.PARAM_SYSTEM_ID, GlobParam.SYSTEM_ID_PLMS);
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

        JSONObject result = null;
        try {
            String url = GlobParam.umUrl;

            JSONObject jsonParam = JSONObject.fromObject(remoteParamMap);
            result = HttpUtil.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);

            if(returnMap == null){
                throw new ErrorException(ReturnCode.UM_SYSTEMT_EXCEPTION, "");
            }
        }catch (Exception e){
            logger.error("调用用户管理系统异常：",e);
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求用户管理系统失败！");
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
            throw new ErrorException(ReturnCode.UM_SYSTEMT_EXCEPTION, "");
        }

        return returnMap;
    }
}
