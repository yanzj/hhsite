package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteInfo;

public interface RouteInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteInfo record);

    int insertSelective(RouteInfo record);

    RouteInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteInfo record);

    int updateByPrimaryKey(RouteInfo record);
}