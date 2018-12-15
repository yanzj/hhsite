package com.vilio.bps.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.bps.commonMapper.pojo.BpsInquiryResultRelation;


/**
 * @实体名称 询价请求与计算结果关联表
 * @数据库表 BPS_INQUIRY_RESULT_RELATION
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public interface IBpsInquiryResultRelationMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param bpsInquiryResultRelation  - 询价请求与计算结果关联表
     * @throws Exception                - 异常捕捉
     */
    public void getInsert(BpsInquiryResultRelation bpsInquiryResultRelation) throws Exception;
    public void getInsertPrmMap(Map<String, Object> map) throws Exception;


    /**
     * 2.删除一条数据
     * 注: 根据Bean实体的主键ID执行删除操作.
     * @param id          - 
     * @return int        - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public int getDelete(Integer id) throws Exception;


    /**
     * 3.变更一条数据
     * 注: 根据Bean实体的主键ID执行变更操作.
     * @param bpsInquiryResultRelation  - 询价请求与计算结果关联表
     * @return int                      - 执行结果
     * @throws Exception                - 异常捕捉
     */
    public int getUpdate(BpsInquiryResultRelation bpsInquiryResultRelation) throws Exception;
    public int getUpdatePrmMap(Map<String, Object> map) throws Exception;


    /**
     * 4.获取一个Bean实体
     * 注: 根据Bean实体的主键ID获取一个Bean实体.
     * @param id                         - 
     * @return BpsInquiryResultRelation  - 执行结果
     * @throws Exception                 - 异常捕捉
     */
    public BpsInquiryResultRelation getBean(Integer id) throws Exception;
    public Map<String ,Object> getBeanRtnMap(Integer id) throws Exception;


    /**
     * 5.条件查询
     * 注: 支持多条件查询、模糊查询、日期比较查询等操作.
     * @param bpsInquiryResultRelation         - 询价请求与计算结果关联表
     * @return List<BpsInquiryResultRelation>  - 执行结果
     * @throws Exception                       - 异常捕捉
     */
    public List<BpsInquiryResultRelation> getList(BpsInquiryResultRelation bpsInquiryResultRelation) throws Exception;
    public List<BpsInquiryResultRelation> getListPrmMapRtnBean(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getListPrmMapRtnMap(Map<String, Object> map) throws Exception;


    /**
     * 6.验证多条件数据是否存在
     * 注: 根据多条件验证该数据是否存在 ,并返回数据量.
     * @param bpsInquiryResultRelation  - 询价请求与计算结果关联表
     * @return int                      - 存在数量
     * @throws Exception                - 异常捕捉
     */
    public int getCheckBy(BpsInquiryResultRelation bpsInquiryResultRelation) throws Exception;
    public int getCheckByPrmMap(Map<String, Object> map) throws Exception;
    int saveInquiryResult(BpsInquiryResultRelation inquiryResultRelation) throws Exception;

}
