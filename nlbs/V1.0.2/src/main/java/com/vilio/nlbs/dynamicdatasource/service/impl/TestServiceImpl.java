package com.vilio.nlbs.dynamicdatasource.service.impl;

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
    BPSTestDao bpsTestDao;

    public Map testService() {

        return null;
    }
}
