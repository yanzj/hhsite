package com.vilio.plms.pojo;

/**
 * 类名： EmailInfo<br>
 * 功能：邮件信息实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月5日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class EmailInfo {

    private Integer id;
    private String code;
    private String systemNo;
    private String displayName;
    private String userName;
    private String toUserList;
    private String toCcList;
    private String subject;
    private String content;
    private String urlFileList;
    private String retCode;
    private String retMsg;
    private String emailType;
    private String sendMethod;
    private String batchNo;
    private String sendStatus;
    private String sendTime;
    private String createTime;
    private String updateTime;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
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
        this.code = code;
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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
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

    public EmailInfo() {
    }

    public EmailInfo(Integer id, String code, String systemNo, String displayName, String userName, String toUserList, String toCcList, String subject, String content, String urlFileList, String retCode, String retMsg, String emailType, String sendMethod, String batchNo, String sendStatus, String sendTime, String createTime, String updateTime) {
        this.id = id;
        this.code = code;
        this.systemNo = systemNo;
        this.displayName = displayName;
        this.userName = userName;
        this.toUserList = toUserList;
        this.toCcList = toCcList;
        this.subject = subject;
        this.content = content;
        this.urlFileList = urlFileList;
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.emailType = emailType;
        this.sendMethod = sendMethod;
        this.batchNo = batchNo;
        this.sendStatus = sendStatus;
        this.sendTime = sendTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
