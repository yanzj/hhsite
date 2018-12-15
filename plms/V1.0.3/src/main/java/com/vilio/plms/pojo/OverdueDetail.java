package com.vilio.plms.pojo;

/**
 * 类名： OverdueDetail<br>
 * 功能：罚息流水实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月28日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class OverdueDetail {

    private Integer id;
    private String code;
    private String overdueAmount;
    private String time;
    private String status;
    private String createDate;
    private String modifyDate;
    private String overdueCode;
    private String type;
    private String overdueDays;

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
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

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getOverdueCode() {
        return overdueCode;
    }

    public void setOverdueCode(String overdueCode) {
        this.overdueCode = overdueCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OverdueDetail() {
    }

    public OverdueDetail(Integer id, String code, String overdueAmount, String time, String status, String createDate, String modifyDate, String overdueCode, String type, String overdueDays) {
        this.id = id;
        this.code = code;
        this.overdueAmount = overdueAmount;
        this.time = time;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.overdueCode = overdueCode;
        this.type = type;
        this.overdueDays = overdueDays;
    }
}
