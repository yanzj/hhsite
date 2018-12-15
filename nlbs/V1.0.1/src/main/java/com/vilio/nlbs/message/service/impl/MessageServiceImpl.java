package com.vilio.nlbs.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.commonMapper.dao.NlbsInquiryApplyMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsMessageReceiveMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsMessageSendMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsPendingUserDistributorMapper;
import com.vilio.nlbs.commonMapper.pojo.NlbsInquiryApply;
import com.vilio.nlbs.commonMapper.pojo.NlbsMessageReceive;
import com.vilio.nlbs.commonMapper.pojo.NlbsMessageSend;
import com.vilio.nlbs.commonMapper.pojo.NlbsUser;
import com.vilio.nlbs.message.service.MessageService;
import com.vilio.nlbs.remote.service.RemoteMpsService;
import com.vilio.nlbs.user.dao.UserDao;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dell on 2017/5/31/0031.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    NlbsMessageSendMapper nlbsMessageSendMapper;

    @Resource
    NlbsMessageReceiveMapper nlbsMessageReceiveMapper;

    @Resource
    UserDao userDao;

    @Resource
    NlbsPendingUserDistributorMapper nlbsPendingUserDistributorMapper;

    @Resource
    NlbsInquiryApplyMapper nlbsInquiryApplyMapper;

    @Resource
    RemoteMpsService remoteMpsService;

    private static Logger logger = Logger.getLogger(MessageServiceImpl.class);
    /**
     * 插入新消息
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public int insertMessage(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        Map msgSendParamMap = new HashMap();

        //Step 1 获取参数
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();//一般指当前登录用户
        String status = paramMap.get(Fields.PARAM_MSG_STATUS) == null ? "" : paramMap.get(Fields.PARAM_MSG_STATUS).toString();//一般指消息状态
        String msgModelCode = paramMap.get(Fields.PARAM_MSG_MSG_MODEL_CODE) == null ? "" : paramMap.get(Fields.PARAM_MSG_MSG_MODEL_CODE).toString();//消息模版
        //以下参数与消息本身无关，都将放入internalParam里
        Map internalParamMap = new HashMap();
        String serialNo = paramMap.get(Fields.PARAM_SERIAL_NO) == null ? "" : paramMap.get(Fields.PARAM_SERIAL_NO).toString();//一般指消息关联的订单序列号
        String msgType = paramMap.get(Fields.PARAM_MSG_MSG_TYPE) == null ? "" : paramMap.get(Fields.PARAM_MSG_MSG_TYPE).toString();//
        String keyAddress = paramMap.get(Fields.PARAM_MSG_KEY_ADDRESS) == null ? "" : paramMap.get(Fields.PARAM_MSG_KEY_ADDRESS).toString();//
        internalParamMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        internalParamMap.put(Fields.PARAM_MSG_MSG_TYPE, msgType);
        internalParamMap.put(Fields.PARAM_MSG_KEY_ADDRESS, keyAddress);

        //Step 1.1 找到当前用户
        NlbsUser nlbsUser = userDao.queryNlbsUserByUserNo(userNo);

        //Step 2 找消息模版，调用不允许出现空值，不作非空校验
        MessageModel messageModel = MessageModel.getMessageModelByCode(msgModelCode);
        //Step 3 整理消息所需公有参数
        String title = messageModel.getTitle();
        String content = MessageModelConfig.getContextProperty(msgModelCode);//此时获取的只是模版，等待后续整理参数后补全
        String userName = nlbsUser.getFullName();
        String internalParamStr = JSONObject.fromObject(internalParamMap).toString();
        List<Map<String, Object>> receiverList = new ArrayList<Map<String, Object>>();

        switch (messageModel){
            case MSG_001:
                //Step 3.1 补全消息内容，模板001，只有1个参数
                content = String.format(content, keyAddress);
                //Step 3.2 获取接收者列表
                receiverList = nlbsPendingUserDistributorMapper.getUserListByUserNo(userNo);
                break;
            case MSG_002:
                //Step 3.1 补全消息内容，模板001，只有1个参数
                content = String.format(content, keyAddress);
                NlbsInquiryApply nlbsInquiryApply = nlbsInquiryApplyMapper.getBeanBySerialNo(serialNo);
                if(nlbsInquiryApply != null) {
                    NlbsUser receiver = userDao.queryNlbsUserByUserNo(nlbsInquiryApply.getUserNo());
                    if(receiver != null){
                        Map userMap = new HashMap();
                        userMap.put(Fields.PARAM_USER_NO, nlbsInquiryApply.getUserNo());
                        userMap.put(Fields.PARAM_USER_NAME, receiver.getFullName());
                        receiverList.add(userMap);
                    }
                }
                //Step 3.2 获取接收者列表
                receiverList = nlbsPendingUserDistributorMapper.getUserListByUserNo(userNo);
                break;
            case MSG_003:
                //Step 3.1 补全消息内容，模板001，只有1个参数
                content = String.format(content, userName, keyAddress);
                //Step 3.2 获取接收者列表
                receiverList = nlbsPendingUserDistributorMapper.getUserListByUserNo(userNo);
                if(receiverList != null) {
                    Map currentUserMap = new HashMap();
                    currentUserMap.put(Fields.PARAM_USER_NO, userNo);
                    currentUserMap.put(Fields.PARAM_USER_NAME, userName);
                    receiverList.remove(currentUserMap);//移除当前用户
                }
                break;
        }
        //Step 4 判断接收者列表--如果为空，则无需保存
        if(receiverList != null && receiverList.size() > 0){
            for(Map userMap : receiverList){
                String code = CommonUtil.getCurrentTimeStr("MS-", "");
                userMap.put(Fields.PARAM_CODE, code);
            }
            //Step 4.1 整理存储数据库的消息对象参数
            msgSendParamMap.put(Fields.PARAM_MSG_TITLE, title);
            msgSendParamMap.put(Fields.PARAM_MSG_CONTENT, content);
            msgSendParamMap.put(Fields.PARAM_USER_NO, userNo);
            msgSendParamMap.put(Fields.PARAM_USER_NAME, userName);
            msgSendParamMap.put(Fields.PARAM_MSG_INTERNAL_PARAM, internalParamStr);
            msgSendParamMap.put(Fields.PARAM_MSG_STATUS, status);
            msgSendParamMap.put(Fields.PARAM_MSG_RECEIVER_USER_LIST, receiverList);

            nlbsMessageSendMapper.getInsertPrmMap(msgSendParamMap);
        }

        //Step 5 把当前消息整理后，发送到MPS
        try{
            if(receiverList != null && receiverList.size() > 0){
                Map remoteMpsMap = new HashMap();
                remoteMpsMap.put("functionNo", "HH000801");
                remoteMpsMap.put("type", "Instation");
                remoteMpsMap.put("title", title);
                remoteMpsMap.put("content", content);
                remoteMpsMap.put("senderCompanyCode", "");
                remoteMpsMap.put("senderCompanyName", "");
                remoteMpsMap.put("senderDepartmentCode", "");
                remoteMpsMap.put("senderDepartmentName", "");
                remoteMpsMap.put("senderIdentityId", userNo);
                remoteMpsMap.put("senderName", userName);
                remoteMpsMap.put("senderSystem", "nlbs");

                List<Map> receiverUserList = new ArrayList<Map>();
                for(Map map : receiverList){
                    Map tempReceiverMap = new HashMap();
                    tempReceiverMap.put("receiverCompanyCode", "");
                    tempReceiverMap.put("receiverCompanyName", "");
                    tempReceiverMap.put("receiverDepartmentCode", "");
                    tempReceiverMap.put("receiverDepartmentName", "");
                    tempReceiverMap.put("receiverIdentityId", map.get(Fields.PARAM_USER_NO));
                    tempReceiverMap.put("receiverSystem", "nlbs");
                    tempReceiverMap.put("receiverName", map.get(Fields.PARAM_USER_NO));
                    receiverUserList.add(tempReceiverMap);
                }
                remoteMpsMap.put("receiveUserList", receiverUserList);
                remoteMpsService.callService(remoteMpsMap);
            }

        }catch (Exception e){
            logger.error("发送消息到消息中心（mps）的时候，发生了故障----------");
        }

        return 0;
    }

    /**
     * 修改发送出的消息，默认修改消息状态
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map modifySendMessage(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        String serialNo = null != paramMap.get(Fields.PARAM_MSG_SERIAL_NO) ? paramMap.get(Fields.PARAM_MSG_SERIAL_NO).toString() : null;
        String code = null != paramMap.get(Fields.PARAM_CODE) ? paramMap.get(Fields.PARAM_CODE).toString() : null;
        String status = null != paramMap.get(Fields.PARAM_MSG_STATUS) ? paramMap.get(Fields.PARAM_MSG_STATUS).toString() : null;

        NlbsMessageSend nlbsMessage = new NlbsMessageSend();
        nlbsMessage.setSerialNo(serialNo);
        nlbsMessage.setCode(code);
        nlbsMessage.setStatus(status);

        nlbsMessageSendMapper.updateStatusBySerialNoOrCode(nlbsMessage);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功修改（更新）消息！");

        return resultMap;
    }
    /**
     * 查询消息列表（含分页）
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map queryMessageList(Map paramMap) throws Exception {

        //Step 1 获取必要参数
        Map<String, Object> resultMap = new HashMap();
        List<Map> returnNlbsMessageMapList = new ArrayList<Map>();
        String userNo = null != paramMap.get(Fields.PARAM_USER_NO) ? paramMap.get(Fields.PARAM_USER_NO).toString() : null;
        String content = null != paramMap.get(Fields.PARAM_MSG_CONTENT) ? paramMap.get(Fields.PARAM_MSG_CONTENT).toString() : null;
        Integer pageNo = null != paramMap.get(Fields.PARAM_PAGE_NO) ? new Integer(paramMap.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != paramMap.get(Fields.PARAM_PAGE_SIZE) ? new Integer(paramMap.get(Fields.PARAM_PAGE_SIZE).toString()) : 4;

        //Step 2 设置过滤参数，供查询或条件查询使用
        NlbsMessageReceive nlbsMessageReceive = new NlbsMessageReceive();
        nlbsMessageReceive.setReceiverIdentityId(userNo);
        nlbsMessageReceive.setContent(content);
        PageHelper.startPage(pageNo, pageSize);
        List<NlbsMessageReceive> nlbsMessageList = nlbsMessageReceiveMapper.selectMessageList(nlbsMessageReceive);
        PageInfo pageInfo = new PageInfo(nlbsMessageList);

        //Step 3 整理出参
        for(NlbsMessageReceive nmr : nlbsMessageList){
            Map tempMessageMap = new HashMap();
            tempMessageMap.put(Fields.PARAM_MSG_SERIAL_NO, nmr.getSerialNo());
            tempMessageMap.put(Fields.PARAM_MSG_TITLE, nmr.getTitle());
            tempMessageMap.put(Fields.PARAM_MSG_CONTENT, nmr.getContent());
            tempMessageMap.put(Fields.PARAM_MSG_SENDER_NAME, nmr.getSenderName());
            tempMessageMap.put(Fields.PARAM_MSG_CREATE_TIME, DateUtil.formatDateForDisplay(nmr.getCreateTime()));
            tempMessageMap.put(Fields.PARAM_MSG_STATUS, nmr.getStatus());
            tempMessageMap.put(Fields.PARAM_MSG_INTERNAL_PARAM, nmr.getInternalParam());

            returnNlbsMessageMapList.add(tempMessageMap);
        }

        resultMap.put(Fields.PARAM_MESSAGE_LIST, returnNlbsMessageMapList);
        resultMap.put(Fields.PARAM_PAGES, pageInfo.getPages() + "");
        resultMap.put(Fields.PARAM_TOTAL, pageInfo.getTotal() + "");
        resultMap.put(Fields.PARAM_CURRENT_PAGE, pageInfo.getPageNum() + "");

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取消息列表！");
        return resultMap;
    }

    /**
     * 修改接收到的消息，默认修改消息状态
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map modifyReceiveMessage(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String serialNo = null != paramMap.get(Fields.PARAM_MSG_SERIAL_NO) ? paramMap.get(Fields.PARAM_MSG_SERIAL_NO).toString() : null;
        String readTime = null != paramMap.get(Fields.PARAM_MSG_READ_TIME) ? paramMap.get(Fields.PARAM_MSG_READ_TIME).toString() : null;
        String readChannel = null != paramMap.get(Fields.PARAM_MSG_READ_CHANNEL) ? paramMap.get(Fields.PARAM_MSG_READ_CHANNEL).toString() : null;
        String status = null != paramMap.get(Fields.PARAM_MSG_STATUS) ? paramMap.get(Fields.PARAM_MSG_STATUS).toString() : null;

        NlbsMessageReceive nlbsMessage = new NlbsMessageReceive();
        nlbsMessage.setSerialNo(serialNo);
        nlbsMessage.setReadTime(readTime == null ? null : sdf.parse(readTime));
        nlbsMessage.setReadChannel(readChannel);
        nlbsMessage.setStatus(status);

        nlbsMessageReceiveMapper.updateStatusAndChannelBySerialNo(nlbsMessage);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功修改（更新）消息！");

        return resultMap;
    }

    /**
     * 接收消息，并保存至数据库中
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map receiveMessage(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();

        List<Map> messageList = (ArrayList<Map>) paramMap.get(Fields.PARAM_MESSAGE_LIST);
        if(messageList != null && messageList.size() > 0) {
            for(Map m : messageList) {
                NlbsMessageReceive nlbsMessage = new NlbsMessageReceive();
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

                nlbsMessageReceiveMapper.saveNlbsMessageReceive(nlbsMessage);
            }
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功接收消息！");
            return resultMap;
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.MSG_EMPTY_RECEIVE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "接收消息为空！");


        return resultMap;
    }
}
