package com.vilio.plms.dao;

import com.vilio.plms.pojo.UserDistributor;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/29.
 */
public interface UserDistributorDao {
    List<UserDistributor> selectUserDistributorByUserId(String userNo);

    List<Map> selectUserDistributorByDistributorCode(String userNo);

}
