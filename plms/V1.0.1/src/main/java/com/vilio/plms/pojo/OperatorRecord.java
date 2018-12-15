package com.vilio.plms.pojo;


/**
 * 类名： OperatorRecord<br>
 * 功能：操作历史表实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class OperatorRecord {

    private Integer id;
    private String code;
    private String type;
    private String operateTime;
    private String operateUser;
    private String status;
    private String createDate;
    private String modifyDate;
    private String keyCode;

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
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

    public OperatorRecord() {
    }

    public OperatorRecord(Integer id, String code, String type, String operateTime, String operateUser, String status, String createDate, String modifyDate, String keyCode) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.operateTime = operateTime;
        this.operateUser = operateUser;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.keyCode = keyCode;
    }
}
