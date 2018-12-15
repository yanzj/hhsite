package com.vilio.plms.pojo;

/**
 * 类名： ReceiptsRecord<br>
 * 功能：资金入账流水实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class ReceiptsRecord {

    private Integer id;
    private String code;
    private String receiptsDate;
    private String receiptsAmount;
    private String contractCode;
    private String accountType;
    private String status;
    private String dealStatus;
    private String dealBatchNo;
    private String createDate;
    private String modifyDate;
    private String repaymentApplyCode;
    private String dealMsg;
    private String dealUser;
    private String dealTime;
    private String remark;
    private String fundSource;

    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDealUser() {
        return dealUser;
    }

    public void setDealUser(String dealUser) {
        this.dealUser = dealUser;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealMsg() {
        return dealMsg;
    }

    public void setDealMsg(String dealMsg) {
        this.dealMsg = dealMsg;
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

    public String getReceiptsDate() {
        return receiptsDate;
    }

    public void setReceiptsDate(String receiptsDate) {
        this.receiptsDate = receiptsDate;
    }

    public String getReceiptsAmount() {
        return receiptsAmount;
    }

    public void setReceiptsAmount(String receiptsAmount) {
        this.receiptsAmount = receiptsAmount;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDealBatchNo() {
        return dealBatchNo;
    }

    public void setDealBatchNo(String dealBatchNo) {
        this.dealBatchNo = dealBatchNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRepaymentApplyCode() {
        return repaymentApplyCode;
    }

    public void setRepaymentApplyCode(String repaymentApplyCode) {
        this.repaymentApplyCode = repaymentApplyCode;
    }

    public ReceiptsRecord() {
    }

    public ReceiptsRecord(Integer id, String code, String receiptsDate, String receiptsAmount, String contractCode, String accountType, String status, String dealStatus, String dealBatchNo, String createDate, String modifyDate, String repaymentApplyCode, String dealMsg, String dealUser, String dealTime, String remark, String fundSource) {
        this.id = id;
        this.code = code;
        this.receiptsDate = receiptsDate;
        this.receiptsAmount = receiptsAmount;
        this.contractCode = contractCode;
        this.accountType = accountType;
        this.status = status;
        this.dealStatus = dealStatus;
        this.dealBatchNo = dealBatchNo;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.repaymentApplyCode = repaymentApplyCode;
        this.dealMsg = dealMsg;
        this.dealUser = dealUser;
        this.dealTime = dealTime;
        this.remark = remark;
        this.fundSource = fundSource;
    }
}
