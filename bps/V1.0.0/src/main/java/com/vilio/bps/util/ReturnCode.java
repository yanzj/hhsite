package com.vilio.bps.util;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public enum  ReturnCode {

    SUCCESS_CODE("0000", "成功"),
    SYSTEM_EXCEPTION("9999", "系统异常"),
    REQUIRED_FIELD_MISSING("9998", "必填字段缺失"),
    NO_CONFIG_APPRAISAL_COMPANY("0001", "暂无询价对接公司！"),
    NO_ONE_COMPANY_PARAM("0002", "需要传入询价公司请求参数！"),
    MONEY_ERROR("0010", "金额输入有误！")
    ;

    private String returnCode;

    private String returnMessage;

    private ReturnCode(String returnCode, String returnMessage){
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public String toString() {
        return this.returnCode + ":" + this.returnMessage;
    }
}
