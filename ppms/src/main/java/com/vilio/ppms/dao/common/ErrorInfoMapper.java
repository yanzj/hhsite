package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.ErrorInfo;

public interface ErrorInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ErrorInfo record);

    int insertSelective(ErrorInfo record);

    ErrorInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErrorInfo record);

    int updateByPrimaryKey(ErrorInfo record);
}