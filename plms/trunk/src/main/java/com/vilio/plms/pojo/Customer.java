package com.vilio.plms.pojo;

/**
 * 类名： Customer<br>
 * 功能：借款人信息实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Customer {

    private Integer id;
    private String code;
    private String name;
    private String umId;
    private String oldName;
    private String idType;
    private String idNo;
    private String startTime;
    private String endTime;
    private String age;
    private String mobile;
    private String organization;
    private String titile;
    private String annualIncome;
    private String address;
    private String marriage;
    private String marriageHistory;
    private String isMain;
    private String applyCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUmId() {
        return umId;
    }

    public void setUmId(String umId) {
        this.umId = umId;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMarriageHistory() {
        return marriageHistory;
    }

    public void setMarriageHistory(String marriageHistory) {
        this.marriageHistory = marriageHistory;
    }

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
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

    public Customer() {
    }

    public Customer(Integer id, String code, String name, String umId, String oldName, String idType, String idNo, String startTime, String endTime, String age, String mobile, String organization, String titile, String annualIncome, String address, String marriage, String marriageHistory, String isMain, String applyCode, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.umId = umId;
        this.oldName = oldName;
        this.idType = idType;
        this.idNo = idNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.age = age;
        this.mobile = mobile;
        this.organization = organization;
        this.titile = titile;
        this.annualIncome = annualIncome;
        this.address = address;
        this.marriage = marriage;
        this.marriageHistory = marriageHistory;
        this.isMain = isMain;
        this.applyCode = applyCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
