package com.vilio.bps.inquiry.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhuxu on 2017/5/4.
 */
public class InquiryBaseValueBean implements Serializable{
    //关联估价公司
    private String companyCode;
    //
    private String code;
    //状态值 1 有估价值；2 等待人工估价； 3 无法估价
    private String status;
    //估值
    private String price;

    private Date priceTime;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(Date priceTime) {
        this.priceTime = priceTime;
    }
}
