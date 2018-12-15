package com.vilio.plms.pojo;

/**
 * 类名： SmsInfo<br>
 * 功能：短信信息表实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class SmsInfo {

    private Integer id;
    private String code;
    private String mobile;
    private String requestNo;
    private String templateParam;
    private String senderName;
    private String signNo;
    private String templateCode;
    private String smsType;
    private String realSendTime;
    private String sendStatus;
    private String batchNo;
    private String sendMethod;
    private String retCode;
    private String retMsg;
    private String repaymentScheduleCode;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getRealSendTime() {
        return realSendTime;
    }

    public void setRealSendTime(String realSendTime) {
        this.realSendTime = realSendTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
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

    public String getRepaymentScheduleCode() {
        return repaymentScheduleCode;
    }

    public void setRepaymentScheduleCode(String repaymentScheduleCode) {
        this.repaymentScheduleCode = repaymentScheduleCode;
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

    public SmsInfo() {
    }

    public SmsInfo(Integer id, String code, String mobile, String requestNo, String templateParam, String senderName, String signNo, String templateCode, String smsType, String realSendTime, String sendStatus, String batchNo, String sendMethod, String retCode, String retMsg, String repaymentScheduleCode, String createTime, String updateTime) {
        this.id = id;
        this.code = code;
        this.mobile = mobile;
        this.requestNo = requestNo;
        this.templateParam = templateParam;
        this.senderName = senderName;
        this.signNo = signNo;
        this.templateCode = templateCode;
        this.smsType = smsType;
        this.realSendTime = realSendTime;
        this.sendStatus = sendStatus;
        this.batchNo = batchNo;
        this.sendMethod = sendMethod;
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.repaymentScheduleCode = repaymentScheduleCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
