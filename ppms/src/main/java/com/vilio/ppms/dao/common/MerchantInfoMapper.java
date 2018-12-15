package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.MerchantInfo;

public interface MerchantInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantInfo record);

    int insertSelective(MerchantInfo record);

    MerchantInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MerchantInfo record);

    int updateByPrimaryKey(MerchantInfo record);
}