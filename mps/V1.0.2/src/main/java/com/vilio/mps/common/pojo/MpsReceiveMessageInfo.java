package com.vilio.mps.common.pojo;


/**
 * 信息请求接收表
 * Created by ZHUXU on 2017/5/11.
 */
public class MpsReceiveMessageInfo implements Cloneable {

    private int id;
    private String serialNo;
    //消息类型(email 邮件,SMS 短信, WeChat 微信, pc_nlbs 进件平台)
    private String type;
    //信息标题
    private String title;
    //信息内容
    private String content;
    //信息发送方公司编号
    private String senderCompanyCode;
    //信息发送方公司名称
    private String senderCompanyName;
    //信息发送方公司部门
    private String senderDepartmentCode;
    //信息发送方所在部门名称
    private String senderDepartmentName;
    //信息发送方身份Id
    private String senderIdentityId;
    //信息发送方名称
    private String senderName;
    //消息来源系统
    private String senderSystem;
    //信息接收方公司编号
    private String receiverCompanyCode;
    //信息接收方公司名称
    private String receiverCompanyName;
    //信息接收方公司部门
    private String receiverDepartmentCode;
    //信息接收方所在部门名称
    private String receiverDepartmentName;
    //信息接收方身份Id
    private String receiverIdentityId;
    //信息接收方名称
    private String receiverName;
    //目标系统
    private String receiverSystem;
    //状态(0 创建成功，1发送成功，2状态位置，3发送失败)
    private String status;
    //内部特用字段
    private String internalParam;
    //请求流水号
    private String requestNo;
    //发送时间
    private String sendTime;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSenderSystem() {
        return senderSystem;
    }

    public void setSenderSystem(String senderSystem) {
        this.senderSystem = senderSystem;
    }

    public String getReceiverCompanyCode() {
        return receiverCompanyCode;
    }

    public void setReceiverCompanyCode(String receiverCompanyCode) {
        this.receiverCompanyCode = receiverCompanyCode;
    }

    public String getReceiverCompanyName() {
        return receiverCompanyName;
    }

    public void setReceiverCompanyName(String receiverCompanyName) {
        this.receiverCompanyName = receiverCompanyName;
    }

    public String getReceiverDepartmentCode() {
        return receiverDepartmentCode;
    }

    public void setReceiverDepartmentCode(String receiverDepartmentCode) {
        this.receiverDepartmentCode = receiverDepartmentCode;
    }

    public String getReceiverDepartmentName() {
        return receiverDepartmentName;
    }

    public void setReceiverDepartmentName(String receiverDepartmentName) {
        this.receiverDepartmentName = receiverDepartmentName;
    }

    public String getReceiverIdentityId() {
        return receiverIdentityId;
    }

    public void setReceiverIdentityId(String receiverIdentityId) {
        this.receiverIdentityId = receiverIdentityId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverSystem() {
        return receiverSystem;
    }

    public void setReceiverSystem(String receiverSystem) {
        this.receiverSystem = receiverSystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInternalParam() {
        return internalParam;
    }

    public void setInternalParam(String internalParam) {
        this.internalParam = internalParam;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public MpsReceiveMessageInfo() {
    }

    public MpsReceiveMessageInfo(int id, String serialNo, String type, String title, String content, String senderCompanyCode, String senderCompanyName, String senderDepartmentCode, String senderDepartmentName, String senderIdentityId, String senderName, String senderSystem, String receiverCompanyCode, String receiverCompanyName, String receiverDepartmentCode, String receiverDepartmentName, String receiverIdentityId, String receiverName, String receiverSystem, String status, String internalParam, String requestNo, String sendTime) {
        this.id = id;
        this.serialNo = serialNo;
        this.type = type;
        this.title = title;
        this.content = content;
        this.senderCompanyCode = senderCompanyCode;
        this.senderCompanyName = senderCompanyName;
        this.senderDepartmentCode = senderDepartmentCode;
        this.senderDepartmentName = senderDepartmentName;
        this.senderIdentityId = senderIdentityId;
        this.senderName = senderName;
        this.senderSystem = senderSystem;
        this.receiverCompanyCode = receiverCompanyCode;
        this.receiverCompanyName = receiverCompanyName;
        this.receiverDepartmentCode = receiverDepartmentCode;
        this.receiverDepartmentName = receiverDepartmentName;
        this.receiverIdentityId = receiverIdentityId;
        this.receiverName = receiverName;
        this.receiverSystem = receiverSystem;
        this.status = status;
        this.internalParam = internalParam;
        this.requestNo = requestNo;
        this.sendTime = sendTime;
    }

    @Override
    public MpsReceiveMessageInfo clone() throws CloneNotSupportedException {
        return (MpsReceiveMessageInfo) super.clone();
    }
}
