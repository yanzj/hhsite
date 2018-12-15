package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.LiquTransStatis;

public interface LiquTransStatisMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiquTransStatis record);

    int insertSelective(LiquTransStatis record);

    LiquTransStatis selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiquTransStatis record);

    int updateByPrimaryKey(LiquTransStatis record);
}