package com.vilio.nlbs.common.service.impl;

import com.vilio.nlbs.apply.service.ApplyService;
import com.vilio.nlbs.bps.service.InquiryService;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.common.service.PretreatmentService;
import com.vilio.nlbs.login.service.LoginService;
import com.vilio.nlbs.message.service.MessageService;
import com.vilio.nlbs.remote.service.RemoteBpsService;
import com.vilio.nlbs.todo.service.NlbsTODOService;
import com.vilio.nlbs.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/24/0024.
 */
@Service
public class PretreatmentServiceImpl implements PretreatmentService {

    private static Logger logger = Logger.getLogger(PretreatmentServiceImpl.class);

    @Resource
    LoginService loginService;

    @Resource
    ApplyService applyService;

    @Resource
    MessageService messageService;

    @Resource
    CommonService commonService;

    @Resource
    InquiryService inquiryService;

    @Resource
    private RemoteBpsService remoteBpsService;

    @Resource
    NlbsTODOService nlbsTODOService;

    /**
     * 分发接口服务，根据头map中的functionNo直行后台相关接口服务；
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map dispatchServices(Map paramMap) throws Exception {

        Map resultMap = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            //先检查消息头
            filterHeadParam(headMap);
            //消息头检查通过
            if (ReturnCode.SUCCESS_CODE.equals(headMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
                bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
                String functionNo = (String) headMap.get(Fields.PARAM_FUNCTION_NO);
                HHInterface hhInterface = HHInterface.getHHInterface(functionNo);
                logger.info("☆☆☆☆☆☆☆☆☆☆☆" + hhInterface.toString() + "\t开始调用服务。。。");
                Map serviceMap = null;
                String userNo = null;
                switch (hhInterface) {
                    case HH_USER_LOGIN:
                        serviceMap = loginService.login(bodyMap);
                        break;
                    case HH_CHANGEPSW_FIRSTLOGIN:
                        serviceMap = loginService.changePsw4FirstLogin(bodyMap);
                        break;
                    case HH_USER_LOGOUT:
                        serviceMap = loginService.logout(bodyMap);
                        break;
                    case HH_INIT_APPLY_SUBMIT:
                        serviceMap = applyService.initApplyInfo(bodyMap);
                        break;
                    case HH_INIT_APPLY_MODIFY:
                        serviceMap = applyService.initModifyApplyRecord(bodyMap);
                        break;
                    case HH_GETLIST_AGENT_PRODUCT_BY_DISTRIBUTOR:
                        serviceMap = applyService.getAgentAndProductList(bodyMap);
                        break;
                    case HH_SUBMIT_APPLY_RECORD:
                        userNo = (String) headMap.get(Fields.PARAM_USER_NO);
                        bodyMap.put(Fields.PARAM_USER_NO, userNo);
                        serviceMap = applyService.saveApplyRecord(bodyMap);
                        break;
                    case HH_MODIFY_APPLY_RECORD:
                        userNo = (String) headMap.get(Fields.PARAM_USER_NO);
                        bodyMap.put(Fields.PARAM_USER_NO, userNo);
                        serviceMap = applyService.modifyApplyRecord(bodyMap);
                        break;
                    case HH_DELETE_APPLY_RECORD:
                        serviceMap = applyService.deleteApplyRecord(bodyMap);
                        break;
                    case HH_QUERY_INFO_APPLY_RECORD:
                        serviceMap = applyService.queryApplyRecord(bodyMap);
                        break;
                    case HH_GETLIST_APPLY_RECORD:
                        serviceMap = applyService.queryApplyRecordList(bodyMap);
                        break;
                    case HH_GETLIST_MATERIAL_TYPE:
                        serviceMap = applyService.queryAllMaterialTypeList();
                        break;
                    case HH_SUPPLY_APPLY_MATERIAL:
                        serviceMap = applyService.supplyApplyMaterial(bodyMap);
                        break;
                    case HH_INIT_APPLY_LIST:
                        serviceMap = applyService.initApplyList(bodyMap);
                        break;
                    case HH_GETLIST_MESSAGE:
                        serviceMap = messageService.queryMessageList(bodyMap);
                        break;
                    case HH_QUERY_INFO_MESSAGE:
                        //serviceMap = messageService.queryMessageInfo(bodyMap);
                        break;
                    case HH_MODIFY_RECEIVE_MESSAGE:
                        serviceMap = messageService.modifyReceiveMessage(bodyMap);
                        break;
                    case HH_MODIFY_SEND_MESSAGE:
                        serviceMap = messageService.modifySendMessage(bodyMap);
                        break;
                    case HH_RECEIVE_MESSAGE:
                        serviceMap = messageService.receiveMessage(bodyMap);
                        break;
                    case HH_QUERY_OPERATION_HISTORY:
                        serviceMap = applyService.queryOperationHistory(bodyMap);
                        break;
                    case HH_INIT_INQUIRY:
                        serviceMap = inquiryService.initInquiry(paramMap);
                        break;
                    case HH_INIT_INQUIRY_LIST:
                        serviceMap = inquiryService.initInquiryList(paramMap);
                        break;
                    case HH_HH000102:
                    case HH_HH000103:
                    case HH_HH000104:
                    case HH_HH000105:
                    case HH_HH000106:
                    case HH_HH000122:
                        serviceMap = remoteBpsService.callService(paramMap);
                        break;
                    case HH_HH000110:
                        serviceMap = inquiryService.apartmentInquiry(paramMap);
                        break;
                    case HH_HH000109:
                        serviceMap = inquiryService.villaInquiry(paramMap);
                        break;
                    case HH_HH000107:
                        serviceMap = inquiryService.manualInquiry(paramMap);
                        break;
                    case HH_QUERY_INQUIRY_LIST:
                        serviceMap = inquiryService.queryInquiryList(bodyMap);
                        break;
                    case HH_QUERY_INQUIRY_INFO_DETAILS:
                        serviceMap = inquiryService.queryInquiryInfo(paramMap);
                        break;
                    case HH_QUERY_OPERATION_HISTORY_LIST:
                        serviceMap = inquiryService.queryOperateHistory(bodyMap);
                        break;
                    case HH_UPDATE_INQUIRY_RESULT:
                        serviceMap = inquiryService.updateInquiryResult(bodyMap);
                        break;
                    case HH_INPUT_INQUIRY_PRICE:
                        serviceMap = inquiryService.inputInquiryPrice(paramMap);
                        break;
                    case HH_CLAIM_INQUIRY_TASK:
                        serviceMap = inquiryService.claimInquiryTask(bodyMap);
                        break;
                    case HH_QUERY_TODO_TASK_LIST:
                        serviceMap = nlbsTODOService.getTodoTaskList(bodyMap);
                        break;
                    case HH_INIT_TODO_TASK_LIST:
                        serviceMap = nlbsTODOService.initTodoTaskList();
                        break;
                    case HH_QUERY_TODO_MSG_TIPS_LIST:
                        serviceMap = commonService.queryTodoMsgCountAndDetails(bodyMap);
                        break;
                    default:
                        break;
                }
                logger.info("☆☆☆☆☆☆☆☆☆☆☆" + hhInterface.toString() + "\t调用服务结束！！！");
                if (serviceMap.containsKey(Fields.PARAM_MESSAGE_HEAD) && serviceMap.containsKey(Fields.PARAM_MESSAGE_BODY)) {
                    headMap = (Map<String, Object>) serviceMap.get(Fields.PARAM_MESSAGE_HEAD);
                    bodyMap = (Map<String, Object>) serviceMap.get(Fields.PARAM_MESSAGE_BODY);
                } else {
                    headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, serviceMap.get(Fields.PARAM_MESSAGE_ERR_CODE));
                    headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, serviceMap.get(Fields.PARAM_MESSAGE_ERR_MESG));
                    serviceMap.remove(Fields.PARAM_MESSAGE_ERR_CODE);
                    serviceMap.remove(Fields.PARAM_MESSAGE_ERR_MESG);
                    bodyMap = serviceMap;
                }
            }
        } catch (HHBizException hhBizEx) {
            logger.error("业务异常：", hhBizEx);
            //整理异常情况下的出参
            bodyMap.clear();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, hhBizEx.getErrorNo());
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, hhBizEx.getErrorMessage());
        } catch (Exception e) {
            logger.error("系统异常：", e);
            //整理异常情况下的出参
            bodyMap.clear();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回

        resultMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        resultMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

        return resultMap;
    }

    /**
     * 处理头map中的参数，后续版本控制等，当前不做处理
     *
     * @param headMap
     * @return
     * @throws Exception
     */
    public Map filterHeadParam(Map headMap) throws Exception {
        if (null == headMap) {
            headMap = new HashMap();
            //该返回仅作测试用，后续删除该return
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
            return headMap;
        }
        //执行一系列检查

        headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
        return headMap;
    }
}
