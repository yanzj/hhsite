package com.vilio.mps.receivepush.service.impl;

import com.vilio.mps.common.dao.CommonDao;
import com.vilio.mps.common.dao.MpsMessageReceiveInfoMapper;
import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.receivepush.service.ReceiveMessage;
import com.vilio.mps.util.CommonUtil;
import com.vilio.mps.glob.Fields;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/18/0018.
 */
@Service
public class ReceiveMessageImpl implements ReceiveMessage {

    public static Logger logger = Logger.getLogger(ReceiveMessageImpl.class);

    @Resource
    MpsMessageReceiveInfoMapper mpsMessageReceiveInfoMapper;
    @Resource
    CommonDao commonDao;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map saveMessage(Map paramMap) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, CloneNotSupportedException {

        Map<String, Object> bodyMap = new HashMap<String, Object>();

        MpsReceiveMessageInfo messageReceiveInfo = (MpsReceiveMessageInfo) CommonUtil.convertMapToEntity(MpsReceiveMessageInfo.class, paramMap);

        List<MpsReceiveMessageInfo> listMessageResp = new ArrayList<MpsReceiveMessageInfo>();

        List<Map> receiverListMap = (List<Map>) paramMap.get(Fields.PARAM_MPS_RECEIVEUSERLIST);
        for(Map receiverMap: receiverListMap){
            messageReceiveInfo.setReceiverCompanyCode((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERCOMPANYCODE));
            messageReceiveInfo.setReceiverCompanyName((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERCOMPANYNAME));
            messageReceiveInfo.setReceiverDepartmentCode((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERDEPARTMENTCODE));
            messageReceiveInfo.setReceiverDepartmentName((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERDEPARTMENTNAME));
            messageReceiveInfo.setReceiverIdentityId((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERIDENTITYID));
            messageReceiveInfo.setReceiverName((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERNAME));
            messageReceiveInfo.setReceiverSystem((String) receiverMap.get(Fields.PARAM_MPS_RECEIVERSYSTEM));
            messageReceiveInfo.setRequestNo((String) receiverMap.get(Fields.PARAM_MPS_RECEIVER_CODE));
            String serialNo = CommonUtil.getCurrentTimeStr("M","X");
            messageReceiveInfo.setSerialNo(serialNo);
            //保存到本地
            mpsMessageReceiveInfoMapper.insertMessageReceiveInfo(messageReceiveInfo);

            listMessageResp.add(messageReceiveInfo.clone());
        }

        bodyMap.put(Fields.PARAM_MPS_MESSAGELIST, listMessageResp);

        return bodyMap;
    }

    public Map getMessageListBySerialNoList(Map paramMap) throws Exception {
        Map<String, Object> bodyMap = new HashMap<String, Object>();


        StringBuffer sb = new StringBuffer();

        List<MpsReceiveMessageInfo> list = new ArrayList<MpsReceiveMessageInfo>();

        list = mpsMessageReceiveInfoMapper.getMessageBySerialNoList(paramMap);

        List<Map>  messageList = new ArrayList<Map>();
        if(null != messageList){
            for(MpsReceiveMessageInfo info :list){
                Map<String, String> messageMap = new HashMap<String, String>();
                messageMap.put(Fields.PARAM_MPS_RECEIVER_SERIAL_NO, info.getSerialNo());
                messageMap.put(Fields.PARAM_MPS_TTITLE, info.getTitle());
                messageMap.put(Fields.PARAM_MPS_CONTENT, info.getContent());

                messageMap.put(Fields.PARAM_MPS_RECEIVERCOMPANYCODE, info.getReceiverCompanyCode());
                messageMap.put(Fields.PARAM_MPS_RECEIVERCOMPANYNAME, info.getReceiverCompanyName());
                messageMap.put(Fields.PARAM_MPS_RECEIVERDEPARTMENTCODE, info.getReceiverDepartmentCode());
                messageMap.put(Fields.PARAM_MPS_RECEIVERDEPARTMENTNAME, info.getReceiverDepartmentName());
                messageMap.put(Fields.PARAM_MPS_RECEIVERIDENTITYID, info.getReceiverIdentityId());
                messageMap.put(Fields.PARAM_MPS_RECEIVERNAME, info.getReceiverName());

                messageMap.put(Fields.PARAM_MPS_SENDERCOMPANYNAME, info.getSenderCompanyName());
                messageMap.put(Fields.PARAM_MPS_SENDERCOMPANYCODE, info.getSenderCompanyCode());
                messageMap.put(Fields.PARAM_MPS_SENDERCOMPANYNAME, info.getSenderCompanyName());
                messageMap.put(Fields.PARAM_MPS_SENDERDEPARTMENTCODE, info.getSenderDepartmentCode());
                messageMap.put(Fields.PARAM_MPS_SENDERDEPARTMENTNAME, info.getSenderDepartmentName());
                messageMap.put(Fields.PARAM_MPS_SENDERIDENTITYID, info.getSenderIdentityId());
                messageMap.put(Fields.PARAM_MPS_RECEIVERNAME, info.getReceiverName());


                messageList.add(messageMap);
            }
        }

        bodyMap.put(Fields.PARAM_MPS_MESSAGELIST, messageList);

        return bodyMap;
    }
}
