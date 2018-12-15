package com.vilio.plms.glob;

/**
 * Created by dell on 2017/5/15/0015.
 */
public enum InterfaceCode {

    PLMS_NOTHING_DEFAULT("  ", "无功能号"),

    /** 登录相关接口 */
    PLMS_USER_LOGIN("plms100001", "用户登录"),
    PLMS_CHANGEPSW_FIRSTLOGIN("plms100002", "用户初次登录修改密码"),
    PLMS_CHANGE_USER_INFO("plms100004", "修改用户信息"),
    PLMS_USER_LOGOUT("plms100003", "退出登录");


    private String functionNo;

    private String functionName;


    private InterfaceCode(String functionNo, String functionName) {
        this.functionNo = functionNo;
        this.functionName = functionName;
    }

    // 普通方法
    public static InterfaceCode getHHInterface(String functionNo) {
        for (InterfaceCode hhi : InterfaceCode.values()) {
            if (functionNo.equals(hhi.getFunctionNo())) {
                return hhi;
            }
        }
        return PLMS_NOTHING_DEFAULT;
    }

    //get set 方法


    public String getFunctionNo() {
        return functionNo;
    }

    public void setFunctionNo(String functionNo) {
        this.functionNo = functionNo;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public String toString() {
        return "[" + this.functionNo + "]:" + this.functionName;
    }


}
