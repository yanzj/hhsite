package com.vilio.plms.pojo;

/**
 * 类名： SysParam<br>
 * 功能：系统参数实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class SysParam {

    private Integer id;
    private String code;
    private String syscode;
    private String sysname;
    private String itemId;
    private String itemName;
    private String itemCval;
    private String itemIval;
    private String itemDesc;
    private String createDate;
    private String modifyDate;
    private String remark;
    private String executeTime;

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

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCval() {
        return itemCval;
    }

    public void setItemCval(String itemCval) {
        this.itemCval = itemCval;
    }

    public String getItemIval() {
        return itemIval;
    }

    public void setItemIval(String itemIval) {
        this.itemIval = itemIval;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public SysParam() {
    }

    public SysParam(Integer id, String code, String syscode, String sysname, String itemId, String itemName, String itemCval, String itemIval, String itemDesc, String createDate, String modifyDate, String remark, String executeTime) {
        this.id = id;
        this.code = code;
        this.syscode = syscode;
        this.sysname = sysname;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCval = itemCval;
        this.itemIval = itemIval;
        this.itemDesc = itemDesc;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.remark = remark;
        this.executeTime = executeTime;
    }
}
