package com.vilio.plms.dao;

import com.vilio.plms.pojo.ApplyInfo;

import java.util.Map;

/**
 * 类名： ApplyInfoDao<br>
 * 功能：进件申请信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface ApplyInfoDao {

    //查询进件申请信息表
    public ApplyInfo qryApplyInfo(ApplyInfo applyInfo);

    //新增
    public void insert(Map param);

    //更新
    public void update(Map param);

    //新增附件
    public void insertMaterial(Map param);

    public ApplyInfo qryApplyInfoByBmsCode(String bmsCode);
}
