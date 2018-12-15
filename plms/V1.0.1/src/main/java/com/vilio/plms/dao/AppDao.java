package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/7/6.
 */
public interface AppDao {
    public Map queryAccountInfo(Map map);
    public List<Map> queryAccountDetail(String accountCode);
    public Map queryFirstRepaymentMap(Map map);
    public Map queryOverdueCountMap(Map map);
    public Map queryBorrowClosedPeriodMap(String contractCode);
    public List<String> queryCertificateAddress(String contractCode);

}
