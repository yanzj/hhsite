package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsRecordersDistributor;

import java.util.List;

/**
 * Created by dell on 2017/6/29.
 */
public interface NlbsRecordersDistributorMapper {
    List<NlbsRecordersDistributor> selectRecordersDistributorByUserNo(String userNo);

}
