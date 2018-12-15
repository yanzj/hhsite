package com.vilio.ppms.pojo.common;

import java.util.Date;

public class SysparamInfo {
    private Long id;

    private String code;

    private String itemId;

    private String itemName;

    private String itemCval;

    private Long itemIval;

    private Date itemDateTime;

    private String itemDesc;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public SysparamInfo(Long id, String code, String itemId, String itemName, String itemCval, Long itemIval, Date itemDateTime, String itemDesc, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCval = itemCval;
        this.itemIval = itemIval;
        this.itemDateTime = itemDateTime;
        this.itemDesc = itemDesc;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public SysparamInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemCval() {
        return itemCval;
    }

    public void setItemCval(String itemCval) {
        this.itemCval = itemCval == null ? null : itemCval.trim();
    }

    public Long getItemIval() {
        return itemIval;
    }

    public void setItemIval(Long itemIval) {
        this.itemIval = itemIval;
    }

    public Date getItemDateTime() {
        return itemDateTime;
    }

    public void setItemDateTime(Date itemDateTime) {
        this.itemDateTime = itemDateTime;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc == null ? null : itemDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }
}