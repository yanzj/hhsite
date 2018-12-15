package com.vilio.pcfs.pojo;

/**
 * Created by dell on 2017/7/4.
 */
public class Verify {
    private Integer id;
    private String verifySerno;
    private String mobileNo;
    private String verifyCode;
    private String verifyType;
    private String effectiveTime;
    private String status;
    private String signNo;
    private String templateNo;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerifySerno() {
        return verifySerno;
    }

    public void setVerifySerno(String verifySerno) {
        this.verifySerno = verifySerno;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Verify() {
    }

    public Verify(Integer id, String verifySerno, String mobileNo, String verifyCode, String verifyType, String effectiveTime, String status, String signNo, String templateNo, String createTime) {
        this.id = id;
        this.verifySerno = verifySerno;
        this.mobileNo = mobileNo;
        this.verifyCode = verifyCode;
        this.verifyType = verifyType;
        this.effectiveTime = effectiveTime;
        this.status = status;
        this.signNo = signNo;
        this.templateNo = templateNo;
        this.createTime = createTime;
    }
}
