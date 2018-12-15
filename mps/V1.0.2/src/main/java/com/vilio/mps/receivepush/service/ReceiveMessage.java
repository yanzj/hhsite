package com.vilio.mps.receivepush.service;

import java.util.Map;

/**
 * Created by dell on 2017/5/18/0018.
 */
public interface ReceiveMessage {

    public Map saveMessage(Map paramMap) throws Exception;

    public Map getMessageListBySerialNoList(Map paramMap) throws Exception;
}
