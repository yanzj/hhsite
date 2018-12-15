package com.vilio.plms.pojo;

/**
 * 类名： AccountInfo<br>
 * 功能：银行卡信息实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class AccountInfo {

    private Integer id;
    private String code;
    private String name;
    private String bank;
    private String province;
    private String city;
    private String accountNo;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public AccountInfo() {
    }

    public AccountInfo(Integer id, String code, String name, String bank, String province, String city, String accountNo, String status, String createDate, String modifyDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.bank = bank;
        this.province = province;
        this.city = city;
        this.accountNo = accountNo;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
