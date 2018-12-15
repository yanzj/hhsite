package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsMenu;

public interface NlbsMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsMenu record);

    int insertSelective(NlbsMenu record);

    NlbsMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsMenu record);

    int updateByPrimaryKey(NlbsMenu record);
}