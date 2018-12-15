package com.vilio.nlbs.dynamicdatasource.dao;

import com.vilio.nlbs.dynamicdatasource.pojo.TestCity;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/22/0022.
 */
public interface BPSTestDao {

    public Map getSystemConfig();

    public List<TestCity> getCitys();
}
