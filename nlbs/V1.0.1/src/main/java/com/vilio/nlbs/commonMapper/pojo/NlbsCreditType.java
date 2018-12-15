package com.vilio.nlbs.commonMapper.pojo;

public class NlbsCreditType {
    private Integer id;

    private String code;

    private String name;

    public NlbsCreditType(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public NlbsCreditType() {
        super();
    }

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
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}