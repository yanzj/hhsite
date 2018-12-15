package com.vilio.plms.dao;

import com.vilio.plms.pojo.AccountDetail;

import java.util.List;
import java.util.Map;

/**
 * 类名： AccountDetailDao<br>
 * 功能：账户明细表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface AccountDetailDao {

    //查询账户信息明细表
    public AccountDetail qryAccountDetail(AccountDetail accountDetail);

    //修改剩余额度
    public int updateAccountDetail(AccountDetail accountDetail);

    //查找需要更新的本金归还日
    public List<AccountDetail> qryPrincipalDateForUpdate();

    //修改剩余金额
    public int updateAccountDetailQuota(Map param);

    AccountDetail getAccountDetailByCode(String contractCode);

    Map findAccountDetailByCode(String contractCode);

    //根据code查询账户明细表
    public AccountDetail queryAccountDetailByCode(AccountDetail accountDetail);

    //更新账户明细表应还罚息、应还服务费违约金
    public int updateAccountDetailAmountByCode(AccountDetail accountDetail);

    public int updateAccountDetailByCode(AccountDetail accountDetail);
    //更新账户明细表应还罚息、应还服务费违约金
    public int updateConfirmedByContractCode(AccountDetail accountDetail);

    /*更新已贷本金、剩余额度、已还本金、应还利息、已还利息、应还服务费、已还服务费、应还罚息、已还罚息、
    应还服务费违约金、已还服务费违约金、宏获账户余额、资方账户余额*/
    public int updateAccountDetailRollBackAmountByCode(AccountDetail accountDetail);

    //更新宏获余额和资方余额
    public int updateAccountDetailBalanceByCode(AccountDetail accountDetail);

    int updatelAccountDetailByContractCode(Map map);

    int updateAccountDetailByCodeOriginalData(AccountDetail accountDetail);

    int saveAccountDetail(AccountDetail accountDetail);

    //累加账户明细的相关金额
    int updateAccountDetailAmountAdd(AccountDetail accountDetail);
}
