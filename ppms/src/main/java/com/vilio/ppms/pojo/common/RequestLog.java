package com.vilio.ppms.pojo.common;

import java.util.Date;

public class RequestLog {
    private Long id;

    private String code;

    private String returnCode;

    private String returnMessage;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RequestLog(Long id, String code, String returnCode, String returnMessage, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RequestLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode == null ? null : returnCode.trim();
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage == null ? null : returnMessage.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }
}