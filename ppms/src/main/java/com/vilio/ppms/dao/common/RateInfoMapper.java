package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RateInfo;

public interface RateInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RateInfo record);

    int insertSelective(RateInfo record);

    RateInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RateInfo record);

    int updateByPrimaryKey(RateInfo record);
}