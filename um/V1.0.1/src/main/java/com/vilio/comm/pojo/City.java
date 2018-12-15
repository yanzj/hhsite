package com.vilio.comm.pojo;

/**
 * 类名： City<br>
 * 功能：城市对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月29日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class City {

    private Integer id;
    private String code;
    private String abbrName;
    private String fullName;
    private String level;
    private String superCode;

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

    public String getAbbrName() {
        return abbrName;
    }

    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    public City() {
    }

    public City(Integer id, String code, String abbrName, String fullName, String level, String superCode) {
        this.id = id;
        this.code = code;
        this.abbrName = abbrName;
        this.fullName = fullName;
        this.level = level;
        this.superCode = superCode;
    }
}
