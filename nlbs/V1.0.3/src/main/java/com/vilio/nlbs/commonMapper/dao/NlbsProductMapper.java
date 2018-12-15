package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsProduct;

import java.util.List;

/**
 * Created by dell on 2017/5/24.
 */
public interface NlbsProductMapper {

    List<NlbsProduct> selectProductByDistributorCode(String code);

    List<NlbsProduct> selectProductByProductCode(String code);
}
