package com.vilio.nlbs.bms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/11/0011.
 */
public interface BmsProductDao {

    public List<Map> queryProductListByDistributorCode(String distributorCode) throws Exception;

}
