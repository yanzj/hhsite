package com.vilio.ppms.pojo.common;

import java.util.Date;

public class LiquRecord {
    private Long id;

    private String code;

    private String taskId;

    private String taskName;

    private Date liquDate;

    private Date beginTime;

    private Date endTime;

    private String respCode;

    private String respMsg;

    private String routeCode;

    private String dealStatus;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public LiquRecord(Long id, String code, String taskId, String taskName, Date liquDate, Date beginTime, Date endTime, String respCode, String respMsg, String routeCode, String dealStatus, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.taskId = taskId;
        this.taskName = taskName;
        this.liquDate = liquDate;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.routeCode = routeCode;
        this.dealStatus = dealStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public LiquRecord() {
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Date getLiquDate() {
        return liquDate;
    }

    public void setLiquDate(Date liquDate) {
        this.liquDate = liquDate;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg == null ? null : respMsg.trim();
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus == null ? null : dealStatus.trim();
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