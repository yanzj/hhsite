package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class RouteTotal {
    private Long id;

    private String code;

    private String routeCode;

    private String merchantCode;

    private Date transDate;

    private BigDecimal totalAmountDay;

    private Long totalCountDay;

    private BigDecimal totalAmountMonth;

    private Long totalCountMonth;

    private BigDecimal totalAmountYear;

    private Long totalCountYear;

    private BigDecimal totalAmount;

    private Long totalCount;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RouteTotal(Long id, String code, String routeCode, String merchantCode, Date transDate, BigDecimal totalAmountDay, Long totalCountDay, BigDecimal totalAmountMonth, Long totalCountMonth, BigDecimal totalAmountYear, Long totalCountYear, BigDecimal totalAmount, Long totalCount, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.routeCode = routeCode;
        this.merchantCode = merchantCode;
        this.transDate = transDate;
        this.totalAmountDay = totalAmountDay;
        this.totalCountDay = totalCountDay;
        this.totalAmountMonth = totalAmountMonth;
        this.totalCountMonth = totalCountMonth;
        this.totalAmountYear = totalAmountYear;
        this.totalCountYear = totalCountYear;
        this.totalAmount = totalAmount;
        this.totalCount = totalCount;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RouteTotal() {
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public BigDecimal getTotalAmountDay() {
        return totalAmountDay;
    }

    public void setTotalAmountDay(BigDecimal totalAmountDay) {
        this.totalAmountDay = totalAmountDay;
    }

    public Long getTotalCountDay() {
        return totalCountDay;
    }

    public void setTotalCountDay(Long totalCountDay) {
        this.totalCountDay = totalCountDay;
    }

    public BigDecimal getTotalAmountMonth() {
        return totalAmountMonth;
    }

    public void setTotalAmountMonth(BigDecimal totalAmountMonth) {
        this.totalAmountMonth = totalAmountMonth;
    }

    public Long getTotalCountMonth() {
        return totalCountMonth;
    }

    public void setTotalCountMonth(Long totalCountMonth) {
        this.totalCountMonth = totalCountMonth;
    }

    public BigDecimal getTotalAmountYear() {
        return totalAmountYear;
    }

    public void setTotalAmountYear(BigDecimal totalAmountYear) {
        this.totalAmountYear = totalAmountYear;
    }

    public Long getTotalCountYear() {
        return totalCountYear;
    }

    public void setTotalCountYear(Long totalCountYear) {
        this.totalCountYear = totalCountYear;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
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