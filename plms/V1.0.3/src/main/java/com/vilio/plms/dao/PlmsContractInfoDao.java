package com.vilio.plms.dao;

import java.util.Map;

/**
 * Created by dell on 2017/7/19.
 */
public interface PlmsContractInfoDao {
    //查询贷款业务信息
    public Map queryContractInfoByCode(String contractCode);
    //根据合同获取产品信息
    public Map getProductInfoAndInterest(String contractCode);

}
