package com.vilio.bps.commonMapper.pojo;

import java.io.Serializable;

/**
 * Created by dell on 2017/6/16.
 */
public class BpsInquiryApplyStatus  implements Serializable {
    //
    private Integer id;
    //
    private String code;
    //状态值
    private String value;
    //状态名称
    private String name;
    //是否可用
    private String status;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
