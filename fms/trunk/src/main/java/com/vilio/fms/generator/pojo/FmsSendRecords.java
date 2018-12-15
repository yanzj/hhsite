package com.vilio.fms.generator.pojo;/**
 * Created by dell on 2017/8/28/0028.
 */

/**
 * 类名： FmsSendRecords<br>
 * 功能：<br>
 * 版本： 1.0<br>
 * 日期： 2017/8/28/0028<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class FmsSendRecords {

    private String id;

    private String dateCreated;

    private String dateModified;

    private String code;

    private String serialNo;

    private String recordNo;

    private String mailReceiverAddress;

    private String mailCcAddress;

    private String shortMsgReceiver;

    private String wechatReceiver;

    private String content;

    private String sendType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getMailReceiverAddress() {
        return mailReceiverAddress;
    }

    public void setMailReceiverAddress(String mailReceiverAddress) {
        this.mailReceiverAddress = mailReceiverAddress;
    }

    public String getMailCcAddress() {
        return mailCcAddress;
    }

    public void setMailCcAddress(String mailCcAddress) {
        this.mailCcAddress = mailCcAddress;
    }

    public String getShortMsgReceiver() {
        return shortMsgReceiver;
    }

    public void setShortMsgReceiver(String shortMsgReceiver) {
        this.shortMsgReceiver = shortMsgReceiver;
    }

    public String getWechatReceiver() {
        return wechatReceiver;
    }

    public void setWechatReceiver(String wechatReceiver) {
        this.wechatReceiver = wechatReceiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
}
