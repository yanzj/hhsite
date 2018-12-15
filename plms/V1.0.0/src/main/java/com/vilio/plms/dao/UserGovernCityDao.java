package com.vilio.plms.dao;


import com.vilio.plms.pojo.UserGovernCity;

import java.util.List;

/**
 * Created by dell on 2017/6/30.
 */
public interface UserGovernCityDao {
    List<UserGovernCity> queryCityByUserNo(String userId) ;
}
