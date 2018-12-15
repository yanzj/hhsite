package com.vilio.pcfs.pojo;

/**
 * Created by dell on 2017/8/15.
 */
public class WaitSendMessage {

    private Integer id;
    private String messageId;
    private String userId;
    private String messageTitle;
    private String messageContent;
    private String sendTime;
    private String createTime;
    private String updateTime;
    private String sendMethod;
    private String batchNo;
    private String messageTicker;
    private String messageSubtitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
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

    public WaitSendMessage() {
    }

    public WaitSendMessage(Integer id, String messageId, String userId, String messageTitle, String messageContent, String sendTime, String createTime, String updateTime, String sendMethod, String batchNo, String messageTicker, String messageSubtitle) {
        this.id = id;
        this.messageId = messageId;
        this.userId = userId;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sendMethod = sendMethod;
        this.batchNo = batchNo;
        this.messageTicker = messageTicker;
        this.messageSubtitle = messageSubtitle;
    }
}
