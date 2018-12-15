package com.vilio.plms.dao;

import com.vilio.plms.pojo.Product;

import java.util.Map;

/**
 * 类名： ProductDao<br>
 * 功能：产品Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface ProductDao {

    //查询产品信息表
    public Product qryProduct(Product product);

    //新增
    public void insert(Map param);

}
