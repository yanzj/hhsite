package com.vilio.mps.common.dao;

import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/11.
 */
public interface MpsMessageReceiveInfoMapper {
    int insertMessageReceiveInfo(MpsReceiveMessageInfo receiveInfo);
    MpsReceiveMessageInfo selectMessageBySerialNo(String serialNo);

    int updateStatusBySerialNo(MpsReceiveMessageInfo receiveInfo);

    List<MpsReceiveMessageInfo> getMessageBySerialNoList(Map map);

    public int insertMessageReceiveInfoBatch(List<MpsReceiveMessageInfo> mpsReceiveMessageInfos);

    public int updateMessageReceiveInfoBatch(Map mpsReceiveMessageInfos);

}
