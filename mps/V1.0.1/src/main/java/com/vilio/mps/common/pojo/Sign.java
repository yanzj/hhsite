package com.vilio.mps.common.pojo;

/**
 * 类名： Sign<br>
 * 功能：签名实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：其他消息有可能也有用到签名实体和模板实体<br>
 */
public class Sign {

    private Integer id;
    private String innerSignNo;
    private String chlSignNo;
    private String signName;
    private String signDesc;
    private String status;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInnerSignNo() {
        return innerSignNo;
    }

    public void setInnerSignNo(String innerSignNo) {
        this.innerSignNo = innerSignNo;
    }

    public String getChlSignNo() {
        return chlSignNo;
    }

    public void setChlSignNo(String chlSignNo) {
        this.chlSignNo = chlSignNo;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignDesc() {
        return signDesc;
    }

    public void setSignDesc(String signDesc) {
        this.signDesc = signDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Sign(Integer id, String innerSignNo, String chlSignNo, String signName, String signDesc, String status, String createTime) {
        this.id = id;
        this.innerSignNo = innerSignNo;
        this.chlSignNo = chlSignNo;
        this.signName = signName;
        this.signDesc = signDesc;
        this.status = status;
        this.createTime = createTime;
    }

    public Sign() {
    }
}
