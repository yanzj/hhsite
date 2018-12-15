package com.vilio.nlbs.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.nlbs.commonMapper.pojo.NlbsPendingUserDistributor;


/**
 * @实体名称 待处理人和渠道关联表
 * @数据库表 NLBS_PENDING_USER_DISTRIBUTOR
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public interface NlbsPendingUserDistributorMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param nlbsPendingUserDistributor  - 待处理人和渠道关联表
     * @throws Exception                  - 异常捕捉
     */
    public void getInsert(NlbsPendingUserDistributor nlbsPendingUserDistributor) throws Exception;
    public void getInsertPrmMap(Map<String, Object> map) throws Exception;


    /**
     * 2.删除一条数据
     * 注: 根据Bean实体的主键ID执行删除操作.
     * @param id          - 数据主键
     * @return int        - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public int getDelete(Integer id) throws Exception;


    /**
     * 3.变更一条数据
     * 注: 根据Bean实体的主键ID执行变更操作.
     * @param nlbsPendingUserDistributor  - 待处理人和渠道关联表
     * @return int                        - 执行结果
     * @throws Exception                  - 异常捕捉
     */
    public int getUpdate(NlbsPendingUserDistributor nlbsPendingUserDistributor) throws Exception;
    public int getUpdatePrmMap(Map<String, Object> map) throws Exception;


    /**
     * 4.获取一个Bean实体
     * 注: 根据Bean实体的主键ID获取一个Bean实体.
     * @param id                           - 数据主键
     * @return NlbsPendingUserDistributor  - 执行结果
     * @throws Exception                   - 异常捕捉
     */
    public NlbsPendingUserDistributor getBean(Integer id) throws Exception;
    public Map<String ,Object> getBeanRtnMap(Integer id) throws Exception;


    /**
     * 5.条件查询
     * 注: 支持多条件查询、模糊查询、日期比较查询等操作.
     * @param nlbsPendingUserDistributor         - 待处理人和渠道关联表
     * @return List<NlbsPendingUserDistributor>  - 执行结果
     * @throws Exception                         - 异常捕捉
     */
    public List<NlbsPendingUserDistributor> getList(NlbsPendingUserDistributor nlbsPendingUserDistributor) throws Exception;
    public List<NlbsPendingUserDistributor> getListPrmMapRtnBean(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getListPrmMapRtnMap(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getUserListByDistributorCode(String distributorCode) throws Exception;


    /**
     * 6.验证多条件数据是否存在
     * 注: 根据多条件验证该数据是否存在 ,并返回数据量.
     * @param nlbsPendingUserDistributor  - 待处理人和渠道关联表
     * @return int                        - 存在数量
     * @throws Exception                  - 异常捕捉
     */
    public int getCheckBy(NlbsPendingUserDistributor nlbsPendingUserDistributor) throws Exception;
    public int getCheckByPrmMap(Map<String, Object> map) throws Exception;



}
