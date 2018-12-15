package com.vilio.plms.dao;

import com.vilio.plms.pojo.Contract;

import java.util.Map;

/**
 * 类名： ContractDao<br>
 * 功能：合同Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface ContractDao {

    //查询合同表
    public Contract qryContract(Contract contract);

    //查询每笔合同明细
    public Map qryContractDetail(Map param);

    int saveContractInfo(Contract contract);

    Map qryContractByContractNo(String contractNo);


}
