package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteTransQuota;

public interface RouteTransQuotaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteTransQuota record);

    int insertSelective(RouteTransQuota record);

    RouteTransQuota selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteTransQuota record);

    int updateByPrimaryKey(RouteTransQuota record);
}