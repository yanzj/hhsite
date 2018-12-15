package com.vilio.nlbs.commonMapper.dao;

import com.vilio.nlbs.commonMapper.pojo.NlbsApplyLoanStatus;

import java.util.List;

/**
 * Created by dell on 2017/5/26.
 */
public interface NlbsApplyLoanStatusMapper {
    List<NlbsApplyLoanStatus> queryAllLocalStatus();

}
