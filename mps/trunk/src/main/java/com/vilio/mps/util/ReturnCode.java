package com.vilio.mps.util;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public enum ReturnCode {
    SUCCESS_CODE("0000", "成功"),
    SYSTEM_EXCEPTION("9999", "系统异常"),
    CONNECT_NLBS_EXCEPTION("9001", "系统异常"),
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
