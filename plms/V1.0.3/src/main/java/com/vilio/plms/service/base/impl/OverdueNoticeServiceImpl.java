package com.vilio.plms.service.base.impl;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.UserDistributor;
import com.vilio.plms.service.base.*;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dell on 2017/9/12.
 */
@Service
public class OverdueNoticeServiceImpl implements RepaymenyDayNoticeService {
    private static Logger logger = Logger.getLogger(OverdueNoticeServiceImpl.class);

    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    CustomerDao customerDao;
    @Resource
    SysInfoParamDao sysInfoParamDao;
    @Resource
    RemoteMpsService remoteMpsService;
    @Resource
    RemoteNlbsService remoteNlbsService;
    @Resource
    CommonService commonService;
    @Resource
    RemoteUmService remoteUmService;
    @Resource
    UserDistributorDao userDistributorDao;
    @Resource
    LoginInfoDao loginInfoDao;
    @Resource
    QueryDao queryDao;

    public void nlbsNotice() throws Exception {
        //step1  获取系统配置参数列表，找出需要提醒的天数配置
        Map sysparamMap = new HashMap();
        sysparamMap.put("itemId", "repaymentNoticeDay");
        List<Map> sysparamInfoList = sysInfoParamDao.querySysparamInfoByItemId(sysparamMap);
        if(null == sysparamInfoList || sysparamInfoList.size() == 0){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "还款日提醒配置参数缺失！");
        }
        Map noticeDayMap = sysparamInfoList.get(0);
        String strNoticeDay = (String) noticeDayMap.get("itemCval");
        String[] noticeDayList = strNoticeDay.split(";");
        //遍历所有配置天数,逐个处理
        for(String  noticeDayStr: noticeDayList) {
            int noticeDay = Integer.parseInt(noticeDayStr);

            //存放提前noticeDay天提醒的用户列表，umid和客户个数
            Map<String, Integer> userAndCountMap = new HashMap();//key:umId,value:客户数目
            Map<String, Object> userEmailMap = new HashMap();//key:umId,value:email
            Map<String, Object> bmsPlmsCodeMap = new HashMap();//key:bmsCode,value:plmsCode
            Map<String, Object> umBmsCodeMap = new HashMap();//key:umId,value:bmsCode
            //获取需要提醒的还款计划--提前noticeDay的还款计划
            Map paramMap = new HashMap();
            Date repaymentDate = DateUtil.getDateDistanceInputDate(new Date(), noticeDay);
            paramMap.put("repaymentDate", repaymentDate);

            List<Map> agentScheduleList = plmsRepaymentScheduleDao.getPayingScheduleByRepaymentDateWithAgentGroup(paramMap);
            if(null != agentScheduleList && agentScheduleList.size() > 0){
                for(Map agentSchedule: agentScheduleList){
                    String plmsAgentCode = agentSchedule.get("agentCode") == null ? "" : agentSchedule.get("agentCode").toString();//此处查询到的是plms的code
                    String bmsDistributeCode = agentSchedule.get("bmsDistributeCode") == null ? "" : agentSchedule.get("bmsDistributeCode").toString();

                    //第一步-获取当前业务经理的进件列表的多个录单员
                    Map forGetClerksMap = new HashMap();
                    forGetClerksMap.put("repaymentDate", repaymentDate);
                    forGetClerksMap.put("agentCode", plmsAgentCode);
                    List<Map> plmsClerkList = plmsRepaymentScheduleDao.getClerkListWithRepaymentDate(forGetClerksMap);//Map的key为clerk

                    //第二步-获取当前业务经理的上级
                    List<Map> parentAgentList = commonService.queryParentAgentByAgentCode(plmsAgentCode);//Map的key是agentCode

                    //第三步-获取录单员和业务员对应的bmsCode
                    List<Map> bmsAgentCodeList = new ArrayList<>();
                    if(plmsClerkList != null){
                        for(Map clerkMap : plmsClerkList){
                            Map bmsAgentMap = new HashMap();
                            bmsAgentMap.put("agentCode", clerkMap.get("clerk"));
                            bmsAgentCodeList.add(bmsAgentMap);
                        }
                    }
                    if(parentAgentList != null){
                        for(Map parentAgentMap : parentAgentList){
                            Map bmsAgentMap = new HashMap();
                            bmsAgentMap.put("agentCode", parentAgentMap.get("agentCode"));
                            bmsAgentCodeList.add(bmsAgentMap);
                        }
                    }
                    if(bmsAgentCodeList.size() == 0){
                        logger.info("NLBS接收人列表为空，无需给NLBS发送站内信。。。");
                    }else {
                        Map forGetBmsCodeMap = new HashMap();
                        forGetBmsCodeMap.put("agentList", bmsAgentCodeList);
                        List<Map> bmsAgentIds = plmsRepaymentScheduleDao.getBmsAgentsWithPlmsAgents(forGetBmsCodeMap);
                        List<Map> forUmAgentIds = new ArrayList<>();
                        if(bmsAgentIds != null){
                            for(Map bmsCodeMap : bmsAgentIds){
                                Map forUmAgent = new HashMap();
                                forUmAgent.put("agentId", bmsCodeMap.get("bmsCode"));
                                forUmAgentIds.add(forUmAgent);
                                bmsPlmsCodeMap.put(bmsCodeMap.get("bmsCode") == null ? "" : bmsCodeMap.get("bmsCode").toString(), bmsCodeMap.get("ode"));
                            }
                        } else {
                            logger.info("获取业务员们的核心系统编码时为空，请检查配置·········");
                        }
                        //第四步-调用UM系统获取UM信息（UMID和接收人的邮件信息）
                        if(forUmAgentIds != null && forUmAgentIds.size() > 0){
                            Map forUmMap = new HashMap();
                            forUmMap.put("systemId", "nlbs");
                            forUmMap.put("agentIds", bmsAgentIds);
                            Map umReturnMap = remoteUmService.callService(forUmMap, GlobParam.UM_FUNCTION_GET_USERS_BY_AGENT_CODE);
                            if(umReturnMap != null){
                                Map umHeadMap = (Map)umReturnMap.get("head");
                                if(umHeadMap != null && ReturnCode.SUCCESS_CODE.equals(umHeadMap.get("returnCode"))){
                                    Map umBodyMap = (Map)umReturnMap.get("body");
                                    if(umBodyMap != null){
                                        List<Map> umUserList = (List<Map>) umBodyMap.get("users");
                                        if(umUserList != null && umUserList.size() > 0){
                                            for(Map umUserMap : umUserList){
                                                String userNo = umUserMap.get("userId") == null ? "" : umUserMap.get("userId").toString();
                                                int currentCount = agentSchedule.get("count") == null ? 0 : Integer.parseInt(agentSchedule.get("count").toString());
                                                if(userAndCountMap.containsKey(userNo)){
                                                    int prevCount = userAndCountMap.get(userNo);
                                                    userAndCountMap.put(userNo, prevCount + currentCount);
                                                }
                                                userEmailMap.put(userNo, umUserMap.get("email"));
                                                umBmsCodeMap.put(userNo, umUserMap.get("agentId"));
                                            }
                                        }
                                    }

                                }
                            } else {
                                logger.info("获取接收人的UM信息失败~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            }
                        }
                    }

                }//end of for N天的记录查询

                //第五步-发送站内信和邮件
                Iterator it = userAndCountMap.keySet().iterator();
                while(it.hasNext()){
                    String receiveUser = (String) it.next();//UMID
                    int count = userAndCountMap.get(receiveUser);
                    List<Map> emailToList = new ArrayList<>();
                    List<Map> emailCcList = new ArrayList<>();

                    //站内信提醒
                    Map remoteMpsMap = new HashMap();
                    remoteMpsMap.put("functionNo", GlobParam.mpsInstationFunctionNo);
                    remoteMpsMap.put("type", GlobParam.mpsInstationType);
                    remoteMpsMap.put("title", "还款日提醒");
                    StringBuffer content = new StringBuffer();
                    if(noticeDay > 0){
                        content.append(count + "名客户需于" + noticeDay + "天后还款，请注意跟踪，点击查看详情。");
                    }else{
                        content.append(count + "名客户需于今日还款，请注意跟踪，点击查看详情。");
                    }

                    remoteMpsMap.put("content", content);
                    remoteMpsMap.put("senderCompanyCode", "");
                    remoteMpsMap.put("senderCompanyName", "");
                    remoteMpsMap.put("senderDepartmentCode", "");
                    remoteMpsMap.put("senderDepartmentName", "");
                    remoteMpsMap.put("senderIdentityId", "plms_system");
                    remoteMpsMap.put("senderName", "贷后系统");
                    remoteMpsMap.put("senderSystem", "plms");
                    Map internalParamMap = new HashMap();
                    internalParamMap.put("keyWords", "点击查看详情");
                    internalParamMap.put("msgType", "004");
                    remoteMpsMap.put(Fields.PARAM_MSG_INTERNAL_PARAM, net.sf.json.JSONObject.fromObject(internalParamMap).toString());

                    List<Map> receiverUserList = new ArrayList<Map>();
                    Map tempReceiverMap = new HashMap();
                    tempReceiverMap.put("code", CommonUtil.getCurrentTimeStr("MS-", ""));
                    tempReceiverMap.put("receiverCompanyCode", "");
                    tempReceiverMap.put("receiverCompanyName", "");
                    tempReceiverMap.put("receiverDepartmentCode", "");
                    tempReceiverMap.put("receiverDepartmentName", "");
                    tempReceiverMap.put("receiverIdentityId", receiveUser);
                    tempReceiverMap.put("receiverSystem", "nlbs");
                    tempReceiverMap.put("receiverName", "");
                    receiverUserList.add(tempReceiverMap);
                    remoteMpsMap.put("receiveUserList", receiverUserList);
                    remoteMpsService.callService(remoteMpsMap);
                    //邮件提醒
                    StringBuffer subject = new StringBuffer();
                    StringBuffer emailContent = new StringBuffer();
                    if(noticeDay > 0){
                        subject.append(noticeDay + "天后客户还款提醒");
                        emailContent.append(count + "名客户需于" + noticeDay + "天后还款，请注意跟踪，点击查看详情。");
                    }else{
                        subject.append("今日客户还款提醒");
                        emailContent.append(count + "名客户需于今日还款，请注意跟踪，点击查看详情。");
                    }

                    //拼接邮件内容
                    Map repaymentScheduleMap = new HashMap();
                    repaymentScheduleMap.put("repaymentStartDate", repaymentDate);
                    repaymentScheduleMap.put("repaymentEndDate", repaymentDate);
                    //还有个筛选条件-----agentClerkCode
                    repaymentScheduleMap.put("agentClerkCode", bmsPlmsCodeMap.get(umBmsCodeMap.get(receiveUser)));

                    List repaymentSchedulelist = queryDao.queryRepaymentScheduleList(repaymentScheduleMap);
                    combindEmailContent(emailContent, repaymentSchedulelist, false);

                    Map toUserMap = new HashMap();
                    toUserMap.put("toUser", userEmailMap.get(receiveUser));
                    emailToList.add(toUserMap);
                    Map forMpsParamMap = new HashMap();
                    forMpsParamMap.put("type", "Email");
                    forMpsParamMap.put("subject", subject);
                    forMpsParamMap.put("toUserList", emailToList);
                    forMpsParamMap.put("displayName", "");//配置文件获取
                    forMpsParamMap.put("requestNo", CommonUtil.getCurrentTimeStr("E-", ""));
                    forMpsParamMap.put("userName", "");//配置文件获取
                    forMpsParamMap.put("content", emailContent);
                    forMpsParamMap.put("senderIdentityId", "plms_system");
                    forMpsParamMap.put("senderName", "");//配置文件获取
                    forMpsParamMap.put("password", "");//配置文件获取
                    forMpsParamMap.put("senderSystem", "plms");
                    forMpsParamMap.put("toCcList", emailCcList);//一般为空
                    Map mpsReturnMap = remoteMpsService.sendEmailService(forMpsParamMap);
                    Map mpsHeadMap = (Map) mpsReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
                    if (mpsHeadMap != null) {
                        Object mpsReturnCode = mpsHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
                        if (ReturnCode.SUCCESS_CODE.equals(mpsReturnCode)) {
                            logger.info("用户" + receiveUser + "[" + userEmailMap.get(receiveUser) + "]的邮件发送成功！");
                        } else {
                            logger.info("用户" + receiveUser + "[" + userEmailMap.get(receiveUser) + "]的邮件发送失败，错误号是：" + mpsReturnCode);
                        }
                    } else {
                        logger.info("用户" + receiveUser + "[" + userEmailMap.get(receiveUser) + "]的邮件发送失败，原因参见消息系统！");
                    }
                }

            } else {
                logger.info("未查到" + noticeDayStr + "天后，即" + repaymentDate + " 的还款日提醒记录！！！");
            }

        }//end of for 系统配置的多个N天的轮询
    }

    public void plmsNotice() throws Exception {
        //step1  获取系统配置参数列表，找出需要提醒的天数配置
        Map sysparamMap = new HashMap();
        sysparamMap.put("itemId", "repaymentNoticeDay");
        List<Map> sysparamInfoList = sysInfoParamDao.querySysparamInfoByItemId(sysparamMap);
        if(null == sysparamInfoList || sysparamInfoList.size() == 0){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "还款日提醒配置参数缺失！");
        }
        Map noticeDayMap = sysparamInfoList.get(0);
        String strNoticeDay = (String) noticeDayMap.get("itemCval");
        String[] noticeDayList = strNoticeDay.split(";");
        //遍历所有配置天数,逐个处理
        for(String  noticeDayStr: noticeDayList) {
            int noticeDay = Integer.parseInt(noticeDayStr);

            //存放提前noticeDay天提醒的用户列表，umid和客户个数
            Map<String, Integer> userAndCountMap = new HashMap();
            Map<String, Object> userEmailMap = new HashMap();//key:umId,value:email
            Map<String, Object> userCodeMap = new HashMap();//key:umId,value:plmsUserCode
            //获取需要提醒的还款计划--提前noticeDay的还款计划
            Map paramMap = new HashMap();
            Date repaymentDate = DateUtil.getDateDistanceInputDate(new Date(), noticeDay);
            paramMap.put("repaymentDate", repaymentDate);

            List<Map> distributorScheduleList = plmsRepaymentScheduleDao.getPayingScheduleByRepaymentDateWithDistributorGroup(paramMap);
            if(null != distributorScheduleList && distributorScheduleList.size() > 0){
                for(Map distributorSchedule: distributorScheduleList){
                    String plmsDistributorCode = distributorSchedule.get("distributeCode") == null ? "" : distributorSchedule.get("distributeCode").toString();//此处查询到的是plms的code

                    //第一步-获取可见当前渠道的用户列表
                    List<Map> plmsUserList = userDistributorDao.selectUserDistributorByDistributorCode(plmsDistributorCode);//Map的key为clerk

                    //第二步-获取某些指定的角色，待定---


                    //第三步-获取接收用户对应的UMID
                    if (plmsUserList != null && plmsUserList.size() > 0) {
                        Map forGetUmIdMap = new HashMap();
                        forGetUmIdMap.put("userList", plmsUserList);
                        List<Map> forUmUsers = loginInfoDao.queryUmIdsWithUserList(forGetUmIdMap);
                        List<Map> bmsUserIds = new ArrayList<>();
                        if(forUmUsers != null && forUmUsers.size() > 0){
                            for(Map forUmMap : forUmUsers){
                                Map umUserMap = new HashMap();
                                umUserMap.put("userId", forUmMap.get("umId"));
                                bmsUserIds.add(umUserMap);
                                userCodeMap.put(forUmMap.get("umId") == null ? "" : forUmMap.get("umId").toString(), forUmMap.get("code"));
                            }
                        }

                        //第四步-调用UM系统获取UM信息（UMID和接收人的邮件信息）
                        if(bmsUserIds != null && bmsUserIds.size() > 0){
                            Map forUmMap = new HashMap();
                            forUmMap.put("systemId", "plms");
                            forUmMap.put("userIds", bmsUserIds);
                            Map umReturnMap = remoteUmService.callService(forUmMap, GlobParam.UM_FUNCTION_GET_USERS_BY_AGENT_CODE);
                            if(umReturnMap != null){
                                Map umHeadMap = (Map)umReturnMap.get("head");
                                if(umHeadMap != null && ReturnCode.SUCCESS_CODE.equals(umHeadMap.get("returnCode"))){
                                    Map umBodyMap = (Map)umReturnMap.get("body");
                                    if(umBodyMap != null){
                                        List<Map> umUserList = (List<Map>) umBodyMap.get("users");
                                        if(umUserList != null && umUserList.size() > 0){
                                            for(Map umUserMap : umUserList){
                                                String userNo = umUserMap.get("userId") == null ? "" : umUserMap.get("userId").toString();
                                                int currentCount = distributorSchedule.get("count") == null ? 0 : Integer.parseInt(distributorSchedule.get("count").toString());
                                                if(userAndCountMap.containsKey(userNo)){
                                                    int prevCount = userAndCountMap.get(userNo);
                                                    userAndCountMap.put(userNo, prevCount + currentCount);
                                                }
                                                userEmailMap.put(userNo, umUserMap.get("email"));
                                            }
                                        }
                                    }

                                }
                            } else {
                                logger.info("获取接收人的UM信息失败~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            }
                        }
                    } else {
                        logger.info("待接收消息和邮件的用户列表为空，无需发送！");
                    }
                }//end of for N天的记录查询

                //第五步-发送站内信和邮件
                Iterator it = userAndCountMap.keySet().iterator();
                while(it.hasNext()){
                    String receiveUser = (String) it.next();//此处是指UM的ID，针对PLMS需要用自己的CODE
                    int count = userAndCountMap.get(receiveUser);
                    List<Map> emailToList = new ArrayList<>();
                    List<Map> emailCcList = new ArrayList<>();

                    //站内信提醒
                    Map remoteMpsMap = new HashMap();
                    remoteMpsMap.put("functionNo", GlobParam.mpsInstationFunctionNo);
                    remoteMpsMap.put("type", GlobParam.mpsInstationType);
                    remoteMpsMap.put("title", "还款日提醒");
                    StringBuffer content = new StringBuffer();
                    if(noticeDay > 0){
                        content.append(count + "名客户需于" + noticeDay + "天后还款，请注意跟踪，点击查看详情。");
                    }else{
                        content.append(count + "名客户需于今日还款，请注意跟踪，点击查看详情。");
                    }
                    remoteMpsMap.put("content", content);
                    remoteMpsMap.put("senderCompanyCode", "");
                    remoteMpsMap.put("senderCompanyName", "");
                    remoteMpsMap.put("senderDepartmentCode", "");
                    remoteMpsMap.put("senderDepartmentName", "");
                    remoteMpsMap.put("senderIdentityId", "plms_system");
                    remoteMpsMap.put("senderName", "贷后系统");
                    remoteMpsMap.put("senderSystem", "plms");
                    Map internalParamMap = new HashMap();
                    internalParamMap.put("keyWords", "点击查看详情");
                    internalParamMap.put("msgType", "004");
                    remoteMpsMap.put(Fields.PARAM_MSG_INTERNAL_PARAM, net.sf.json.JSONObject.fromObject(internalParamMap).toString());

                    List<Map> receiverUserList = new ArrayList<Map>();
                    Map tempReceiverMap = new HashMap();
                    tempReceiverMap.put("code", CommonUtil.getCurrentTimeStr("MS-", ""));
                    tempReceiverMap.put("receiverCompanyCode", "");
                    tempReceiverMap.put("receiverCompanyName", "");
                    tempReceiverMap.put("receiverDepartmentCode", "");
                    tempReceiverMap.put("receiverDepartmentName", "");
                    tempReceiverMap.put("receiverIdentityId", userCodeMap.get(receiveUser));
                    tempReceiverMap.put("receiverSystem", "nlbs");
                    tempReceiverMap.put("receiverName", "");
                    receiverUserList.add(tempReceiverMap);
                    remoteMpsMap.put("receiveUserList", receiverUserList);
                    remoteMpsService.callService(remoteMpsMap);
                    //邮件提醒
                    StringBuffer subject = new StringBuffer();
                    StringBuffer emailContent = new StringBuffer();
                    if(noticeDay > 0){
                        subject.append(noticeDay + "天后客户还款提醒");
                        emailContent.append(count + "名客户需于" + noticeDay + "天后还款，请注意跟踪，点击查看详情。");
                    }else{
                        subject.append("今日客户还款提醒");
                        emailContent.append(count + "名客户需于今日还款，请注意跟踪，点击查看详情。");
                    }
                    //拼接邮件内容
                    Map repaymentScheduleMap = new HashMap();
                    repaymentScheduleMap.put("repaymentStartDate", repaymentDate);
                    repaymentScheduleMap.put("repaymentEndDate", repaymentDate);
                    List<UserDistributor> userDistributorList = userDistributorDao.selectUserDistributorByUserId(userCodeMap.get(receiveUser) == null ? "" : userCodeMap.get(receiveUser).toString());
                    List<Map> distributorList = null;
                    if(userDistributorList != null && userDistributorList.size() > 0){//逻辑上讲，不会为空
                        distributorList = new ArrayList<>();
                        for(UserDistributor ud : userDistributorList){
                            Map distributorMap = new HashMap();
                            distributorMap.put("distributorCode", ud.getDistributorCode());
                            distributorList.add(distributorMap);
                        }
                    }
                    repaymentScheduleMap.put("distributorList", distributorList);

                    List repaymentSchedulelist = queryDao.queryRepaymentScheduleList(repaymentScheduleMap);
                    combindEmailContent(emailContent, repaymentSchedulelist, true);

                    Map toUserMap = new HashMap();
                    toUserMap.put("toUser", userEmailMap.get(receiveUser));
                    emailToList.add(toUserMap);
                    Map forMpsParamMap = new HashMap();
                    forMpsParamMap.put("type", "Email");
                    forMpsParamMap.put("subject", subject);
                    forMpsParamMap.put("toUserList", emailToList);
                    forMpsParamMap.put("displayName", "");//配置文件获取
                    forMpsParamMap.put("requestNo", CommonUtil.getCurrentTimeStr("E-", ""));
                    forMpsParamMap.put("userName", "");//配置文件获取
                    forMpsParamMap.put("content", emailContent);
                    forMpsParamMap.put("senderIdentityId", "plms_system");
                    forMpsParamMap.put("senderName", "");//配置文件获取
                    forMpsParamMap.put("password", "");//配置文件获取
                    forMpsParamMap.put("senderSystem", "plms");
                    forMpsParamMap.put("toCcList", emailCcList);//一般为空
                    Map mpsReturnMap = remoteMpsService.sendEmailService(forMpsParamMap);
                    Map mpsHeadMap = (Map) mpsReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
                    if (mpsHeadMap != null) {
                        Object mpsReturnCode = mpsHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
                        if (ReturnCode.SUCCESS_CODE.equals(mpsReturnCode)) {
                            logger.info("用户" + userCodeMap.get(receiveUser) + "[" + userEmailMap.get(receiveUser) + "]的邮件发送成功！");
                        } else {
                            logger.info("用户" + userCodeMap.get(receiveUser) + "[" + userEmailMap.get(receiveUser) + "]的邮件发送失败，错误号是：" + mpsReturnCode);
                        }
                    } else {
                        logger.info("用户" + userCodeMap.get(receiveUser) + "[" + userEmailMap.get(receiveUser) + "]的邮件发送失败，原因参见消息系统！");
                    }
                }

            } else {
                logger.info("未查到" + noticeDayStr + "天后，即" + repaymentDate + " 的还款日提醒记录！！！");
            }

        }//end of for 系统配置的多个N天的轮询
    }

    /**
     * @Description: 拼接邮件内容，主要是表格内容
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/15/0015
     */
    private void combindEmailContent(StringBuffer emailContent, List<Map> repaymentSchedulelist, boolean sendToPlms){

        emailContent.append("<p>详情请登录");
        emailContent.append("https://plms.vilio.com.cn");//配置文件获取
        emailContent.append("查询</p>");
        emailContent.append("<p>逾期客户记录如下：</p>");
        emailContent.append("<table style=\"border: 1px solid #000; border-collapse: collapse\">");
        emailContent.append("<thead>");
        emailContent.append("<tr style=\"border: 1px solid #000;\">");
        emailContent.append("<th style=\"border: 1px solid #000;\">序号</th>");
        emailContent.append("<th style=\"border: 1px solid #000;\">城市</th>");
        if(sendToPlms) {
            emailContent.append("<th style=\"border: 1px solid #000;\">渠道</th>");
        }
        emailContent.append("<th style=\"border: 1px solid #000;\">借款人</th>");
        if(!sendToPlms){
            emailContent.append("<th style=\"border: 1px solid #000;\">负责人</th>");
        }
        emailContent.append("<th style=\"border: 1px solid #000;\">应还总额</th>");
        emailContent.append("<th style=\"border: 1px solid #000;\">逾期天数</th>");
        emailContent.append("</tr>");
        emailContent.append("</thead>");
        emailContent.append("<tbody>");

        if(repaymentSchedulelist != null && repaymentSchedulelist.size() > 0){
            for(int i = 0; i < repaymentSchedulelist.size(); i++){//不用foreach，可能会使邮件爆掉，具体展示多少行待测试后定下来
                Map repaymentMap = repaymentSchedulelist.get(i);
                emailContent.append("<tr style=\"border: 1px solid #000;\">");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + (i + 1) + "</td>");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("cityName") + "</td>");
                if(sendToPlms) {
                    emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("distributorName") + "</td>");
                }
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("customerName") + "</td>");
                if(!sendToPlms) {
                    emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("agentName") + "</td>");
                }
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("amount") + "</td>");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("amount") + "</td>");
                emailContent.append("</tr>");
            }
        }
        emailContent.append("</tbody>");
        emailContent.append("</table>");
    }
}
