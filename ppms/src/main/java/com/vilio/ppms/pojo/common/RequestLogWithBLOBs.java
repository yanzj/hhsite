package com.vilio.ppms.pojo.common;

import java.util.Date;

public class RequestLogWithBLOBs extends RequestLog {
    private String requestMsg;

    private String responseMsg;

    public RequestLogWithBLOBs(Long id, String code, String returnCode, String returnMessage, String status, Date createTime, Date updateTime, String remark1, String requestMsg, String responseMsg) {
        super(id, code, returnCode, returnMessage, status, createTime, updateTime, remark1);
        this.requestMsg = requestMsg;
        this.responseMsg = responseMsg;
    }

    public RequestLogWithBLOBs() {
        super();
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg == null ? null : requestMsg.trim();
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg == null ? null : responseMsg.trim();
    }
}