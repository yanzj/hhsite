package com.vilio.plms.dao;

import com.vilio.plms.pojo.ApplyInteresting;

import java.util.Map;

/**
 * 类名： ApplyInterestingDao<br>
 * 功能：进件利息信息表Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface ApplyInterestingDao {

    //查询进件申请信息表
    public ApplyInteresting qryApplyInteresting(ApplyInteresting applyInteresting);
    int saveApplyInteresting(ApplyInteresting applyInteresting);

    //新增
    public void insert(Map param);

}
