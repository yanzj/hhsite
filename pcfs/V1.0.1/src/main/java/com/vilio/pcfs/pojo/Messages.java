package com.vilio.pcfs.pojo;

/**
 * Created by dell on 2017/8/15.
 */
public class Messages {

    private Integer id;
    private Integer messageId;
    private Integer userId;
    private Integer messageTitle;
    private Integer messageContent;
    private Integer realitySendTime;
    private Integer sendTime;
    private Integer readFlag;
    private Integer createTime;
    private Integer updateTime;
    private Integer status;
    private Integer messageType;
    private Integer sendStatus;
    private Integer batchNo;
    private Integer messageTicker;
    private Integer messageSubtitle;
    private Integer deviceToken;
    private Integer channel;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(Integer deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(Integer messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Integer getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Integer messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getRealitySendTime() {
        return realitySendTime;
    }

    public void setRealitySendTime(Integer realitySendTime) {
        this.realitySendTime = realitySendTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getMessageTicker() {
        return messageTicker;
    }

    public void setMessageTicker(Integer messageTicker) {
        this.messageTicker = messageTicker;
    }

    public Integer getMessageSubtitle() {
        return messageSubtitle;
    }

    public void setMessageSubtitle(Integer messageSubtitle) {
        this.messageSubtitle = messageSubtitle;
    }

    public Messages(Integer id, Integer messageId, Integer userId, Integer messageTitle, Integer messageContent, Integer realitySendTime, Integer sendTime, Integer readFlag, Integer createTime, Integer updateTime, Integer status, Integer messageType, Integer sendStatus, Integer batchNo, Integer messageTicker, Integer messageSubtitle, Integer deviceToken, Integer channel) {
        this.id = id;
        this.messageId = messageId;
        this.userId = userId;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.realitySendTime = realitySendTime;
        this.sendTime = sendTime;
        this.readFlag = readFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.messageType = messageType;
        this.sendStatus = sendStatus;
        this.batchNo = batchNo;
        this.messageTicker = messageTicker;
        this.messageSubtitle = messageSubtitle;
        this.deviceToken = deviceToken;
        this.channel = channel;
    }

    public Messages() {
    }
}
