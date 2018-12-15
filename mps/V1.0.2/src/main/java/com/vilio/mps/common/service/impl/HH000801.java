package com.vilio.mps.common.service.impl;
import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.receivepush.service.PushMessage;
import com.vilio.mps.receivepush.service.ReceiveMessage;
import com.vilio.mps.util.MessageUtil;
import com.vilio.mps.util.ReturnCode;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息Controller类
 * 包含消息接收，发送，查询功能
 * Created by liuzhu.feng on 2017/5/18/0018.
 *
 */
@Service
public class HH000801 extends BaseService {
    private static Logger logger = Logger.getLogger(HH000801.class);

    @Resource
    ReceiveMessage receiveMessage;
    @Resource
    PushMessage pushMessage;


    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map<String, Object> bodyMap = new HashMap<String, Object>();

        String pushType = (String) paramMap.get("type");
        //保存消息到数据库
        bodyMap = receiveMessage.saveMessage(paramMap);
        List responseUserList = new ArrayList();
        //发送消息
        Map pushResult = null;
        if(Constants.PUSH_TYPE_SMS.equals(pushType)){

        }else if(Constants.PUSH_TYPE_EMAIL.equals(pushType)){

        }else if(Constants.PUSH_TYPE_WECHAT.equals(pushType)){

        }else if(Constants.PUSH_TYPE_INSTATION.equals(pushType)){

            List<MpsReceiveMessageInfo> nlbsInfoList = new ArrayList<MpsReceiveMessageInfo>();
            //分拣消息接系统
            Map<String, List<MpsReceiveMessageInfo>> resultMap = MessageUtil.classifyInfoByReceiverSystem((List<MpsReceiveMessageInfo>) bodyMap.get(Fields.PARAM_MPS_MESSAGELIST));
            if(null != resultMap){
                nlbsInfoList = resultMap.get(Constants.RECEIVER_SYSTEM_NLBS);
            }
            //发往nlbs系统的消息
            if(null != nlbsInfoList && nlbsInfoList.size() >0){
                Map requestMap = new HashMap();
                requestMap.put(Fields.PARAM_MPS_MESSAGELIST, nlbsInfoList);
                try{
                    pushResult = pushMessage.pushMessageToNlbs(requestMap);
                }catch (Exception e){
                    logger.error("发往nlbs系统消息返回异常：" + e.getStackTrace());
                    throw new ErrorException(ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnCode(), ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnMessage());
                }
                if(null != pushResult){
                    logger.info("发往nlbs系统消息返回：" + pushResult);
                    List l = (List) pushResult.get(Fields.PARAM_MPS_RECEIVEUSERLIST);
                    responseUserList.addAll(l);
                }
            }
        }
        bodyMap = new HashedMap();
        bodyMap.put(Fields.PARAM_MPS_RECEIVEUSERLIST, responseUserList);
        bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "消息已成功发送！");
        return bodyMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "接收并发送消息接口";
    }


}
