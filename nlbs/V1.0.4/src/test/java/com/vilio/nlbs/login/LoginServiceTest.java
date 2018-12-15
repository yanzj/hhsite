package com.vilio.nlbs.login;

import com.vilio.nlbs.login.service.LoginService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by xiezhilei on 2016/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class LoginServiceTest {
    private static Logger logger = Logger.getLogger(LoginServiceTest.class);

    @Resource
    private LoginService loginService = null;

    @Test
    public void testLogin() throws Exception{
//        Login login = new Login();
//        login.setUser("15601936981");
//        login.setPassword("15601936981");
//
//        Map result = loginService.login(login);
//        System.out.println(result);
//        Assert.assertEquals("0000",result.get("rsbCode"));
    }

    @Test
    public void  testChangePsw4firstLogin(){
//        ChangePsw4FirstLogin changePsw4firstLogin = new ChangePsw4FirstLogin();
//        changePsw4firstLogin.setUser("15601936981");
//        changePsw4firstLogin.setNewPassword("15601936982");
//        changePsw4firstLogin.setNewPasswordAgain("15601936982");
//
//        Map result = loginService.changePsw4FirstLogin(changePsw4firstLogin);
//        System.out.println(result);
//        Assert.assertEquals(1,result.get("num"));
    }
}
