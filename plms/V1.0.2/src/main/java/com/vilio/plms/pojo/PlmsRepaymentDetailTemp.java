package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by martin on 2017/9/15.
 */
public class PlmsRepaymentDetailTemp {
    private int id;

    private String code;
    //扣款类型
    private String paymentMethod;
    //还款明细表code
    private String detailCode;
    //科目
    private int subject;
    //金额
    private BigDecimal amount;
    //状态
    private String status;
    //应还日期
    private Date timeHappened;
    //本次原子操作的批次号
    private String batchCode;

    private String scheduleDetailCode;

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

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getTimeHappened() {
        return timeHappened;
    }

    public void setTimeHappened(Date timeHappened) {
        this.timeHappened = timeHappened;
    }

    public String getScheduleDetailCode() {
        return scheduleDetailCode;
    }

    public void setScheduleDetailCode(String scheduleDetailCode) {
        this.scheduleDetailCode = scheduleDetailCode;
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
