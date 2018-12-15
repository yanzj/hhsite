package com.vilio.bps.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.bps.commonMapper.pojo.BpsCompanyCity;


/**
 * @实体名称 估价公司与城市关联表
 * @数据库表 BPS_COMPANY_CITY
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public interface IBpsCompanyCityMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param bpsCompanyCity  - 估价公司与城市关联表
     * @throws Exception      - 异常捕捉
     */
    public void getInsert(BpsCompanyCity bpsCompanyCity) throws Exception;
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
     * @param bpsCompanyCity  - 估价公司与城市关联表
     * @return int            - 执行结果
     * @throws Exception      - 异常捕捉
     */
    public int getUpdate(BpsCompanyCity bpsCompanyCity) throws Exception;
    public int getUpdatePrmMap(Map<String, Object> map) throws Exception;


    /**
     * 4.获取一个Bean实体
     * 注: 根据Bean实体的主键ID获取一个Bean实体.
     * @param id               - 数据主键
     * @return BpsCompanyCity  - 执行结果
     * @throws Exception       - 异常捕捉
     */
    public BpsCompanyCity getBean(String id) throws Exception;
    public Map<String ,Object> getBeanRtnMap(String id) throws Exception;


    /**
     * 5.条件查询
     * 注: 支持多条件查询、模糊查询、日期比较查询等操作.
     * @param bpsCompanyCity         - 估价公司与城市关联表
     * @return List<BpsCompanyCity>  - 执行结果
     * @throws Exception             - 异常捕捉
     */
    public List<BpsCompanyCity> getList(BpsCompanyCity bpsCompanyCity) throws Exception;
    public List<BpsCompanyCity> getListPrmMapRtnBean(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getListPrmMapRtnMap(Map<String, Object> map) throws Exception;


    /**
     * 6.验证多条件数据是否存在
     * 注: 根据多条件验证该数据是否存在 ,并返回数据量.
     * @param bpsCompanyCity  - 估价公司与城市关联表
     * @return int            - 存在数量
     * @throws Exception      - 异常捕捉
     */
    public int getCheckBy(BpsCompanyCity bpsCompanyCity) throws Exception;
    public int getCheckByPrmMap(Map<String, Object> map) throws Exception;

    //根据城市id获取关联估价公司
    public List<BpsCompanyCity> queryCompanyCityByCityCode(String cityCode);

}
