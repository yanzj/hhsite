package com.vilio.plms.dao;

import com.vilio.plms.pojo.PaidVoucher;

import java.util.List;

/**
 * 还款凭证
 */
public interface PlmsPaidVoucherDao {
    public int saveVoucherInfo(List<PaidVoucher> paidVouchers) throws Exception;
}
