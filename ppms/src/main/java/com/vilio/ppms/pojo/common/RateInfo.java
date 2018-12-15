package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class RateInfo {
    private Long id;

    private String code;

    private String routeCode;

    private String transType;

    private String chargeType;

    private String stairType;

    private BigDecimal feeMaxAmount;

    private BigDecimal feeMinAmount;

    private BigDecimal feeRate;

    private String totalCountType;

    private String totalType;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RateInfo(Long id, String code, String routeCode, String transType, String chargeType, String stairType, BigDecimal feeMaxAmount, BigDecimal feeMinAmount, BigDecimal feeRate, String totalCountType, String totalType, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.routeCode = routeCode;
        this.transType = transType;
        this.chargeType = chargeType;
        this.stairType = stairType;
        this.feeMaxAmount = feeMaxAmount;
        this.feeMinAmount = feeMinAmount;
        this.feeRate = feeRate;
        this.totalCountType = totalCountType;
        this.totalType = totalType;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RateInfo() {
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

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public String getStairType() {
        return stairType;
    }

    public void setStairType(String stairType) {
        this.stairType = stairType == null ? null : stairType.trim();
    }

    public BigDecimal getFeeMaxAmount() {
        return feeMaxAmount;
    }

    public void setFeeMaxAmount(BigDecimal feeMaxAmount) {
        this.feeMaxAmount = feeMaxAmount;
    }

    public BigDecimal getFeeMinAmount() {
        return feeMinAmount;
    }

    public void setFeeMinAmount(BigDecimal feeMinAmount) {
        this.feeMinAmount = feeMinAmount;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public String getTotalCountType() {
        return totalCountType;
    }

    public void setTotalCountType(String totalCountType) {
        this.totalCountType = totalCountType == null ? null : totalCountType.trim();
    }

    public String getTotalType() {
        return totalType;
    }

    public void setTotalType(String totalType) {
        this.totalType = totalType == null ? null : totalType.trim();
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