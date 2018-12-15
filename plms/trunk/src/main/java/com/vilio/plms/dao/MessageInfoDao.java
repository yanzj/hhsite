package com.vilio.plms.dao;

import com.vilio.plms.pojo.MessageInfo;

import java.util.List;
import java.util.Map;

/**
 * 类名： MessageInfoDao<br>
 * 功能：推送信息表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface MessageInfoDao {

    //插入推送信息表
    public int insertMessageInfoBatch(List<Map> params);


    //根据老状态修改成新状态
    public int updateMessageInfoStatusByStatus(Map param);

    //查询出所有这个批次好的推送信息
    public List<MessageInfo> queryMessageInfoByBatch(Map param);

    //根据批次号和系统标识更改数据库
    public int updateMessageInfoByBatchAndSystem(Map param);

    //转移历史表
    public int insertMessageInfoLogByBatch(Map param);
    //根据批次号删除推送信息表数据
    public int deleteMessageInfoByBatch(Map param);


}
