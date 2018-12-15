package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/7/24.
 */
public class PlmsRepaymentScheduleDetail {
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
    //应还服务费
    private BigDecimal serviceFee;
    //应还保证金
    private BigDecimal bail;
    //应还保证金违约金
    private BigDecimal bailPenalty;
    //应还服务费违约金
    private BigDecimal serviceFeePenalty;
    //已还服务费
    private BigDecimal repaymentedServiceFee;
    //已还服务费违约金
    private BigDecimal repaymentedServiceFeePenalty;
    //已还本金
    private BigDecimal repaymentedPrincipal;
    //已还利息
    private BigDecimal repaymentedInterest;
    //已还保证金
    private BigDecimal repaymentedBail;
    //已还保证金违约金
    private BigDecimal repaymentedBailPenalty;

    //已还罚息
    private BigDecimal repaymentedOverdue;

    //更新日期
    private Date modifyDate;
    //创建时间
    private Date createDate;

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

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getServiceFeePenalty() {
        return serviceFeePenalty;
    }

    public void setServiceFeePenalty(BigDecimal serviceFeePenalty) {
        this.serviceFeePenalty = serviceFeePenalty;
    }

    public BigDecimal getRepaymentedServiceFee() {
        return repaymentedServiceFee;
    }

    public void setRepaymentedServiceFee(BigDecimal repaymentedServiceFee) {
        this.repaymentedServiceFee = repaymentedServiceFee;
    }

    public BigDecimal getRepaymentedServiceFeePenalty() {
        return repaymentedServiceFeePenalty;
    }

    public void setRepaymentedServiceFeePenalty(BigDecimal repaymentedServiceFeePenalty) {
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
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
    public BigDecimal getRepaymentedBail() {
        return repaymentedBail;
    }

    public void setRepaymentedBail(BigDecimal repaymentedBail) {
        this.repaymentedBail = repaymentedBail;
    }
    public BigDecimal getRepaymentedBailPenalty() {
        return repaymentedBailPenalty;
    }

    public void setRepaymentedBailPenalty(BigDecimal repaymentedBailPenalty) {
        this.repaymentedBailPenalty = repaymentedBailPenalty;
    }



    //状态(01、已结清;02、未结清)
    private String status;

    //放款信息表code
    private String paidCode;
    //还款计划表code
    private String scheduleCode;
    //本金收款账户表code
    private String principalAccountCode;
    //利息收款账户表code
    private String interestAccountCode;

    private String totalOverdueDays;
    private String serviceFeeTotalOverdueDays;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidCode() {
        return paidCode;
    }

    public void setPaidCode(String paidCode) {
        this.paidCode = paidCode;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        this.scheduleCode = scheduleCode;
    }

    public String getPrincipalAccountCode() {
        return principalAccountCode;
    }

    public void setPrincipalAccountCode(String principalAccountCode) {
        this.principalAccountCode = principalAccountCode;
    }

    public String getInterestAccountCode() {
        return interestAccountCode;
    }

    public void setInterestAccountCode(String interestAccountCode) {
        this.interestAccountCode = interestAccountCode;
    }

    public String getTotalOverdueDays() {
        return totalOverdueDays;
    }

    public void setTotalOverdueDays(String totalOverdueDays) {
        this.totalOverdueDays = totalOverdueDays;
    }

    public String getServiceFeeTotalOverdueDays() {
        return serviceFeeTotalOverdueDays;
    }

    public void setServiceFeeTotalOverdueDays(String serviceFeeTotalOverdueDays) {
        this.serviceFeeTotalOverdueDays = serviceFeeTotalOverdueDays;
    }

    public PlmsRepaymentScheduleDetail() {
    }

    public PlmsRepaymentScheduleDetail(int id, String code, int currentPeriod, int totalPeriod, Date repaymentDate, BigDecimal amount, BigDecimal principal, BigDecimal interest, BigDecimal overdue, BigDecimal serviceFee, BigDecimal serviceFeePenalty, BigDecimal repaymentedServiceFee, BigDecimal repaymentedServiceFeePenalty, BigDecimal repaymentedPrincipal, BigDecimal repaymentedInterest, BigDecimal repaymentedOverdue, BigDecimal bail,BigDecimal bailPenalty,BigDecimal repaymentedBail,BigDecimal repaymentedBailPenalty,Date modifyDate, Date createDate, String status, String paidCode, String scheduleCode, String principalAccountCode, String interestAccountCode, String totalOverdueDays, String serviceFeeTotalOverdueDays) {
        this.id = id;
        this.code = code;
        this.currentPeriod = currentPeriod;
        this.totalPeriod = totalPeriod;
        this.repaymentDate = repaymentDate;
        this.amount = amount;
        this.principal = principal;
        this.interest = interest;
        this.overdue = overdue;
        this.serviceFee = serviceFee;
        this.serviceFeePenalty = serviceFeePenalty;
        this.repaymentedServiceFee = repaymentedServiceFee;
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
        this.repaymentedPrincipal = repaymentedPrincipal;
        this.repaymentedInterest = repaymentedInterest;
        this.repaymentedOverdue = repaymentedOverdue;
        this.modifyDate = modifyDate;
        this.createDate = createDate;
        this.status = status;
        this.paidCode = paidCode;
        this.scheduleCode = scheduleCode;
        this.principalAccountCode = principalAccountCode;
        this.interestAccountCode = interestAccountCode;
        this.totalOverdueDays = totalOverdueDays;
        this.serviceFeeTotalOverdueDays = serviceFeeTotalOverdueDays;
        this.bail = bail;
        this.bailPenalty = bailPenalty;
        this.repaymentedBail = repaymentedBail;
        this.repaymentedBailPenalty = repaymentedBailPenalty;

    }
}
