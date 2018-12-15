package com.vilio.plms.dao;

import com.vilio.plms.pojo.Customer;

import java.util.List;
import java.util.Map;

/**
 * 类名： CustomerDao<br>
 * 功能：借款人Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CustomerDao {

    //查询借款人信息
    public Customer qryCustomer(Customer customer);

    //新增
    public void insert(Map param);

    List<Map> getCustomerInfoByApplyCode(Map param);

}
