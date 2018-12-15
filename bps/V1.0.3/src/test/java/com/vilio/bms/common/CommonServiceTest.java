package com.vilio.bms.common;

import com.vilio.bps.common.service.CommonService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by xiezhilei on 2017/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class CommonServiceTest {
    private static Logger logger = Logger.getLogger(CommonServiceTest.class);

    @Resource
    private CommonService iBpsReceiveDataMapper;

    @Test
    public void testSelectAllDistributors() throws Exception{


    }
}
