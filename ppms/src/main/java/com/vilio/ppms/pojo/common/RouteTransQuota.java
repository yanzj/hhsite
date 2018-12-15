package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class RouteTransQuota {
    private Long id;

    private String code;

    private String routeCode;

    private String transType;

    private String cardType;

    private String pcFlag;

    private BigDecimal singleMinAmount;

    private BigDecimal singleMaxAmount;

    private BigDecimal dayMaxAmount;

    private BigDecimal monthMaxAmount;

    private Long dayMaxCount;

    private String monthMaxCount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RouteTransQuota(Long id, String code, String routeCode, String transType, String cardType, String pcFlag, BigDecimal singleMinAmount, BigDecimal singleMaxAmount, BigDecimal dayMaxAmount, BigDecimal monthMaxAmount, Long dayMaxCount, String monthMaxCount, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.routeCode = routeCode;
        this.transType = transType;
        this.cardType = cardType;
        this.pcFlag = pcFlag;
        this.singleMinAmount = singleMinAmount;
        this.singleMaxAmount = singleMaxAmount;
        this.dayMaxAmount = dayMaxAmount;
        this.monthMaxAmount = monthMaxAmount;
        this.dayMaxCount = dayMaxCount;
        this.monthMaxCount = monthMaxCount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RouteTransQuota() {
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getPcFlag() {
        return pcFlag;
    }

    public void setPcFlag(String pcFlag) {
        this.pcFlag = pcFlag == null ? null : pcFlag.trim();
    }

    public BigDecimal getSingleMinAmount() {
        return singleMinAmount;
    }

    public void setSingleMinAmount(BigDecimal singleMinAmount) {
        this.singleMinAmount = singleMinAmount;
    }

    public BigDecimal getSingleMaxAmount() {
        return singleMaxAmount;
    }

    public void setSingleMaxAmount(BigDecimal singleMaxAmount) {
        this.singleMaxAmount = singleMaxAmount;
    }

    public BigDecimal getDayMaxAmount() {
        return dayMaxAmount;
    }

    public void setDayMaxAmount(BigDecimal dayMaxAmount) {
        this.dayMaxAmount = dayMaxAmount;
    }

    public BigDecimal getMonthMaxAmount() {
        return monthMaxAmount;
    }

    public void setMonthMaxAmount(BigDecimal monthMaxAmount) {
        this.monthMaxAmount = monthMaxAmount;
    }

    public Long getDayMaxCount() {
        return dayMaxCount;
    }

    public void setDayMaxCount(Long dayMaxCount) {
        this.dayMaxCount = dayMaxCount;
    }

    public String getMonthMaxCount() {
        return monthMaxCount;
    }

    public void setMonthMaxCount(String monthMaxCount) {
        this.monthMaxCount = monthMaxCount == null ? null : monthMaxCount.trim();
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