package com.vilio.nlbs.commonMapper.dao;

import java.util.List;
import java.util.Map;
import com.vilio.nlbs.commonMapper.pojo.NlbsTodo;


/**
 * @实体名称 待办任务列表
 * @数据库表 NLBS_TODO
 * @开发日期 2017-06-20
 * @技术服务 www.fwjava.com
 */
public interface NlbsTodoMapper {

    /**
     * 1.新增一条数据
     * 注: 根据Bean实体执行新增操作.
     * @param
     * @throws Exception  - 异常捕捉
     */
    public int getInsertPrmMap(Map<String, Object> map) throws Exception;


    /**
     * 2.删除一条数据
     * 注: 根据Bean实体的主键ID执行删除操作.
     * @param           - 数据主键
     * @return int        - 执行结果
     * @throws Exception  - 异常捕捉
     */
    public int deleteTask(Map<String, Object> map) throws Exception;


    public List<Map<String ,Object>> getTodoListWithSelective(Map<String, Object> map) throws Exception;


    public List<Map<String ,Object>> getTodoType() throws Exception;

    public List<Map<String ,Object>> getTodoListGroupByType(String userNo) throws Exception;

}
