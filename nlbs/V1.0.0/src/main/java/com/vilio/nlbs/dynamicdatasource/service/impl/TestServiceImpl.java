package com.vilio.nlbs.dynamicdatasource.service.impl;

import com.vilio.nlbs.commonMapper.pojo.NlbsUser;
import com.vilio.nlbs.dynamicdatasource.CustomerContextHolder;
import com.vilio.nlbs.dynamicdatasource.dao.BPSTestDao;
import com.vilio.nlbs.dynamicdatasource.pojo.TestCity;
import com.vilio.nlbs.dynamicdatasource.service.TestService;
import com.vilio.nlbs.user.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/22/0022.
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    UserDao userDao;

    @Resource
    BPSTestDao bpsTestDao;

    public Map testService() {
        NlbsUser nlbsUser = userDao.queryNlbsUserByUserName("13681692659");
        List<TestCity> citysMap = null;
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);

        citysMap = bpsTestDao.getCitys();
//        CustomerContextHolder.clearCustomerType();
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        nlbsUser = userDao.queryNlbsUserByUserName("15601936981");
        return null;
    }
}
