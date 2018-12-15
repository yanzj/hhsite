package com.vilio.nlbs.remote.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
public interface RemotePlmsService {

    public Map callService(String functionNo, Map paramMap) throws Exception;
}
