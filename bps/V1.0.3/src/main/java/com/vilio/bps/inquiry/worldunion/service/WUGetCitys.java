package com.vilio.bps.inquiry.worldunion.service;

import com.vilio.bps.inquiry.worldunion.pojo.WUCity;

import java.util.List;

/**
 * Created by dell on 2017/5/11/0011.
 */
public interface WUGetCitys {

    public List<WUCity> getProvinces() throws Exception;

    public List<WUCity> getCitys() throws Exception;

    public List<WUCity> getAreas() throws Exception;

    public List<WUCity> getAreasByCityCode(String cityCode) throws Exception;
}
