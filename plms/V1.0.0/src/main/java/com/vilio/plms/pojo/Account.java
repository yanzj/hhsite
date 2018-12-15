package com.vilio.plms.pojo;

/**
 * 类名： Account<br>
 * 功能：账户汇总实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Account {

    private Integer id;
    private String code;
    private String totalQuota;
    private String remainingQuota;
    private String principal;
    private String interest;
    private String serviceFee;
    private String overdue;
    private String serviceFeePenalty;
    private String userCode;
    private String creditEndDate;
    private String status;
    private String createDate;
    private String modifyDate;
    private String repaymentedPrincipal;
    private String repaymentedInterest;
    private String repaymentedServiceFee;
    private String repaymentedOverdue;
    private String repaymentedServiceFeePenalty;

    private String bail;
    private String bailPenalty;
    private String repaymentedBail;
    private String repaymentedBailPenalty;
    //已还提前还款违约金
    private String repaymentedPenalty;

    private String repaymentedAheadInterestPenalty;
    private String repaymentedAheadServiceFeePenalty;

    public Account() {
    }

    public Account(int id, String code, String totalQuota, String remainingQuota, String principal, String interest, String serviceFee, String overdue, String serviceFeePenalty, String userCode, String creditEndDate, String status, String createDate, String modifyDate, String repaymentedPrincipal, String repaymentedInterest, String repaymentedServiceFee, String repaymentedOverdue, String repaymentedServiceFeePenalty, String bail, String bailPenalty, String repaymentedBail, String repaymentedBailPenalty, String repaymentedPenalty, String repaymentedAheadInterestPenalty, String repaymentedAheadServiceFeePenalty) {
        this.id = id;
        this.code = code;
        this.totalQuota = totalQuota;
        this.remainingQuota = remainingQuota;
        this.principal = principal;
        this.interest = interest;
        this.serviceFee = serviceFee;
        this.overdue = overdue;
        this.serviceFeePenalty = serviceFeePenalty;
        this.userCode = userCode;
        this.creditEndDate = creditEndDate;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.repaymentedPrincipal = repaymentedPrincipal;
        this.repaymentedInterest = repaymentedInterest;
        this.repaymentedServiceFee = repaymentedServiceFee;
        this.repaymentedOverdue = repaymentedOverdue;
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
        this.repaymentedPenalty = repaymentedPenalty;
        this.bail = bail;
        this.bailPenalty = bailPenalty;
        this.repaymentedBail = repaymentedBail;
        this.repaymentedBailPenalty = repaymentedBailPenalty;
        this.repaymentedPenalty = repaymentedPenalty;
        this.repaymentedAheadInterestPenalty = repaymentedAheadInterestPenalty;
        this.repaymentedAheadServiceFeePenalty = repaymentedAheadServiceFeePenalty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(String totalQuota) {
        this.totalQuota = totalQuota;
    }

    public String getRemainingQuota() {
        return remainingQuota;
    }

    public void setRemainingQuota(String remainingQuota) {
        this.remainingQuota = remainingQuota;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getServiceFeePenalty() {
        return serviceFeePenalty;
    }

    public void setServiceFeePenalty(String serviceFeePenalty) {
        this.serviceFeePenalty = serviceFeePenalty;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCreditEndDate() {
        return creditEndDate;
    }

    public void setCreditEndDate(String creditEndDate) {
        this.creditEndDate = creditEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRepaymentedPrincipal() {
        return repaymentedPrincipal;
    }

    public void setRepaymentedPrincipal(String repaymentedPrincipal) {
        this.repaymentedPrincipal = repaymentedPrincipal;
    }

    public String getRepaymentedInterest() {
        return repaymentedInterest;
    }

    public void setRepaymentedInterest(String repaymentedInterest) {
        this.repaymentedInterest = repaymentedInterest;
    }

    public String getRepaymentedServiceFee() {
        return repaymentedServiceFee;
    }

    public void setRepaymentedServiceFee(String repaymentedServiceFee) {
        this.repaymentedServiceFee = repaymentedServiceFee;
    }

    public String getRepaymentedOverdue() {
        return repaymentedOverdue;
    }

    public void setRepaymentedOverdue(String repaymentedOverdue) {
        this.repaymentedOverdue = repaymentedOverdue;
    }

    public String getRepaymentedServiceFeePenalty() {
        return repaymentedServiceFeePenalty;
    }

    public void setRepaymentedServiceFeePenalty(String repaymentedServiceFeePenalty) {
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
    }

    public String getRepaymentedPenalty() {
        return repaymentedPenalty;
    }

    public void setRepaymentedPenalty(String repaymentedPenalty) {
        this.repaymentedPenalty = repaymentedPenalty;
    }

    public String getBail() {
        return bail;
    }

    public void setBail(String bail) {
        this.bail = bail;
    }

    public String getBailPenalty() {
        return bailPenalty;
    }

    public void setBailPenalty(String bailPenalty) {
        this.bailPenalty = bailPenalty;
    }

    public String getRepaymentedBail() {
        return repaymentedBail;
    }

    public void setRepaymentedBail(String repaymentedBail) {
        this.repaymentedBail = repaymentedBail;
    }

    public String getRepaymentedBailPenalty() {
        return repaymentedBailPenalty;
    }

    public void setRepaymentedBailPenalty(String repaymentedBailPenalty) {
        this.repaymentedBailPenalty = repaymentedBailPenalty;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepaymentedAheadInterestPenalty() {
        return repaymentedAheadInterestPenalty;
    }

    public void setRepaymentedAheadInterestPenalty(String repaymentedAheadInterestPenalty) {
        this.repaymentedAheadInterestPenalty = repaymentedAheadInterestPenalty;
    }

    public String getRepaymentedAheadServiceFeePenalty() {
        return repaymentedAheadServiceFeePenalty;
    }

    public void setRepaymentedAheadServiceFeePenalty(String repaymentedAheadServiceFeePenalty) {
        this.repaymentedAheadServiceFeePenalty = repaymentedAheadServiceFeePenalty;
    }
}
