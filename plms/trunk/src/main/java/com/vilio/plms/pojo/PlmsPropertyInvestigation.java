package com.vilio.plms.pojo;

import java.util.Date;

/**
 * 产调信息表
 */
public class PlmsPropertyInvestigation {
    private int id;
    private String code;
    //产调时间
    private Date investigationTime;
    //在办案件信息
    private String inCaseInformation;
    //抵押物编码
    private String houseCode;
    //状态
    private String status;

    private Date createDate;
    private Date modifyDate;

    public PlmsPropertyInvestigation() {
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

    public Date getInvestigationTime() {
        return investigationTime;
    }

    public void setInvestigationTime(Date investigationTime) {
        this.investigationTime = investigationTime;
    }

    public String getInCaseInformation() {
        return inCaseInformation;
    }

    public void setInCaseInformation(String inCaseInformation) {
        this.inCaseInformation = inCaseInformation;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
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
