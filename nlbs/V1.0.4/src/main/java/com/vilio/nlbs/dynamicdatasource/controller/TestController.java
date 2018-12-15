package com.vilio.nlbs.dynamicdatasource.controller;

import com.vilio.nlbs.dynamicdatasource.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by dell on 2017/5/22/0022.
 */
@Controller
public class TestController {

    @Resource
    TestService testService;


    @RequestMapping(value = "/api/test",method = RequestMethod.POST)
    @ResponseBody
    public void login() throws Exception{
        testService.testService();

    }
}
