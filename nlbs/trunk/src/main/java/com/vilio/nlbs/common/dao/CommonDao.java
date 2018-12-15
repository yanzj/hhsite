package com.vilio.nlbs.common.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/15.
 */
public interface CommonDao {
    public String getUUID();

    public List<Map> queryAllMaterialTypeList();

}
