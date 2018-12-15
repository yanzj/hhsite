package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsRoleMenu;

public interface NlbsRoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsRoleMenu record);

    int insertSelective(NlbsRoleMenu record);

    NlbsRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsRoleMenu record);

    int updateByPrimaryKey(NlbsRoleMenu record);
}