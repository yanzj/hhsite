package com.vilio.plms.pojo;

import java.io.Serializable;

/**
 * 类名： FundSide<br>
 * 功能：资方信息实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class FundSide implements Serializable{
    private Integer id;
    private String code;
    private String companyCode;
    private String lenders;
    private String serviceChargeRate;
    private String serviceChargeAmount;
    private String serviceChargePaidAmount;
    private String serviceChargePaidTime;
    private String bailRate;
    private String bailAmount;
    private String bailPayedAmount;
    private String bailPayedTime;
    private String receiptFeeRate;
    private String receiptFeeAmount;
    private String receiptFeePaidAmount;
    private String receiptFeePaidTime;
    private String yearPeriod;
    private String defaultInterestRate;
    private String penaltyPeriod;
    private String overInterest;
    private String contractInterest;
    private String actualInterest;
    private String receiveInterestMethod;
    private String receiveInterestDate;
    private String mortgageInterestPeriod;
    private String interestGraceDate;
    private String contractCode;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLenders() {
        return lenders;
    }

    public void setLenders(String lenders) {
        this.lenders = lenders;
    }

    public String getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(String serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public String getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(String serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public String getServiceChargePaidAmount() {
        return serviceChargePaidAmount;
    }

    public void setServiceChargePaidAmount(String serviceChargePaidAmount) {
        this.serviceChargePaidAmount = serviceChargePaidAmount;
    }

    public String getServiceChargePaidTime() {
        return serviceChargePaidTime;
    }

    public void setServiceChargePaidTime(String serviceChargePaidTime) {
        this.serviceChargePaidTime = serviceChargePaidTime;
    }

    public String getBailRate() {
        return bailRate;
    }

    public void setBailRate(String bailRate) {
        this.bailRate = bailRate;
    }

    public String getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(String bailAmount) {
        this.bailAmount = bailAmount;
    }

    public String getBailPayedAmount() {
        return bailPayedAmount;
    }

    public void setBailPayedAmount(String bailPayedAmount) {
        this.bailPayedAmount = bailPayedAmount;
    }

    public String getBailPayedTime() {
        return bailPayedTime;
    }

    public void setBailPayedTime(String bailPayedTime) {
        this.bailPayedTime = bailPayedTime;
    }

    public String getReceiptFeeRate() {
        return receiptFeeRate;
    }

    public void setReceiptFeeRate(String receiptFeeRate) {
        this.receiptFeeRate = receiptFeeRate;
    }

    public String getReceiptFeeAmount() {
        return receiptFeeAmount;
    }

    public void setReceiptFeeAmount(String receiptFeeAmount) {
        this.receiptFeeAmount = receiptFeeAmount;
    }

    public String getReceiptFeePaidAmount() {
        return receiptFeePaidAmount;
    }

    public void setReceiptFeePaidAmount(String receiptFeePaidAmount) {
        this.receiptFeePaidAmount = receiptFeePaidAmount;
    }

    public String getReceiptFeePaidTime() {
        return receiptFeePaidTime;
    }

    public void setReceiptFeePaidTime(String receiptFeePaidTime) {
        this.receiptFeePaidTime = receiptFeePaidTime;
    }

    public String getYearPeriod() {
        return yearPeriod;
    }

    public void setYearPeriod(String yearPeriod) {
        this.yearPeriod = yearPeriod;
    }

    public String getDefaultInterestRate() {
        return defaultInterestRate;
    }

    public void setDefaultInterestRate(String defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public String getPenaltyPeriod() {
        return penaltyPeriod;
    }

    public void setPenaltyPeriod(String penaltyPeriod) {
        this.penaltyPeriod = penaltyPeriod;
    }

    public String getOverInterest() {
        return overInterest;
    }

    public void setOverInterest(String overInterest) {
        this.overInterest = overInterest;
    }

    public String getContractInterest() {
        return contractInterest;
    }

    public void setContractInterest(String contractInterest) {
        this.contractInterest = contractInterest;
    }

    public String getActualInterest() {
        return actualInterest;
    }

    public void setActualInterest(String actualInterest) {
        this.actualInterest = actualInterest;
    }

    public String getReceiveInterestMethod() {
        return receiveInterestMethod;
    }

    public void setReceiveInterestMethod(String receiveInterestMethod) {
        this.receiveInterestMethod = receiveInterestMethod;
    }

    public String getReceiveInterestDate() {
        return receiveInterestDate;
    }

    public void setReceiveInterestDate(String receiveInterestDate) {
        this.receiveInterestDate = receiveInterestDate;
    }

    public String getMortgageInterestPeriod() {
        return mortgageInterestPeriod;
    }

    public void setMortgageInterestPeriod(String mortgageInterestPeriod) {
        this.mortgageInterestPeriod = mortgageInterestPeriod;
    }

    public String getInterestGraceDate() {
        return interestGraceDate;
    }

    public void setInterestGraceDate(String interestGraceDate) {
        this.interestGraceDate = interestGraceDate;
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

    public FundSide() {
    }

    public FundSide(Integer id, String code, String companyCode, String lenders, String serviceChargeRate, String serviceChargeAmount, String serviceChargePaidAmount, String serviceChargePaidTime, String bailRate, String bailAmount, String bailPayedAmount, String bailPayedTime, String receiptFeeRate, String receiptFeeAmount, String receiptFeePaidAmount, String receiptFeePaidTime, String yearPeriod, String defaultInterestRate, String penaltyPeriod, String overInterest, String contractInterest, String actualInterest, String receiveInterestMethod, String receiveInterestDate, String mortgageInterestPeriod, String interestGraceDate, String contractCode, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.companyCode = companyCode;
        this.lenders = lenders;
        this.serviceChargeRate = serviceChargeRate;
        this.serviceChargeAmount = serviceChargeAmount;
        this.serviceChargePaidAmount = serviceChargePaidAmount;
        this.serviceChargePaidTime = serviceChargePaidTime;
        this.bailRate = bailRate;
        this.bailAmount = bailAmount;
        this.bailPayedAmount = bailPayedAmount;
        this.bailPayedTime = bailPayedTime;
        this.receiptFeeRate = receiptFeeRate;
        this.receiptFeeAmount = receiptFeeAmount;
        this.receiptFeePaidAmount = receiptFeePaidAmount;
        this.receiptFeePaidTime = receiptFeePaidTime;
        this.yearPeriod = yearPeriod;
        this.defaultInterestRate = defaultInterestRate;
        this.penaltyPeriod = penaltyPeriod;
        this.overInterest = overInterest;
        this.contractInterest = contractInterest;
        this.actualInterest = actualInterest;
        this.receiveInterestMethod = receiveInterestMethod;
        this.receiveInterestDate = receiveInterestDate;
        this.mortgageInterestPeriod = mortgageInterestPeriod;
        this.interestGraceDate = interestGraceDate;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
