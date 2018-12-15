package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class LiquTransStatis {
    private Long id;

    private String code;

    private Date clearDate;

    private String routeCode;

    private String merchantCode;

    private Long succCount;

    private BigDecimal succAmount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public LiquTransStatis(Long id, String code, Date clearDate, String routeCode, String merchantCode, Long succCount, BigDecimal succAmount, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.clearDate = clearDate;
        this.routeCode = routeCode;
        this.merchantCode = merchantCode;
        this.succCount = succCount;
        this.succAmount = succAmount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public LiquTransStatis() {
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
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