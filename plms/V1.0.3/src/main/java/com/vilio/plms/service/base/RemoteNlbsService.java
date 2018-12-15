package com.vilio.plms.service.base;

import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/9/14.
 */
@Service
public class RemoteNlbsService {
    private static Logger logger = Logger.getLogger(RemoteMpsService.class);
    /**
     * 调用Nlbs服务
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = GlobParam.nlbsUrl;
            Map remoteMap = new HashMap();
            Map headMap = new HashMap();
            headMap.put("functionNo", paramMap.get("functionNo"));
            paramMap.remove("functionNo");
            remoteMap.put("head", headMap);
            remoteMap.put("body", paramMap);

            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            result = HttpUtil.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);
        }catch (Exception e){
            logger.error("调用进件系统异常：",e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求进件系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }

        return returnMap;
    }
}
