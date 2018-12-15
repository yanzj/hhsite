package com.vilio.mps.util;

import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.glob.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/21.
 */
public class MessageUtil {

    //分拣消息接系统
    public static Map classifyInfoByReceiverSystem(List<MpsReceiveMessageInfo> receiveMessageInfo){
        Map<String, List<MpsReceiveMessageInfo>> returnMap = new HashMap<String, List<MpsReceiveMessageInfo>>();

        if(null == receiveMessageInfo || receiveMessageInfo.size() <= 0){
            return null;
        }
        //发往nlbs系统
        List<MpsReceiveMessageInfo> nlbsInfoList = new ArrayList<MpsReceiveMessageInfo>();


        for(MpsReceiveMessageInfo info: receiveMessageInfo){
            if(Constants.RECEIVER_SYSTEM_NLBS.equals(info.getReceiverSystem())){
                nlbsInfoList.add(info);
            }
        }

        if(nlbsInfoList.size() >0){
            returnMap.put(Constants.RECEIVER_SYSTEM_NLBS, nlbsInfoList);
        }

        return returnMap;
    }
}
