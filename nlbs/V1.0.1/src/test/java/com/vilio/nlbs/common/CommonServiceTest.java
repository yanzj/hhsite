package com.vilio.nlbs.common;

import com.vilio.nlbs.common.service.CommonService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiezhilei on 2017/1/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class CommonServiceTest {
    private static Logger logger = Logger.getLogger(CommonServiceTest.class);

    @Resource
    private CommonService commonService;


    @Test
    public void testSelectAllHouseTypes() throws Exception{
        System.out.println();
    }
}
