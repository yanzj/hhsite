package com.vilio.nlbs.apply;

import com.vilio.nlbs.apply.service.ApplyService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class ApplyServiceTest {
    private static Logger logger = Logger.getLogger(ApplyServiceTest.class);

    @Resource
    private ApplyService applyService;

    @Test
    public void testInitApply() throws Exception{

        System.out.println();
    }


}
