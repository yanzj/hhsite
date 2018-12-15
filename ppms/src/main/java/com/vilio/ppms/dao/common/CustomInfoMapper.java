package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CustomInfo;

public interface CustomInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomInfo record);

    int insertSelective(CustomInfo record);

    CustomInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomInfo record);

    int updateByPrimaryKey(CustomInfo record);
}