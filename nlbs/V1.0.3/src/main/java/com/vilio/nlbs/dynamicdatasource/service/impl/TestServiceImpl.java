package com.vilio.nlbs.dynamicdatasource.service.impl;

import com.vilio.nlbs.bms.dao.BmsProductDao;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.pojo.NlbsCity;
import com.vilio.nlbs.dynamicdatasource.CustomerContextHolder;
import com.vilio.nlbs.dynamicdatasource.dao.BPSTestDao;
import com.vilio.nlbs.dynamicdatasource.pojo.TestCity;
import com.vilio.nlbs.dynamicdatasource.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/22/0022.
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    BmsProductDao bmsProductDao;

    @Resource
    CommonService commonService;

    public Map testService() {
        try {
            List<NlbsCity> cityList = commonService.queryAllCity();
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
            List<Map> testList = bmsProductDao.queryProductListByDistributorCode("");
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
            cityList = commonService.queryAllInquiryCity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
