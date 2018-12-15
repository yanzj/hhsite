package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentBatch {
    private Long id;

    private String code;

    private String sysSerno;

    private String transType;

    private String reqFileName;

    private String reqFileId;

    private Long totalCount;

    private BigDecimal totalAmount;

    private Long succCount;

    private BigDecimal succAmount;

    private Long failCount;

    private BigDecimal failAmount;

    private Long unsettledCount;

    private BigDecimal unsettledAmount;

    private String batchStatus;

    private String respFileName;

    private String respFileId;

    private Date respTime;

    private Long respCount;

    private String respCode;

    private String respMsg;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public PaymentBatch(Long id, String code, String sysSerno, String transType, String reqFileName, String reqFileId, Long totalCount, BigDecimal totalAmount, Long succCount, BigDecimal succAmount, Long failCount, BigDecimal failAmount, Long unsettledCount, BigDecimal unsettledAmount, String batchStatus, String respFileName, String respFileId, Date respTime, Long respCount, String respCode, String respMsg, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.transType = transType;
        this.reqFileName = reqFileName;
        this.reqFileId = reqFileId;
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
        this.succCount = succCount;
        this.succAmount = succAmount;
        this.failCount = failCount;
        this.failAmount = failAmount;
        this.unsettledCount = unsettledCount;
        this.unsettledAmount = unsettledAmount;
        this.batchStatus = batchStatus;
        this.respFileName = respFileName;
        this.respFileId = respFileId;
        this.respTime = respTime;
        this.respCount = respCount;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public PaymentBatch() {
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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getReqFileName() {
        return reqFileName;
    }

    public void setReqFileName(String reqFileName) {
        this.reqFileName = reqFileName == null ? null : reqFileName.trim();
    }

    public String getReqFileId() {
        return reqFileId;
    }

    public void setReqFileId(String reqFileId) {
        this.reqFileId = reqFileId == null ? null : reqFileId.trim();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getSuccCount() {
        return succCount;
    }

    public void setSuccCount(Long succCount) {
        this.succCount = succCount;
    }

    public BigDecimal getSuccAmount() {
        return succAmount;
    }

    public void setSuccAmount(BigDecimal succAmount) {
        this.succAmount = succAmount;
    }

    public Long getFailCount() {
        return failCount;
    }

    public void setFailCount(Long failCount) {
        this.failCount = failCount;
    }

    public BigDecimal getFailAmount() {
        return failAmount;
    }

    public void setFailAmount(BigDecimal failAmount) {
        this.failAmount = failAmount;
    }

    public Long getUnsettledCount() {
        return unsettledCount;
    }

    public void setUnsettledCount(Long unsettledCount) {
        this.unsettledCount = unsettledCount;
    }

    public BigDecimal getUnsettledAmount() {
        return unsettledAmount;
    }

    public void setUnsettledAmount(BigDecimal unsettledAmount) {
        this.unsettledAmount = unsettledAmount;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus == null ? null : batchStatus.trim();
    }

    public String getRespFileName() {
        return respFileName;
    }

    public void setRespFileName(String respFileName) {
        this.respFileName = respFileName == null ? null : respFileName.trim();
    }

    public String getRespFileId() {
        return respFileId;
    }

    public void setRespFileId(String respFileId) {
        this.respFileId = respFileId == null ? null : respFileId.trim();
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public Long getRespCount() {
        return respCount;
    }

    public void setRespCount(Long respCount) {
        this.respCount = respCount;
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
}