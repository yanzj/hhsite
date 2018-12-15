package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RefundInfo;

public interface RefundInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RefundInfo record);

    int insertSelective(RefundInfo record);

    RefundInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RefundInfo record);

    int updateByPrimaryKey(RefundInfo record);
}