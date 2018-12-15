package com.vilio.plms.pojo;

import java.io.Serializable;

/**
 * 类名： ApplyInfo<br>
 * 功能：进件申请信息实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class ApplyInfo implements Serializable{

    private Integer id;
    private String code;
    private String applyDate;
    private String applyAmount;
    private String applyPeriod;
    private String lendingMethods;
    private String intentionMoney;
    private String identifyingCode;
    private String remark;
    private String status;
    private String createDate;
    private String modifyDate;
    private String accountCode;
    private String agentCode;
    private String bmsCode;
    private String distributeCode;
    private String userCode;


    public ApplyInfo() {
    }

    public ApplyInfo(Integer id, String code, String applyDate, String applyAmount, String applyPeriod, String lendingMethods, String intentionMoney, String identifyingCode, String remark, String status, String createDate, String modifyDate, String accountCode, String agentCode, String bmsCode, String distributeCode, String userCode) {
        this.id = id;
        this.code = code;
        this.applyDate = applyDate;
        this.applyAmount = applyAmount;
        this.applyPeriod = applyPeriod;
        this.lendingMethods = lendingMethods;
        this.intentionMoney = intentionMoney;
        this.identifyingCode = identifyingCode;
        this.remark = remark;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.accountCode = accountCode;
        this.agentCode = agentCode;
        this.bmsCode = bmsCode;
        this.distributeCode = distributeCode;
        this.userCode = userCode;
    }

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getApplyPeriod() {
        return applyPeriod;
    }

    public void setApplyPeriod(String applyPeriod) {
        this.applyPeriod = applyPeriod;
    }

    public String getLendingMethods() {
        return lendingMethods;
    }

    public void setLendingMethods(String lendingMethods) {
        this.lendingMethods = lendingMethods;
    }

    public String getIntentionMoney() {
        return intentionMoney;
    }

    public void setIntentionMoney(String intentionMoney) {
        this.intentionMoney = intentionMoney;
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getBmsCode() {
        return bmsCode;
    }

    public void setBmsCode(String bmsCode) {
        this.bmsCode = bmsCode;
    }

    public String getDistributeCode() {
        return distributeCode;
    }

    public void setDistributeCode(String distributeCode) {
        this.distributeCode = distributeCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
