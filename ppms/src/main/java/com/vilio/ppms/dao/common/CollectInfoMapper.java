package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CollectInfo;

public interface CollectInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CollectInfo record);

    int insertSelective(CollectInfo record);

    CollectInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CollectInfo record);

    int updateByPrimaryKey(CollectInfo record);
}