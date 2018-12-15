package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class CollectInfo {
    private Long id;

    private String code;

    private String sysSerno;

    private String detailSerno;

    private String routeType;

    private String routeList;

    private String routeNum;

    private String transSerno;

    private String routeSerno;

    private Date transDate;

    private String transTime;

    private String transType;

    private String payerCardNo;

    private String payerCardType;

    private String payerCardMobile;

    private String payerName;

    private String payerCertType;

    private String payerCerNo;

    private String payerExpYear;

    private String payerExpMonth;

    private String payerCvv;

    private String transStatus;

    private String stepStatus;

    private String sysStatus;

    private String checkFlag;

    private String routeRetCode;

    private String routeRetMsg;

    private String routeTransResult;

    private String respCode;

    private String respMsg;

    private BigDecimal estimateFee;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    private BigDecimal sourceFee;

    private BigDecimal realFee;

    private BigDecimal transAmount;

    private BigDecimal transAmtTotal;

    private BigDecimal realTransAmount;

    public CollectInfo(Long id, String code, String sysSerno, String detailSerno, String routeType, String routeList, String routeNum, String transSerno, String routeSerno, Date transDate, String transTime, String transType, String payerCardNo, String payerCardType, String payerCardMobile, String payerName, String payerCertType, String payerCerNo, String payerExpYear, String payerExpMonth, String payerCvv, String transStatus, String stepStatus, String sysStatus, String checkFlag, String routeRetCode, String routeRetMsg, String routeTransResult, String respCode, String respMsg, BigDecimal estimateFee, String status, Date createTime, Date updateTime, String remark1, BigDecimal sourceFee, BigDecimal realFee, BigDecimal transAmount, BigDecimal transAmtTotal, BigDecimal realTransAmount) {
        this.id = id;
        this.code = code;
        this.sysSerno = sysSerno;
        this.detailSerno = detailSerno;
        this.routeType = routeType;
        this.routeList = routeList;
        this.routeNum = routeNum;
        this.transSerno = transSerno;
        this.routeSerno = routeSerno;
        this.transDate = transDate;
        this.transTime = transTime;
        this.transType = transType;
        this.payerCardNo = payerCardNo;
        this.payerCardType = payerCardType;
        this.payerCardMobile = payerCardMobile;
        this.payerName = payerName;
        this.payerCertType = payerCertType;
        this.payerCerNo = payerCerNo;
        this.payerExpYear = payerExpYear;
        this.payerExpMonth = payerExpMonth;
        this.payerCvv = payerCvv;
        this.transStatus = transStatus;
        this.stepStatus = stepStatus;
        this.sysStatus = sysStatus;
        this.checkFlag = checkFlag;
        this.routeRetCode = routeRetCode;
        this.routeRetMsg = routeRetMsg;
        this.routeTransResult = routeTransResult;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.estimateFee = estimateFee;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
        this.sourceFee = sourceFee;
        this.realFee = realFee;
        this.transAmount = transAmount;
        this.transAmtTotal = transAmtTotal;
        this.realTransAmount = realTransAmount;
    }

    public CollectInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getSysSerno() {
        return sysSerno;
    }

    public void setSysSerno(String sysSerno) {
        this.sysSerno = sysSerno == null ? null : sysSerno.trim();
    }

    public String getDetailSerno() {
        return detailSerno;
    }

    public void setDetailSerno(String detailSerno) {
        this.detailSerno = detailSerno == null ? null : detailSerno.trim();
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType == null ? null : routeType.trim();
    }

    public String getRouteList() {
        return routeList;
    }

    public void setRouteList(String routeList) {
        this.routeList = routeList == null ? null : routeList.trim();
    }

    public String getRouteNum() {
        return routeNum;
    }

    public void setRouteNum(String routeNum) {
        this.routeNum = routeNum == null ? null : routeNum.trim();
    }

    public String getTransSerno() {
        return transSerno;
    }

    public void setTransSerno(String transSerno) {
        this.transSerno = transSerno == null ? null : transSerno.trim();
    }

    public String getRouteSerno() {
        return routeSerno;
    }

    public void setRouteSerno(String routeSerno) {
        this.routeSerno = routeSerno == null ? null : routeSerno.trim();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime == null ? null : transTime.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getPayerCardNo() {
        return payerCardNo;
    }

    public void setPayerCardNo(String payerCardNo) {
        this.payerCardNo = payerCardNo == null ? null : payerCardNo.trim();
    }

    public String getPayerCardType() {
        return payerCardType;
    }

    public void setPayerCardType(String payerCardType) {
        this.payerCardType = payerCardType == null ? null : payerCardType.trim();
    }

    public String getPayerCardMobile() {
        return payerCardMobile;
    }

    public void setPayerCardMobile(String payerCardMobile) {
        this.payerCardMobile = payerCardMobile == null ? null : payerCardMobile.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public String getPayerCertType() {
        return payerCertType;
    }

    public void setPayerCertType(String payerCertType) {
        this.payerCertType = payerCertType == null ? null : payerCertType.trim();
    }

    public String getPayerCerNo() {
        return payerCerNo;
    }

    public void setPayerCerNo(String payerCerNo) {
        this.payerCerNo = payerCerNo == null ? null : payerCerNo.trim();
    }

    public String getPayerExpYear() {
        return payerExpYear;
    }

    public void setPayerExpYear(String payerExpYear) {
        this.payerExpYear = payerExpYear == null ? null : payerExpYear.trim();
    }

    public String getPayerExpMonth() {
        return payerExpMonth;
    }

    public void setPayerExpMonth(String payerExpMonth) {
        this.payerExpMonth = payerExpMonth == null ? null : payerExpMonth.trim();
    }

    public String getPayerCvv() {
        return payerCvv;
    }

    public void setPayerCvv(String payerCvv) {
        this.payerCvv = payerCvv == null ? null : payerCvv.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus == null ? null : stepStatus.trim();
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus == null ? null : sysStatus.trim();
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
    }

    public String getRouteRetCode() {
        return routeRetCode;
    }

    public void setRouteRetCode(String routeRetCode) {
        this.routeRetCode = routeRetCode == null ? null : routeRetCode.trim();
    }

    public String getRouteRetMsg() {
        return routeRetMsg;
    }

    public void setRouteRetMsg(String routeRetMsg) {
        this.routeRetMsg = routeRetMsg == null ? null : routeRetMsg.trim();
    }

    public String getRouteTransResult() {
        return routeTransResult;
    }

    public void setRouteTransResult(String routeTransResult) {
        this.routeTransResult = routeTransResult == null ? null : routeTransResult.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg == null ? null : respMsg.trim();
    }

    public BigDecimal getEstimateFee() {
        return estimateFee;
    }

    public void setEstimateFee(BigDecimal estimateFee) {
        this.estimateFee = estimateFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public BigDecimal getSourceFee() {
        return sourceFee;
    }

    public void setSourceFee(BigDecimal sourceFee) {
        this.sourceFee = sourceFee;
    }

    public BigDecimal getRealFee() {
        return realFee;
    }

    public void setRealFee(BigDecimal realFee) {
        this.realFee = realFee;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public BigDecimal getTransAmtTotal() {
        return transAmtTotal;
    }

    public void setTransAmtTotal(BigDecimal transAmtTotal) {
        this.transAmtTotal = transAmtTotal;
    }

    public BigDecimal getRealTransAmount() {
        return realTransAmount;
    }

    public void setRealTransAmount(BigDecimal realTransAmount) {
        this.realTransAmount = realTransAmount;
    }
}