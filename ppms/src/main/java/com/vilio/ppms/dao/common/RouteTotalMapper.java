package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteTotal;

public interface RouteTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteTotal record);

    int insertSelective(RouteTotal record);

    RouteTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteTotal record);

    int updateByPrimaryKey(RouteTotal record);
}