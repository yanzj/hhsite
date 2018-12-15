package com.vilio.plms.service;

import com.vilio.plms.dao.AppDao;
import com.vilio.plms.dao.MessageReceiveDao;
import com.vilio.plms.dao.TodoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.MessageReceive;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.LoginService;
import com.vilio.plms.service.base.MessageService;
import com.vilio.plms.service.base.RemoteUmService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类名： Plms100004<br>
 * 功能：根据用户编号，查询待办任务和消息的提示信息   待办任务数量-未读消息数量-待办任务详情列表-4条消息列表<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100004 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100004.class);

    @Resource
    MessageReceiveDao messageReceiveDao;

    @Resource
    TodoDao todoDao;

    @Resource
    MessageService messageService;

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
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //Step1 参数检查
        if(body == null){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "参数有误");
        }

        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();
        if(StringUtils.isBlank(userNo)){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[userNo]");
        }
        int todoCount = 0;
        int msgCount = 0;
        //Step2 获取未读消息个数
        MessageReceive nlbsMessageReceive = new MessageReceive();
        nlbsMessageReceive.setReceiverIdentityId(userNo);
        msgCount = messageReceiveDao.getUnReadMsgCount(nlbsMessageReceive);

        //Step3 获取代办任务的提示列表，同时计算待办任务总个数
        List<Map<String ,Object>> todoTipsList = todoDao.getTodoListGroupByType(userNo);
        if(todoTipsList != null && todoTipsList.size() > 0){
            for (Map todoTips : todoTipsList) {
                Object count = todoTips.get(Fields.PARAM_COUNT);
                if(count != null){
                    todoCount += Integer.parseInt(count.toString());
                }
            }
        } else {
            todoTipsList = new ArrayList<>();
        }
        //Step4 获取4条消息的列表
        Map messageMap = new HashMap();
        messageMap.put(Fields.PARAM_USER_NO, userNo);
        messageMap.put(Fields.PARAM_PAGE_NO, "1");
        messageMap.put(Fields.PARAM_PAGE_SIZE, "4");
        Map msgReturnMap = messageService.queryMessageList(messageMap);
        List<Map> messageTipsList = new ArrayList<>();
        if(msgReturnMap != null && ReturnCode.SUCCESS_CODE.equals(msgReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE))){
            messageTipsList = msgReturnMap.get(Fields.PARAM_MESSAGE_LIST) == null ? new ArrayList<>() : (List<Map>)msgReturnMap.get(Fields.PARAM_MESSAGE_LIST);
        }
        resultMap.put(Fields.PARAM_TODO_COUNT, todoCount);
        resultMap.put(Fields.PARAM_MESSAGE_COUNT, msgCount);
        resultMap.put(Fields.PARAM_TODO_TIPS_LIST, todoTipsList);
        resultMap.put(Fields.PARAM_MESSAGE_TIPS_LIST, messageTipsList);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取待办任务和消息提醒列表！");
    }

}
