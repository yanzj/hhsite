package com.vilio.plms.pojo;

/**
 * 类名： Iou<br>
 * 功能：借款借据实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Iou {
    private Integer id;
    private String code;
    private String loanAmount;
    private String signDate;
    private String paidDate;
    private String bussinessCode;
    private String customer;
    private String accountNo;
    private String accountName;
    private String currency;
    private String annualInteresting;
    private String overdueInteresting;
    private String contractNo;
    private String interestStartDate;
    private String interestEndDate;
    private String repaymentMethod;
    private String interestCycle;
    private String interestingAdjustMethod;
    private String promise;
    private String elecSign;
    private String status;
    private String createDate;
    private String modifyDate;

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

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getBussinessCode() {
        return bussinessCode;
    }

    public void setBussinessCode(String bussinessCode) {
        this.bussinessCode = bussinessCode;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAnnualInteresting() {
        return annualInteresting;
    }

    public void setAnnualInteresting(String annualInteresting) {
        this.annualInteresting = annualInteresting;
    }

    public String getOverdueInteresting() {
        return overdueInteresting;
    }

    public void setOverdueInteresting(String overdueInteresting) {
        this.overdueInteresting = overdueInteresting;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getInterestStartDate() {
        return interestStartDate;
    }

    public void setInterestStartDate(String interestStartDate) {
        this.interestStartDate = interestStartDate;
    }

    public String getInterestEndDate() {
        return interestEndDate;
    }

    public void setInterestEndDate(String interestEndDate) {
        this.interestEndDate = interestEndDate;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(String interestCycle) {
        this.interestCycle = interestCycle;
    }

    public String getInterestingAdjustMethod() {
        return interestingAdjustMethod;
    }

    public void setInterestingAdjustMethod(String interestingAdjustMethod) {
        this.interestingAdjustMethod = interestingAdjustMethod;
    }

    public String getPromise() {
        return promise;
    }

    public void setPromise(String promise) {
        this.promise = promise;
    }

    public String getElecSign() {
        return elecSign;
    }

    public void setElecSign(String elecSign) {
        this.elecSign = elecSign;
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

    public Iou() {
    }

    public Iou(Integer id, String code, String loanAmount, String signDate, String paidDate, String bussinessCode, String customer, String accountNo, String accountName, String currency, String annualInteresting, String overdueInteresting, String contractNo, String interestStartDate, String interestEndDate, String repaymentMethod, String interestCycle, String interestingAdjustMethod, String promise, String elecSign, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.loanAmount = loanAmount;
        this.signDate = signDate;
        this.paidDate = paidDate;
        this.bussinessCode = bussinessCode;
        this.customer = customer;
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.currency = currency;
        this.annualInteresting = annualInteresting;
        this.overdueInteresting = overdueInteresting;
        this.contractNo = contractNo;
        this.interestStartDate = interestStartDate;
        this.interestEndDate = interestEndDate;
        this.repaymentMethod = repaymentMethod;
        this.interestCycle = interestCycle;
        this.interestingAdjustMethod = interestingAdjustMethod;
        this.promise = promise;
        this.elecSign = elecSign;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
