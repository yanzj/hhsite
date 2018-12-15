package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsRoleUser;

import java.util.List;

public interface NlbsRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsRoleUser record);

    int insertSelective(NlbsRoleUser record);

    NlbsRoleUser selectByPrimaryKey(Integer id);

    List<NlbsRoleUser> selectUserRole(NlbsRoleUser nlbsRoleUser);

    int updateByPrimaryKeySelective(NlbsRoleUser record);

    int updateByPrimaryKey(NlbsRoleUser record);

    int deleteRoleUserByUserNo(String userNo);
}