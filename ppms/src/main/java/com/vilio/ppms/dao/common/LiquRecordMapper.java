package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.LiquRecord;

public interface LiquRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiquRecord record);

    int insertSelective(LiquRecord record);

    LiquRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiquRecord record);

    int updateByPrimaryKey(LiquRecord record);
}