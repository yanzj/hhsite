package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/26.
 */
//进件状态
public class NlbsApplyLoanStatus {
    private String id;
    //本地状态值
    private String code;
    //状态名
    private String name;
    //核心系统状态值
    private String bmsCode;
    //核心系统状态名
    private String bmsName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBmsCode() {
        return bmsCode;
    }

    public void setBmsCode(String bmsCode) {
        this.bmsCode = bmsCode;
    }

    public String getBmsName() {
        return bmsName;
    }

    public void setBmsName(String bmsName) {
        this.bmsName = bmsName;
    }
}
