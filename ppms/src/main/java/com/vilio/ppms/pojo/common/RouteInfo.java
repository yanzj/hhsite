package com.vilio.ppms.pojo.common;

import java.util.Date;

public class RouteInfo {
    private Long id;

    private String code;

    private String englishAbbr;

    private String routeName;

    private String pubKeyFile;

    private String pubKey;

    private String batchFlag;

    private String cutTime;

    private String signCheckFlag;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    private String updator;

    public RouteInfo(Long id, String code, String englishAbbr, String routeName, String pubKeyFile, String pubKey, String batchFlag, String cutTime, String signCheckFlag, String status, Date createTime, Date updateTime, String remark1, String updator) {
        this.id = id;
        this.code = code;
        this.englishAbbr = englishAbbr;
        this.routeName = routeName;
        this.pubKeyFile = pubKeyFile;
        this.pubKey = pubKey;
        this.batchFlag = batchFlag;
        this.cutTime = cutTime;
        this.signCheckFlag = signCheckFlag;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
        this.updator = updator;
    }

    public RouteInfo() {
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

    public String getEnglishAbbr() {
        return englishAbbr;
    }

    public void setEnglishAbbr(String englishAbbr) {
        this.englishAbbr = englishAbbr == null ? null : englishAbbr.trim();
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName == null ? null : routeName.trim();
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

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag == null ? null : batchFlag.trim();
    }

    public String getCutTime() {
        return cutTime;
    }

    public void setCutTime(String cutTime) {
        this.cutTime = cutTime == null ? null : cutTime.trim();
    }

    public String getSignCheckFlag() {
        return signCheckFlag;
    }

    public void setSignCheckFlag(String signCheckFlag) {
        this.signCheckFlag = signCheckFlag == null ? null : signCheckFlag.trim();
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

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }
}