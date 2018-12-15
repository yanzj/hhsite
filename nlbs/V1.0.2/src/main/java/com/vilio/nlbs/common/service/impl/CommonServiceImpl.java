package com.vilio.nlbs.common.service.impl;

import com.vilio.nlbs.common.dao.CommonDao;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
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

    public List<Map> queryAllApplyRecordStatusList() throws Exception {
        return commonDao.queryAllApplyRecordStatusList();
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
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取消息列表！");

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
