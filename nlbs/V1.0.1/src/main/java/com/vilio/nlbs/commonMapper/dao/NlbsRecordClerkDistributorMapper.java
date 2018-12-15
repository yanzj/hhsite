package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsRecordClerkDistributor;

import java.util.List;
import java.util.Map;

public interface NlbsRecordClerkDistributorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsRecordClerkDistributor record);

    int insertSelective(NlbsRecordClerkDistributor record);

    NlbsRecordClerkDistributor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsRecordClerkDistributor record);

    int updateByPrimaryKey(NlbsRecordClerkDistributor record);

    int deleteByDistributorCode(String distributorCode);

    List<Map> selectDistributorByRecordClerk(String userNo);

}