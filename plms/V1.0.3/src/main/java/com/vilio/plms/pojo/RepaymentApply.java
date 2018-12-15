package com.vilio.plms.pojo;

/**
 * 类名： RepaymentApply<br>
 * 功能：还款申请实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class RepaymentApply {

    private Integer id;
    private String code;
    private String amount;
    private String applyTime;
    private String applyStatus;
    private String businessCode;
    private String contractCode;
    private String createDate;
    private String modifyDate;
    private String userCode;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public RepaymentApply() {
    }

    public RepaymentApply(Integer id, String code, String amount, String applyTime, String applyStatus, String businessCode, String contractCode, String createDate, String modifyDate, String userCode) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.applyTime = applyTime;
        this.applyStatus = applyStatus;
        this.businessCode = businessCode;
        this.contractCode = contractCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.userCode = userCode;
    }
}
