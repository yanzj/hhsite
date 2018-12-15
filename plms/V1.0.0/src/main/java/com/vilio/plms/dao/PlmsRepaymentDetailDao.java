package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsRepaymentDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/31.
 */
public interface PlmsRepaymentDetailDao {

    int saveRepaymentDetail(PlmsRepaymentDetail plmsRepaymentDetail) throws Exception;

    //根据还款计划明细表，查询到账日到现在的所有还款明细信息
    public List<PlmsRepaymentDetail> queryRepaymentDetailByScheduleDetailCodeAndDate(Map param);

    //批量将还款明细标记为无效
    public int updateRepaymentDetailByCodeBatch(List<PlmsRepaymentDetail> repaymentDetails);
}
