package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.SuningpayTransInfo;

public interface SuningpayTransInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SuningpayTransInfo record);

    int insertSelective(SuningpayTransInfo record);

    SuningpayTransInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SuningpayTransInfo record);

    int updateByPrimaryKey(SuningpayTransInfo record);
}