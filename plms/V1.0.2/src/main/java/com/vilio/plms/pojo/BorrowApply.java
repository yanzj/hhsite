package com.vilio.plms.pojo;

/**
 * 类名： BorrowApply<br>
 * 功能：借款申请实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BorrowApply {

    private Integer id;
    private String code;
    private String amount;
    private String applyTime;
    private String applyStatus;
    private String businessCode;
    private String contractCode;
    private String applyCode;
    private String createDate;
    private String modifyDate;
    private String paidAmount;
    private String paidTime;
    private String borrowPeriod;
    private String comments;
    private String borrowEndDate;

    public String getBorrowEndDate() {
        return borrowEndDate;
    }

    public void setBorrowEndDate(String borrowEndDate) {
        this.borrowEndDate = borrowEndDate;
    }

    //用户信息code
    private String customerCode;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
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

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public BorrowApply() {
    }

    public BorrowApply(Integer id, String code, String amount, String applyTime, String applyStatus, String businessCode, String contractCode, String applyCode, String createDate, String modifyDate, String paidAmount, String paidTime, String borrowPeriod, String comments, String customerCode, String borrowEndDate) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.applyTime = applyTime;
        this.applyStatus = applyStatus;
        this.businessCode = businessCode;
        this.contractCode = contractCode;
        this.applyCode = applyCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.paidAmount = paidAmount;
        this.paidTime = paidTime;
        this.borrowPeriod = borrowPeriod;
        this.comments = comments;
        this.customerCode = customerCode;
        this.borrowEndDate = borrowEndDate;
    }
}
