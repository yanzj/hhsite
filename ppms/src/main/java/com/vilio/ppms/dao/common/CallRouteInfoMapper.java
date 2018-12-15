package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CallRouteInfo;

public interface CallRouteInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CallRouteInfo record);

    int insertSelective(CallRouteInfo record);

    CallRouteInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CallRouteInfo record);

    int updateByPrimaryKey(CallRouteInfo record);
}