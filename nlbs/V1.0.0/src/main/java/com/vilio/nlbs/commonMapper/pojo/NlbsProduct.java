package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/24.
 */
public class NlbsProduct {
    private String id;
    private String code;
    private String name;
    private String maxLoanAmount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(String maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }
}
