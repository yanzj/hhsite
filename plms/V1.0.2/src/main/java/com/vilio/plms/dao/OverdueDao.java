package com.vilio.plms.dao;

import com.vilio.plms.pojo.Overdue;
import com.vilio.plms.pojo.PlmsRepaymentDetail;
import com.vilio.plms.pojo.PlmsRepaymentScheduleDetail;

import java.util.List;

/**
 * 类名： OverdueDao<br>
 * 功能：罚息信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface OverdueDao {

    //根据划款计划明细，查询所有罚息信息
    public List<Overdue> queryOverdueByScheduleDetail(PlmsRepaymentScheduleDetail repaymentScheduleDetail);

    //修改金额、累计金额、章台
    public Integer updateOverdueByCode(Overdue overdue);

    //更新罚息表状态，批量根据还款明细更新
    public int updateOverdueStatusByRepaymentDetailCodeBatch(List<PlmsRepaymentDetail> repaymentDetails);


}
