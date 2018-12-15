package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RouteCardBin;

public interface RouteCardBinMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RouteCardBin record);

    int insertSelective(RouteCardBin record);

    RouteCardBin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RouteCardBin record);

    int updateByPrimaryKey(RouteCardBin record);
}