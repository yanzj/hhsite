package com.vilio.fms.common.service.impl;


import com.vilio.fms.common.dao.CommonDao;
import com.vilio.fms.common.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dell on 2017/5/5.
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    CommonDao commonDao;
    public String getUUID() throws Exception{
        return commonDao.getUUID();
    }

}

