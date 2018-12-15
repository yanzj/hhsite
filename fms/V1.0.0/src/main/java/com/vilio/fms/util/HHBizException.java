package com.vilio.fms.util;

/**
 * Created by dell on 2017/6/9/0009.
 */
public class HHBizException extends Exception {

    private String errorNo;

    private String errorMessage;

    public HHBizException(String errorNo, String errorMessage){
        this.errorNo = errorNo;
        this.errorMessage = errorMessage;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "业务异常：[" + this.errorNo + "]" + this.errorMessage;
    }
}
