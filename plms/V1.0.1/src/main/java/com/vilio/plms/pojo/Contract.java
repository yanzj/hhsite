package com.vilio.plms.pojo;

/**
 * 类名： Contract<br>
 * 功能：合同实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Contract {

    private Integer id;
    private String code;
    private String contractNo;
    private String applyCode;
    private String status;
    private String createDate;
    private String modifyDate;
    private String creditStartDate;
    private String creditEndDate;
    private String creditPeriod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreditStartDate() {
        return creditStartDate;
    }

    public void setCreditStartDate(String creditStartDate) {
        this.creditStartDate = creditStartDate;
    }

    public String getCreditEndDate() {
        return creditEndDate;
    }

    public void setCreditEndDate(String creditEndDate) {
        this.creditEndDate = creditEndDate;
    }

    public String getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(String creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public Contract() {
    }

    public Contract(Integer id, String code, String contractNo, String applyCode, String status, String createDate, String modifyDate, String creditStartDate, String creditEndDate, String creditPeriod) {
        this.id = id;
        this.code = code;
        this.contractNo = contractNo;
        this.applyCode = applyCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.creditStartDate = creditStartDate;
        this.creditEndDate = creditEndDate;
        this.creditPeriod = creditPeriod;
    }
}
