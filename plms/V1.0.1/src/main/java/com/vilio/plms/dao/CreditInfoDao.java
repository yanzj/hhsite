package com.vilio.plms.dao;

import java.util.Map;

/**
 * Created by martin on 2017/8/16.
 */
public interface CreditInfoDao {
    //新增
    public void insert(Map param);

    //新增担保信息
    public int insertLoanGuaranteeInfo(Map param);
    //新增贷记卡信息
    public int insertCreaditCard(Map param);
    //新增已贷款信息
    public int insertLoanInfo(Map param);
    //新增查询记录信息
    public int insertQueryRecord(Map param);
    //新增未销户贷记卡信息
    public int insertUnclosingCard(Map param);
    //新增未结清贷款信息
    public int insertLoanUnSettle(Map param);
}
