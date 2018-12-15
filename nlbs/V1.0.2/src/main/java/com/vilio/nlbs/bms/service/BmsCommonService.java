package com.vilio.nlbs.bms.service;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/12.
 */
public interface BmsCommonService {

    //分页查询进件列表
    Map pageQueryApplyList(Integer pageNo, Integer pageSize, Map paraMap) throws Exception;


}
