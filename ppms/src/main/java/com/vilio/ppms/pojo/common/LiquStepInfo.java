package com.vilio.ppms.pojo.common;

import java.util.Date;

public class LiquStepInfo {
    private Long id;

    private String code;

    private Date clearDate;

    private String routeCode;

    private String stepType;

    private String totalFlag;

    private String preLiquCode;

    private String isRepeat;

    private String dealStatus;

    private String dealMsg;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public LiquStepInfo(Long id, String code, Date clearDate, String routeCode, String stepType, String totalFlag, String preLiquCode, String isRepeat, String dealStatus, String dealMsg, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.clearDate = clearDate;
        this.routeCode = routeCode;
        this.stepType = stepType;
        this.totalFlag = totalFlag;
        this.preLiquCode = preLiquCode;
        this.isRepeat = isRepeat;
        this.dealStatus = dealStatus;
        this.dealMsg = dealMsg;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public LiquStepInfo() {
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

    public Date getClearDate() {
        return clearDate;
    }

    public void setClearDate(Date clearDate) {
        this.clearDate = clearDate;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType == null ? null : stepType.trim();
    }

    public String getTotalFlag() {
        return totalFlag;
    }

    public void setTotalFlag(String totalFlag) {
        this.totalFlag = totalFlag == null ? null : totalFlag.trim();
    }

    public String getPreLiquCode() {
        return preLiquCode;
    }

    public void setPreLiquCode(String preLiquCode) {
        this.preLiquCode = preLiquCode == null ? null : preLiquCode.trim();
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat == null ? null : isRepeat.trim();
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus == null ? null : dealStatus.trim();
    }

    public String getDealMsg() {
        return dealMsg;
    }

    public void setDealMsg(String dealMsg) {
        this.dealMsg = dealMsg == null ? null : dealMsg.trim();
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