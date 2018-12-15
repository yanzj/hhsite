package com.vilio.plms.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 担保公司信息表
 */
public class PlmsGuaranteeCorporation implements Serializable{
    private int id;
    private String code;
    private String companyCode;
    private BigDecimal guaranteeRate;
    private BigDecimal guaranteeAmount;
    private BigDecimal guaranteePayedAmount;
    private Date guaranteePayedTime;

    private BigDecimal bailRate;
    private BigDecimal bailAmount;
    private BigDecimal bailPayedAmount;
    private Date bailPayedTime;
    private String contractCode;
    private String status;
    private Date createDate;
    private Date modifyDate;

    public PlmsGuaranteeCorporation() {
    }

    public PlmsGuaranteeCorporation(int id, String code, String companyCode, BigDecimal guaranteeRate, BigDecimal guaranteeAmount, BigDecimal guaranteePayedAmount, Date guaranteePayedTime, BigDecimal bailRate, BigDecimal bailAmount, BigDecimal bailPayedAmount, Date bailPayedTime, String contractCode, String status, Date createDate, Date modifyDate) {
        this.id = id;
        this.code = code;
        this.companyCode = companyCode;
        this.guaranteeRate = guaranteeRate;
        this.guaranteeAmount = guaranteeAmount;
        this.guaranteePayedAmount = guaranteePayedAmount;
        this.guaranteePayedTime = guaranteePayedTime;
        this.bailRate = bailRate;
        this.bailAmount = bailAmount;
        this.bailPayedAmount = bailPayedAmount;
        this.bailPayedTime = bailPayedTime;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
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

    public BigDecimal getGuaranteeRate() {
        return guaranteeRate;
    }

    public void setGuaranteeRate(BigDecimal guaranteeRate) {
        this.guaranteeRate = guaranteeRate;
    }

    public BigDecimal getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public BigDecimal getGuaranteePayedAmount() {
        return guaranteePayedAmount;
    }

    public void setGuaranteePayedAmount(BigDecimal guaranteePayedAmount) {
        this.guaranteePayedAmount = guaranteePayedAmount;
    }

    public Date getGuaranteePayedTime() {
        return guaranteePayedTime;
    }

    public void setGuaranteePayedTime(Date guaranteePayedTime) {
        this.guaranteePayedTime = guaranteePayedTime;
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

    public BigDecimal getBailPayedAmount() {
        return bailPayedAmount;
    }

    public void setBailPayedAmount(BigDecimal bailPayedAmount) {
        this.bailPayedAmount = bailPayedAmount;
    }

    public Date getBailPayedTime() {
        return bailPayedTime;
    }

    public void setBailPayedTime(Date bailPayedTime) {
        this.bailPayedTime = bailPayedTime;
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
}
