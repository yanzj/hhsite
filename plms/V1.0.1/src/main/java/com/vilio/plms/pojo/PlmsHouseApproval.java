package com.vilio.plms.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/8/14.
 */
public class PlmsHouseApproval implements Serializable{
    private int id;
    private String code;
    private BigDecimal approveAmount;
    private int approvePeroid;
    private BigDecimal houseValue;
    private String houseCode;
    private Date createDate;
    private Date modifyDate;
    private String status;
    private BigDecimal guaranteeLimit;
    private BigDecimal mortgageTotalAmount;

    public PlmsHouseApproval() {
    }

    public PlmsHouseApproval(int id, String code, BigDecimal approveAmount, int approvePeroid, BigDecimal houseValue, String houseCode, Date createDate, Date modifyDate, String status, BigDecimal guaranteeLimit, BigDecimal mortgageTotalAmount) {
        this.id = id;
        this.code = code;
        this.approveAmount = approveAmount;
        this.approvePeroid = approvePeroid;
        this.houseValue = houseValue;
        this.houseCode = houseCode;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.status = status;
        this.guaranteeLimit = guaranteeLimit;
        this.mortgageTotalAmount = mortgageTotalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(BigDecimal approveAmount) {
        this.approveAmount = approveAmount;
    }

    public int getApprovePeroid() {
        return approvePeroid;
    }

    public void setApprovePeroid(int approvePeroid) {
        this.approvePeroid = approvePeroid;
    }

    public BigDecimal getHouseValue() {
        return houseValue;
    }

    public void setHouseValue(BigDecimal houseValue) {
        this.houseValue = houseValue;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getGuaranteeLimit() {
        return guaranteeLimit;
    }

    public void setGuaranteeLimit(BigDecimal guaranteeLimit) {
        this.guaranteeLimit = guaranteeLimit;
    }

    public BigDecimal getMortgageTotalAmount() {
        return mortgageTotalAmount;
    }

    public void setMortgageTotalAmount(BigDecimal mortgageTotalAmount) {
        this.mortgageTotalAmount = mortgageTotalAmount;
    }
}
