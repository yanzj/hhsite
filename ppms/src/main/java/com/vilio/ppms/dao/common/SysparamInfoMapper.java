package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.SysparamInfo;

public interface SysparamInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysparamInfo record);

    int insertSelective(SysparamInfo record);

    SysparamInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysparamInfo record);

    int updateByPrimaryKey(SysparamInfo record);
}