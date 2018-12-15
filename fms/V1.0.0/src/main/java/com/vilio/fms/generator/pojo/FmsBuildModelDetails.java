package com.vilio.fms.generator.pojo;

/**
 * Created by dell on 2017/7/6/0006.
 */
public class FmsBuildModelDetails {

    private String id;

    private String code;

    private String serialNo;

    private String modelCode;

    private String dateCreated;

    private String fileName;

    private String fileCode;

    private String requiredParam;

    private String mortgageType;

    private String certificateNumberFirst;

    private String certificateNumberSecond;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getRequiredParam() {
        return requiredParam;
    }

    public void setRequiredParam(String requiredParam) {
        this.requiredParam = requiredParam;
    }

    public String getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(String mortgageType) {
        this.mortgageType = mortgageType;
    }

    public String getCertificateNumberFirst() {
        return certificateNumberFirst;
    }

    public void setCertificateNumberFirst(String certificateNumberFirst) {
        this.certificateNumberFirst = certificateNumberFirst;
    }

    public String getCertificateNumberSecond() {
        return certificateNumberSecond;
    }

    public void setCertificateNumberSecond(String certificateNumberSecond) {
        this.certificateNumberSecond = certificateNumberSecond;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
