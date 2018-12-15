package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsCity;

import java.util.List;

/**
 * Created by dell on 2017/5/25.
 */
public interface NlbsCityMapper {

    List<NlbsCity> queryAllCity() throws Exception;

    NlbsCity queryCityByCode(String cityCode) throws Exception;

    List<NlbsCity> queryAllInquiryCity() throws Exception;
}
