package com.vilio.bps.inquiry.worldunion.util;

/**
 * Created by dell on 2017/6/16.
 */
public enum WURespCode {
    WAIT_HAND_PRICE("15", "等待人工给价"),
    AUTO_PRICE("16", "自动估价"),
    HAND_PRICE("17", "人工估价"),
    INVALID_ADDRESS("23", "无效的物业请求地址")
    ;

    private String code;
    private String desc;

    WURespCode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
