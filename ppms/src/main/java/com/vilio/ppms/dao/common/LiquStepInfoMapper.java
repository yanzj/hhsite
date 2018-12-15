package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.LiquStepInfo;

public interface LiquStepInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiquStepInfo record);

    int insertSelective(LiquStepInfo record);

    LiquStepInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiquStepInfo record);

    int updateByPrimaryKey(LiquStepInfo record);
}