package com.vilio.ppms.pojo.common;

import java.util.Date;

public class MerchantInfo {
    private Long id;

    private String code;

    private String merchantCode;

    private String merchantName;

    private String routeCode;

    private String pubKeyFile;

    private String pubKey;

    private String privateKeyFile;

    private String privateKey;

    private String umGroupCode;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public MerchantInfo(Long id, String code, String merchantCode, String merchantName, String routeCode, String pubKeyFile, String pubKey, String privateKeyFile, String privateKey, String umGroupCode, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.merchantCode = merchantCode;
        this.merchantName = merchantName;
        this.routeCode = routeCode;
        this.pubKeyFile = pubKeyFile;
        this.pubKey = pubKey;
        this.privateKeyFile = privateKeyFile;
        this.privateKey = privateKey;
        this.umGroupCode = umGroupCode;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public MerchantInfo() {
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode == null ? null : routeCode.trim();
    }

    public String getPubKeyFile() {
        return pubKeyFile;
    }

    public void setPubKeyFile(String pubKeyFile) {
        this.pubKeyFile = pubKeyFile == null ? null : pubKeyFile.trim();
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey == null ? null : pubKey.trim();
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }

    public void setPrivateKeyFile(String privateKeyFile) {
        this.privateKeyFile = privateKeyFile == null ? null : privateKeyFile.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    public String getUmGroupCode() {
        return umGroupCode;
    }

    public void setUmGroupCode(String umGroupCode) {
        this.umGroupCode = umGroupCode == null ? null : umGroupCode.trim();
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