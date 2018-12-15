package com.vilio.plms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 罚息表
 */
public class PlmsOverdue {
    private int id;
    private String code;
    //科目
    private String subject;
    //逾期天数
    private int overdueDays;
    //逾期金额
    private BigDecimal overdueAmount;
    //罚息金额
    private BigDecimal amount;
    //还款明细计划表code
    private String scheduleDetailCode;
    //还款明细表code
    private String repaymentDetailCode;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getScheduleDetailCode() {
        return scheduleDetailCode;
    }

    public void setScheduleDetailCode(String scheduleDetailCode) {
        this.scheduleDetailCode = scheduleDetailCode;
    }

    public String getRepaymentDetailCode() {
        return repaymentDetailCode;
    }

    public void setRepaymentDetailCode(String repaymentDetailCode) {
        this.repaymentDetailCode = repaymentDetailCode;
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
