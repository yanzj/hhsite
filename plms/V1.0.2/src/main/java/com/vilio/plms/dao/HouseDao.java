package com.vilio.plms.dao;

import com.vilio.plms.pojo.House;

import java.util.List;
import java.util.Map;

/**
 * 类名： HouseDao<br>
 * 功能：抵押物Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface HouseDao {

    //查询抵押物信息表
    public House qryHouse(House house);

    //查询抵押物信息表（用户抵押物信息信息查询）
    public List<House> qryHouseForContract(String contractCode);

    public Map qryHouseByCode(String houseCode);

    //新增
    public void insert(Map param);

    int saveHouseInfo(House house);

}
