package com.vilio.nlbs.util;

/**
 * Created by dell on 2017/5/15/0015.
 */
public enum HHInterface {

    HH_NOTHING_DEFAULT("  ", "无功能号"),


    HH_USER_LOGIN("HH000001", "用户登录"),
    HH_CHANGEPSW_FIRSTLOGIN("HH000002", "用户初次登录修改密码"),
    HH_CHANGE_USER_INFO("HH000003", "修改用户信息"),
    HH_USER_LOGOUT("HH000004", "退出登录"),

    HH_INIT_APPLY_SUBMIT("HH000005", "初始化进件申请"),
    HH_INIT_APPLY_MODIFY("HH000006", "初始化修改进件申请"),
    HH_GETLIST_AGENT_PRODUCT_BY_DISTRIBUTOR("HH000007", "根据渠道获取业务经理和产品列表"),
    HH_SUBMIT_APPLY_RECORD("HH000008", "提交保存进件申请"),
    HH_MODIFY_APPLY_RECORD("HH000009", "修改进件提交"),
    HH_QUERY_INFO_APPLY_RECORD("HH000010", "查询进件申请详情"),
    HH_GETLIST_APPLY_RECORD("HH000011", "获取进件申请列表"),
    HH_GETLIST_MATERIAL_TYPE("HH000012", "获取所有进件材料类型"),
    //HH_GETLIST_APPLY_RECORD_STATUS("HH000013", "获取所有进件申请单状态"),
    HH_INIT_SUPPLY_APPLY_MATERIAL("HH000014", "初始化补充进件材料"),
    HH_SUPPLY_APPLY_MATERIAL("HH000015", "补充进件材料"),
    HH_INIT_APPLY_LIST("HH000016", "初始化进件列表"),
    HH_DELETE_APPLY_RECORD("HH000023", "删除暂存的进件申请"),

    /* 透传至询价系统接口 */
    //HH_HH000101("HH000101", "询价初始化（城市、房屋类型列表）"),
    HH_INIT_INQUIRY("HH000101", "询价初始化（城市、房屋类型列表）"),
    HH_HH000102("HH000102", "根据城市获取关联估价公司列表"),
    HH_HH000103("HH000103", "根据城市获取小区（楼盘列表）"),
    HH_HH000104("HH000104", "根据小区（楼盘）获取楼栋列表"),
    HH_HH000105("HH000105", "根据楼栋号获取房间号列表"),
    HH_HH000106("HH000106", "根据城市获取行政区域列表"),
    //HH_HH000121("HH000121", "询价列表初始化"),
    HH_INIT_INQUIRY_LIST("HH000121", "询价列表初始化"),
    HH_HH000122("HH000122","录入评估价初始化"),
    /* 询价系统亦有的接口，本系统做部分拆封包后透传的接口 */
    HH_HH000110("HH000110", "公寓估价"),
    HH_HH000109("HH000109", "别墅估价"),
    HH_HH000107("HH000107", "人工估价"),
    HH_QUERY_INQUIRY_INFO_DETAILS("HH000120", "查询询价记录详情"),
    HH_INPUT_INQUIRY_PRICE("HH000123","录入评估价提交(待评估提交)"),

    /* 本系统特有的询价相关接口 */
    HH_QUERY_INQUIRY_LIST("HH000034", "查询询价记录列表"),
    HH_QUERY_OPERATION_HISTORY_LIST("HH000036", "查询询价操作历史列表"),
    HH_UPDATE_INQUIRY_RESULT("HH000037", "更新询价结果（供回调）"),
    HH_CLAIM_INQUIRY_TASK("HH000038", "认领评估单"),
    HH_QUERY_TODO_TASK_LIST("HH000041", "查询待办任务列表"),
    HH_INIT_TODO_TASK_LIST("HH000042", "初始化待办任务列表"),
    HH_QUERY_TODO_MSG_TIPS_LIST("HH000040", "获取待办任务和消息提醒列表"),


    HH_GETLIST_MESSAGE("HH000017", "查询站内消息列表"),
    HH_QUERY_INFO_MESSAGE("HH000018", "查询站内消息详情"),
    HH_MODIFY_RECEIVE_MESSAGE("HH000019", "修改站内消息状态(Receive)"),
    HH_RECEIVE_MESSAGE("HH000020", "接收站内消息"),
    HH_QUERY_OPERATION_HISTORY("HH000021", "查询操作历史"),
    HH_MODIFY_SEND_MESSAGE("HH000022", "修改站内消息状态(Send)");


    private String functionNo;

    private String functionName;


    private HHInterface(String functionNo, String functionName) {
        this.functionNo = functionNo;
        this.functionName = functionName;
    }

    // 普通方法
    public static HHInterface getHHInterface(String functionNo) {
        for (HHInterface hhi : HHInterface.values()) {
            if (functionNo.equals(hhi.getFunctionNo())) {
                return hhi;
            }
        }
        return HH_NOTHING_DEFAULT;
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
