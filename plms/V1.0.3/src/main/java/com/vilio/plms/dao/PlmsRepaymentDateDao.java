package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsRepaymentDate;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/24.
 */
public interface PlmsRepaymentDateDao {

    List<PlmsRepaymentDate> getProductInfoAndInterest(Map map);

    PlmsRepaymentDate getProductInfoByContractCodeAndDate(Map map);

    //查询固定还款日列表，大于今天，小于等于本金归还日的所有固定日还款表数据
    public List<PlmsRepaymentDate> queryPlmsRepaymentDateForNowAndPrincipalDate(Map param);

    List<PlmsRepaymentDate> selectRepaymentDateByParams(PlmsRepaymentDate plmsRepaymentDate);

    int saveRepaymentDate(PlmsRepaymentDate plmsRepaymentDate);
}
