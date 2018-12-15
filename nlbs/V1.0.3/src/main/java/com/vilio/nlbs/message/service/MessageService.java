package com.vilio.nlbs.message.service;

import java.util.Map;

/**
 * Created by dell on 2017/5/31/0031.
 */
public interface MessageService {

    int insertMessage(Map paramMap) throws Exception;

    Map modifySendMessage(Map paramMap) throws Exception;

    Map queryMessageList(Map paramMap) throws Exception;

    Map modifyReceiveMessage(Map paramMap) throws Exception;

    Map receiveMessage(Map paramMap) throws Exception;

}
