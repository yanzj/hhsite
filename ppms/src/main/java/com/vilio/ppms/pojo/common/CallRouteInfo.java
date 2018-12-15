package com.vilio.ppms.pojo.common;

import java.util.Date;

public class CallRouteInfo {
    private Long id;

    private String code;

    private String sysSerno;

    private String routeCode;

    private String transSerno;

    private String routeSerno;

    private Date transTime;

    private String transType;

    private String routeRetCode;

    private String routeRetMsg;

    private String routeTransResult;

    private String transStatus;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public CallRouteInfo(Long id, String code, String sysSerno, String routeCode, String transSerno, String routeSerno, Date transTime, String transType, String routeRetCode, String routeRetMsg, String routeTransResult, String transStatus, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.routeCode = routeCode;
        this.transSerno = transSerno;
        this.routeSerno = routeSerno;
        this.transTime = transTime;
        this.transType = transType;
        this.routeRetCode = routeRetCode;
        this.routeRetMsg = routeRetMsg;
        this.routeTransResult = routeTransResult;
        this.transStatus = transStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public CallRouteInfo() {
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

    public String getSysSerno() {
        return sysSerno;
    }

    public void setSysSerno(String sysSerno) {
        this.sysSerno = sysSerno == null ? null : sysSerno.trim();
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public String getTransSerno() {
        return transSerno;
    }

    public void setTransSerno(String transSerno) {
        this.transSerno = transSerno == null ? null : transSerno.trim();
    }

    public String getRouteSerno() {
        return routeSerno;
    }

    public void setRouteSerno(String routeSerno) {
        this.routeSerno = routeSerno == null ? null : routeSerno.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getRouteRetCode() {
        return routeRetCode;
    }

    public void setRouteRetCode(String routeRetCode) {
        this.routeRetCode = routeRetCode == null ? null : routeRetCode.trim();
    }

    public String getRouteRetMsg() {
        return routeRetMsg;
    }

    public void setRouteRetMsg(String routeRetMsg) {
        this.routeRetMsg = routeRetMsg == null ? null : routeRetMsg.trim();
    }

    public String getRouteTransResult() {
        return routeTransResult;
    }

    public void setRouteTransResult(String routeTransResult) {
        this.routeTransResult = routeTransResult == null ? null : routeTransResult.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
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