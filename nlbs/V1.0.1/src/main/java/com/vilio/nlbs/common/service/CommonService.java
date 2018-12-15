package com.vilio.nlbs.common.service;

import com.vilio.nlbs.commonMapper.pojo.NlbsCity;
import com.vilio.nlbs.commonMapper.pojo.NlbsDistributor;
import com.vilio.nlbs.commonMapper.pojo.NlbsUser;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/12.
 */
public interface CommonService {
    public String getUUID() throws Exception;

    public List<NlbsDistributor> selectChildrenListDistributor(String distributorCode) throws Exception;

    public List<NlbsUser> selectChildrenListUser(String userCode) throws Exception;

    List<Map> queryAllMaterialTypeList() throws Exception;

    List<Map> queryAllApplyRecordStatusList() throws Exception;

    List<NlbsCity> queryAllCity() throws Exception;

    public NlbsUser queryNlbsUserByUserNoIgnoreStatus(String userNo) throws Exception;

    public boolean isAdministrator(String userNo, List<String> roleList) throws Exception;

}
