package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RateStair;

public interface RateStairMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RateStair record);

    int insertSelective(RateStair record);

    RateStair selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RateStair record);

    int updateByPrimaryKey(RateStair record);
}