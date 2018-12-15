package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/19.
 */
public class NlbsCity {
    private Integer id;

    private String code;

    private String abbrName;

    private String fullName;

    private String allowInquiry;

    public NlbsCity(Integer id, String code, String abbrName, String fullName, String allowInquiry) {
        this.id = id;
        this.code = code;
        this.abbrName = abbrName;
        this.fullName = fullName;
        this.allowInquiry = allowInquiry;
    }

    public NlbsCity() {
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

    public String getAllowInquiry() {
        return allowInquiry;
    }

    public void setAllowInquiry(String allowInquiry) {
        this.allowInquiry = allowInquiry;
    }
}
