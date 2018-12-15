package com.vilio.ppms.pojo.common;

import java.util.Date;

public class RouteWeight {
    private Long id;

    private String code;

    private String ruleName;

    private String ruleWeight;

    private String ruleMethod;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public RouteWeight(Long id, String code, String ruleName, String ruleWeight, String ruleMethod, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.ruleName = ruleName;
        this.ruleWeight = ruleWeight;
        this.ruleMethod = ruleMethod;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public RouteWeight() {
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

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getRuleWeight() {
        return ruleWeight;
    }

    public void setRuleWeight(String ruleWeight) {
        this.ruleWeight = ruleWeight == null ? null : ruleWeight.trim();
    }

    public String getRuleMethod() {
        return ruleMethod;
    }

    public void setRuleMethod(String ruleMethod) {
        this.ruleMethod = ruleMethod == null ? null : ruleMethod.trim();
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