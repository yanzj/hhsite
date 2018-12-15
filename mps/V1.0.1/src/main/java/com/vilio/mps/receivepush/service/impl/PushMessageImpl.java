package com.vilio.mps.receivepush.service.impl;

import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.receivepush.pojo.NlbsMessageResp;
import com.vilio.mps.receivepush.service.PushMessage;
import com.vilio.mps.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/31.
 */
@Service
public class PushMessageImpl implements PushMessage {
    public static Logger logger = Logger.getLogger(PushMessageImpl.class);

    public Map pushMessageToNlbs(Map paramMap) throws Exception {

        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap.put(Fields.PARAM_FUNCTION_NO, "HH000020");

        List<MpsReceiveMessageInfo> messageList = (List<MpsReceiveMessageInfo>) paramMap.get(Fields.PARAM_MPS_MESSAGELIST);

        List<NlbsMessageResp> listMessageResp = new ArrayList<NlbsMessageResp>();
        for(MpsReceiveMessageInfo messageReceiveInfo:messageList){
            NlbsMessageResp nlbsMessageResp = new NlbsMessageResp();
            nlbsMessageResp.setSerialNo(messageReceiveInfo.getSerialNo());
            nlbsMessageResp.setTitle(messageReceiveInfo.getTitle());
            nlbsMessageResp.setContent(messageReceiveInfo.getContent());
            nlbsMessageResp.setSenderCompanyCode(messageReceiveInfo.getSenderCompanyCode());
            nlbsMessageResp.setSenderCompanyName(messageReceiveInfo.getSenderCompanyName());
            nlbsMessageResp.setSenderDepartmentCode(messageReceiveInfo.getSenderDepartmentCode());
            nlbsMessageResp.setSenderDepartmentName(messageReceiveInfo.getSenderDepartmentName());
            nlbsMessageResp.setSenderIdentityId(messageReceiveInfo.getSenderIdentityId());
            nlbsMessageResp.setSenderName(messageReceiveInfo.getSenderName());
            nlbsMessageResp.setReceiverName(messageReceiveInfo.getReceiverName());
            nlbsMessageResp.setReceiverUserId(messageReceiveInfo.getReceiverIdentityId());
            nlbsMessageResp.setInternalParam(messageReceiveInfo.getInternalParam());
            nlbsMessageResp.setCode(messageReceiveInfo.getRequestNo());
            listMessageResp.add(nlbsMessageResp);
        }

        Map requestMap = new HashMap();
        requestMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        requestMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);

        bodyMap.put(Fields.PARAM_MPS_MESSAGELIST, listMessageResp);
        //发送消息
        JSONObject jsonParam = JSONObject.fromObject(requestMap);
        JSONObject   resultJson = HttpRequestUtils.httpPost(ConfigInfo.nlbsUrl ,jsonParam);

        List receiverList = new ArrayList();
        for(NlbsMessageResp message:listMessageResp){
            Map receiverMap = new HashMap();
            receiverMap.put(Fields.PARAM_MPS_RECEIVERIDENTITYID, message.getReceiverUserId());
            receiverMap.put(Fields.PARAM_MPS_RECEIVER_SERIAL_NO, message.getSerialNo());
            receiverMap.put(Fields.PARAM_MPS_RECEIVERNAME, message.getReceiverName());
            receiverMap.put(Fields.PARAM_MPS_RECEIVER_CODE, message.getCode());
            receiverMap.put(Fields.PARAM_MPS_RECEIVERSYSTEM, Constants.RECEIVER_SYSTEM_NLBS);
            receiverList.add(receiverMap);
        }

        bodyMap.put(Fields.PARAM_MPS_RECEIVEUSERLIST, receiverList);

        return bodyMap;
    }
}
