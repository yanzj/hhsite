package com.vilio.nlbs.bms.dao;

import com.vilio.nlbs.bms.pojo.ApplyRecordRespBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuxu on 2017/1/15.
 */
public interface BmsCommonDao {

    public List<ApplyRecordRespBean> queryApplyList(Map paraMap);
}
