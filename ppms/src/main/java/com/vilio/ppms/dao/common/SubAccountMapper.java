package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.SubAccount;

public interface SubAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SubAccount record);

    int insertSelective(SubAccount record);

    SubAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubAccount record);

    int updateByPrimaryKey(SubAccount record);
}