package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.SystemRoute;

public interface SystemRouteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemRoute record);

    int insertSelective(SystemRoute record);

    SystemRoute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemRoute record);

    int updateByPrimaryKey(SystemRoute record);
}