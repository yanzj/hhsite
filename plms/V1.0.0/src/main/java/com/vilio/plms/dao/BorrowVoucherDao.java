package com.vilio.plms.dao;

import com.vilio.plms.pojo.BorrowVoucher;

import java.util.List;

/**
 * 类名： BorrowVoucherDao<br>
 * 功能：放款凭证Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface BorrowVoucherDao {
    //批量保存放款凭证信息
    public int saveBorrowVoucher(List<BorrowVoucher> borrowVouchers);

}
