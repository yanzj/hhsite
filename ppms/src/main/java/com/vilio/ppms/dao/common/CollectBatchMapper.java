package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CollectBatch;

public interface CollectBatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CollectBatch record);

    int insertSelective(CollectBatch record);

    CollectBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CollectBatch record);

    int updateByPrimaryKey(CollectBatch record);
}