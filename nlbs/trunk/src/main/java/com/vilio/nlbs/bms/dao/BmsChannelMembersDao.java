package com.vilio.nlbs.bms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/11/0011.
 */
public interface BmsChannelMembersDao {

    public Map queryAgentById(String agentId) throws Exception;

    public List<Map> queryChildrenAgents(String agentId) throws Exception;

    public List<Map> queryAgentsByDistributorCode(String distributorCode) throws Exception;

}
