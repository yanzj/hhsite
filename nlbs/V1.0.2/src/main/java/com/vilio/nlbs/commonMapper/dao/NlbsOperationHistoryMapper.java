package com.vilio.nlbs.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.nlbs.commonMapper.pojo.NlbsOperationHistory;


/**
 * @实体名称 操作历史表
 * @数据库表 NLBS_OPERATION_HISTORY
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public interface NlbsOperationHistoryMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param nlbsOperationHistory  - 操作历史表
     * @throws Exception            - 异常捕捉
     */
    public void getInsert(NlbsOperationHistory nlbsOperationHistory) throws Exception;



    public List<Map<String ,Object>> getListBySerialNo(String serialNo) throws Exception;
}
