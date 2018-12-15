package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsLoginNum;

public interface NlbsLoginNumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsLoginNum record);

    int insertSelective(NlbsLoginNum record);

    NlbsLoginNum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsLoginNum record);

    int updateByPrimaryKey(NlbsLoginNum record);
}