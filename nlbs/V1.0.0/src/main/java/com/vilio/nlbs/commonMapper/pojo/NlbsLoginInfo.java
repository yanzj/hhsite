package com.vilio.nlbs.commonMapper.pojo;

public class NlbsLoginInfo {
    private Integer id;

    private String code;

    private String userNo;

    private String systemTimestamp;

    private String clientTimestamp;

    public NlbsLoginInfo(Integer id, String code, String userNo, String systemTimestamp, String clientTimestamp) {
        this.id = id;
        this.code = code;
        this.userNo = userNo;
        this.systemTimestamp = systemTimestamp;
        this.clientTimestamp = clientTimestamp;
    }

    public NlbsLoginInfo() {
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

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getSystemTimestamp() {
        return systemTimestamp;
    }

    public void setSystemTimestamp(String systemTimestamp) {
        this.systemTimestamp = systemTimestamp == null ? null : systemTimestamp.trim();
    }

    public String getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(String clientTimestamp) {
        this.clientTimestamp = clientTimestamp == null ? null : clientTimestamp.trim();
    }
}