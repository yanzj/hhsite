package com.vilio.mps.receivepush.pojo;

import java.util.Date;

/**
 * Created by dell on 2017/5/31.
 */
public class NlbsMessageResp {
    //消息序列号(消息平台序号)
    private String serialNo;
    private String code;
    //消息标题
    private String title;
    //消息内容
    private String content;
    //发送方公司编号
    private String senderCompanyCode;
    //发送方公司名称
    private String senderCompanyName;
    //发送方所在部门编号
    private String senderDepartmentCode;
    //发送方所在部门名称
    private String senderDepartmentName;
    //发送用户编号
    private String senderIdentityId;
    //发送用户名
    private String senderName;
    //接收用户编号
    private String receiverUserId;
    //接收用户名
    private String receiverName;
    private String createTime;

    private String internalParam;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderCompanyCode() {
        return senderCompanyCode;
    }

    public void setSenderCompanyCode(String senderCompanyCode) {
        this.senderCompanyCode = senderCompanyCode;
    }

    public String getSenderCompanyName() {
        return senderCompanyName;
    }

    public void setSenderCompanyName(String senderCompanyName) {
        this.senderCompanyName = senderCompanyName;
    }

    public String getSenderDepartmentCode() {
        return senderDepartmentCode;
    }

    public void setSenderDepartmentCode(String senderDepartmentCode) {
        this.senderDepartmentCode = senderDepartmentCode;
    }

    public String getSenderDepartmentName() {
        return senderDepartmentName;
    }

    public void setSenderDepartmentName(String senderDepartmentName) {
        this.senderDepartmentName = senderDepartmentName;
    }

    public String getSenderIdentityId() {
        return senderIdentityId;
    }

    public void setSenderIdentityId(String senderIdentityId) {
        this.senderIdentityId = senderIdentityId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInternalParam() {
        return internalParam;
    }

    public void setInternalParam(String internalParam) {
        this.internalParam = internalParam;
    }
}
