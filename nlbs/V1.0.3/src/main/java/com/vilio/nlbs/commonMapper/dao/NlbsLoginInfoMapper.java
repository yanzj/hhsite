package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsLoginInfo;

import java.util.Map;

public interface NlbsLoginInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsLoginInfo record);

    int insertSelective(NlbsLoginInfo record);

    NlbsLoginInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsLoginInfo record);

    int updateByPrimaryKey(NlbsLoginInfo record);

    public NlbsLoginInfo queryNlbsUserByUserNo(String userNo);

    /*public NlbsUser queryNlbsUserByUserNoIgnoreStatus(String userNo);

    public NlbsUser queryNlbsUserByUserName(String userName);*/

    public Map selectLoginInfoByUserNo(String userNo);

    public void deleteLoginInfoByUserNo(String userNo);

    public void updateLoginInfoByUserNo(Map param);
}