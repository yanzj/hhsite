package com.vilio.plms.pojo;

import java.util.Date;

/**
 * Created by dell on 2017/5/19.
 */
public class City {
    private Integer id;

    private String code;

    private String abbrName;

    private String fullName;

    private String orderByNo;

    private String areaCode;

    private String status;

    private Date createDate;

    private Date modifyDate;

    public City(Integer id, String code, String abbrName, String fullName, String orderByNo, String status,
        Date createDate, Date modifyDate) {
        this.id = id;
        this.code = code;
        this.abbrName = abbrName;
        this.fullName = fullName;
        this.orderByNo = orderByNo;
        this.areaCode = areaCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public City() {
        super();
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
        this.code = code == null ? null : code.trim();
    }

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName == null ? null : abbrName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getOrderByNo() {
        return orderByNo;
    }

    public void setOrderByNo(String orderByNo) {
        this.orderByNo = orderByNo;
    }

    public String getAreaCode(){return areaCode;}

    public void setAreaCode(String areaCode){this.areaCode = areaCode;}

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
