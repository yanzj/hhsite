package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class RateStair {
    private Long id;

    private String code;

    private String rateCode;

    private BigDecimal amountMin;

    private BigDecimal amountMax;

    private Long countMin;

    private Long countMax;

    private String feeType;

    private Long rate;

    private BigDecimal amount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RateStair(Long id, String code, String rateCode, BigDecimal amountMin, BigDecimal amountMax, Long countMin, Long countMax, String feeType, Long rate, BigDecimal amount, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.rateCode = rateCode;
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.countMin = countMin;
        this.countMax = countMax;
        this.feeType = feeType;
        this.rate = rate;
        this.amount = amount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RateStair() {
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

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode == null ? null : rateCode.trim();
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(BigDecimal amountMin) {
        this.amountMin = amountMin;
    }

    public BigDecimal getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(BigDecimal amountMax) {
        this.amountMax = amountMax;
    }

    public Long getCountMin() {
        return countMin;
    }

    public void setCountMin(Long countMin) {
        this.countMin = countMin;
    }

    public Long getCountMax() {
        return countMax;
    }

    public void setCountMax(Long countMax) {
        this.countMax = countMax;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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