package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/19.
 */
public class NlbsUserSessionBusinessFlag {
    private Integer id;

    private String code;

    private String userNo;

    private String businessType;

    private Boolean operateFlag;

    private String mainCode;

    public NlbsUserSessionBusinessFlag(Integer id, String code, String userNo, String businessType, Boolean operateFlag, String mainCode) {
        this.id = id;
        this.code = code;
        this.userNo = userNo;
        this.businessType = businessType;
        this.operateFlag = operateFlag;
        this.mainCode = mainCode;
    }

    public NlbsUserSessionBusinessFlag() {
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public Boolean getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(Boolean operateFlag) {
        this.operateFlag = operateFlag;
    }

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode == null ? null : mainCode.trim();
    }
}
