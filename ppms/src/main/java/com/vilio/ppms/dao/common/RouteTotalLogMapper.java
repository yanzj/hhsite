package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteTotalLog;

public interface RouteTotalLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteTotalLog record);

    int insertSelective(RouteTotalLog record);

    RouteTotalLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteTotalLog record);

    int updateByPrimaryKey(RouteTotalLog record);
}