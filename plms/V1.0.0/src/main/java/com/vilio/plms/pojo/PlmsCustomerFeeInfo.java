package com.vilio.plms.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/8/15.
 */
public class PlmsCustomerFeeInfo implements Serializable{
    private int id;
    private String code;
    private BigDecimal serviceChargeRate;
    private BigDecimal serviceChargeAmount;
    private BigDecimal serviceChargeReceiveAmount;
    private Date serviceChargeReceiveTime;
    private BigDecimal guaranteeFeeRate;
    private BigDecimal guaranteeFeeAmount;
    private BigDecimal guaranteeFeeReceiveAmount;
    private Date guaranteeFeeReceiveTime;
    private BigDecimal bailRate;
    private BigDecimal bailAmount;
    private BigDecimal bailReceiveAmount;
    private Date bailReceiveTime;
    private BigDecimal performanceBondRate;
    private BigDecimal performanceBondAmount;
    private BigDecimal performanceBondReceiveAmount;
    private Date performanceBondReceiveTime;
    private String secondInsuranceName;
    private BigDecimal secondInsuranceFeeRate;
    private BigDecimal secondInsuranceFeeAmount;
    private BigDecimal secondInsuranceFeeReceiveAmount;
    private Date secondInsuranceFeeReceiveTime;
    private String contractCode;
    private String status;
    private Date createDate;
    private Date modifyDate;

    public PlmsCustomerFeeInfo() {
    }

    public PlmsCustomerFeeInfo(int id, String code, BigDecimal serviceChargeRate, BigDecimal serviceChargeAmount, BigDecimal serviceChargeReceiveAmount, Date serviceChargeReceiveTime, BigDecimal guaranteeFeeRate, BigDecimal guaranteeFeeAmount, BigDecimal guaranteeFeeReceiveAmount, Date guaranteeFeeReceiveTime, BigDecimal bailRate, BigDecimal bailAmount, BigDecimal bailReceiveAmount, Date bailReceiveTime, BigDecimal performanceBondRate, BigDecimal performanceBondAmount, BigDecimal performanceBondReceiveAmount, Date performanceBondReceiveTime, String secondInsuranceName, BigDecimal secondInsuranceFeeRate, BigDecimal secondInsuranceFeeAmount, BigDecimal secondInsuranceFeeReceiveAmount, Date secondInsuranceFeeReceiveTime, String contractCode, String status, Date createDate, Date modifyDate) {
        this.id = id;
        this.code = code;
        this.serviceChargeRate = serviceChargeRate;
        this.serviceChargeAmount = serviceChargeAmount;
        this.serviceChargeReceiveAmount = serviceChargeReceiveAmount;
        this.serviceChargeReceiveTime = serviceChargeReceiveTime;
        this.guaranteeFeeRate = guaranteeFeeRate;
        this.guaranteeFeeAmount = guaranteeFeeAmount;
        this.guaranteeFeeReceiveAmount = guaranteeFeeReceiveAmount;
        this.guaranteeFeeReceiveTime = guaranteeFeeReceiveTime;
        this.bailRate = bailRate;
        this.bailAmount = bailAmount;
        this.bailReceiveAmount = bailReceiveAmount;
        this.bailReceiveTime = bailReceiveTime;
        this.performanceBondRate = performanceBondRate;
        this.performanceBondAmount = performanceBondAmount;
        this.performanceBondReceiveAmount = performanceBondReceiveAmount;
        this.performanceBondReceiveTime = performanceBondReceiveTime;
        this.secondInsuranceName = secondInsuranceName;
        this.secondInsuranceFeeRate = secondInsuranceFeeRate;
        this.secondInsuranceFeeAmount = secondInsuranceFeeAmount;
        this.secondInsuranceFeeReceiveAmount = secondInsuranceFeeReceiveAmount;
        this.secondInsuranceFeeReceiveTime = secondInsuranceFeeReceiveTime;
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

    public BigDecimal getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(BigDecimal serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public BigDecimal getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    public void setServiceChargeAmount(BigDecimal serviceChargeAmount) {
        this.serviceChargeAmount = serviceChargeAmount;
    }

    public BigDecimal getServiceChargeReceiveAmount() {
        return serviceChargeReceiveAmount;
    }

    public void setServiceChargeReceiveAmount(BigDecimal serviceChargeReceiveAmount) {
        this.serviceChargeReceiveAmount = serviceChargeReceiveAmount;
    }

    public Date getServiceChargeReceiveTime() {
        return serviceChargeReceiveTime;
    }

    public void setServiceChargeReceiveTime(Date serviceChargeReceiveTime) {
        this.serviceChargeReceiveTime = serviceChargeReceiveTime;
    }

    public BigDecimal getGuaranteeFeeRate() {
        return guaranteeFeeRate;
    }

    public void setGuaranteeFeeRate(BigDecimal guaranteeFeeRate) {
        this.guaranteeFeeRate = guaranteeFeeRate;
    }

    public BigDecimal getGuaranteeFeeAmount() {
        return guaranteeFeeAmount;
    }

    public void setGuaranteeFeeAmount(BigDecimal guaranteeFeeAmount) {
        this.guaranteeFeeAmount = guaranteeFeeAmount;
    }

    public BigDecimal getGuaranteeFeeReceiveAmount() {
        return guaranteeFeeReceiveAmount;
    }

    public void setGuaranteeFeeReceiveAmount(BigDecimal guaranteeFeeReceiveAmount) {
        this.guaranteeFeeReceiveAmount = guaranteeFeeReceiveAmount;
    }

    public Date getGuaranteeFeeReceiveTime() {
        return guaranteeFeeReceiveTime;
    }

    public void setGuaranteeFeeReceiveTime(Date guaranteeFeeReceiveTime) {
        this.guaranteeFeeReceiveTime = guaranteeFeeReceiveTime;
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

    public BigDecimal getBailReceiveAmount() {
        return bailReceiveAmount;
    }

    public void setBailReceiveAmount(BigDecimal bailReceiveAmount) {
        this.bailReceiveAmount = bailReceiveAmount;
    }

    public Date getBailReceiveTime() {
        return bailReceiveTime;
    }

    public void setBailReceiveTime(Date bailReceiveTime) {
        this.bailReceiveTime = bailReceiveTime;
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

    public BigDecimal getPerformanceBondReceiveAmount() {
        return performanceBondReceiveAmount;
    }

    public void setPerformanceBondReceiveAmount(BigDecimal performanceBondReceiveAmount) {
        this.performanceBondReceiveAmount = performanceBondReceiveAmount;
    }

    public Date getPerformanceBondReceiveTime() {
        return performanceBondReceiveTime;
    }

    public void setPerformanceBondReceiveTime(Date performanceBondReceiveTime) {
        this.performanceBondReceiveTime = performanceBondReceiveTime;
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

    public BigDecimal getSecondInsuranceFeeReceiveAmount() {
        return secondInsuranceFeeReceiveAmount;
    }

    public void setSecondInsuranceFeeReceiveAmount(BigDecimal secondInsuranceFeeReceiveAmount) {
        this.secondInsuranceFeeReceiveAmount = secondInsuranceFeeReceiveAmount;
    }

    public Date getSecondInsuranceFeeReceiveTime() {
        return secondInsuranceFeeReceiveTime;
    }

    public void setSecondInsuranceFeeReceiveTime(Date secondInsuranceFeeReceiveTime) {
        this.secondInsuranceFeeReceiveTime = secondInsuranceFeeReceiveTime;
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
