package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsUser;

import java.util.List;
import java.util.Map;

public interface NlbsUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsUser record);

    int insertSelective(NlbsUser record);

    NlbsUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsUser record);

    int updateByPrimaryKey(NlbsUser record);

    int addNewUser(NlbsUser user);

    int updateUserInfoByUserNo(NlbsUser user);

    List<Map> selectAll();

    List<NlbsUser> selectChildListUser(String userNo);

}