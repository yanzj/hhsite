package com.vilio.bps.inquiry.shchengshi.util;

/**
 * 上海城市 -  属性类
 */
public class SCFields {
    //请求关键字
    public final static String REQUEST_KEY = "Key";

    //返回 小区ID 或者 楼栋id
    public final static String RESPONSE_ID = "Id";

    //小区名称 (string)
    public final static String PROJECT_NAME = "ProjectName";
    //小区地址 (string)
    public final static String ADDRESS = "Address";
    //小区类型  (int) 1:公寓， 2：别墅， 3：混合， 4，新里洋房， 6： 农民自建别墅小区， 7： 工房小区
    public final static String PROJECT_TYPE = "ProjectType";
    //小区Id  (int)
    public final static String PROJECT_ID = "ProjectId";

    //楼栋 Id(int)
    public final static String UNIT_ID = "UnitId";
    //楼栋号(string)
    public final static String UNIT_NO= "UnitNo";
    //室号(string)
    public final static String ROOM_NO = "RoomNo";
    //朝向(int)（东:1 南:2 西:3 北:4）
    public final static String TOWARDS = "Towards";
    //所在层(int)
    public final static String FLOOR = "Floor";
    //总楼层(int)
    public final static String TOTAL_FLOOR = "TotalFloor";
    //建筑面积(float)   保留两位小数
    public final static String AREA = "Area";
    //建造年代(int)
    public final static String YEAR = "Year";

    //房屋价格(float)
    public final static String SC_A_PRICE = "Price";

}
