package com.vilio.nlbs.commonMapper.pojo;

public class NlbsDistributor {
    private Integer id;

    private String code;

    private String parentCode;

    private String cityCode;

    private String firstCharacterCode;

    private String abbrName;

    private String fullName;

    private Integer orderByNo;

    public NlbsDistributor(Integer id, String code, String parentCode, String cityCode, String firstCharacterCode, String abbrName, String fullName, Integer orderByNo) {
        this.id = id;
        this.code = code;
        this.parentCode = parentCode;
        this.cityCode = cityCode;
        this.firstCharacterCode = firstCharacterCode;
        this.abbrName = abbrName;
        this.fullName = fullName;
        this.orderByNo = orderByNo;
    }

    public NlbsDistributor() {
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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getFirstCharacterCode() {
        return firstCharacterCode;
    }

    public void setFirstCharacterCode(String firstCharacterCode) {
        this.firstCharacterCode = firstCharacterCode == null ? null : firstCharacterCode.trim();
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

    public Integer getOrderByNo() {
        return orderByNo;
    }

    public void setOrderByNo(Integer orderByNo) {
        this.orderByNo = orderByNo;
    }
}