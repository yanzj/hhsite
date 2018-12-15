package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产调明细表
 */
public class PlmsInvestDetail {
    private int id;
    private String code;
    //抵押权顺位
    private String mortgageRank;
    //债权类型
    private String creditorRightsType;
    //债权人
    private String creditorRightsPerson;
    //债权性质
    private String creditorProperty;
    //债权金额
    private BigDecimal creditorAmount;
    //产调信息表编码
    private String investigationCode;

    //
    private String status;

    private Date createDate;
    private Date modifyDate;

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

    public String getMortgageRank() {
        return mortgageRank;
    }

    public void setMortgageRank(String mortgageRank) {
        this.mortgageRank = mortgageRank;
    }

    public String getCreditorRightsType() {
        return creditorRightsType;
    }

    public void setCreditorRightsType(String creditorRightsType) {
        this.creditorRightsType = creditorRightsType;
    }

    public String getCreditorRightsPerson() {
        return creditorRightsPerson;
    }

    public void setCreditorRightsPerson(String creditorRightsPerson) {
        this.creditorRightsPerson = creditorRightsPerson;
    }

    public String getCreditorProperty() {
        return creditorProperty;
    }

    public void setCreditorProperty(String creditorProperty) {
        this.creditorProperty = creditorProperty;
    }

    public BigDecimal getCreditorAmount() {
        return creditorAmount;
    }

    public void setCreditorAmount(BigDecimal creditorAmount) {
        this.creditorAmount = creditorAmount;
    }

    public String getInvestigationCode() {
        return investigationCode;
    }

    public void setInvestigationCode(String investigationCode) {
        this.investigationCode = investigationCode;
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
