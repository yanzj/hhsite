package com.vilio.fms.common.service;

import java.util.Map;

/**
 * Created by dell on 2017/5/24/0024.
 */
public interface PretreatmentService {

    public Map dispatchServices(Map paramMap) throws Exception;

    public Map filterHeadParam(Map headParamMap) throws Exception;
}
