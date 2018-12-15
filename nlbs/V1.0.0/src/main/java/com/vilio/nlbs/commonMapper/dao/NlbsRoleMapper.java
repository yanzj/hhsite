package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsRole;

public interface NlbsRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsRole record);

    int insertSelective(NlbsRole record);

    NlbsRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsRole record);

    int updateByPrimaryKey(NlbsRole record);
}