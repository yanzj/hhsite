package com.vilio.nlbs.apply.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public interface ApplyService {

    Map initApplyInfo(Map paramMap) throws Exception;

    Map initApplyList(Map paramMap) throws Exception;

    Map getAgentAndProductList(Map paramMap) throws Exception;

    Map saveApplyRecord(Map paramMap) throws Exception;

    Map queryApplyRecord(Map paramMap) throws Exception;

    Map queryApplyRecordList(Map paramMap) throws Exception;

    Map queryAllMaterialTypeList() throws Exception;

    Map initModifyApplyRecord(Map paramMap) throws Exception;

    Map modifyApplyRecord(Map paramMap) throws Exception;

    Map deleteApplyRecord(Map paramMap) throws Exception;

    Map supplyApplyMaterial(Map paramMap) throws Exception;

    Map queryOperationHistory(Map paramMap) throws Exception;

    Map uploadFile(Map paramMap) throws Exception;

    public Map getFile(Map map, HttpServletResponse rsp) throws Exception;

    public Map getUrlList(Map map) throws Exception;




}