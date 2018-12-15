package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CardBinInfo;

public interface CardBinInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CardBinInfo record);

    int insertSelective(CardBinInfo record);

    CardBinInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CardBinInfo record);

    int updateByPrimaryKey(CardBinInfo record);
}