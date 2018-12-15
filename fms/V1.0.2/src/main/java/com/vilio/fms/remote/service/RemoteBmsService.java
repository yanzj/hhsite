package com.vilio.fms.remote.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
public interface RemoteBmsService {

    public Map callAddOnlinenstitutionsService(Map paramMap) throws Exception;

    public Map callOnlineloanMasterService(Map paramMap) throws Exception;

}
