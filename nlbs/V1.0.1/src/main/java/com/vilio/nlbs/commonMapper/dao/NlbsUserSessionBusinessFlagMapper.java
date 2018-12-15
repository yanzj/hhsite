package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsUserSessionBusinessFlag;

public interface NlbsUserSessionBusinessFlagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsUserSessionBusinessFlag record);

    int insertSelective(NlbsUserSessionBusinessFlag record);

    NlbsUserSessionBusinessFlag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsUserSessionBusinessFlag record);

    int updateByPrimaryKey(NlbsUserSessionBusinessFlag record);
}