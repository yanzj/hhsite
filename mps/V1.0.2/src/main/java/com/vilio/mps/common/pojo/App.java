package com.vilio.mps.common.pojo;

/**
 * 类名： App<br>
 * 功能：应用实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月10日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class App {

    private Integer id;
    private String appCode;
    private String appName;
    private String appType;
    private String appKey;
    private String appMessageSecret;
    private String appMasterSecret;
    private String desc;
    private String createTime;
    private String updateTime;
    private String remark1;
    private String remark2;
    private String status;
    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppMessageSecret() {
        return appMessageSecret;
    }

    public void setAppMessageSecret(String appMessageSecret) {
        this.appMessageSecret = appMessageSecret;
    }

    public String getAppMasterSecret() {
        return appMasterSecret;
    }

    public void setAppMasterSecret(String appMasterSecret) {
        this.appMasterSecret = appMasterSecret;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public App() {
    }

    public App(Integer id, String appCode, String appName, String appType, String appKey, String appMessageSecret, String appMasterSecret, String desc, String createTime, String updateTime, String remark1, String remark2, String status, String systemType) {
        this.id = id;
        this.appCode = appCode;
        this.appName = appName;
        this.appType = appType;
        this.appKey = appKey;
        this.appMessageSecret = appMessageSecret;
        this.appMasterSecret = appMasterSecret;
        this.desc = desc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.status = status;
        this.systemType = systemType;
    }
}
