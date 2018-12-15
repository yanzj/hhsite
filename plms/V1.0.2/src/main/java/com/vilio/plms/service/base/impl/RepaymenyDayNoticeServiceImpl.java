package com.vilio.plms.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;
import com.vilio.plms.pojo.UserDistributor;
import com.vilio.plms.service.base.*;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.HttpUtil;
import com.vilio.plms.util.JsonUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by dell on 2017/9/12.
 */
@Service("repaymenyDayNoticeService")
public class RepaymenyDayNoticeServiceImpl implements RepaymenyDayNoticeService {
    private static Logger logger = Logger.getLogger(RepaymenyDayNoticeServiceImpl.class);
    static final String  message_receive_status_able = "01";

    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    CustomerDao customerDao;
    @Resource
    SysInfoParamDao sysInfoParamDao;
    @Resource
    RemoteMpsService remoteMpsService;
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
        if(StringUtils.isBlank(strNoticeDay)){
            logger.info("还款日提醒配置参数配置为空");
            return;
        }
        String[] noticeDayList = strNoticeDay.split(";");
        //遍历所有配置天数,逐个处理
        for(String  noticeDayStr: noticeDayList) {
            int noticeDay = Integer.parseInt(noticeDayStr);

            //存放提醒的用户列表，agent_Code和 提醒相关信息
            Map<String, Map> userAndCountMap = new HashMap();//key:agent_Code,value:提醒相关信息
            //bms_agent_Code 映射 agent_Code
            Map<String, String> bmsAgentCodeConverMap = new HashMap();//key:bms_agent_Code,agent_Code:提醒相关信息

            //获取需要提醒的还款计划--提前noticeDay的还款计划
            Map paramMap = new HashMap();
            Date repaymentDate = DateUtil.getDateDistanceInputDate(DateUtil.parseDateTimeForPattern(DateUtil.getCurrentDate(), DateUtil.DATE_PATTERN), noticeDay);
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
                    //当前业务员
                    bmsAgentCodeList.add(agentSchedule);
                    if(plmsClerkList != null){
                        for(Map clerkMap : plmsClerkList){
                            if(StringUtils.isBlank((String) clerkMap.get("clerk"))){
                                continue;
                            }
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
                        forGetBmsCodeMap.put("status", message_receive_status_able);
                        List<Map> bmsAgentIds = plmsRepaymentScheduleDao.getBmsAgentsWithPlmsAgents(forGetBmsCodeMap);
                        List<Map> forUmAgentIds = new ArrayList<>();
                        if(bmsAgentIds != null){
                            for(Map bmsCodeMap : bmsAgentIds){
                                String agentCode = (String) bmsCodeMap.get("code");
                                String bmsAgentCode = (String) bmsCodeMap.get("bmsCode");
                                //统计提醒数量
                                if(!userAndCountMap.containsKey(agentCode)){
                                    Map noticeMap = new HashMap();
                                    userAndCountMap.put(agentCode, noticeMap);
                                    //还款计划数目
                                    noticeMap.put("count", 0);
                                }
                                Map noticeMap = userAndCountMap.get(agentCode);
                                int currentCount = agentSchedule.get("count") == null ? 0 : Integer.parseInt(agentSchedule.get("count").toString());
                                int prevCount = (int) noticeMap.get("count");
                                noticeMap.put("count", prevCount + currentCount);
                                //邮箱
                                noticeMap.put("email", StringUtils.isBlank((String) bmsCodeMap.get("email")) ? null : bmsCodeMap.get("email"));
                                //bms_agent_code
                                noticeMap.put("bmsAgentCode", bmsAgentCode);

                                Map forUmAgent = new HashMap();
                                forUmAgent.put("agentId", bmsCodeMap.get("bmsCode"));
                                forUmAgentIds.add(forUmAgent);

                                if(!bmsAgentCodeConverMap.containsKey(bmsAgentCode)){
                                    bmsAgentCodeConverMap.put(bmsAgentCode, agentCode);
                                }
                            }
                        } else {
                            logger.info("获取业务员们的核心系统编码时为空，请检查配置·········");
                        }
                        //第四步-调用UM系统获取UM信息（UMID和接收人的邮件信息）
                        if(forUmAgentIds != null && forUmAgentIds.size() > 0){
                            Map forUmMap = new HashMap();
                            forUmMap.put("systemId", "nlbs");
                            forUmMap.put("agentIds", forUmAgentIds);
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
                                                if(bmsAgentCodeConverMap.containsKey(umUserMap.get("agentId")) && userAndCountMap.containsKey(bmsAgentCodeConverMap.get(umUserMap.get("agentId")))){
                                                    Map noticeMap = userAndCountMap.get(bmsAgentCodeConverMap.get(umUserMap.get("agentId")));
                                                    noticeMap.put("userId", userNo);
                                                }
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

                //第五步-发送站内信 和 邮件
                Iterator it = userAndCountMap.keySet().iterator();
                while (it.hasNext()) {
                    String agentCode = (String) it.next();

                    Map noticeMap = userAndCountMap.get(agentCode);
                    Integer count = (Integer) noticeMap.get("count");
                    if(null == count || count < 1) continue;
                    //发送站内信
                    if(!StringUtils.isBlank((String) noticeMap.get("userId"))){
                        sentInstationMessage(count, (String) noticeMap.get("userId"), "nlbs", noticeDay, repaymentDate);
                    }
                    //发送邮件
                    /*if(!StringUtils.isBlank((String) noticeMap.get("email"))){
                        sentEmailMessageForNlbs(count, (String) noticeMap.get("email"), agentCode, repaymentDate, noticeDay);
                    }*/
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
        if(StringUtils.isBlank(strNoticeDay)){
            logger.info("还款日提醒配置参数配置为空");
            return;
        }
        String[] noticeDayList = strNoticeDay.split(";");
        //遍历所有配置天数,逐个处理
        for(String  noticeDayStr: noticeDayList) {
            int noticeDay = Integer.parseInt(noticeDayStr);

            //存放提醒的用户列表，agent_Code和 提醒相关信息
            Map<String, Map> userAndCountMap = new HashMap();//key:user_id,value:提醒相关信息

            //获取需要提醒的还款计划--提前noticeDay的还款计划
            Map paramMap = new HashMap();
            Date repaymentDate = DateUtil.getDateDistanceInputDate(DateUtil.parseDateTimeForPattern(DateUtil.getCurrentDate(), DateUtil.DATE_PATTERN), noticeDay);
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
                                String userId = (String) forUmMap.get("code");
                                String umId = (String) forUmMap.get("umId");

                                Map umUserMap = new HashMap();
                                umUserMap.put("userId", forUmMap.get("umId"));
                                bmsUserIds.add(umUserMap);

                                //统计提醒数量
                                if(!userAndCountMap.containsKey(userId)){
                                    Map noticeMap = new HashMap();
                                    userAndCountMap.put(userId, noticeMap);
                                    //还款计划数目
                                    noticeMap.put("count", 0);
                                }
                                Map noticeMap = userAndCountMap.get(userId);
                                int currentCount = distributorSchedule.get("count") == null ? 0 : Integer.parseInt(distributorSchedule.get("count").toString());
                                int prevCount = (int) noticeMap.get("count");
                                noticeMap.put("count", prevCount + currentCount);
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
                                                if(userAndCountMap.containsKey(userNo)){
                                                    Map noticeMap = userAndCountMap.get(userNo);
                                                    noticeMap.put("email", umUserMap.get("email"));
                                                }
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
                    String userId = (String) it.next();
                    Map noticeMap = userAndCountMap.get(userId);
                    Integer count = (Integer) noticeMap.get("count");
                    if(null == count || count < 1)  continue;
                    //发送站内信
                    sentInstationMessage(count, (String) noticeMap.get("userId"), "plms", noticeDay, repaymentDate);
                    //发送邮件
                    if(!StringUtils.isBlank((String) noticeMap.get("email"))){
                        sentEmailMessageForPlms(count, (String) noticeMap.get("email"), userId, repaymentDate,  noticeDay);
                    }
                }

            } else {
                logger.info("未查到" + noticeDayStr + "天后，即" + repaymentDate + " 的还款日提醒记录！！！");
            }

        }//end of for 系统配置的多个N天的轮询
    }

    /**
     * 发送邮件（进件）
     * @param count  提醒还款计划数量
     * @param email  邮箱
     * @param agentCode  本系统内业务经理
     * @param noticeDay  提前提醒天数
     * @param repaymentDate  还款日
     * @throws Exception
     */
    void sentEmailMessageForNlbs(int count, String email, String agentCode, Date repaymentDate, int noticeDay) throws Exception {
        List<Map> emailToList = new ArrayList<>();
        List<Map> emailCcList = new ArrayList<>();

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
        repaymentScheduleMap.put("agentClerkCode", agentCode);

        List repaymentSchedulelist = queryDao.queryRepaymentScheduleList(repaymentScheduleMap);
        combindEmailContent(emailContent, repaymentSchedulelist, false);

        Map toUserMap = new HashMap();
        toUserMap.put("toUser", email);
        emailToList.add(toUserMap);
        Map forMpsParamMap = new HashMap();
        forMpsParamMap.put("type", "Email");
        forMpsParamMap.put("subject", subject);
        forMpsParamMap.put("toUserList", emailToList);
        forMpsParamMap.put("displayName", GlobParam.systemEmailManagentName);//配置文件获取
        forMpsParamMap.put("requestNo", CommonUtil.getCurrentTimeStr("E-", ""));
        forMpsParamMap.put("userName", GlobParam.systemManagentEmailUserName);//配置文件获取
        forMpsParamMap.put("content", emailContent.toString());
        forMpsParamMap.put("senderIdentityId", "plms_system");
        forMpsParamMap.put("senderName", GlobParam.systemManagentEmailUserName);//配置文件获取
        forMpsParamMap.put("password", GlobParam.systemManagentEmailPassword);//配置文件获取
        forMpsParamMap.put("senderSystem", "plms");
        forMpsParamMap.put("toCcList", emailCcList);//一般为空
        Map mpsReturnMap = remoteMpsService.sendEmailService(forMpsParamMap);
        Map mpsHeadMap = (Map) mpsReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        if (mpsHeadMap != null) {
            Object mpsReturnCode = mpsHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if (ReturnCode.SUCCESS_CODE.equals(mpsReturnCode)) {
                logger.info("用户" + agentCode + "[" + email + "]的邮件发送成功！");
            } else {
                logger.info("用户" + agentCode + "[" + email + "]的邮件发送失败，错误号是：" + mpsReturnCode);
            }
        } else {
            logger.info("用户" + agentCode + "[" + email + "]的邮件发送失败，原因参见消息系统！");
        }
    }
    /**
     * 发送邮件（贷后）
     * @param count  提醒还款计划数量
     * @param email  邮箱
     * @param userId  本系统内用户
     * @throws Exception
     */
    void sentEmailMessageForPlms(int count, String email, String userId, Date repaymentDate, int noticeDay) throws Exception{
        List<Map> emailToList = new ArrayList<>();
        List<Map> emailCcList = new ArrayList<>();

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
        List<UserDistributor> userDistributorList = userDistributorDao.selectUserDistributorByUserId(userId);
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
        toUserMap.put("toUser", email);
        emailToList.add(toUserMap);
        Map forMpsParamMap = new HashMap();
        forMpsParamMap.put("type", "Email");
        forMpsParamMap.put("subject", subject);
        forMpsParamMap.put("toUserList", emailToList);
        forMpsParamMap.put("displayName", GlobParam.systemEmailManagentName);//配置文件获取
        forMpsParamMap.put("requestNo", CommonUtil.getCurrentTimeStr("E-", ""));
        forMpsParamMap.put("userName", GlobParam.systemManagentEmailUserName);//配置文件获取
        forMpsParamMap.put("content", emailContent.toString());
        forMpsParamMap.put("senderIdentityId", "plms_system");
        forMpsParamMap.put("senderName", "");//配置文件获取
        forMpsParamMap.put("password", GlobParam.systemManagentEmailPassword);//配置文件获取
        forMpsParamMap.put("senderSystem", "plms");
        forMpsParamMap.put("toCcList", emailCcList);//一般为空
        Map mpsReturnMap = remoteMpsService.sendEmailService(forMpsParamMap);
        Map mpsHeadMap = (Map) mpsReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        if (mpsHeadMap != null) {
            Object mpsReturnCode = mpsHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if (ReturnCode.SUCCESS_CODE.equals(mpsReturnCode)) {
                logger.info("用户" + userId + "[" + email + "]的邮件发送成功！");
            } else {
                logger.info("用户" + userId + "[" + email + "]的邮件发送失败，错误号是：" + mpsReturnCode);
            }
        } else {
            logger.info("用户" + userId + "[" + email + "]的邮件发送失败，原因参见消息系统！");
        }
    }
    /**
     * 发送站内信
     * @param count 提醒还款计划数量
     * @param userId 接收信息用户id(用户所在系统)
     * @param targetSystem  接收消息系统
     * @param noticeDay  提前提醒天数
     * @param repaymentDate  还款日
     * @throws Exception
     */
    void sentInstationMessage(int count, String userId, String targetSystem, int noticeDay, Date repaymentDate) throws Exception {
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

        remoteMpsMap.put("content", content.toString());
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
        internalParamMap.put("repaymentDate", DateUtil.formatDateTime(repaymentDate, DateUtil.DATE_PATTERN2));
        remoteMpsMap.put(Fields.PARAM_MSG_INTERNAL_PARAM, net.sf.json.JSONObject.fromObject(internalParamMap).toString());

        List<Map> receiverUserList = new ArrayList<Map>();
        Map tempReceiverMap = new HashMap();
        tempReceiverMap.put("code", CommonUtil.getCurrentTimeStr("MS-", ""));
        tempReceiverMap.put("receiverCompanyCode", "");
        tempReceiverMap.put("receiverCompanyName", "");
        tempReceiverMap.put("receiverDepartmentCode", "");
        tempReceiverMap.put("receiverDepartmentName", "");
        tempReceiverMap.put("receiverIdentityId", userId);
        tempReceiverMap.put("receiverSystem", targetSystem);
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
        emailContent.append(" 查询。</p>");
        emailContent.append("<p>客户还款计划如下：</p>");
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
        emailContent.append("</tr>");
        emailContent.append("</thead>");
        emailContent.append("<tbody>");

        int configNum = 100;
        if(repaymentSchedulelist != null && repaymentSchedulelist.size() > 0){
            configNum = configNum > repaymentSchedulelist.size() ? repaymentSchedulelist.size() : configNum;
            for(int i = 0; i < configNum; i++){//不用foreach，可能会使邮件爆掉，具体展示多少行待测试后定下来
                Map repaymentMap = repaymentSchedulelist.get(i);
                emailContent.append("<tr style=\"border: 1px solid #000;\">");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + (i + 1) + "</td>");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("cityName") + "</td>");
                if(sendToPlms) {
                    emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("distributorName") + "</td>");
                }
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("customerName") + "</td>");
                if(!sendToPlms){
                    emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("agentName") + "</td>");
                }
                emailContent.append("<td style=\"border: 1px solid #000;\">" + repaymentMap.get("amount") + "</td>");
                emailContent.append("</tr>");
            }
            if(configNum < repaymentSchedulelist.size()){
                //
                emailContent.append("<tr style=\"border: 1px solid #000;\">");
                emailContent.append("<td style=\"border: 1px solid #000;\">" + (configNum + 1) + "</td>");
                emailContent.append("<td colspan=\"5\" style=\"border: 1px solid #000; \">" + "......" + "</td>");
                emailContent.append("</tr>");
            }
        }
        emailContent.append("</tbody>");
        emailContent.append("</table>");
    }
}
