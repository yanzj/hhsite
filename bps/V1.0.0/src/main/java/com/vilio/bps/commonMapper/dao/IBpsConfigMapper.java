package com.vilio.bps.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.bps.commonMapper.pojo.BpsConfig;


/**
 * @实体名称 系统配置表
 * @数据库表 BPS_CONFIG
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public interface IBpsConfigMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param bpsConfig   - 系统配置表
     * @throws Exception  - 异常捕捉
     */
    public void getInsert(BpsConfig bpsConfig) throws Exception;
    public void getInsertPrmMap(Map<String, Object> map) throws Exception;


    /**
     * 2.删除一条数据
     * 注: 根据Bean实体的主键ID执行删除操作.
     * @param id          - 数据主键
     * @return int        - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public int getDelete(String id) throws Exception;


    /**
     * 3.变更一条数据
     * 注: 根据Bean实体的主键ID执行变更操作.
     * @param bpsConfig   - 系统配置表
     * @return int        - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public int getUpdate(BpsConfig bpsConfig) throws Exception;
    public int getUpdatePrmMap(Map<String, Object> map) throws Exception;


    /**
     * 4.获取一个Bean实体
     * 注: 根据Bean实体的主键ID获取一个Bean实体.
     * @param id          - 数据主键
     * @return BpsConfig  - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public BpsConfig getBean(String id) throws Exception;
    public Map<String ,Object> getBeanRtnMap(String id) throws Exception;


    /**
     * 5.条件查询
     * 注: 支持多条件查询、模糊查询、日期比较查询等操作.
     * @param bpsConfig         - 系统配置表
     * @return List<BpsConfig>  - 执行结果
     * @throws Exception        - 异常捕捉
     */
    public List<BpsConfig> getList(BpsConfig bpsConfig) throws Exception;
    public List<BpsConfig> getListPrmMapRtnBean(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getListPrmMapRtnMap(Map<String, Object> map) throws Exception;


    /**
     * 6.验证多条件数据是否存在
     * 注: 根据多条件验证该数据是否存在 ,并返回数据量.
     * @param bpsConfig   - 系统配置表
     * @return int        - 存在数量
     * @throws Exception  - 异常捕捉
     */
    public int getCheckBy(BpsConfig bpsConfig) throws Exception;
    public int getCheckByPrmMap(Map<String, Object> map) throws Exception;



}
