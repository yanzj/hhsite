package com.vilio.fms.generator.dao;

import com.vilio.fms.generator.pojo.FmsModelBean;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/2/0002.
 */
public interface FmsModelBeanMapper {

    public int insertModel(Map<String, Object> paramMap) throws Exception;

    public FmsModelBean queryModelByModelCode(String modelCode) throws Exception;

    public List<FmsModelBean> queryModelsWithGroup() throws Exception;

    public List<FmsModelBean> queryModelFiles(FmsModelBean fmsModelBean) throws Exception;

}
