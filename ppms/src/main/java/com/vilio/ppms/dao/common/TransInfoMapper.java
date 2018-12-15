package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.TransInfo;

public interface TransInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransInfo record);

    int insertSelective(TransInfo record);

    TransInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransInfo record);

    int updateByPrimaryKeyWithBLOBs(TransInfo record);

    int updateByPrimaryKey(TransInfo record);
}