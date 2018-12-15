package com.vilio.bps.commonMapper.dao;

import com.vilio.bps.commonMapper.pojo.BpsInquiryStatus;

import java.util.List;

/**
 * Created by dell on 2017/6/16.
 */
public interface BpsInquiryStatusMapper {

    List<BpsInquiryStatus> queryAllInquiryStatus(String status);
}
