package com.vilio.plms.pojo;

/**
 * 类名： MessageInfo<br>
 * 功能：推送信息表实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class MessageInfo {
    private Integer id;
    private String code;
    private String umId;
    private String messageTitle;
    private String messageTicker;
    private String messageSubtitle;
    private String messageContent;
    private String sendTime;
    private String messageType;
    private String sendMethod;
    private String batchNo;
    private String sendStatus;
    private String sendSystem;
    private String createTime;
    private String updateTime;

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

    public String getUmId() {
        return umId;
    }

    public void setUmId(String umId) {
        this.umId = umId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageTicker() {
        return messageTicker;
    }

    public void setMessageTicker(String messageTicker) {
        this.messageTicker = messageTicker;
    }

    public String getMessageSubtitle() {
        return messageSubtitle;
    }

    public void setMessageSubtitle(String messageSubtitle) {
        this.messageSubtitle = messageSubtitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

    public String getSendSystem() {
        return sendSystem;
    }

    public void setSendSystem(String sendSystem) {
        this.sendSystem = sendSystem;
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

    public MessageInfo() {
    }

    public MessageInfo(Integer id, String code, String umId, String messageTitle, String messageTicker, String messageSubtitle, String messageContent, String sendTime, String messageType, String sendMethod, String batchNo, String sendStatus, String sendSystem, String createTime, String updateTime) {
        this.id = id;
        this.code = code;
        this.umId = umId;
        this.messageTitle = messageTitle;
        this.messageTicker = messageTicker;
        this.messageSubtitle = messageSubtitle;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.messageType = messageType;
        this.sendMethod = sendMethod;
        this.batchNo = batchNo;
        this.sendStatus = sendStatus;
        this.sendSystem = sendSystem;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
