package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTurnoverInfo {
    private Long id;

    private String code;

    private String merchantCode;

    private BigDecimal amount;

    private String acountFlag;

    private Date transDate;

    private String transSysSerno;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public AccountTurnoverInfo(Long id, String code, String merchantCode, BigDecimal amount, String acountFlag, Date transDate, String transSysSerno, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.merchantCode = merchantCode;
        this.amount = amount;
        this.acountFlag = acountFlag;
        this.transDate = transDate;
        this.transSysSerno = transSysSerno;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public AccountTurnoverInfo() {
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAcountFlag() {
        return acountFlag;
    }

    public void setAcountFlag(String acountFlag) {
        this.acountFlag = acountFlag == null ? null : acountFlag.trim();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getTransSysSerno() {
        return transSysSerno;
    }

    public void setTransSysSerno(String transSysSerno) {
        this.transSysSerno = transSysSerno == null ? null : transSysSerno.trim();
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