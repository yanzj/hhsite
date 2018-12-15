package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class SuningpayTransInfoLog {
    private Long id;

    private String code;

    private String snTransType;

    private String snAcctSerno;

    private String snTransSerno;

    private String snGoodsOrderNo;

    private Date snOrderCreateTime;

    private String snOpposite;

    private String snOrderNo;

    private String snOrderName;

    private Date snReceiptsTime;

    private BigDecimal snReceiptsAmount;

    private BigDecimal snExpenseAmount;

    private BigDecimal snAountBalance;

    private String snPayChl;

    private String snRemark;

    private String snMerhantOrderNo;

    private String checkFlag;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public SuningpayTransInfoLog(Long id, String code, String snTransType, String snAcctSerno, String snTransSerno, String snGoodsOrderNo, Date snOrderCreateTime, String snOpposite, String snOrderNo, String snOrderName, Date snReceiptsTime, BigDecimal snReceiptsAmount, BigDecimal snExpenseAmount, BigDecimal snAountBalance, String snPayChl, String snRemark, String snMerhantOrderNo, String checkFlag, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.snTransType = snTransType;
        this.snAcctSerno = snAcctSerno;
        this.snTransSerno = snTransSerno;
        this.snGoodsOrderNo = snGoodsOrderNo;
        this.snOrderCreateTime = snOrderCreateTime;
        this.snOpposite = snOpposite;
        this.snOrderNo = snOrderNo;
        this.snOrderName = snOrderName;
        this.snReceiptsTime = snReceiptsTime;
        this.snReceiptsAmount = snReceiptsAmount;
        this.snExpenseAmount = snExpenseAmount;
        this.snAountBalance = snAountBalance;
        this.snPayChl = snPayChl;
        this.snRemark = snRemark;
        this.snMerhantOrderNo = snMerhantOrderNo;
        this.checkFlag = checkFlag;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public SuningpayTransInfoLog() {
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

    public String getSnTransType() {
        return snTransType;
    }

    public void setSnTransType(String snTransType) {
        this.snTransType = snTransType == null ? null : snTransType.trim();
    }

    public String getSnAcctSerno() {
        return snAcctSerno;
    }

    public void setSnAcctSerno(String snAcctSerno) {
        this.snAcctSerno = snAcctSerno == null ? null : snAcctSerno.trim();
    }

    public String getSnTransSerno() {
        return snTransSerno;
    }

    public void setSnTransSerno(String snTransSerno) {
        this.snTransSerno = snTransSerno == null ? null : snTransSerno.trim();
    }

    public String getSnGoodsOrderNo() {
        return snGoodsOrderNo;
    }

    public void setSnGoodsOrderNo(String snGoodsOrderNo) {
        this.snGoodsOrderNo = snGoodsOrderNo == null ? null : snGoodsOrderNo.trim();
    }

    public Date getSnOrderCreateTime() {
        return snOrderCreateTime;
    }

    public void setSnOrderCreateTime(Date snOrderCreateTime) {
        this.snOrderCreateTime = snOrderCreateTime;
    }

    public String getSnOpposite() {
        return snOpposite;
    }

    public void setSnOpposite(String snOpposite) {
        this.snOpposite = snOpposite == null ? null : snOpposite.trim();
    }

    public String getSnOrderNo() {
        return snOrderNo;
    }

    public void setSnOrderNo(String snOrderNo) {
        this.snOrderNo = snOrderNo == null ? null : snOrderNo.trim();
    }

    public String getSnOrderName() {
        return snOrderName;
    }

    public void setSnOrderName(String snOrderName) {
        this.snOrderName = snOrderName == null ? null : snOrderName.trim();
    }

    public Date getSnReceiptsTime() {
        return snReceiptsTime;
    }

    public void setSnReceiptsTime(Date snReceiptsTime) {
        this.snReceiptsTime = snReceiptsTime;
    }

    public BigDecimal getSnReceiptsAmount() {
        return snReceiptsAmount;
    }

    public void setSnReceiptsAmount(BigDecimal snReceiptsAmount) {
        this.snReceiptsAmount = snReceiptsAmount;
    }

    public BigDecimal getSnExpenseAmount() {
        return snExpenseAmount;
    }

    public void setSnExpenseAmount(BigDecimal snExpenseAmount) {
        this.snExpenseAmount = snExpenseAmount;
    }

    public BigDecimal getSnAountBalance() {
        return snAountBalance;
    }

    public void setSnAountBalance(BigDecimal snAountBalance) {
        this.snAountBalance = snAountBalance;
    }

    public String getSnPayChl() {
        return snPayChl;
    }

    public void setSnPayChl(String snPayChl) {
        this.snPayChl = snPayChl == null ? null : snPayChl.trim();
    }

    public String getSnRemark() {
        return snRemark;
    }

    public void setSnRemark(String snRemark) {
        this.snRemark = snRemark == null ? null : snRemark.trim();
    }

    public String getSnMerhantOrderNo() {
        return snMerhantOrderNo;
    }

    public void setSnMerhantOrderNo(String snMerhantOrderNo) {
        this.snMerhantOrderNo = snMerhantOrderNo == null ? null : snMerhantOrderNo.trim();
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
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