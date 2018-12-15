package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsFundSideProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/8/31.
 */
public interface PlmsFundSideProductDao {
    List<Map> queryFundSideProductByParms(Map map);
}
