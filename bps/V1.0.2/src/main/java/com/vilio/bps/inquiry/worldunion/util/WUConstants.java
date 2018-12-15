package com.vilio.bps.inquiry.worldunion.util;

/**
 * Created by dell on 2017/5/11/0011.
 */
public class WUConstants {

    /* API 登录token */
    public static String WU_API_LOGIN_TOKEN = "ew0KICAiVXNlckNvZGUiOiAiVGVzdCIsDQogICJVc2VyUGFzc1dvcmQiOiAiVGVzdDEyMyIsDQogICJJcEFkZHJlc3MiOiAiOjoxIiwNCiAgIkV4cGlyeURhdGUiOiAiMjAxNy0wOS0xM1QwOTozOTowNC40MDE2NDM0KzA4OjAwIg0KfQ==";

    /* API 消息头前缀 */
    public final static String WU_API_HEADER_PREFIX = "Basic ";


    /* API 登录 */
    public final static String WU_API_LOGIN = "/QueryPrice/Login";
    /* API 获取省份信息 */
    public final static String WU_API_GETPROVINCES = "/QueryPrice/Provinces";
    /* API 获取城市信息 */
    public final static String WU_API_GETCITYS = "/QueryPrice/Citys";
    /* API 获取区域信息 */
    public final static String WU_API_GETAREAS = "/QueryPrice/Areas";
    /* API 获取楼盘信息 */
    public final static String WU_API_GETCONSTRUCTION = "/QueryPrice/GetConstruction";
    /* API 根据ID获取楼盘信息 */
    public final static String WU_API_GETCONSTRUTIONVIEWINFOBYID = "/QueryPrice/GetConstrutionViewInfoById";
    /* API 获取楼栋信息 */
    public final static String WU_API_GETBUILD = "/QueryPrice/GetBuild";
    /* API 获取房号信息 */
    public final static String WU_API_GETHOUSE = "/QueryPrice/GetHouse";
    /* API 询价 */
    public final static String WU_API_AUTOPRICE = "/QueryPrice/AutoPrice";
    /* API 别墅询价 */
    public final static String WU_API_QUERY_PRICE = "/QueryPrice/GetQueryPrice";


    /* API 自动询价返回状态-可以自动估价 */
    public final static String WU_AUTOPRICE_STATUS_AVALIABLE = "1";
    /* API 自动询价返回状态-无法估价 */
    public final static String WU_AUTOPRICE_STATUS_UNAVALIABLE = "2";

}
