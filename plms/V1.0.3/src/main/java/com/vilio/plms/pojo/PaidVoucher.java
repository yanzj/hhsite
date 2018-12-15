package com.vilio.plms.pojo;

/**
 * 类名： PaidVoucher<br>
 * 功能：还款凭证实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class PaidVoucher {

    private Integer id;
    private String code;
    private String fileCode;
    private String status;
    private String createDate;
    private String modifyDate;
    private String repaymentApplyCode;

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

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public PaidVoucher() {
    }

    public PaidVoucher(Integer id, String code, String fileCode, String status, String createDate, String modifyDate, String repaymentApplyCode) {
        this.id = id;
        this.code = code;
        this.fileCode = fileCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.repaymentApplyCode = repaymentApplyCode;
    }
}
