package com.vilio.fms.commonMapper.dao;

import com.vilio.fms.commonMapper.pojo.FmsFileLoad;

import java.util.List;

/**
 * Created by dell on 2017/5/17.
 */
public interface FmsFileLoadMapper {
    FmsFileLoad selectByPrimaryKey(Integer id);

    FmsFileLoad queryBySerialNo(String serialNo);

    int saveFileLoad(FmsFileLoad fmsFileLoad);

    int modifyFileLoadUrl(FmsFileLoad fmsFileLoad);

}
