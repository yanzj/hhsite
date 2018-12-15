package com.vilio.mps.receivepush.service;

import java.util.Map;

/**
 * Created by dell on 2017/5/18/0018.
 */
public interface PushMessage {
    public Map pushMessageToNlbs(Map paramMap) throws Exception;
    public Map pushMessageToPlms(Map paramMap) throws Exception;
}
