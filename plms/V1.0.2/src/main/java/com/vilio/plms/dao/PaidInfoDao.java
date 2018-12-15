package com.vilio.plms.dao;

import com.vilio.plms.pojo.PaidInfo;

import java.util.List;

/**
 * 类名： PaidInfoDao<br>
 * 功能：放款历史表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface PaidInfoDao {

    public int savePaidInfo(PaidInfo paidInfo);

    //查询放款信息
    public PaidInfo queryPaidInfoByCode(PaidInfo paidInfo);

    //更新应还罚金和应还服务费违约金
    public int updatePaidInfoOverdueAndServiceFeePenalty(PaidInfo paidInfo);

    //更新已还本金、已还利息、已还服务费、已还罚息、已还服务费违约金、状态
    public int updatePaidInfoToRepaymentedByCode(PaidInfo paidInfo);

    //根据合同编码查询放款列表
    public List queryPaidInfoByContractCode(String contractCode);
}
