package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * 类名： DistributorDao<br>
 * 功能：渠道信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface DistributorDao {

    //查询渠道信息
    public Map queryDistributorByApplyNo(Map param);

    public Map queryDistributorByCode(String distributorCode) throws Exception;

    public List<Map> queryAllDistributors() throws Exception;

    public List<Map> queryChildrenDistributors(String distributorCode) throws Exception;

    public List<Map> queryDistributorBatchByCode(List<String> code);

    public List<Map> queryDistributorListByBmsCode(Map param);
}
