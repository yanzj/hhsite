package com.vilio.plms.service;

import com.vilio.plms.dao.PlmsMessageReceiveMapper;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PlmsMessageReceive;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/9/13.
 */
@Service
public class Plms400001 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms400001.class);
    @Resource
    PlmsMessageReceiveMapper plmsMessageReceiveMapper;
    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        List<Map> messageList = (ArrayList<Map>) body.get(Fields.PARAM_MESSAGE_LIST);
        if(messageList != null && messageList.size() > 0) {
            for(Map m : messageList) {
                PlmsMessageReceive nlbsMessage = new PlmsMessageReceive();
                String code = CommonUtil.getCurrentTimeStr(Fields.RECEIVE_MESSAGE_CODE_PREFIX, Fields.RECEIVE_MESSAGE_CODE_SUFFIX);
                nlbsMessage.setCode(code);
                nlbsMessage.setTitle((String) m.get(Fields.PARAM_MSG_TITLE));
                nlbsMessage.setContent((String) m.get(Fields.PARAM_MSG_CONTENT));
                nlbsMessage.setSerialNo((String) m.get(Fields.PARAM_MSG_SERIAL_NO));
                nlbsMessage.setSenderCompanyCode((String) m.get(Fields.PARAM_MSG_SENDER_COMPANY_CODE));
                nlbsMessage.setSenderCompanyName((String) m.get(Fields.PARAM_MSG_SENDER_COMPANY_NAME));
                nlbsMessage.setSenderDepartmentCode((String) m.get(Fields.PARAM_MSG_SENDER_DEPARTMENT_CODE));
                nlbsMessage.setSenderDepartmentName((String) m.get(Fields.PARAM_MSG_SENDER_DEPARTMENT_NAME));
                nlbsMessage.setSenderIdentityId((String) m.get(Fields.PARAM_MSG_SENDER_IDENTITY_ID));
                nlbsMessage.setSenderName((String) m.get(Fields.PARAM_MSG_SENDER_NAME));
                nlbsMessage.setReceiverIdentityId((String) m.get(Fields.PARAM_MSG_RECEIVER_USER_ID));
                nlbsMessage.setInternalParam((String) m.get(Fields.PARAM_MSG_INTERNAL_PARAM));

                plmsMessageReceiveMapper.savePlmsMessageReceive(nlbsMessage);
            }
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功接收消息！");
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.MSG_EMPTY_RECEIVE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "接收消息为空！");
    }
}
