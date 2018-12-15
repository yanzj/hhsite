package com.vilio.plms.dao;

import com.vilio.plms.pojo.OverdueDetail;

import java.util.List;
import java.util.Map;

/**
 * 类名： OverdueDetailDao<br>
 * 功能：罚息流水Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface OverdueDetailDao {

    //查询罚息流水表记录：时间所属日期大于资金到账日期小于等于当前日期。
    public List<OverdueDetail> queryOverdueDetailByOverdueCodeAndTime(Map param);

    //罚息流水批量标记为无效，根据code
    public int updateOverdueDetailStatusByCodeBatch(List<OverdueDetail> overdueDetails);

    //根据日期分组，查询罚息明细表（统计出每条还款明细回滚的逾期天数，因为本金、利息和服务费罚息明细日期会重叠）
    public Integer queryOverdueGroupDateByScheduleDetailCode(Map param);

    //根据日期和逾期天数进行分组，统计出逾期天数（本息逾期天数计算，统计出每条还款明细回滚的逾期天数，因为本金、利息和服务费罚息明细日期会重叠）
    public Integer queryPrcpalAndIntstOverdueGroupByScheduleDetailCode(Map param);

    //根据日期和逾期天数进行分组，统计出逾期天数（服务费逾期天数计算，统计出每条还款明细回滚的逾期天数，因为本金、利息和服务费罚息明细日期会重叠）
    public Integer queryServiceFeeOverdueGroupByScheduleDetailCode(Map param);
}
