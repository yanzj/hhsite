package com.vilio.plms.pojo;

/**
 * 类名： BorrowVoucher<br>
 * 功能：放款凭证实体类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BorrowVoucher {

    private Integer id;
    private String code;
    private String fileCode;
    private String status;
    private String createDate;
    private String modifyDate;
    private String paidInfoCode;
    private String fileSuffix;
    private String fileName;
    private String uploadTime;

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

    public String getPaidInfoCode() {
        return paidInfoCode;
    }

    public void setPaidInfoCode(String paidInfoCode) {
        this.paidInfoCode = paidInfoCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }


    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public BorrowVoucher() {
    }

    public BorrowVoucher(Integer id, String code, String fileCode, String status, String createDate, String modifyDate, String paidInfoCode,String fileSuffix) {
        this.id = id;
        this.code = code;
        this.fileCode = fileCode;
        this.fileSuffix = fileSuffix;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.paidInfoCode = paidInfoCode;
    }

    public BorrowVoucher(Integer id, String code, String fileCode, String status, String createDate, String modifyDate, String paidInfoCode, String fileName, String uploadTime,String fileSuffix) {
        this.id = id;
        this.code = code;
        this.fileCode = fileCode;
        this.fileSuffix = fileSuffix;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.paidInfoCode = paidInfoCode;
        this.fileName = fileName;
        this.uploadTime = uploadTime;
    }
}
