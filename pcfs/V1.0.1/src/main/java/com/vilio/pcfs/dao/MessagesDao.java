package com.vilio.pcfs.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/8/15.
 */
public interface MessagesDao {

    //插入信息表中（包含在用户信息表中的用户的待推送信息）
    public int insertMessagesForWaitMessage(String batchNo);

    //更新状态和批次号
    public int updateMessagesByBatchNo(Map updateParam);

    //根据批次号查询推送信息表
    public List<Map> queryMessagesByBatchNo(Map param);

    //根据id修改发送状态
    public int updateMessagesById(Map param);

    //查询未读条数
    public Integer queryMessagesCountByReadFlag(Map param);

}
