package com.vilio.nlbs.util;

/**
 * Created by dell on 2017/6/22/0022.
 */
public enum MessageModel {

    MSG_001("001", "房产待评估通知"),
    MSG_002("002", "房产已评估通知"),
    MSG_003("003", "房产正在评估通知");

    private String code;
    private String title;

    private MessageModel(String code, String title) {
        this.code = code;
        this.title = title;
    }

    // 普通方法
    public static MessageModel getMessageModelByCode(String code) {
        for (MessageModel mm : MessageModel.values()) {
            if (mm.getCode().equals(code)){
                return mm;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
