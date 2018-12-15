package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划表
 */
public class PlmsRepaymentScheduleBean {

    private int id;
    //
    private String code;
    //当前期数
    private int currentPeriod;
    //总期数
    private int totalPeriod;
    //应还日期
    private Date repaymentDate;
    //应还金额
    private BigDecimal amount;
    //应还本金
    private BigDecimal principal;
    //应还利息
    private BigDecimal interest;
    //应还罚息
    private BigDecimal overdue;
    //状态(01、已结清;02、未结清)
    private String status;
    //账户明细表code
    private String detailCode;
    //用户信息表code
    private String userCode;
    //
    private Date createDate;
    //
    private Date modifyDate;
    //已还本金
    private BigDecimal repaymentedPrincipal;
    //已还利息
    private BigDecimal repaymentedInterest;
    //已还罚息
    private BigDecimal repaymentedOverdue;
    //应还服务费
    private BigDecimal serviceFee;
    //应还服务费
    private BigDecimal repaymentedServiceFee;
    private BigDecimal serviceFeePenalty;
    private BigDecimal repaymentedServiceFeePenalty;

    private BigDecimal bail;
    private BigDecimal bailPenalty;
    private BigDecimal repaymentedBailPenalty;
    private BigDecimal repaymentedBail;

    public PlmsRepaymentScheduleBean() {
    }

    public PlmsRepaymentScheduleBean(int id, String code, int currentPeriod, int totalPeriod, Date repaymentDate, BigDecimal amount, BigDecimal principal, BigDecimal interest, BigDecimal overdue, String status, String detailCode, String userCode, Date createDate, Date modifyDate, BigDecimal repaymentedPrincipal, BigDecimal repaymentedInterest, BigDecimal repaymentedOverdue, BigDecimal serviceFee, BigDecimal repaymentedServiceFee, BigDecimal serviceFeePenalty, BigDecimal repaymentedServiceFeePenalty,BigDecimal bail,BigDecimal bailPenalty,BigDecimal repaymentedBail,BigDecimal repaymentedBailPenalty) {
        this.id = id;
        this.code = code;
        this.currentPeriod = currentPeriod;
        this.totalPeriod = totalPeriod;
        this.repaymentDate = repaymentDate;
        this.amount = amount;
        this.principal = principal;
        this.interest = interest;
        this.overdue = overdue;
        this.status = status;
        this.detailCode = detailCode;
        this.userCode = userCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.repaymentedPrincipal = repaymentedPrincipal;
        this.repaymentedInterest = repaymentedInterest;
        this.repaymentedOverdue = repaymentedOverdue;
        this.serviceFee = serviceFee;
        this.repaymentedServiceFee = repaymentedServiceFee;
        this.serviceFeePenalty = serviceFeePenalty;
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
        this.bail = bail;
        this.bailPenalty = bailPenalty;
        this.repaymentedBail = repaymentedBail;
        this.repaymentedBailPenalty = repaymentedBailPenalty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getOverdue() {
        return overdue;
    }

    public void setOverdue(BigDecimal overdue) {
        this.overdue = overdue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public BigDecimal getRepaymentedPrincipal() {
        return repaymentedPrincipal;
    }

    public void setRepaymentedPrincipal(BigDecimal repaymentedPrincipal) {
        this.repaymentedPrincipal = repaymentedPrincipal;
    }

    public BigDecimal getRepaymentedInterest() {
        return repaymentedInterest;
    }

    public void setRepaymentedInterest(BigDecimal repaymentedInterest) {
        this.repaymentedInterest = repaymentedInterest;
    }

    public BigDecimal getRepaymentedOverdue() {
        return repaymentedOverdue;
    }

    public void setRepaymentedOverdue(BigDecimal repaymentedOverdue) {
        this.repaymentedOverdue = repaymentedOverdue;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getRepaymentedServiceFee() {
        return repaymentedServiceFee;
    }

    public void setRepaymentedServiceFee(BigDecimal repaymentedServiceFee) {
        this.repaymentedServiceFee = repaymentedServiceFee;
    }
    public BigDecimal getServiceFeePenalty() {
        return serviceFeePenalty;
    }

    public void setServiceFeePenalty(BigDecimal serviceFeePenalty) {
        this.serviceFeePenalty = serviceFeePenalty;
    }

    public BigDecimal getRepaymentedServiceFeePenalty() {
        return repaymentedServiceFeePenalty;
    }

    public void setRepaymentedServiceFeePenalty(BigDecimal repaymentedServiceFeePenalty) {
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
    }

    public BigDecimal getRepaymentedBailPenalty() {
        return repaymentedBailPenalty;
    }

    public void setRepaymentedBailPenalty(BigDecimal repaymentedBailPenalty) {
        this.repaymentedBailPenalty = repaymentedBailPenalty;
    }

    public BigDecimal getRepaymentedBail() {
        return repaymentedBail;
    }

    public void setRepaymentedBail(BigDecimal repaymentedBail) {
        this.repaymentedBail = repaymentedBail;
    }

    public BigDecimal getBail() {
        return bail;
    }

    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    public BigDecimal getBailPenalty() {
        return bailPenalty;
    }

    public void setBailPenalty(BigDecimal bailPenalty) {
        this.bailPenalty = bailPenalty;
    }
}
