package com.vilio.bps.inquiry.service;

import com.vilio.bps.commonMapper.pojo.BpsPlots;
import com.vilio.bps.inquiry.worldunion.pojo.SearchBean;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/15/0015.
 */
public interface GetHouseInfo {

    public List<Map<String, String>> getDisplayPlots(Map paramMap) throws Exception;

    public List<Map<String, String>> getDisplayUnits(Map paramMap) throws Exception;

    public List<Map<String, String>> getDisplayRooms(Map paramMap) throws Exception;
}
