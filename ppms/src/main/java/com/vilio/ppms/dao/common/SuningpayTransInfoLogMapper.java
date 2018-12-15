package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.SuningpayTransInfoLog;

public interface SuningpayTransInfoLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SuningpayTransInfoLog record);

    int insertSelective(SuningpayTransInfoLog record);

    SuningpayTransInfoLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SuningpayTransInfoLog record);

    int updateByPrimaryKey(SuningpayTransInfoLog record);
}