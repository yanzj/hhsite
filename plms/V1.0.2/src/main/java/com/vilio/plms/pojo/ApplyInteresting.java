package com.vilio.plms.pojo;

/**
 * 类名： ApplyInteresting<br>
 * 功能：进件利息信息表<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月23日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class ApplyInteresting {

    private Integer id;
    private String code;
    private String annualizedInterest;
    private String principalOverInterest;
    private String interestOverInterest;
    private String defaultInterestRate;
    private String contractCode;
    private String createDate;
    private String modifyDate;
    private String status;
    private String isPrincipalInstead;
    private String isInterestInstead;
    private String isFullRepurchase;
    private String serviceFeeInterestRate;
    private String fundSideCode;
    private String honghuoCode;
    private String honghuoBailAccountCode;

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

    public String getAnnualizedInterest() {
        return annualizedInterest;
    }

    public void setAnnualizedInterest(String annualizedInterest) {
        this.annualizedInterest = annualizedInterest;
    }

    public String getPrincipalOverInterest() {
        return principalOverInterest;
    }

    public void setPrincipalOverInterest(String principalOverInterest) {
        this.principalOverInterest = principalOverInterest;
    }

    public String getInterestOverInterest() {
        return interestOverInterest;
    }

    public void setInterestOverInterest(String interestOverInterest) {
        this.interestOverInterest = interestOverInterest;
    }

    public String getDefaultInterestRate() {
        return defaultInterestRate;
    }

    public void setDefaultInterestRate(String defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsPrincipalInstead() {
        return isPrincipalInstead;
    }

    public void setIsPrincipalInstead(String isPrincipalInstead) {
        this.isPrincipalInstead = isPrincipalInstead;
    }

    public String getIsInterestInstead() {
        return isInterestInstead;
    }

    public void setIsInterestInstead(String isInterestInstead) {
        this.isInterestInstead = isInterestInstead;
    }

    public String getIsFullRepurchase() {
        return isFullRepurchase;
    }

    public void setIsFullRepurchase(String isFullRepurchase) {
        this.isFullRepurchase = isFullRepurchase;
    }

    public String getServiceFeeInterestRate() {
        return serviceFeeInterestRate;
    }

    public void setServiceFeeInterestRate(String serviceFeeInterestRate) {
        this.serviceFeeInterestRate = serviceFeeInterestRate;
    }


    public String getFundSideCode() {
        return fundSideCode;
    }

    public void setFundSideCode(String fundSideCode) {
        this.fundSideCode = fundSideCode;
    }

    public String getHonghuoCode() {
        return honghuoCode;
    }

    public void setHonghuoCode(String honghuoCode) {
        this.honghuoCode = honghuoCode;
    }

    public String getHonghuoBailAccountCode() {
        return honghuoBailAccountCode;
    }

    public void setHonghuoBailAccountCode(String honghuoBailAccountCode) {
        this.honghuoBailAccountCode = honghuoBailAccountCode;
    }

    public ApplyInteresting() {
    }

    public ApplyInteresting(Integer id, String code, String annualizedInterest, String principalOverInterest, String interestOverInterest, String defaultInterestRate, String contractCode, String createDate, String modifyDate, String status, String isPrincipalInstead, String isInterestInstead, String isFullRepurchase, String serviceFeeInterestRate, String fundSideCode, String honghuoCode, String honghuoBailAccountCode) {
        this.id = id;
        this.code = code;
        this.annualizedInterest = annualizedInterest;
        this.principalOverInterest = principalOverInterest;
        this.interestOverInterest = interestOverInterest;
        this.defaultInterestRate = defaultInterestRate;
        this.contractCode = contractCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.status = status;
        this.isPrincipalInstead = isPrincipalInstead;
        this.isInterestInstead = isInterestInstead;
        this.isFullRepurchase = isFullRepurchase;
        this.serviceFeeInterestRate = serviceFeeInterestRate;
        this.fundSideCode = fundSideCode;
        this.honghuoCode = honghuoCode;
        this.honghuoBailAccountCode = honghuoBailAccountCode;
    }
}
