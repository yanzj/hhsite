package com.vilio.bps.inquiry.worldunion.service;

import com.vilio.bps.commonMapper.pojo.BpsPlots;
import com.vilio.bps.inquiry.worldunion.pojo.SearchBean;
import com.vilio.bps.inquiry.worldunion.pojo.WUConstruction;

import java.util.List;

/**
 * Created by dell on 2017/5/12/0012.
 */
public interface WUGetHousingInfo {

    public List<SearchBean> getConstruction(String cityId, String name) throws Exception;

    public List<WUConstruction> getConstrutionViewInfoById(String conId) throws Exception;

    public List<SearchBean> getBuild(String conId) throws Exception;

    public List<SearchBean> getHouse(String buildId) throws Exception;

}
