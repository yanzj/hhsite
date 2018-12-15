package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsAgent;

import java.util.List;

/**
 * Created by dell on 2017/5/24.
 */
public interface NlbsAgentMapper {
    List<NlbsAgent> selectAgentByDistributorCode(String code);
}
