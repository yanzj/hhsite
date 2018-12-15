package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/6/30.
 */
public class NlbsUserGovernCity {
    private Integer id;

    private String cityCode;
    private String cityName;

    private String userNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
