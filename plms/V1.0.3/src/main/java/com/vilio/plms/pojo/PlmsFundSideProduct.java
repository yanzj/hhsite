package com.vilio.plms.pojo;

import java.io.Serializable;

/**
 * Created by dell on 2017/8/31.
 */
public class PlmsFundSideProduct implements Serializable{
    private int id;
    private String code;
    private String product_name;
    private String isPrincipalInstead;
    private String isInterestInstead;
    private String companyCode;
    private String bmsCode;
    private String status;
    private String createDate;
    private String modifyDate;

    public PlmsFundSideProduct() {
    }

    public PlmsFundSideProduct(int id, String code, String product_name, String isPrincipalInstead, String isInterestInstead, String companyCode, String bmsCode, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.product_name = product_name;
        this.isPrincipalInstead = isPrincipalInstead;
        this.isInterestInstead = isInterestInstead;
        this.companyCode = companyCode;
        this.bmsCode = bmsCode;
        this.status = status;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getIsPrincipalInstead() {
        return isPrincipalInstead;
    }

    public void setIsPrincipalInstead(String isPrincipalInstead) {
        this.isPrincipalInstead = isPrincipalInstead;
    }

    public String getIsInterestInstead() {
        return isInterestInstead;
    }

    public void setIsInterestInstead(String isInterestInstead) {
        this.isInterestInstead = isInterestInstead;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getBmsCode() {
        return bmsCode;
    }

    public void setBmsCode(String bmsCode) {
        this.bmsCode = bmsCode;
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
}
