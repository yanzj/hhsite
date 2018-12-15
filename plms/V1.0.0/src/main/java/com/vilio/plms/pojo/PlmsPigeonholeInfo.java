package com.vilio.plms.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 归档材料表
 */
public class PlmsPigeonholeInfo implements Serializable{
    private int id;
    private String code;
    private String fileCode;
    private String contractCode;
    private String type;
    private String fileSuffix;
    private Date uploadTime;
    private String fileName;
    private String status;
    private Date createDate;
    private Date modifyDate;

    public PlmsPigeonholeInfo() {
    }

    public PlmsPigeonholeInfo(int id, String code, String fileCode, String contractCode, String type, Date uploadTime, String fileName, String status,String fileSuffix, Date createDate, Date modifyDate) {
        this.id = id;
        this.code = code;
        this.fileCode = fileCode;
        this.contractCode = contractCode;
        this.type = type;
        this.uploadTime = uploadTime;
        this.fileName = fileName;
        this.fileSuffix = fileSuffix;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
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

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
