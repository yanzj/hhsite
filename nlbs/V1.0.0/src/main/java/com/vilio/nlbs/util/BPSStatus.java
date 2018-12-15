package com.vilio.nlbs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/15/0015.
 */
public enum BPSStatus {

    ORDER_STATUS_PENDING_EVALUATION("00", "待评估"),
    ORDER_STATUS_EVALUATING("01", "评估中"),
    ORDER_STATUS_EVALUATED("02", "已评估"),
    ORDER_STATUS_EVALUATION_INVALID("99", "评估失效"),
    ORDER_STATUS_DEFAULT("","");

    private String code;

    private String name;


    private BPSStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static BPSStatus getBPSStatusByCode(String code) {
        for (BPSStatus ac : BPSStatus.values()) {
            if (code.equals(ac.getCode())) {
                return ac;
            }
        }
        return ORDER_STATUS_DEFAULT;
    }

    public static String getNameByCode(String code) {
        if(code == null){
            return "";
        }
        for (BPSStatus ac : BPSStatus.values()) {
            if (code.equals(ac.getCode())) {
                return ac.getName();
            }
        }
        return "";
    }

    public static String getCodeByName(String name) {
        for (BPSStatus ac : BPSStatus.values()) {
            if (name.equals(ac.getName())) {
                return ac.getCode();
            }
        }
        return "";
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

    public static List<Map> toList(){
        List<Map> statusList = new ArrayList<Map>();
        for (BPSStatus bpss : BPSStatus.values()) {
            if(bpss == ORDER_STATUS_DEFAULT || bpss == ORDER_STATUS_EVALUATION_INVALID){
                continue;
            }
            Map statusMap = new HashMap();
            statusMap.put(Fields.PARAM_STATUS, bpss.getCode());
            statusMap.put(Fields.PARAM_NAME, bpss.getName());
            statusList.add(statusMap);
        }

        return statusList;
    }

}
