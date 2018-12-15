package com.vilio.fms.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/16.
 */
public class Constants {
    //文件的上载路径
    public final static String  APPLY_FILE_PATH ="fileSystem/files/";

    // 生成目标文件单子的状态--初始化--待生成
    //public final static String GENERATOR_STATUS_INIT = "01";
    // 生成目标文件单子的状态--生成中
    public final static String GENERATOR_STATUS_DOING = "01";
    // 生成目标文件单子的状态--生成成功
    public final static String GENERATOR_STATUS_SUCCESS = "02";
    // 生成目标文件单子的状态--部分失败
    public final static String GENERATOR_STATUS_PART_FAIL = "03";
    // 生成目标文件单子的状态--无法生成或全部生成失败
    public final static String GENERATOR_STATUS_ALL_FAIL = "04";

    // 生成目标文件详情的状态--生成中
    public final static String GENERATOR_DETAILS_STATUS_DOING = "00";
    // 生成目标文件详情的状态--生成成功
    public final static String GENERATOR_DETAILS_STATUS_SUCCESS = "01";
    // 生成目标文件详情的状态--生成失败
    public final static String GENERATOR_DETAILS_STATUS_FAIL = "02";

    // 生成目标文件外发的状态--（待发送）--生成文件的请求，未指明外发方式和接收人
    public final static String OUT_SEND_STATUS_INIT = "11";
    // 生成目标文件外发的状态--发送中（待发送）
    public final static String OUT_SEND_STATUS_DOING = "00";
    // 生成目标文件外发的状态--发送成功
    public final static String OUT_SEND_STATUS_SUCCESS = "01";
    // 生成目标文件外发的状态--发送失败
    public final static String OUT_SEND_STATUS_FAIL = "02";

    //借款合同标识 01：借款合同 02：非借款合同
    public final static String IS_LOAN_CONTRACT = "01";
    public final static String ISNOT_LOAN_CONTRACT = "02";

    //抵押方式  01：联抵联押 02：分抵分押
    public final static String MORTGAGE_TYPE_JOINT_MORTGAGE = "01";
    public final static String MORTGAGE_TYPE_SUB_MORTGAGE = "02";

}
