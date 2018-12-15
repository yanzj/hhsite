package com.vilio.pcfs.dao;

import java.util.List;

/**
 * Created by 63236 on 2017/8/4.
 */
public interface PushMessageDao {
    //插入 又贷后系统发送的   待推送消息
    public int insertPushMessagesList(List messagesList);

    //查询当前用户消息列表
    public  List queryUserMessagesListInfo(String userId);

    //查询当前用户未读条数
    public  String queryUserUnreadMessageNum(String userId);

    //将当前用户指定信息标志为已读
    public int updateUserMesaageReadFlag(String messageId);

    //将当前用户指定信息置为失效（删除）
    public  int updateUserMessageStatu(String messageId);

}
