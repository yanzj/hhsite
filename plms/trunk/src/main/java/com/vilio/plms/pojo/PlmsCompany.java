package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司表
 */
public class PlmsCompany implements Serializable{
    private int id;
    private String code;
    private String companyName;
    private String abbrName;
    private String companyType;
    private String status;
    private String bmsCode;
    private Date createDate;
    private Date modifyDate;

    public PlmsCompany() {
    }

    public PlmsCompany(int id, String code, String companyName, String abbrName, String companyType, String status, String bmsCode, Date createDate, Date modifyDate) {
        this.id = id;
        this.code = code;
        this.companyName = companyName;
        this.abbrName = abbrName;
        this.companyType = companyType;
        this.status = status;
        this.bmsCode = bmsCode;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBmsCode() {
        return bmsCode;
    }

    public void setBmsCode(String bmsCode) {
        this.bmsCode = bmsCode;
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
