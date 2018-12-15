package com.vilio.bps.common.dao;

import com.vilio.bps.commonMapper.pojo.BpsConfig;
import com.vilio.bps.commonMapper.pojo.BpsThreshold;
import com.vilio.bps.temptest.bean.Threshold;

import java.util.List;
import java.util.Map;


/**
 * @实体名称 系统配置表
 * @数据库表 BPS_CONFIG
 * @开发日期 2017-05-08
 */
public interface IBpsSystemConfigMapper {

    int updateSystemConfig(Map<String, Object> map) throws Exception;

    public List<BpsConfig> getAllSystemConfig() throws Exception;

    public List<BpsConfig> getValidSystemConfig() throws Exception;

    public int updateCityThreshold(Map<String, Object> map) throws Exception;

}
