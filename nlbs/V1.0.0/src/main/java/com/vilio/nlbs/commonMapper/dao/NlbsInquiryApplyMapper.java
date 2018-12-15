package com.vilio.nlbs.commonMapper.dao;

import java.util.List;
import java.util.Map;

import com.vilio.nlbs.commonMapper.pojo.NlbsCity;
import com.vilio.nlbs.commonMapper.pojo.NlbsInquiryApply;


/**
 * @实体名称 用户申请房屋询价记录表
 * @数据库表 NLBS_INQUIRY_APPLY
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public interface NlbsInquiryApplyMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param nlbsInquiryApply  - 用户申请房屋询价记录表
     * @throws Exception        - 异常捕捉
     */
    public void getInsert(NlbsInquiryApply nlbsInquiryApply) throws Exception;
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
     * @param nlbsInquiryApply  - 用户申请房屋询价记录表
     * @return int              - 执行结果
     * @throws Exception        - 异常捕捉
     */
    public int getUpdate(NlbsInquiryApply nlbsInquiryApply) throws Exception;
    public int getUpdateByBPSCode(NlbsInquiryApply nlbsInquiryApply) throws Exception;
    public int getUpdateForClaim(NlbsInquiryApply nlbsInquiryApply) throws Exception;
    public int getUpdatePrmMap(Map<String, Object> map) throws Exception;


    /**
     * 4.获取一个Bean实体
     * 注: 根据Bean实体的主键ID获取一个Bean实体.
     * @param id                 - 数据主键
     * @return NlbsInquiryApply  - 执行结果
     * @throws Exception         - 异常捕捉
     */
    public NlbsInquiryApply getBean(Integer id) throws Exception;
    public NlbsInquiryApply getBeanBySerialNo(String serialNo) throws Exception;
    public Map<String ,Object> getBeanRtnMap(Integer id) throws Exception;


    /**
     * 5.条件查询
     * 注: 支持多条件查询、模糊查询、日期比较查询等操作.
     * @param nlbsInquiryApply         - 用户申请房屋询价记录表
     * @return List<NlbsInquiryApply>  - 执行结果
     * @throws Exception               - 异常捕捉
     */
    public List<NlbsInquiryApply> getList(NlbsInquiryApply nlbsInquiryApply) throws Exception;
    public List<Map<String, Object>> getAllList(Map<String, Object> map) throws Exception;
    public List<NlbsInquiryApply> getListPrmMapRtnBean(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getListPrmMapRtnMap(Map<String, Object> map) throws Exception;
    public List<Map<String ,Object>> getInquiryApplyList(Map<String, Object> map) throws Exception;


    /**
     * 6.验证多条件数据是否存在
     * 注: 根据多条件验证该数据是否存在 ,并返回数据量.
     * @param nlbsInquiryApply  - 用户申请房屋询价记录表
     * @return int              - 存在数量
     * @throws Exception        - 异常捕捉
     */
    public int getCheckBy(NlbsInquiryApply nlbsInquiryApply) throws Exception;
    public int getCheckByPrmMap(Map<String, Object> map) throws Exception;


    public List<Map> getCitys() throws Exception;


}