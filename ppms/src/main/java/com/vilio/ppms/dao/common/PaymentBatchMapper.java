package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.PaymentBatch;

public interface PaymentBatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentBatch record);

    int insertSelective(PaymentBatch record);

    PaymentBatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentBatch record);

    int updateByPrimaryKey(PaymentBatch record);
}