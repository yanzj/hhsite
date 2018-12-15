package com.vilio.plms.dao;


import com.vilio.plms.pojo.Iou;

import java.util.Map;

/**
 * 类名： IouDao<br>
 * 功能：借款借据Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface IouDao {

    //保存借款借据
    public int saveIou(Iou iou);

    int updateIouStats(Map map);

}
