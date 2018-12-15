package com.vilio.fms.generator.dao;

import com.vilio.fms.generator.pojo.FmsBuildModelRecords;
import com.vilio.fms.generator.pojo.FmsModelBean;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
public interface FmsBuildModelRecordsMapper {

    public int insertRecords(Map<String, Object> paramMap) throws Exception;

    public int updateRecords(FmsBuildModelRecords fmsBuildModelRecords) throws Exception;

    public FmsBuildModelRecords queryRecordBySerialNo(String serialNo) throws Exception;

    public List<FmsBuildModelRecords> queryRecords(FmsBuildModelRecords fmsBuildModelRecords) throws Exception;
}
