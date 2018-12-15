package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsLoanPeriod;

import java.util.List;

public interface NlbsLoanPeriodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NlbsLoanPeriod record);

    int insertSelective(NlbsLoanPeriod record);

    NlbsLoanPeriod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NlbsLoanPeriod record);

    int updateByPrimaryKey(NlbsLoanPeriod record);

    List selectAll();
}