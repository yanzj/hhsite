package com.vilio.plms.service.bms;

import java.util.Map;

/**
 * Created by martin on 2017/8/24.
 */
public interface BmsSynchronizationService {
    public void synchronizeApplyInfo(Map map) throws Exception;
    public void synchronize(Map map) throws Exception;

}
