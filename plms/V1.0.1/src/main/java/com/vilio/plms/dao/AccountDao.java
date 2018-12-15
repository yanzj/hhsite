package com.vilio.plms.dao;

import com.vilio.plms.pojo.Account;

import java.util.Map;

/**
 * 类名： AccountDao<br>
 * 功能：账户汇总Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface AccountDao {

    //查询账户信息汇总表
    public Account qryAccount(Account account);

    //修改剩余额度
    public int updateAccount(Account account);

    //修改剩余额度
    public int updateAccountQuota(Map param);

    //修改罚息和服务费违约金
    public int updateOverdueAndServiceFeePenaltyByCode(Account account);

    int updateAccountByCode(Account account);

    /*更新已贷本金、剩余额度、已还本金、应还利息、已还利息、应还服务费、已还服务费、应还罚息、已还罚息、
    应还服务费违约金、已还服务费违约金*/
    public int updateAccountRollBackAmountByCode(Account account);

    Account getAccountByContractCode(String contractCode);

    int updateAccountByCodeOriginalData(Account account);

    int saveAccount(Account account);

    int updateAccountAmountAdd(Account account);

}
