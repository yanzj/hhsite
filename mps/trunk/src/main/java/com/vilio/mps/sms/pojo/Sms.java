package com.vilio.mps.sms.pojo;

/**
 * 类名： Sms<br>
 * 功能：短信实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月28日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Sms {

    private Integer id;
    private String serialNo;
    private String innerSignNo;
    private String innerTemplateCode;
    private String templateParam;
    private String outId;
    private String receiptNo;
    private String chlRetCode;
    private String chlRetMsg;
    private String timelyNews;
    private String content;
    private String mobile;
    private String status;
    private String sendNum;
    private String lockNo;
    private String createTime;
    private String sendStatus;

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
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

    public String getInnerSignNo() {
        return innerSignNo;
    }

    public void setInnerSignNo(String innerSignNo) {
        this.innerSignNo = innerSignNo;
    }

    public String getInnerTemplateCode() {
        return innerTemplateCode;
    }

    public void setInnerTemplateCode(String innerTemplateCode) {
        this.innerTemplateCode = innerTemplateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getChlRetCode() {
        return chlRetCode;
    }

    public void setChlRetCode(String chlRetCode) {
        this.chlRetCode = chlRetCode;
    }

    public String getChlRetMsg() {
        return chlRetMsg;
    }

    public void setChlRetMsg(String chlRetMsg) {
        this.chlRetMsg = chlRetMsg;
    }

    public String getTimelyNews() {
        return timelyNews;
    }

    public void setTimelyNews(String timelyNews) {
        this.timelyNews = timelyNews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Sms() {
    }

    public Sms(Integer id, String serialNo, String innerSignNo, String innerTemplateCode, String templateParam, String outId, String receiptNo, String chlRetCode, String chlRetMsg, String timelyNews, String content, String mobile, String status, String sendNum, String lockNo, String createTime, String sendStatus) {
        this.id = id;
        this.serialNo = serialNo;
        this.innerSignNo = innerSignNo;
        this.innerTemplateCode = innerTemplateCode;
        this.templateParam = templateParam;
        this.outId = outId;
        this.receiptNo = receiptNo;
        this.chlRetCode = chlRetCode;
        this.chlRetMsg = chlRetMsg;
        this.timelyNews = timelyNews;
        this.content = content;
        this.mobile = mobile;
        this.status = status;
        this.sendNum = sendNum;
        this.lockNo = lockNo;
        this.createTime = createTime;
        this.sendStatus = sendStatus;
    }
}
