package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class TransInfo {
    private Long id;

    private String code;

    private String sysSerno;

    private String chlSerno;

    private Date reqDate;

    private String reqTime;

    private Date workDate;

    private Date sysTime;

    private String sourceSystem;

    private String transCode;

    private String transType;

    private BigDecimal transAmount;

    private String payerGroupId;

    private String dealStatus;

    private String respCode;

    private String respMsg;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    private String payeeGroupId;

    private BigDecimal sourceFee;

    private BigDecimal transAmtTotal;

    private String requestJson;

    public TransInfo(Long id, String code, String sysSerno, String chlSerno, Date reqDate, String reqTime, Date workDate, Date sysTime, String sourceSystem, String transCode, String transType, BigDecimal transAmount, String payerGroupId, String dealStatus, String respCode, String respMsg, String status, Date createTime, Date updateTime, String remark1, String payeeGroupId, BigDecimal sourceFee, BigDecimal transAmtTotal, String requestJson) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.chlSerno = chlSerno;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
        this.workDate = workDate;
        this.sysTime = sysTime;
        this.sourceSystem = sourceSystem;
        this.transCode = transCode;
        this.transType = transType;
        this.transAmount = transAmount;
        this.payerGroupId = payerGroupId;
        this.dealStatus = dealStatus;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
        this.payeeGroupId = payeeGroupId;
        this.sourceFee = sourceFee;
        this.transAmtTotal = transAmtTotal;
        this.requestJson = requestJson;
    }

    public TransInfo() {
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

    public String getChlSerno() {
        return chlSerno;
    }

    public void setChlSerno(String chlSerno) {
        this.chlSerno = chlSerno == null ? null : chlSerno.trim();
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime == null ? null : reqTime.trim();
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getSysTime() {
        return sysTime;
    }

    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem == null ? null : sourceSystem.trim();
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
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

    public String getPayerGroupId() {
        return payerGroupId;
    }

    public void setPayerGroupId(String payerGroupId) {
        this.payerGroupId = payerGroupId == null ? null : payerGroupId.trim();
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus == null ? null : dealStatus.trim();
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

    public String getPayeeGroupId() {
        return payeeGroupId;
    }

    public void setPayeeGroupId(String payeeGroupId) {
        this.payeeGroupId = payeeGroupId == null ? null : payeeGroupId.trim();
    }

    public BigDecimal getSourceFee() {
        return sourceFee;
    }

    public void setSourceFee(BigDecimal sourceFee) {
        this.sourceFee = sourceFee;
    }

    public BigDecimal getTransAmtTotal() {
        return transAmtTotal;
    }

    public void setTransAmtTotal(BigDecimal transAmtTotal) {
        this.transAmtTotal = transAmtTotal;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson == null ? null : requestJson.trim();
    }
}