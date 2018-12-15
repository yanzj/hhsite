package com.vilio.bps.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/16.
 */
public class Constants {

    /**
     * 渠道代码，全国
     */
    public final static String CITY_CODE_QUANGUO = "000000";

    /* 城市 -- 上海 */
    public final static String CITY_CODE_SHANGHAI = "310100";

    /* 房型 - 别墅 */
    public final static String HOUSE_TYPE_VILLADOM = "002";
    /* 房型 - 公寓 */
    public final static String HOUSE_TYPE_ORDINARY= "001";


    /* 询价订单状态  待评估  */
    public final static String BPS_ORDER_STATUS_PENDING_EVALUATION = "00";
    /* 询价订单状态  评估中  */
    public final static String BPS_ORDER_STATUS_EVALUATING = "01";
    /* 询价订单状态  已评估  */
    public final static String BPS_ORDER_STATUS_EVALUATED = "02";
    /* 询价订单状态  评估失败  */
    public final static String BPS_ORDER_STATUS_FALE_EVALUATED = "03";
    /* 询价订单状态  评估失效  */
    public final static String BPS_ORDER_STATUS_EVALUATION_INVALID = "99";

    /* 估价公司 合作类型 - 0 人工 */
    public final static String APPRAISAL_COMAPNY_COOPERATION_TYPE_MAN = "0";
    /* 估价公司 合作类型 - 1 极速 */
    public final static String APPRAISAL_COMAPNY_COOPERATION_TYPE_QUICK = "1";
    /* 估价公司 合作类型 - 2 极速+人工 */
    public final static String APPRAISAL_COMAPNY_COOPERATION_TYPE_MIX = "2";

    /**
     * 是否要求所有询价公司必须有结果(Y代表必须都有结果)
     */
    public static String CONFIG_NEED_ALL_RESULT = "needAllResult";
    public static String CONFIG_NEED_ALL_RESULT_VALUE_YES = "Y";
    /**
     * 差价百分比阈值
     */
    public static String CONFIG__PERCENT_OF_DIFF_THRESHOLD = "percentOfDiffThreshold";
    /**
     * 小于差价百分比阈值是算法
     */
    public static String CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD = "lessPercentOfDiffThreshold";
    /**
     * 大于等于差价百分比阈值是算法
     */
    public static String CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD = "morePercentOfDiffThreshold";
    //评估有效时间阈值
    public static String CONFIG__EFFECTIVE_HOUR_LIMIT = "effectiveHourLimit";
    /**
     * 浮动百分比
     */
    public static String CONFIG__FLOAT_PERCENT = "lowestUpPercent";
    /**
     * 大于等于差价百分比阈值是否转人工
     */
    public static String CONFIG__OVER_PERCENT_TURN_ARTIFICIAL = "overPercentTurnArtificial";
    public static String CONFIG__OVER_PERCENT_TURN_ARTIFICIAL_VALUE_YES = "Y";

    /**
     * 客服电话
     */
    public final static String CUSTOMER_SERVICE_PHONE = "01010000";

    //状态 ：0 不可用
    public final static String BPS_STATUS_UNAVALIABLE = "0";
    //状态 ：1 可用
    public final static String BPS_STATUS_AVALIABLE = "1";


    /* 世联-别墅评估时的访问类型 -- 0 新请求 */
    public final static String WU_TYPE_CODE_NEW_REQUEST = "0";
    /* 世联-别墅评估时的访问类型 -- 1 获取（人工）结果 */
    public final static String WU_TYPE_CODE_GET_RESULT = "1";

    /* 人工给值 0*/
    public final static String APPLY_AUTO_PRICE_MAN = "0";
    /* 1 询价公司取值*/
    public final static String APPLY_AUTO_PRICE_COMPANY = "1";


}
