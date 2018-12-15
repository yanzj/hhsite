package com.vilio.nlbs.commonMapper.pojo;

public class NlbsLoanPeriod {
    private int id;

    private String code;

    private int period;

    public NlbsLoanPeriod(int id, String code, int period) {
        this.id = id;
        this.code = code;
        this.period = period;
    }

    public NlbsLoanPeriod() {
        super();
    }

    public int getId() {
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}