package com.vilio.nlbs.user.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsLoginNum;
import com.vilio.nlbs.commonMapper.pojo.NlbsUser;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/30.
 */
public interface UserDao {

    public NlbsLoginNum queryNlbsLoginNumByUserNo(String userNo);

    public NlbsLoginNum queryNlbsLoginNumByUserName(String userName);

    public NlbsUser queryNlbsUserByUserNo(String userNo);

    public NlbsUser queryNlbsUserByUserNoIgnoreStatus(String userNo);

    public NlbsUser queryNlbsUserByUserName(String userName);

    public NlbsUser queryNlbsUserByUserNameOrUserNoAndPsw(NlbsUser nlbsUser);

    public void deleteNlbsLoginNumByUserNo(String userNo);

    public void deleteNlbsLoginAll();

    public int updatePswByUserName(Map paramMap);

    public List getMenu(Map param);

    public int getSpecialRoleNum(String userNo);

    public List getRoleList(String userNo);

    public Map selectLoginInfoByUserNo(String userNo);

    public void deleteLoginInfoByUserNo(String userNo);

    public void updateLoginInfoByUserNo(Map param);

    public int updateUserStatusByUserNo(Map param);

    public NlbsUser findUserByUserNoAndPsw(NlbsUser nlbsUser);
}
