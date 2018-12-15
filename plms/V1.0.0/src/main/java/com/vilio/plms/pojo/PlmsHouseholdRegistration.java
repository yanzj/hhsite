package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2017/8/14.
 */
public class PlmsHouseholdRegistration implements Serializable{
    private int id;
    private String code;
    private String name;
    private String idNo;
    private String houseCode;
    private Date createDate;
    private Date modifyDate;
    private String status;

    public PlmsHouseholdRegistration() {
    }

    public PlmsHouseholdRegistration(int id, String code, String name, String idNo, String houseCode, Date createDate, Date modifyDate, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.idNo = idNo;
        this.houseCode = houseCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
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

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
