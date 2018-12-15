package com.vilio.nlbs.remote.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/27/0027.
 */
public interface RemoteUmService {

    public Map callService(Map paramMap, String functionNo) throws Exception;
}
