package com.vilio.fms.generator.dao;

import com.vilio.fms.generator.pojo.FmsBuildModelDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
public interface FmsBuildModelDetailsMapper {

    public int insertDetails(FmsBuildModelDetails fmsBuildModelDetails) throws Exception;

    public int updateDetails(FmsBuildModelDetails fmsBuildModelDetails) throws Exception;

    public int deleteDetailsBySerialNo(String serialNo) throws Exception;

    public List<FmsBuildModelDetails> queryDetails(FmsBuildModelDetails fmsBuildModelDetails) throws Exception;
}
