package com.vilio.ppms.pojo.common;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsInfo {
    private Long id;

    private String code;

    private String goodsCode;

    private String routeGoodsCode;

    private String goodsName;

    private String merchantCode;

    private String goodsType;

    private BigDecimal goodsPrice;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String remark1;

    public GoodsInfo(Long id, String code, String goodsCode, String routeGoodsCode, String goodsName, String merchantCode, String goodsType, BigDecimal goodsPrice, String status, Date createTime, Date updateTime, String remark1) {
        this.id = id;
        this.code = code;
        this.goodsCode = goodsCode;
        this.routeGoodsCode = routeGoodsCode;
        this.goodsName = goodsName;
        this.merchantCode = merchantCode;
        this.goodsType = goodsType;
        this.goodsPrice = goodsPrice;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark1 = remark1;
    }

    public GoodsInfo() {
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getRouteGoodsCode() {
        return routeGoodsCode;
    }

    public void setRouteGoodsCode(String routeGoodsCode) {
        this.routeGoodsCode = routeGoodsCode == null ? null : routeGoodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
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