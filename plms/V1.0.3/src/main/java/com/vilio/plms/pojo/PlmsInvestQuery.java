package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 产调查询表
 */
public class PlmsInvestQuery implements Serializable{
    private int id;
    private String code;
    private Date investQueryTime;
    private String investQueryResult;
    private String remark;
    private String contractCode;
    private String status;
    private Date createTime;
    private Date modifyTime;

    public PlmsInvestQuery() {
    }

    public PlmsInvestQuery(int id, String code, Date investQueryTime, String investQueryResult, String remark, String contractCode, String status, Date createTime, Date modifyTime) {
        this.id = id;
        this.code = code;
        this.investQueryTime = investQueryTime;
        this.investQueryResult = investQueryResult;
        this.remark = remark;
        this.contractCode = contractCode;
        this.status = status;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
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

    public Date getInvestQueryTime() {
        return investQueryTime;
    }

    public void setInvestQueryTime(Date investQueryTime) {
        this.investQueryTime = investQueryTime;
    }

    public String getInvestQueryResult() {
        return investQueryResult;
    }

    public void setInvestQueryResult(String investQueryResult) {
        this.investQueryResult = investQueryResult;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
