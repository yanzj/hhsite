package com.vilio.plms.pojo;

/**
 * 类名： UserInfo<br>
 * 功能：用户信息表实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UserInfo {

    private Integer id;
    private String code;
    private String umId;
    private String name;
    private String idType;
    private String idNo;
    private String status;
    private String createDate;
    private String modifyDate;

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

    public String getUmId() {
        return umId;
    }

    public void setUmId(String umId) {
        this.umId = umId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
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

    public UserInfo() {
    }

    public UserInfo(Integer id, String code, String umId, String name, String idType, String idNo, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.umId = umId;
        this.name = name;
        this.idType = idType;
        this.idNo = idNo;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
