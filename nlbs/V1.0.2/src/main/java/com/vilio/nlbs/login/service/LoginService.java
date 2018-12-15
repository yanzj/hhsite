package com.vilio.nlbs.login.service;

import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/30.
 */
public interface LoginService {

    /**
     * 登录
     * @param paramMap
     * @return
     */
    public Map login(Map paramMap) throws Exception;

    /**
     * 首次登录变更密码
     * @param paramMap
     * @return
     */
    public Map changePsw4FirstLogin(Map paramMap) throws Exception;

    /**
     * 登出
     * @param paramMap
     * @return
     * @throws Exception
     */
    Map logout(Map paramMap) throws Exception;

    Map getSession(Map paramMap) throws Exception;

    Map setSession(Map paramMap) throws Exception;

    Map removeSession(Map paramMap) throws Exception;
}
