package com.vilio.ppms.pojo.common;

import java.util.Date;

public class CutomCardNo {
    private Long id;

    private String code;

    private String cardNo;

    private String mobileNo;

    private String customCode;

    private String cardType;

    private String expYear;

    private String expMonth;

    private String cvv;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public CutomCardNo(Long id, String code, String cardNo, String mobileNo, String customCode, String cardType, String expYear, String expMonth, String cvv, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.cardNo = cardNo;
        this.mobileNo = mobileNo;
        this.customCode = customCode;
        this.cardType = cardType;
        this.expYear = expYear;
        this.expMonth = expMonth;
        this.cvv = cvv;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public CutomCardNo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode == null ? null : customCode.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear == null ? null : expYear.trim();
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth == null ? null : expMonth.trim();
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv == null ? null : cvv.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }
}