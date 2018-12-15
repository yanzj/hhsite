package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteWeight;

public interface RouteWeightMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteWeight record);

    int insertSelective(RouteWeight record);

    RouteWeight selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteWeight record);

    int updateByPrimaryKey(RouteWeight record);
}