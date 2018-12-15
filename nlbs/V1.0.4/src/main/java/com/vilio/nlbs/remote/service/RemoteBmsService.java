package com.vilio.nlbs.remote.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
public interface RemoteBmsService {

    public Map callOnlineloanMasterService(Map paramMap, String httpMethod) throws Exception;

    public Map callAddOnlineloanAttachService(Map paramMap) throws Exception;
}
