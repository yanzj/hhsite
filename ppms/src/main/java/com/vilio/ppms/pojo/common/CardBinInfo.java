package com.vilio.ppms.pojo.common;

import java.util.Date;

public class CardBinInfo {
    private Long id;

    private String code;

    private String issuerCode;

    private String issuserName;

    private String cardName;

    private String cardNoStartPos;

    private String cardNoLen;

    private String binLen;

    private String binNum;

    private String cardType;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public CardBinInfo(Long id, String code, String issuerCode, String issuserName, String cardName, String cardNoStartPos, String cardNoLen, String binLen, String binNum, String cardType, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.issuerCode = issuerCode;
        this.issuserName = issuserName;
        this.cardName = cardName;
        this.cardNoStartPos = cardNoStartPos;
        this.cardNoLen = cardNoLen;
        this.binLen = binLen;
        this.binNum = binNum;
        this.cardType = cardType;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public CardBinInfo() {
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

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode == null ? null : issuerCode.trim();
    }

    public String getIssuserName() {
        return issuserName;
    }

    public void setIssuserName(String issuserName) {
        this.issuserName = issuserName == null ? null : issuserName.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public String getCardNoStartPos() {
        return cardNoStartPos;
    }

    public void setCardNoStartPos(String cardNoStartPos) {
        this.cardNoStartPos = cardNoStartPos == null ? null : cardNoStartPos.trim();
    }

    public String getCardNoLen() {
        return cardNoLen;
    }

    public void setCardNoLen(String cardNoLen) {
        this.cardNoLen = cardNoLen == null ? null : cardNoLen.trim();
    }

    public String getBinLen() {
        return binLen;
    }

    public void setBinLen(String binLen) {
        this.binLen = binLen == null ? null : binLen.trim();
    }

    public String getBinNum() {
        return binNum;
    }

    public void setBinNum(String binNum) {
        this.binNum = binNum == null ? null : binNum.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
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
}