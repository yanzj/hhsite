package com.vilio.plms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/7/28.
 */
public interface BaseTableDao {

    //更新城市信息
    public void updateCity(Map city);
    //更新区域表
    public void updateArea(Map area);
    //更新业务经理信息
    public void updateAgent(Map agent);
    //更新公司表
    public void updateCompany(Map company);
    //更新渠道表
    public void updateDistribute(Map distribute);

    //查询城市信息
    public HashMap qryCity(String bmsCityCode);
    //查询区域信息
    public HashMap qryArea(String bmsAreaCode);
    //查询业务经理信息
    public HashMap qryAgent(String bmsAgentCode);
    //查询公司信息
    public HashMap qryCompany(String bmsCompanyCode);
    //查询渠道信息
    public HashMap qryDistribute(String bmsDistributeCode);

    //插入城市信息
    public void insertCity(Map city);
    //插入区域表
    public void insertArea(Map area);
    //插入业务经理信息
    public void insertAgent(Map agent);
    //插入公司表
    public void insertCompany(Map company);
    //插入渠道表
    public void insertDistribute(Map distribute);

}
