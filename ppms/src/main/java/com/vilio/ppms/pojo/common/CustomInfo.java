package com.vilio.ppms.pojo.common;

import java.util.Date;

public class CustomInfo {
    private Long id;

    private String code;

    private String customName;

    private String certType;

    private String certNo;

    private String firstCardCode;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public CustomInfo(Long id, String code, String customName, String certType, String certNo, String firstCardCode, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.customName = customName;
        this.certType = certType;
        this.certNo = certNo;
        this.firstCardCode = firstCardCode;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public CustomInfo() {
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

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getFirstCardCode() {
        return firstCardCode;
    }

    public void setFirstCardCode(String firstCardCode) {
        this.firstCardCode = firstCardCode == null ? null : firstCardCode.trim();
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