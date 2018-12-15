package com.vilio.nlbs.commonMapper.pojo;

public class NlbsRecordClerkDistributor {
    private String id;

    private String userNo;

    private String distributorCode;

    public NlbsRecordClerkDistributor(String id, String userNo, String distributorCode) {
        this.id = id;
        this.userNo = userNo;
        this.distributorCode = distributorCode;
    }

    public NlbsRecordClerkDistributor() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode == null ? null : distributorCode.trim();
    }

}