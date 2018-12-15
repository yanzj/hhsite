package com.vilio.plms.dao;

import com.vilio.plms.pojo.LoginInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/7/19.
 */
public interface LoginInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginInfo record);

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByPrimaryKey(LoginInfo record);

    public LoginInfo queryNlbsUserByUserNo(String userNo);

    public Map selectLoginInfoByUserNo(String userNo);

    public void deleteLoginInfoByUserNo(String userNo);

    public void updateLoginInfoByUserNo(Map param);

    public Map queryUserInfoByUmId(String umId);

    public List queryUserInfoByParentUserCode(String parentCode);

    public List queryAgentInfoByParentAgentId(String parentAgentId);

    public Map queryAgentInfoByAgentId(String agentId);

    public Map queryUserInfoByCode(String code);

    public List<Map> queryUmIdsWithUserList(Map paramMap);

}
