package com.vilio.mps.mail.pojo;

/**
 * 类名： Mail<br>
 * 功能：邮件实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class MailInfo {

    private Integer id;
    private String serialNo;
    private String displayName;
    private String userName;
    private String md5Pwd;
    private String toUserList;
    private String toCcList;
    private String subject;
    private String content;
    private String urlFileList;
    private String chlState;
    private String chlMessage;
    private String taskId;
    private String timelyNews;
    private String sendStatus;
    private String lockNo;
    private String createTime;
    private String updateTime;
    private String sendNum;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMd5Pwd() {
        return md5Pwd;
    }

    public void setMd5Pwd(String md5Pwd) {
        this.md5Pwd = md5Pwd;
    }

    public String getToUserList() {
        return toUserList;
    }

    public void setToUserList(String toUserList) {
        this.toUserList = toUserList;
    }

    public String getToCcList() {
        return toCcList;
    }

    public void setToCcList(String toCcList) {
        this.toCcList = toCcList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlFileList() {
        return urlFileList;
    }

    public void setUrlFileList(String urlFileList) {
        this.urlFileList = urlFileList;
    }

    public String getChlState() {
        return chlState;
    }

    public void setChlState(String chlState) {
        this.chlState = chlState;
    }

    public String getChlMessage() {
        return chlMessage;
    }

    public void setChlMessage(String chlMessage) {
        this.chlMessage = chlMessage;
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

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public MailInfo(Integer id, String serialNo, String displayName, String userName, String md5Pwd, String toUserList, String toCcList, String subject, String content, String urlFileList, String chlState, String chlMessage, String taskId, String timelyNews, String sendStatus, String lockNo, String createTime, String updateTime, String sendNum) {
        this.id = id;
        this.serialNo = serialNo;
        this.displayName = displayName;
        this.userName = userName;
        this.md5Pwd = md5Pwd;
        this.toUserList = toUserList;
        this.toCcList = toCcList;
        this.subject = subject;
        this.content = content;
        this.urlFileList = urlFileList;
        this.chlState = chlState;
        this.chlMessage = chlMessage;
        this.taskId = taskId;
        this.timelyNews = timelyNews;
        this.sendStatus = sendStatus;
        this.lockNo = lockNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sendNum = sendNum;
    }

    public MailInfo() {
    }
}
