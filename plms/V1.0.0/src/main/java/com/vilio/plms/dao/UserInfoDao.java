package com.vilio.plms.dao;

import com.vilio.plms.pojo.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： UserInfoDao<br>
 * 功能：用户信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface UserInfoDao {


    public UserInfo queryUserInfo(UserInfo userInfo);

    //通过姓名、证件类型、证件号码查询用户信息
    public UserInfo queryUserInfoByNameAndIdNo(String name,String idType,String idNo);

    //通过姓名、证件类型、证件号码查询用户信息
    public HashMap queryUserInfoMapByNameAndIdNo(Map customerMap);

    //新增
    public void insert(Map param);

    //更新
    public void update(Map param);


}
