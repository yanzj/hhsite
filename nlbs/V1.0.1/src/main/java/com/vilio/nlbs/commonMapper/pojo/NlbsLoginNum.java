package com.vilio.nlbs.commonMapper.pojo;

public class NlbsLoginNum {
    private Integer id;

    private String userNo;

    private Integer errorNum;

    private Boolean haslock;

    public NlbsLoginNum(Integer id, String userNo, Integer errorNum, Boolean haslock) {
        this.id = id;
        this.userNo = userNo;
        this.errorNum = errorNum;
        this.haslock = haslock;
    }

    public NlbsLoginNum() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Boolean getHaslock() {
        return haslock;
    }

    public void setHaslock(Boolean haslock) {
        this.haslock = haslock;
    }
}