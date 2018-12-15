package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsUserGovernCity;

import java.util.List;

/**
 * Created by dell on 2017/6/30.
 */
public interface NlbsUserGovernCityMapper {
    List<NlbsUserGovernCity> queryCityByUserNo(String userNo) ;
}
