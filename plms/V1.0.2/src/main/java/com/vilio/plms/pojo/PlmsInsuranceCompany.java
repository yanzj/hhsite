package com.vilio.plms.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/8/14.
 */
public class PlmsInsuranceCompany implements Serializable{
    private int id;
    private String code;
    private String companyCode;
    private BigDecimal bailRate;
    private BigDecimal bailAmount;
    private BigDecimal bailPayedPaidAmount;
    private Date bailPayedTime;
    private String bailPayedMethod;
    private BigDecimal performanceBondRate;
    private BigDecimal performanceBondAmount;
    private BigDecimal performanceBondPaidAmount;
    private Date performanceBondPayedTime;
    private String performanceBondPayedMethod;

    private String secondInsuranceName;
    private BigDecimal secondInsuranceFeeRate;
    private BigDecimal secondInsuranceFeeAmount;
    private BigDecimal secondInsurancePaidAmount;
    private Date secondInsurancePayedTime;
    private String secondInsurancePayedMethod;
    private String contractCode;
    private String status;
    private Date createDate;
    private Date modifyDate;

    private BigDecimal bailPayedAmount;
    private BigDecimal performanceBondPayedAmount;
    private BigDecimal secondInsurancePayedAmount;

    public PlmsInsuranceCompany() {
    }

    public PlmsInsuranceCompany(int id, String code, String companyCode, BigDecimal bailRate, BigDecimal bailAmount, BigDecimal bailPayedPaidAmount, Date bailPayedTime, String bailPayedMethod, BigDecimal performanceBondRate, BigDecimal performanceBondAmount, BigDecimal performanceBondPaidAmount, Date performanceBondPayedTime, String performanceBondPayedMethod, String secondInsuranceName, BigDecimal secondInsuranceFeeRate, BigDecimal secondInsuranceFeeAmount, BigDecimal secondInsurancePaidAmount, Date secondInsurancePayedTime, String secondInsurancePayedMethod, String contractCode, String status, Date createDate, Date modifyDate, BigDecimal bailPayedAmount) {
        this.id = id;
        this.code = code;
        this.companyCode = companyCode;
        this.bailRate = bailRate;
        this.bailAmount = bailAmount;
        this.bailPayedPaidAmount = bailPayedPaidAmount;
        this.bailPayedTime = bailPayedTime;
        this.bailPayedMethod = bailPayedMethod;
        this.performanceBondRate = performanceBondRate;
        this.performanceBondAmount = performanceBondAmount;
        this.performanceBondPaidAmount = performanceBondPaidAmount;
        this.performanceBondPayedTime = performanceBondPayedTime;
        this.performanceBondPayedMethod = performanceBondPayedMethod;
        this.secondInsuranceName = secondInsuranceName;
        this.secondInsuranceFeeRate = secondInsuranceFeeRate;
        this.secondInsuranceFeeAmount = secondInsuranceFeeAmount;
        this.secondInsurancePaidAmount = secondInsurancePaidAmount;
        this.secondInsurancePayedTime = secondInsurancePayedTime;
        this.secondInsurancePayedMethod = secondInsurancePayedMethod;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.bailPayedAmount = bailPayedAmount;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public BigDecimal getBailRate() {
        return bailRate;
    }

    public void setBailRate(BigDecimal bailRate) {
        this.bailRate = bailRate;
    }

    public BigDecimal getBailAmount() {
        return bailAmount;
    }

    public void setBailAmount(BigDecimal bailAmount) {
        this.bailAmount = bailAmount;
    }

    public BigDecimal getBailPayedPaidAmount() {
        return bailPayedPaidAmount;
    }

    public void setBailPayedPaidAmount(BigDecimal bailPayedPaidAmount) {
        this.bailPayedPaidAmount = bailPayedPaidAmount;
    }

    public Date getBailPayedTime() {
        return bailPayedTime;
    }

    public void setBailPayedTime(Date bailPayedTime) {
        this.bailPayedTime = bailPayedTime;
    }

    public String getBailPayedMethod() {
        return bailPayedMethod;
    }

    public void setBailPayedMethod(String bailPayedMethod) {
        this.bailPayedMethod = bailPayedMethod;
    }

    public BigDecimal getPerformanceBondRate() {
        return performanceBondRate;
    }

    public void setPerformanceBondRate(BigDecimal performanceBondRate) {
        this.performanceBondRate = performanceBondRate;
    }

    public BigDecimal getPerformanceBondAmount() {
        return performanceBondAmount;
    }

    public void setPerformanceBondAmount(BigDecimal performanceBondAmount) {
        this.performanceBondAmount = performanceBondAmount;
    }

    public BigDecimal getPerformanceBondPaidAmount() {
        return performanceBondPaidAmount;
    }

    public void setPerformanceBondPaidAmount(BigDecimal performanceBondPaidAmount) {
        this.performanceBondPaidAmount = performanceBondPaidAmount;
    }

    public Date getPerformanceBondPayedTime() {
        return performanceBondPayedTime;
    }

    public void setPerformanceBondPayedTime(Date performanceBondPayedTime) {
        this.performanceBondPayedTime = performanceBondPayedTime;
    }

    public String getPerformanceBondPayedMethod() {
        return performanceBondPayedMethod;
    }

    public void setPerformanceBondPayedMethod(String performanceBondPayedMethod) {
        this.performanceBondPayedMethod = performanceBondPayedMethod;
    }

    public String getSecondInsuranceName() {
        return secondInsuranceName;
    }

    public void setSecondInsuranceName(String secondInsuranceName) {
        this.secondInsuranceName = secondInsuranceName;
    }

    public BigDecimal getSecondInsuranceFeeRate() {
        return secondInsuranceFeeRate;
    }

    public void setSecondInsuranceFeeRate(BigDecimal secondInsuranceFeeRate) {
        this.secondInsuranceFeeRate = secondInsuranceFeeRate;
    }

    public BigDecimal getSecondInsuranceFeeAmount() {
        return secondInsuranceFeeAmount;
    }

    public void setSecondInsuranceFeeAmount(BigDecimal secondInsuranceFeeAmount) {
        this.secondInsuranceFeeAmount = secondInsuranceFeeAmount;
    }

    public BigDecimal getSecondInsurancePaidAmount() {
        return secondInsurancePaidAmount;
    }

    public void setSecondInsurancePaidAmount(BigDecimal secondInsurancePaidAmount) {
        this.secondInsurancePaidAmount = secondInsurancePaidAmount;
    }

    public Date getSecondInsurancePayedTime() {
        return secondInsurancePayedTime;
    }

    public void setSecondInsurancePayedTime(Date secondInsurancePayedTime) {
        this.secondInsurancePayedTime = secondInsurancePayedTime;
    }

    public String getSecondInsurancePayedMethod() {
        return secondInsurancePayedMethod;
    }

    public void setSecondInsurancePayedMethod(String secondInsurancePayedMethod) {
        this.secondInsurancePayedMethod = secondInsurancePayedMethod;
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

    public BigDecimal getBailPayedAmount() {
        return bailPayedAmount;
    }

    public void setBailPayedAmount(BigDecimal bailPayedAmount) {
        this.bailPayedAmount = bailPayedAmount;
    }

    public BigDecimal getPerformanceBondPayedAmount() {
        return performanceBondPayedAmount;
    }

    public void setPerformanceBondPayedAmount(BigDecimal performanceBondPayedAmount) {
        this.performanceBondPayedAmount = performanceBondPayedAmount;
    }

    public BigDecimal getSecondInsurancePayedAmount() {
        return secondInsurancePayedAmount;
    }

    public void setSecondInsurancePayedAmount(BigDecimal secondInsurancePayedAmount) {
        this.secondInsurancePayedAmount = secondInsurancePayedAmount;
    }
}
