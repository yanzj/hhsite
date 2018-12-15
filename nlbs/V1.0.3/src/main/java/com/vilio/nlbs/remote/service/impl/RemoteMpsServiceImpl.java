package com.vilio.nlbs.remote.service.impl;

import com.vilio.nlbs.commonMapper.dao.NlbsMessageSendMapper;
import com.vilio.nlbs.commonMapper.pojo.NlbsMessageSend;
import com.vilio.nlbs.message.service.MessageService;
import com.vilio.nlbs.remote.service.RemoteMpsService;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
@Service
public class RemoteMpsServiceImpl implements RemoteMpsService {

    @Resource
    ConfigInfo configInfo;

    @Resource
    MessageService messageService;


    private static Logger logger = Logger.getLogger(RemoteMpsServiceImpl.class);
    /**
     * 调用MPS服务，直接透传
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = configInfo.getMpsUrl();
            Map remoteMap = new HashMap();
            Map headMap = new HashMap();
            headMap.put("functionNo", paramMap.get("functionNo"));
            paramMap.remove("functionNo");
            remoteMap.put("head", headMap);
            remoteMap.put("body", paramMap);

            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            result = HttpRequestUtils.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);
        }catch (Exception e){
            logger.error("调用消息中心系统异常：",e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求消息中心系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }

        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) returnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) returnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        // 回写消息状态和流水号
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
            List<Map>  receiveUserList = (List<Map>) remoteBodyMap.get(Fields.PARAM_RECEIVEUSERLIST);
            if(receiveUserList != null && receiveUserList.size() > 0){
                for(Map receUser : receiveUserList){
                    messageService.modifySendMessage(receUser);
                }
            }
        }
        return returnMap;
    }
}
