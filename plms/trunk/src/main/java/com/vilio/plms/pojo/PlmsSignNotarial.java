package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2017/8/15.
 */
public class PlmsSignNotarial implements Serializable{
    private int id;
    private String code;
    private Date signTime;
    private String signResult;
    private String remark;
    private String contractCode;
    private String status;
    private Date createDate;
    private Date modifyDate;
    private String signPlace;

    public PlmsSignNotarial() {
    }

    public PlmsSignNotarial(int id, String code, Date signTime, String signResult, String remark, String contractCode, String status, Date createDate, Date modifyDate, String signPlace) {
        this.id = id;
        this.code = code;
        this.signTime = signTime;
        this.signResult = signResult;
        this.remark = remark;
        this.contractCode = contractCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.signPlace = signPlace;
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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
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

    public String getSignPlace() {
        return signPlace;
    }

    public void setSignPlace(String signPlace) {
        this.signPlace = signPlace;
    }
}
