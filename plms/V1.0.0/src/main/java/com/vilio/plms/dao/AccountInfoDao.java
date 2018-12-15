package com.vilio.plms.dao;

import com.vilio.plms.pojo.AccountInfo;

import java.util.Map;

/**
 * 类名： AccountInfoDao<br>
 * 功能：银行账户表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface AccountInfoDao {

    //查询银行卡信息
    public AccountInfo qryAccountInfo(AccountInfo accountInfo);

    //新增
    public void insert(Map param);

    int saveAccountInfo(AccountInfo accountInfo);

}
