package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsOverdue;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/28.
 */
public interface PlmsOverdueDao {

    List<PlmsOverdue> queryOverdueByDetailCodeAndStatus(Map map) throws Exception;

    int updateOverdueStatusByCode(PlmsOverdue plmsOverdue) throws Exception;
}
