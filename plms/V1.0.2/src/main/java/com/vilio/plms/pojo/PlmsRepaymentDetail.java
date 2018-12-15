package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款明细
 */
public class PlmsRepaymentDetail {
    private int id;
    //
    private String code;
    //还款计划明细表code
    private String scheduleDetailCode;
    //科目
    private int subject;
    //金额
    private BigDecimal amount;
    //
    private String status;
    //应还日期
    private Date timeHappened;

    private String paymentMethod;

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

    public String getScheduleDetailCode() {
        return scheduleDetailCode;
    }

    public void setScheduleDetailCode(String scheduleDetailCode) {
        this.scheduleDetailCode = scheduleDetailCode;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getTimeHappened() {
        return timeHappened;
    }

    public void setTimeHappened(Date timeHappened) {
        this.timeHappened = timeHappened;
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
