package com.vilio.plms.pojo;

/**
 * 类名： Overdue<br>
 * 功能：罚息表实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月28日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Overdue {

    private Integer id;
    private String code;
    private String subject;
    private String overdueDays;
    private String overdueAmount;
    private String amount;
    private String scheduleDetailCode;
    private String status;
    private String createDate;
    private String modifyDate;
    private String repaymentDetailCode;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getScheduleDetailCode() {
        return scheduleDetailCode;
    }

    public void setScheduleDetailCode(String scheduleDetailCode) {
        this.scheduleDetailCode = scheduleDetailCode;
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

    public String getRepaymentDetailCode() {
        return repaymentDetailCode;
    }

    public void setRepaymentDetailCode(String repaymentDetailCode) {
        this.repaymentDetailCode = repaymentDetailCode;
    }

    public Overdue() {
    }

    public Overdue(Integer id, String code, String subject, String overdueDays, String overdueAmount, String amount, String scheduleDetailCode, String status, String createDate, String modifyDate, String repaymentDetailCode) {
        this.id = id;
        this.code = code;
        this.subject = subject;
        this.overdueDays = overdueDays;
        this.overdueAmount = overdueAmount;
        this.amount = amount;
        this.scheduleDetailCode = scheduleDetailCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.repaymentDetailCode = repaymentDetailCode;
    }
}
