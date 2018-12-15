package com.vilio.mps.push.pojo;

/**
 * 类名： Umeng<br>
 * 功能：友盟实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月28日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Umeng {
    private Integer id;
    private String serialNo;
    private String appCode;
    private String subtitle;
    private String ticker;
    private String title;
    private String text;
    private String deviceToken;
    private String chlRet;
    private String chlRetData;
    private String chlErrorCode;
    private String chlMsgId;
    private String taskId;
    private String timelyNews;
    private String sendStatus;
    private String sendNum;
    private String lockNo;
    private String createTime;
    private String updateTime;
    private String systemType;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getChlRet() {
        return chlRet;
    }

    public void setChlRet(String chlRet) {
        this.chlRet = chlRet;
    }

    public String getChlRetData() {
        return chlRetData;
    }

    public void setChlRetData(String chlRetData) {
        this.chlRetData = chlRetData;
    }

    public String getChlErrorCode() {
        return chlErrorCode;
    }

    public void setChlErrorCode(String chlErrorCode) {
        this.chlErrorCode = chlErrorCode;
    }

    public String getChlMsgId() {
        return chlMsgId;
    }

    public void setChlMsgId(String chlMsgId) {
        this.chlMsgId = chlMsgId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTimelyNews() {
        return timelyNews;
    }

    public void setTimelyNews(String timelyNews) {
        this.timelyNews = timelyNews;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getLockNo() {
        return lockNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
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

    public Umeng() {
    }

    public Umeng(Integer id, String serialNo, String appCode, String subtitle, String ticker, String title, String text, String deviceToken, String chlRet, String chlRetData, String chlErrorCode, String chlMsgId, String taskId, String timelyNews, String sendStatus, String sendNum, String lockNo, String createTime, String updateTime, String systemType) {
        this.id = id;
        this.serialNo = serialNo;
        this.appCode = appCode;
        this.subtitle = subtitle;
        this.ticker = ticker;
        this.title = title;
        this.text = text;
        this.deviceToken = deviceToken;
        this.chlRet = chlRet;
        this.chlRetData = chlRetData;
        this.chlErrorCode = chlErrorCode;
        this.chlMsgId = chlMsgId;
        this.taskId = taskId;
        this.timelyNews = timelyNews;
        this.sendStatus = sendStatus;
        this.sendNum = sendNum;
        this.lockNo = lockNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.systemType = systemType;
    }
}
