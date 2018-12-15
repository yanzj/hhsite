package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class ErrorInfo {
    private Long id;

    private String code;

    private Date clearDate;

    private String routeCode;

    private String errorMsg;

    private Date workDate;

    private String sysSerno;

    private String transType;

    private BigDecimal transAmount;

    private String checkStatus;

    private String amountSubject;

    private BigDecimal errorAmount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public ErrorInfo(Long id, String code, Date clearDate, String routeCode, String errorMsg, Date workDate, String sysSerno, String transType, BigDecimal transAmount, String checkStatus, String amountSubject, BigDecimal errorAmount, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.clearDate = clearDate;
        this.routeCode = routeCode;
        this.errorMsg = errorMsg;
        this.workDate = workDate;
        this.sysSerno = sysSerno;
        this.transType = transType;
        this.transAmount = transAmount;
        this.checkStatus = checkStatus;
        this.amountSubject = amountSubject;
        this.errorAmount = errorAmount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public ErrorInfo() {
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getSysSerno() {
        return sysSerno;
    }

    public void setSysSerno(String sysSerno) {
        this.sysSerno = sysSerno == null ? null : sysSerno.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getAmountSubject() {
        return amountSubject;
    }

    public void setAmountSubject(String amountSubject) {
        this.amountSubject = amountSubject == null ? null : amountSubject.trim();
    }

    public BigDecimal getErrorAmount() {
        return errorAmount;
    }

    public void setErrorAmount(BigDecimal errorAmount) {
        this.errorAmount = errorAmount;
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