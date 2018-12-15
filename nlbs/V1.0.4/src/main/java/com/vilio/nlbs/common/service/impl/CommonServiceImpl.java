package com.vilio.nlbs.common.service.impl;

import com.vilio.nlbs.bms.dao.BmsChannelMembersDao;
import com.vilio.nlbs.bms.dao.BmsChannelsDao;
import com.vilio.nlbs.common.dao.CommonDao;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
import com.vilio.nlbs.dynamicdatasource.CustomerContextHolder;
import com.vilio.nlbs.message.service.MessageService;
import com.vilio.nlbs.remote.service.RemoteUmService;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.HHBizException;
import com.vilio.nlbs.util.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xiezhilei on 2017/1/12.
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    CommonDao commonDao;

    @Resource
    NlbsCityMapper nlbsCityMapper;

    @Resource
    RemoteUmService remoteUmService;

    @Resource
    NlbsRecordersDistributorMapper nlbsRecordersDistributorMapper;

    @Resource
    NlbsTodoMapper nlbsTodoMapper;

    @Resource
    NlbsMessageReceiveMapper nlbsMessageReceiveMapper;

    @Resource
    MessageService messageService;

    @Resource
    BmsChannelsDao bmsChannelsDao;

    @Resource
    BmsChannelMembersDao bmsChannelMembersDao;

    @Resource
    NlbsApplyLoanStatusMapper nlbsApplyLoanStatusMapper;

    @Resource
    NlbsUserGovernCityMapper nlbsUserGovernCityMapper;

    @Resource
    NlbsLoginInfoMapper nlbsLoginInfoMapper;

    public String getUUID() throws Exception{
        return commonDao.getUUID();
    }

    /**
     * 调用UM获取用户的角色列表
     * 角色状态：1-正常 0-停用 2-已删除（不会返回）
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryUserRoleList(Map paramMap) throws Exception {
        //Step1 入参检查
        if(paramMap == null){
            return new ArrayList<Map>();
        }
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String roleId = paramMap.get(Fields.PARAM_ROLE_ID) == null ? "" : paramMap.get(Fields.PARAM_ROLE_ID).toString();
        String roleStatus = paramMap.get(Fields.PARAM_ROLE_STATUS) == null ? "" : paramMap.get(Fields.PARAM_ROLE_STATUS).toString();

        List<Map> returnRoleList = new ArrayList<Map>();
        Map resultMap = new HashMap();

        //Step2 整理调用UM参数
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_USER_ID, userNo);
        remoteParamMap.put(Fields.PARAM_ROLE_ID, roleId);
        remoteParamMap.put(Fields.PARAM_ROLE_STATUS, roleStatus);
        remoteParamMap.put(Fields.PARAM_SYSTEM_ID, Constants.SYSTEM_ID_NLBS);
        resultMap =  remoteUmService.callService(remoteParamMap, Constants.UM_FUNCTION_GET_ROLES);
        //Step3 判断返回参数并整理出参
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                Object roles = bodyMap.get(Fields.PARAM_ROLES);

                if(roles != null){
                    returnRoleList = (ArrayList<Map>) roles;
                }
            }
        }


        return returnRoleList;
    }

    public List<Map> queryAllMaterialTypeList() throws Exception {
        return commonDao.queryAllMaterialTypeList();
    }


    public List<NlbsCity> queryAllCity() throws Exception {
        return nlbsCityMapper.queryAllCity();
    }

    @Override
    public List<NlbsCity> queryAllInquiryCity() throws Exception {

        return nlbsCityMapper.queryAllInquiryCity();
    }

    /**
     * 远程调用um返回渠道列表
     * 4-仅返回自己
     * 0-返回包含自己的所有上级渠道
     * 1-返回包含自己的所有下级渠道
     * 2-返回所有的上级和下级渠道
     * 3-返回所有渠道
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryDistributors(Map paramMap) throws Exception {
        //Step1 入参检查
        if(paramMap == null){
            return new ArrayList<Map>();
        }
        //Step2 整理调用UM参数
        String distributorCode = paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE) == null ? "" : paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE).toString();
        String type = paramMap.get(Fields.PARAM_TYPE) == null ? "" : paramMap.get(Fields.PARAM_TYPE).toString();
        List<Map> returnDistributorList = new ArrayList<Map>();
        Map resultMap = new HashMap();
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_DISTRIBUTRO_CODE, distributorCode);
        remoteParamMap.put(Fields.PARAM_TYPE, type);
        resultMap = remoteUmService.callService(remoteParamMap, Constants.UM_FUNCTION_GET_DISTRIBUTORS);

        //Step3 判断返回参数并整理出参
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                List<Map> distributors = (List<Map>)bodyMap.get(Fields.PARAM_GROUPS);
                if(distributors != null && distributors.size() > 0){
                    returnDistributorList = convertMapToList(distributors.get(0), Fields.PARAM_CHILD_GROUPS);
                }
            }
        }
        return returnDistributorList;
    }

    /**
     * 根据传入的渠道号在BMS查询其子孙渠道(不含自身)
     * @param distributorCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryBmsDistributors(String distributorCode) throws Exception {
        if(StringUtils.isBlank(distributorCode)){
            return new ArrayList<>();
        }
        List<Map> returnDistributorList = new ArrayList<>();
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> childrenDistributors = bmsChannelsDao.queryChildrenChannels(distributorCode);
        if(childrenDistributors != null && childrenDistributors.size() > 0){
            for(Map distributor : childrenDistributors){
                Object childDistributorCode = distributor.get(Fields.PARAM_DISTRIBUTRO_CODE);
                if(childDistributorCode != null) {
                    returnDistributorList.addAll(queryBmsDistributors(childDistributorCode.toString()));
                }
            }
            returnDistributorList.addAll(childrenDistributors);
        }
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        //去除重复项
        Set distributorSet = new LinkedHashSet();
        distributorSet.addAll(returnDistributorList);
        returnDistributorList.clear();
        returnDistributorList.addAll(distributorSet);
        return returnDistributorList;
    }

    /**
     * 根据传入的渠道号在BMS查询其子孙渠道(含自身)
     * @param distributorCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryBmsDistributorsIncludeItself(String distributorCode) throws Exception {
        List<Map> bmsDistributorList = new ArrayList<Map>();
        //添加自身所在渠道
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        Map bmsChannel = bmsChannelsDao.queryChannelById(distributorCode);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if(bmsChannel != null){
            bmsDistributorList.add(bmsChannel);
        }
        bmsDistributorList.addAll(queryBmsDistributors(distributorCode));
        return bmsDistributorList;
    }

    /**
     * 根据传入的业务员编号在BMS查询其子孙业务员(不含自身)
     * @param agentId
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryBmsAgents(String agentId) throws Exception {
        if(StringUtils.isBlank(agentId)){
            return new ArrayList<>();
        }
        List<Map> returnAgentList = new ArrayList<>();
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> childrenAgents = bmsChannelMembersDao.queryChildrenAgents(agentId);
        if(childrenAgents != null && childrenAgents.size() > 0){
            for(Map agent : childrenAgents){
                Object childAgentId = agent.get(Fields.PARAM_AGENT_ID);
                if(childAgentId != null) {
                    returnAgentList.addAll(queryBmsAgents(childAgentId.toString()));
                }
            }
            returnAgentList.addAll(childrenAgents);
        }
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        //去除重复项
        Set agentSet = new LinkedHashSet();
        agentSet.addAll(returnAgentList);
        returnAgentList.clear();
        returnAgentList.addAll(agentSet);
        return returnAgentList;
    }

    /**
     * 根据传入的业务员编号在BMS查询其子孙业务员(含自身)
     * @param agentId
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryBmsAgentsIncludeItself(String agentId) throws Exception {
        List<Map> bmsAgentList = new ArrayList<Map>();
        //添加自身所在渠道
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        Map bmsAgent = bmsChannelMembersDao.queryAgentById(agentId);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if(bmsAgent != null){
            bmsAgentList.add(bmsAgent);
        }
        bmsAgentList.addAll(queryBmsAgents(agentId));
        return bmsAgentList;
    }

    /**
     * 远程调用um返回用户列表
     * 4-仅返回自己
     * 0-返回包含自己的所有上级用户
     * 1-返回包含自己的所有下级用户
     * 2-返回所有的上级和下级用户
     * 3-返回所有用户
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryUsers(Map paramMap) throws Exception {
        //Step1 入参检查
        if(paramMap == null){
            return new ArrayList<Map>();
        }

        //Step2 整理调用UM参数
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String type = paramMap.get(Fields.PARAM_TYPE) == null ? "" : paramMap.get(Fields.PARAM_TYPE).toString();
        List<Map> returnUserList = new ArrayList<Map>();
        Map resultMap = new HashMap();
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_USER_ID, userNo);
        remoteParamMap.put(Fields.PARAM_TYPE, type);
        resultMap = remoteUmService.callService(remoteParamMap, Constants.UM_FUNCTION_GET_USERS);

        //Step3 判断返回参数并整理出参
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                List<Map> users = (List<Map>)bodyMap.get(Fields.PARAM_USERS);
                if(users != null && users.size() > 0){
                    returnUserList = convertMapToList(users.get(0), Fields.PARAM_CHILD_USERS);
                }
            }
        }
        return returnUserList;
    }

    @Override
    public Map queryUserByUserNo(String userNo) throws Exception {
        Map forUserSelfMap = new HashMap();
        forUserSelfMap.put(Fields.PARAM_USER_NO, userNo);
        forUserSelfMap.put(Fields.PARAM_TYPE, Constants.USER_TREE_ITSELF);
        List<Map> nlbsUserList = queryUsers(forUserSelfMap);
        if(nlbsUserList != null){
            for(Map user : nlbsUserList){
                return user;
            }
        }
        return null;
    }

    /**
     * 判断用户是否是超级管理员
     * @param userNo--需判断的用户
     * @param roleList--管理员的角色代码列表
     * @return
     * @throws Exception
     */
    @Override
    public boolean isAdministrator(String userNo, List<String> roleList) throws Exception {

        if(StringUtils.isBlank(userNo)){
            return false;
        }
        //获取当前用户所拥有的所有角色
        Map queryRoleMap = new HashMap();
        queryRoleMap.put(Fields.PARAM_USER_NO, userNo);
        queryRoleMap.put(Fields.PARAM_ROLE_STATUS, Constants.ROLE_STATUS_VALID);
        List<Map> userRoleList = queryUserRoleList(queryRoleMap);

        //判断当前用户的角色是否在传入的角色列表中
        if(userRoleList != null && userRoleList.size() > 0 && roleList != null && roleList.size() > 0){
            for(String roleStr : roleList) {
                for (Map roleMap : userRoleList) {
                    if(roleStr.equals(roleMap.get(Fields.PARAM_ROLE_ID))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isRecordClerk(String userNo) throws Exception {
        if(null == userNo){
            return false;
        }
        List<NlbsRecordersDistributor>  list = nlbsRecordersDistributorMapper.selectRecordersDistributorByUserNo(userNo);

        if(null != list && list.size() > 0){
            return true;
        }
        return false;
    }

    /**
     * 根据渠道号获取当前渠道下的所有用户
     * @param distributorCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> selectUsersByDistributorCode(String distributorCode) throws Exception {
        //Step1 入参检查
        if(StringUtils.isBlank(distributorCode)){
            return new ArrayList<Map>();
        }

        //Step2 整理调用UM参数
        List<Map> returnUserList = new ArrayList<Map>();
        Map resultMap = new HashMap();
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_GROUP_ID, distributorCode);
        resultMap = remoteUmService.callService(remoteParamMap, Constants.UM_FUNCTION_GET_USERS_BY_DISTRIBUTOR_CODE);

        //Step3 判断返回参数并整理出参
        if(resultMap != null){
            Map headMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) resultMap.get(Fields.PARAM_MESSAGE_BODY);
                returnUserList = (List<Map>) bodyMap.get(Fields.PARAM_USERS);
            }
        }
        return returnUserList;
    }

    /**
     * 根据用户编号，查询待办任务和消息的提示信息
     * @param paramMap
     * @return 待办任务数量-未读消息数量-待办任务详情列表-4条消息列表
     * @throws Exception
     */
    @Override
    public Map queryTodoMsgCountAndDetails(Map paramMap) throws Exception {
        //Step1 参数检查
        if(paramMap == null){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "参数有误");
        }
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        if(StringUtils.isBlank(userNo)){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[userNo]");
        }
        int todoCount = 0;
        int msgCount = 0;
        //Step2 获取未读消息个数
        NlbsMessageReceive nlbsMessageReceive = new NlbsMessageReceive();
        nlbsMessageReceive.setReceiverIdentityId(userNo);
        msgCount = nlbsMessageReceiveMapper.getUnReadMsgCount(nlbsMessageReceive);

        //Step3 获取代办任务的提示列表，同时计算待办任务总个数
        List<Map<String ,Object>> todoTipsList = nlbsTodoMapper.getTodoListGroupByType(userNo);
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
        returnMap.put(Fields.PARAM_TODO_COUNT, todoCount);
        returnMap.put(Fields.PARAM_MESSAGE_COUNT, msgCount);
        returnMap.put(Fields.PARAM_TODO_TIPS_LIST, todoTipsList);
        returnMap.put(Fields.PARAM_MESSAGE_TIPS_LIST, messageTipsList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取待办任务和消息提醒列表！");

        return returnMap;
    }

    /**
     * 根据核心系统的状态编码获取进件系统对应的状态编码
     * @param bmsStatusCode
     * @return
     * @throws Exception
     */
    @Override
    public Map getNlbsStatusMapByBmsStatusCode(List<NlbsApplyLoanStatus> nlbsApplyLoanStatusList, String bmsStatusCode, List<Map> subOperationList, boolean currentStatus) throws Exception {
        Map returnMap = new HashMap();
        if(nlbsApplyLoanStatusList == null || nlbsApplyLoanStatusList.size() == 0){
            nlbsApplyLoanStatusList = nlbsApplyLoanStatusMapper.queryApplyStatusMap();
        }
        boolean isAfterMortgage = false;
        if(currentStatus) {
            //1.授信放款中---当前进件下的所有借款合同在抵押后的状态不是下面放款成功的情况
            //2.授信成功---当前进件下的所有借款合同在抵押后的状态至少有一个为放款成功，且其它合同的状态为拒单\撤单\作废
            //3.拒单--若为抵押后状态，所有合同的状态都为拒单
            //4.撤单--若为抵押后状态，所有合同的状态都为撤单
            //5.作废--若为抵押后状态，所有合同的状态都为作废
            //抵押后状态目前有：0081-0090-0091-0100
            List<String> afterMortgageStatusList = Arrays.asList(Constants.BMS_APPLY_STATUS_PENDING_QUERY, Constants.BMS_APPLY_STATUS_CHECKING_LOANCONDITION, Constants.BMS_APPLY_STATUS_PENDING_LOAN, Constants.BMS_APPLY_STATUS_LOAN_SUCCESS);

            //若为抵押后状态
            if (afterMortgageStatusList.contains(bmsStatusCode)) {
                if (subOperationList != null && subOperationList.size() > 0) {
                    isAfterMortgage = true;
                    int subApplyCount = subOperationList.size();//子单个数
                    int successCount = 0;
                    int refuseCount = 0;
                    int cancelCount = 0;
                    int deleteCount = 0;
                    for (Map subOperationMap : subOperationList) {
                        //成功
                        if (Constants.BMS_APPLY_STATUS_LOAN_SUCCESS.equals(subOperationMap.get("subStatus"))) {
                            successCount++;
                        }
                        //拒单
                        if (Constants.BMS_APPLY_STATUS_REFUSE.equals(subOperationMap.get("subStatus"))) {
                            refuseCount++;
                        }
                        //撤单
                        if (Constants.BMS_APPLY_STATUS_CANCEL.equals(subOperationMap.get("subStatus"))) {
                            cancelCount++;
                        }
                        //作废
                        if (Constants.BMS_APPLY_STATUS_DELETE.equals(subOperationMap.get("subStatus"))) {
                            deleteCount++;
                        }
                    }
                    if (successCount == subApplyCount) {//全部成功
                        returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "08");
                        returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "成功");
                        return returnMap;
                    }
                    if (refuseCount == subApplyCount) {//全部拒单
                        returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "98");
                        returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "拒单");
                        return returnMap;
                    }
                    if (cancelCount == subApplyCount) {//全部撤单
                        returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "97");
                        returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "撤单");
                        return returnMap;
                    }
                    if (deleteCount == subApplyCount) {//全部作废
                        returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "99");
                        returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "作废");
                        return returnMap;
                    }
                    returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "07");
                    returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "授信放款中");
                    return returnMap;
                }
            }
        }
        //若为抵押前状态
        if(!isAfterMortgage && nlbsApplyLoanStatusList != null && StringUtils.isNotBlank(bmsStatusCode)){
            for(NlbsApplyLoanStatus nlbsApplyLoanStatus : nlbsApplyLoanStatusList){
                if(bmsStatusCode.equals(nlbsApplyLoanStatus.getBmsCode())){
                    returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, nlbsApplyLoanStatus.getCode());
                    returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, nlbsApplyLoanStatus.getName());
                    return returnMap;
                }
            }
        }
        //都不满足时返回空
        returnMap.put(Fields.PARAM_LOAN_STATUS_CODE, "");
        returnMap.put(Fields.PARAM_LOAN_STATUS_NAME, "");
        return returnMap;
    }

    /**
     * 根据进件系统的状态编码获取对应的核心系统的一系列状态编码
     * @param nlbsApplyLoanStatusList
     * @param nlbsStatusCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> getBmsStatusListByNlbsStatusCode(List<NlbsApplyLoanStatus> nlbsApplyLoanStatusList, String nlbsStatusCode) throws Exception {
        List<Map> returnList = new ArrayList<>();
        if(nlbsApplyLoanStatusList == null || nlbsApplyLoanStatusList.size() == 0){
            nlbsApplyLoanStatusList = nlbsApplyLoanStatusMapper.queryApplyStatusMap();
        }
        if(nlbsApplyLoanStatusList != null && StringUtils.isNotBlank(nlbsStatusCode)){
            for(NlbsApplyLoanStatus nlbsApplyLoanStatus : nlbsApplyLoanStatusList){
                if(nlbsStatusCode.equals(nlbsApplyLoanStatus.getCode())){
                    Map bmsStatusMap = new HashMap();
                    bmsStatusMap.put(Fields.PARAM_LOAN_STATUS_CODE, nlbsApplyLoanStatus.getBmsCode());
                    bmsStatusMap.put(Fields.PARAM_LOAN_STATUS_NAME, nlbsApplyLoanStatus.getBmsName());
                    returnList.add(bmsStatusMap);
                }
            }
        }
        return returnList;
    }

    /**
     * @Description: 根据用户编号查找可见渠道列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/7/0007
     */
    @Override
    public List<Map> queryDistributorListByUserNo(String userNo) throws Exception {
        Map returnMap = new HashMap();

        List<Map> returnDistributorList = new ArrayList<Map>();

        //--获取当前用户所在渠道，并直接添加进list
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        if (null != nlbsLoginInfo) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
            Map bmsChannel = bmsChannelsDao.queryChannelByAgentId(nlbsLoginInfo.getAgentId());
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
            if (bmsChannel != null) {
                Map standNb = new HashMap();
                standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, bmsChannel.get(Fields.PARAM_DISTRIBUTRO_CODE));
                standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, bmsChannel.get(Fields.PARAM_DISTRIBUTRO_NAME));
                returnDistributorList.add(standNb);
            }
        }

        //查询用户渠道表（<业务员渠道表><录单员渠道表>，即用户可以录入哪些渠道的单子）<存储的是BMS核心系统的渠道编码>
        List<NlbsRecordersDistributor> nlbsRecordersDistributorList = nlbsRecordersDistributorMapper.selectRecordersDistributorByUserNo(userNo);
        //先查找关联表的数据
        if (nlbsRecordersDistributorList != null && nlbsRecordersDistributorList.size() > 0) {
            for (NlbsRecordersDistributor nrd : nlbsRecordersDistributorList) {
                returnDistributorList.addAll(queryBmsDistributorsIncludeItself(nrd.getDistributorCode()));
            }
            //去除重复项
            HashSet hs = new HashSet(returnDistributorList);
            returnDistributorList.clear();
            returnDistributorList.addAll(hs);
        }

        returnMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);
        return null;
    }

    /**
     * @Description: 根据用户编号查找可见渠道所在城市列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/7/0007
     */
    @Override
    public List<Map> queryCityListByUserNo(String userNo) throws Exception {
        //获取城市列表
        List<Map> cityList = new ArrayList<Map>();
        List<NlbsUserGovernCity> nlbsUserGovernCityList = nlbsUserGovernCityMapper.queryCityByUserNo(userNo);
        if (nlbsUserGovernCityList != null && nlbsUserGovernCityList.size() > 0) {
            for (NlbsUserGovernCity nugc : nlbsUserGovernCityList) {
                //如果存在全国的业务管辖，则直接查找全部城市即可
                if (Constants.CITY_CODE_QUANGUO.equals(nugc.getCityCode())) {
                    List<NlbsCity> nlbsCityList = queryAllCity();
                    cityList.clear();
                    if (nlbsCityList != null && nlbsCityList.size() > 0) {
                        for (NlbsCity nc : nlbsCityList) {
                            if (Constants.CITY_CODE_QUANGUO.equals(nc.getCode())) {
                                continue;
                            }
                            Map cityMap = new HashMap();
                            cityMap.put(Fields.PARAM_CITY_CODE, nc.getCode());
                            cityMap.put(Fields.PARAM_CITY_NAME, nc.getAbbrName());
                            cityList.add(cityMap);
                        }
                    }
                    break;
                }
                Map cityMap = new HashMap();
                cityMap.put(Fields.PARAM_CITY_CODE, nugc.getCityCode());
                cityMap.put(Fields.PARAM_CITY_NAME, nugc.getCityName());
                cityList.add(cityMap);
            }
        }

        return cityList;
    }

    /**
     * @Description: 根据渠道编码查询管辖该渠道的业务员列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/14/0014
     */
    @Override
    public Map queryRecordsByDistributorCode(Map paramMap) throws Exception {
        String distributorCode = paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE) == null ? "" : paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE).toString();
        List<Map> userNoList = nlbsRecordersDistributorMapper.queryRecordsByDistributorCode(distributorCode);

        Map returnMap = new HashMap();
        returnMap.put(Fields.PARAM_USER_LIST, userNoList == null ? new ArrayList<>() : userNoList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取管辖渠道的业务员列表成功！");
        return returnMap;
    }

    /**
     * 根据关键词keyParam把Map转成List<Map>
     * @param paramMap
     * @param keyParam
     * @return
     */
    private List<Map> convertMapToList(Map paramMap, String keyParam){
        List<Map> returnMapList = new ArrayList<>();
        if(paramMap == null || keyParam == null){
            return returnMapList;
        }
        Object keyList = paramMap.get(keyParam);
        paramMap.remove(keyParam);
        returnMapList.add(paramMap);
        if(keyList instanceof List){
            List<Map> keyMapList = (List<Map>) keyList;
            for(Map map : keyMapList){
                returnMapList.addAll(convertMapToList(map, keyParam));
            }
        }

        return returnMapList;
    }
}
