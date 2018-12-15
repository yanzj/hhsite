package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsLoginInfo;

public interface NlbsLoginInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsLoginInfo record);

    int insertSelective(NlbsLoginInfo record);

    NlbsLoginInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsLoginInfo record);

    int updateByPrimaryKey(NlbsLoginInfo record);
}