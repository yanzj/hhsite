package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 抵押登记信息表
 */
public class PlmsMortgage implements Serializable{
    private int id;
    private String code;
    private Date registrationTime;
    private String registrationResult;
    private String remark;
    private String contractCode;
    private String status;
    private Date createDate;
    private Date modifyDate;
    private String registrationPlace;

    public PlmsMortgage() {
    }

    public PlmsMortgage(int id, String code, Date registrationTime, String registrationResult, String remark, String contractCode, String status, Date createDate, Date modifyDate, String registrationPlace) {
        this.id = id;
        this.code = code;
        this.registrationTime = registrationTime;
        this.registrationResult = registrationResult;
        this.remark = remark;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.registrationPlace = registrationPlace;
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

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getRegistrationResult() {
        return registrationResult;
    }

    public void setRegistrationResult(String registrationResult) {
        this.registrationResult = registrationResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public void setRegistrationPlace(String registrationPlace) {
        this.registrationPlace = registrationPlace;
    }
}
