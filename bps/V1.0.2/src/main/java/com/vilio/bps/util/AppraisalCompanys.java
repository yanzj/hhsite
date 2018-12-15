package com.vilio.bps.util;

/**
 * Created by dell on 2017/5/15/0015.
 */
public enum AppraisalCompanys {

    WORLD_UNION("001", "世联"),

    SH_CHENGSHI("002", "上海城市");

    private String code;

    private String name;


    private AppraisalCompanys(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static AppraisalCompanys getACEnumByIndex(String code) {
        for (AppraisalCompanys ac : AppraisalCompanys.values()) {
            if (code.equals(ac.getCode())) {
                return ac;
            }
        }
        return null;
    }

    //get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + "-" + this.name;
    }


}
