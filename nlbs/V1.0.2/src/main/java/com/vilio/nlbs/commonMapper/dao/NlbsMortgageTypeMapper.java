package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsMortgageType;

import java.util.List;

/**
 * Created by dell on 2017/6/1/0001.
 */
public interface NlbsMortgageTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(NlbsMortgageType record);

    int insertSelective(NlbsMortgageType record);

    NlbsMortgageType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsMortgageType record);

    int updateByPrimaryKey(NlbsMortgageType record);

    List selectAll();
}
