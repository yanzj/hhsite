package com.vilio.plms.dao;

import com.vilio.plms.pojo.BorrowApply;

import java.util.List;

/**
 * 类名： BorrowApplyDao<br>
 * 功能：借款申请Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface BorrowApplyDao {

    public List<BorrowApply> queryBorrowApply(BorrowApply borrowApply);


    public int insertBorrowApply(BorrowApply borrowApply);

    //修改订单状态
    public int updateBorrowApplyForStatus(BorrowApply borrowApply);

    //根据code查询借款申请
    public BorrowApply queryBorrowApplyByCode(BorrowApply borrowApply);



}
