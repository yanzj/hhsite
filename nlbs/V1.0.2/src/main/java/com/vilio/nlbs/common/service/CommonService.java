package com.vilio.nlbs.common.service;

import com.vilio.nlbs.commonMapper.pojo.NlbsCity;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/12.
 */
public interface CommonService {
    public String getUUID() throws Exception;

    List<Map> queryUserRoleList(Map paramMap) throws Exception;

    List<Map> queryAllMaterialTypeList() throws Exception;

    List<Map> queryAllApplyRecordStatusList() throws Exception;

    List<NlbsCity> queryAllCity() throws Exception;

    List<NlbsCity> queryAllInquiryCity() throws Exception;

    List<Map> queryDistributors(Map paramMap) throws Exception;

    List<Map> queryUsers(Map paramMap) throws Exception;

    Map queryUserByUserNo(String userNo) throws Exception;

    public boolean isAdministrator(String userNo, List<String> roleList) throws Exception;

    public boolean isRecordClerk(String userNo) throws Exception;

    public List<Map> selectUsersByDistributorCode(String distributorCode) throws Exception;

    Map queryTodoMsgCountAndDetails(Map paramMap) throws Exception;

}
