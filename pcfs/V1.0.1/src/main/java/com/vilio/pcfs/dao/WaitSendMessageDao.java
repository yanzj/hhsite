package com.vilio.pcfs.dao;

import java.util.Map;

/**
 * Created by dell on 2017/8/15.
 */
public interface WaitSendMessageDao {

    //更新待发送数据
    public int updateWaitSendMessage(Map waitSendMessage);

    //根据批次号更新状态
    public int updateWaitSendMessageStatusByBatchNo(Map waitSendMessage);
    //转移历史表
    public int insertWaitSendMessageLogByBatchNo(Map waitSendMessage);
    //删除待发送表
    public int deleteWaitSendMessageLogByBatchNo(Map waitSendMessage);

}
