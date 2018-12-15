package com.vilio.plms.dao;

import com.vilio.plms.pojo.ApplyInteresting;

import java.util.HashMap;
import java.util.List;

/**
 * Created by martin on 2017/7/28.
 */
public interface BmsBaseTableDao {
    //查询核心系统城市信息
    public List<HashMap> qryBmsCity();
    //查询核心系统区域表
    public List<HashMap> qryBmsAreaList();
    //查询核心系统业务经理信息
    public List<HashMap> qryBmsAgentList();
    //查询核心系统公司表
    public List<HashMap> qryBmsCompanyList();
    //查询核心系统渠道表
    public List<HashMap> qryBmsDistributeList();
}
