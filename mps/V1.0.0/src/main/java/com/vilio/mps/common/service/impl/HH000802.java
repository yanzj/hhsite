package com.vilio.mps.common.service.impl;

import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.receivepush.service.PushMessage;
import com.vilio.mps.receivepush.service.ReceiveMessage;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/6/21.
 */
@Service
public class HH000802 extends BaseService{
    private static Logger logger = Logger.getLogger(HH000802.class);

    @Resource
    ReceiveMessage receiveMessage;
    @Resource
    PushMessage pushMessage;


    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map<String, Object> bodyMap = new HashMap<String, Object>();

        bodyMap = receiveMessage.getMessageListBySerialNoList(paramMap);

        bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取消息列表完成！");

        return bodyMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "获取消息列表接口";
    }
}
