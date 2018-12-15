package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class SubAccount {
    private Long id;

    private String code;

    private String sysSerno;

    private BigDecimal transAmount;

    private BigDecimal subAccAmount;

    private String subAccStatus;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public SubAccount(Long id, String code, String sysSerno, BigDecimal transAmount, BigDecimal subAccAmount, String subAccStatus, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.transAmount = transAmount;
        this.subAccAmount = subAccAmount;
        this.subAccStatus = subAccStatus;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public SubAccount() {
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

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public BigDecimal getSubAccAmount() {
        return subAccAmount;
    }

    public void setSubAccAmount(BigDecimal subAccAmount) {
        this.subAccAmount = subAccAmount;
    }

    public String getSubAccStatus() {
        return subAccStatus;
    }

    public void setSubAccStatus(String subAccStatus) {
        this.subAccStatus = subAccStatus == null ? null : subAccStatus.trim();
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