package com.vilio.plms.pojo;

/**
 * 类名： AccountDetail<br>
 * 功能：账户明细实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class AccountDetail {

    private Integer id;
    private String code;
    private String totalQuota;
    private String remainingQuota;
    private String principal;
    private String interest;
    private String serviceFee;
    private String overdue;
    private String serviceFeePenalty;
    private String accountCode;
    private String creditEndDate;
    private String contractCode;
    private String status;
    private String createDate;
    private String modifyDate;
    private String fundSideBalance;
    private String honghuoBalance;
    private String repaymentedPrincipal;
    private String repaymentedInterest;
    private String repaymentedServiceFee;
    private String repaymentedOverdue;
    private String repaymentedServiceFeePenalty;
    private String repaymentedPenalty;
    private String confirmed;
    private String firstAmount;
    private String bail;
    private String bailPenalty;
    private String repaymentedBail;
    private String repaymentedBailPenalty;
    private String repaymentedAheadInterestPenalty;
    private String repaymentedAheadServiceFeePenalty;
    private String honghuoBailAccountBalance;
    public AccountDetail() {
    }

    public AccountDetail(Integer id, String code, String totalQuota, String remainingQuota, String principal, String interest, String serviceFee, String overdue, String serviceFeePenalty, String accountCode, String creditEndDate, String contractCode, String status, String createDate, String modifyDate, String fundSideBalance, String honghuoBalance, String repaymentedPrincipal, String repaymentedInterest, String repaymentedServiceFee, String repaymentedOverdue, String repaymentedServiceFeePenalty, String repaymentedPenalty, String confirmed, String firstAmount, String bail, String bailPenalty, String repaymentedBail, String repaymentedBailPenalty, String repaymentedAheadInterestPenalty, String repaymentedAheadServiceFeePenalty, String honghuoBailAccountBalance) {
        this.id = id;
        this.code = code;
        this.totalQuota = totalQuota;
        this.remainingQuota = remainingQuota;
        this.principal = principal;
        this.interest = interest;
        this.serviceFee = serviceFee;
        this.overdue = overdue;
        this.serviceFeePenalty = serviceFeePenalty;
        this.accountCode = accountCode;
        this.creditEndDate = creditEndDate;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.fundSideBalance = fundSideBalance;
        this.honghuoBalance = honghuoBalance;
        this.repaymentedPrincipal = repaymentedPrincipal;
        this.repaymentedInterest = repaymentedInterest;
        this.repaymentedServiceFee = repaymentedServiceFee;
        this.repaymentedOverdue = repaymentedOverdue;
        this.repaymentedServiceFeePenalty = repaymentedServiceFeePenalty;
        this.repaymentedPenalty = repaymentedPenalty;
        this.confirmed = confirmed;
        this.firstAmount = firstAmount;
        this.bail = bail;
        this.bailPenalty = bailPenalty;
        this.repaymentedBail = repaymentedBail;
        this.repaymentedBailPenalty = repaymentedBailPenalty;
        this.repaymentedAheadInterestPenalty = repaymentedAheadInterestPenalty;
        this.repaymentedAheadServiceFeePenalty = repaymentedAheadServiceFeePenalty;
        this.honghuoBailAccountBalance = honghuoBailAccountBalance;
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

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCreditEndDate() {
        return creditEndDate;
    }

    public void setCreditEndDate(String creditEndDate) {
        this.creditEndDate = creditEndDate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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

    public String getFundSideBalance() {
        return fundSideBalance;
    }

    public void setFundSideBalance(String fundSideBalance) {
        this.fundSideBalance = fundSideBalance;
    }

    public String getHonghuoBalance() {
        return honghuoBalance;
    }

    public void setHonghuoBalance(String honghuoBalance) {
        this.honghuoBalance = honghuoBalance;
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

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(String firstAmount) {
        this.firstAmount = firstAmount;
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

    public String getHonghuoBailAccountBalance() {
        return honghuoBailAccountBalance;
    }

    public void setHonghuoBailAccountBalance(String honghuoBailAccountBalance) {
        this.honghuoBailAccountBalance = honghuoBailAccountBalance;
    }

}
