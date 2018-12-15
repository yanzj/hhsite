package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteBankDayTotal;

public interface RouteBankDayTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteBankDayTotal record);

    int insertSelective(RouteBankDayTotal record);

    RouteBankDayTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteBankDayTotal record);

    int updateByPrimaryKey(RouteBankDayTotal record);
}