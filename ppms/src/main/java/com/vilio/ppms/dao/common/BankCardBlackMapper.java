package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.BankCardBlack;

public interface BankCardBlackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BankCardBlack record);

    int insertSelective(BankCardBlack record);

    BankCardBlack selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCardBlack record);

    int updateByPrimaryKey(BankCardBlack record);
}