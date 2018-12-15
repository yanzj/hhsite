package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.RequestLog;
import com.vilio.ppms.pojo.common.RequestLogWithBLOBs;

public interface RequestLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RequestLogWithBLOBs record);

    int insertSelective(RequestLogWithBLOBs record);

    RequestLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RequestLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RequestLogWithBLOBs record);

    int updateByPrimaryKey(RequestLog record);
}