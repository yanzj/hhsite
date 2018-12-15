package com.vilio.mps.push.dao;

import com.vilio.mps.push.pojo.Umeng;

import java.util.List;
import java.util.Map;

/**
 * 类名： UmengDao<br>
 * 功能：友盟Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface UmengDao {

    public int insertUmengBatch(List<Umeng> umengs);
    public int updateUmengBatch(Map umengs);

}
