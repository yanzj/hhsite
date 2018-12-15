package com.vilio.plms.dao;

import com.vilio.plms.pojo.FundSide;

import java.util.Map;

/**
 * 类名： FundSideDao<br>
 * 功能：资方信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface FundSideDao {

    //查询资方信息表
    public FundSide queryFundSideByContract(FundSide fundSide);
    int saveFundSideInfo(FundSide fundSide);

    //新增
    public void insert(Map param);

}
