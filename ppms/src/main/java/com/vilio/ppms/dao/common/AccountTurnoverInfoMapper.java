package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.AccountTurnoverInfo;

public interface AccountTurnoverInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountTurnoverInfo record);

    int insertSelective(AccountTurnoverInfo record);

    AccountTurnoverInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountTurnoverInfo record);

    int updateByPrimaryKey(AccountTurnoverInfo record);
}