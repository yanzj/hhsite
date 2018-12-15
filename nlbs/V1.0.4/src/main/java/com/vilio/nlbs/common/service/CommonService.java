package com.vilio.nlbs.common.service;

import com.vilio.nlbs.commonMapper.pojo.NlbsApplyLoanStatus;
import com.vilio.nlbs.commonMapper.pojo.NlbsCity;
import com.vilio.nlbs.commonMapper.pojo.NlbsUserGovernCity;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/12.
 */
public interface CommonService {
    public String getUUID() throws Exception;

    List<Map> queryUserRoleList(Map paramMap) throws Exception;

    List<Map> queryAllMaterialTypeList() throws Exception;

    List<NlbsCity> queryAllCity() throws Exception;

    List<NlbsCity> queryAllInquiryCity() throws Exception;

    List<Map> queryDistributors(Map paramMap) throws Exception;

    List<Map> queryBmsDistributors(String distributorCode) throws Exception;

    List<Map> queryBmsDistributorsIncludeItself(String distributorCode) throws Exception;

    List<Map> queryBmsAgents(String agentId) throws Exception;

    List<Map> queryBmsAgentsIncludeItself(String agentId) throws Exception;

    List<Map> queryUsers(Map paramMap) throws Exception;

    Map queryUserByUserNo(String userNo) throws Exception;

    public boolean isAdministrator(String userNo, List<String> roleList) throws Exception;

    public boolean isRecordClerk(String userNo) throws Exception;

    public List<Map> selectUsersByDistributorCode(String distributorCode) throws Exception;

    Map queryTodoMsgCountAndDetails(Map paramMap) throws Exception;

    Map getNlbsStatusMapByBmsStatusCode(List<NlbsApplyLoanStatus> nlbsApplyLoanStatusList, String bmsStatusCode, List<Map> subOperationList, boolean currentStatus) throws Exception;

    List<Map> getBmsStatusListByNlbsStatusCode(List<NlbsApplyLoanStatus> nlbsApplyLoanStatusList, String nlbsStatusCode) throws Exception;

    List<Map> queryDistributorListByUserNo(String userNo) throws Exception;

    List<Map> queryCityListByUserNo(String userNo) throws Exception;

    Map queryRecordsByDistributorCode(Map paramMap) throws Exception;

}
