package com.vilio.plms.dao;

import com.vilio.plms.pojo.City;

import java.util.List;

/**
 * Created by dell on 2017/5/25.
 */
public interface CityDao {

    List<City> queryAllCity() throws Exception;

    City queryCityByCode(String cityCode) throws Exception;

    List<City> queryCityByBmsCode(String code) throws Exception;

    List<City> insert(City city) throws Exception;

}
