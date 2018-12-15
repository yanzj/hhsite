package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class RefundInfo {
    private Long id;

    private String code;

    private String sysSerno;

    private String transSerno;

    private String routeSerno;

    private Date transDate;

    private String transTime;

    private String transType;

    private String refundSysSerno;

    private String refundTransSerno;

    private BigDecimal refundAmount;

    private String routeRetCode;

    private String routeRetMsg;

    private String routeTransResult;

    private String respCode;

    private String respMsg;

    private String checkFlag;

    private BigDecimal estimateFee;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    private String transStatus;

    private String stepStatus;

    private String sysStatus;

    private BigDecimal sourceFee;

    private BigDecimal realFee;

    public RefundInfo(Long id, String code, String sysSerno, String transSerno, String routeSerno, Date transDate, String transTime, String transType, String refundSysSerno, String refundTransSerno, BigDecimal refundAmount, String routeRetCode, String routeRetMsg, String routeTransResult, String respCode, String respMsg, String checkFlag, BigDecimal estimateFee, String status, Date createTime, Date updateTime, String remark1, String transStatus, String stepStatus, String sysStatus, BigDecimal sourceFee, BigDecimal realFee) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.transSerno = transSerno;
        this.routeSerno = routeSerno;
        this.transDate = transDate;
        this.transTime = transTime;
        this.transType = transType;
        this.refundSysSerno = refundSysSerno;
        this.refundTransSerno = refundTransSerno;
        this.refundAmount = refundAmount;
        this.routeRetCode = routeRetCode;
        this.routeRetMsg = routeRetMsg;
        this.routeTransResult = routeTransResult;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.checkFlag = checkFlag;
        this.estimateFee = estimateFee;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
        this.transStatus = transStatus;
        this.stepStatus = stepStatus;
        this.sysStatus = sysStatus;
        this.sourceFee = sourceFee;
        this.realFee = realFee;
    }

    public RefundInfo() {
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

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime == null ? null : transTime.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getRefundSysSerno() {
        return refundSysSerno;
    }

    public void setRefundSysSerno(String refundSysSerno) {
        this.refundSysSerno = refundSysSerno == null ? null : refundSysSerno.trim();
    }

    public String getRefundTransSerno() {
        return refundTransSerno;
    }

    public void setRefundTransSerno(String refundTransSerno) {
        this.refundTransSerno = refundTransSerno == null ? null : refundTransSerno.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
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

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
    }

    public BigDecimal getEstimateFee() {
        return estimateFee;
    }

    public void setEstimateFee(BigDecimal estimateFee) {
        this.estimateFee = estimateFee;
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

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus == null ? null : stepStatus.trim();
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus == null ? null : sysStatus.trim();
    }

    public BigDecimal getSourceFee() {
        return sourceFee;
    }

    public void setSourceFee(BigDecimal sourceFee) {
        this.sourceFee = sourceFee;
    }

    public BigDecimal getRealFee() {
        return realFee;
    }

    public void setRealFee(BigDecimal realFee) {
        this.realFee = realFee;
    }
}