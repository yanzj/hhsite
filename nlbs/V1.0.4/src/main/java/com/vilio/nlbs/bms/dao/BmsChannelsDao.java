package com.vilio.nlbs.bms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/11/0011.
 */
public interface BmsChannelsDao {

    public Map queryChannelById(String distributorCode) throws Exception;

    public Map queryChannelByAgentId(String agentId) throws Exception;

    public List<Map> queryAllChannels() throws Exception;

    public List<Map> queryChildrenChannels(String distributorCode) throws Exception;

}
