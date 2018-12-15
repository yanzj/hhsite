package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsDistributor;

import java.util.List;

public interface NlbsDistributorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsDistributor record);

    int insertSelective(NlbsDistributor record);

    NlbsDistributor selectByPrimaryKey(Integer id);

    NlbsDistributor selectByCode(String code);

    int updateByPrimaryKeySelective(NlbsDistributor record);

    int updateByCodeSelective(NlbsDistributor record);

    int updateByPrimaryKey(NlbsDistributor record);

    List selectAll();

    NlbsDistributor selectDistributorByUserNo(String userNo);

    List<NlbsDistributor> selectChildListDistributor(String distributorCode);

    List<NlbsDistributor> selectAgentDistributorListByAgentId(String agentId);

}