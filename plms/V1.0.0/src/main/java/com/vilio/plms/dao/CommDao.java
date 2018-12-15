package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsFileModels;
import com.vilio.plms.pojo.SysParam;

import java.util.List;
import java.util.Map;

/**
 * 类名： CommDao<br>
 * 功能：公共Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CommDao {

    //序列增加
    public int nextval(String seqName);

    //查询序列
    public Long currval(String seqName);

    //获取全局唯一编码
    public String getUUID();

    //获取系统参数
    public SysParam getSysParam(String itemId);
    //设置系统参数
    public int setItemVal(SysParam sysParam);

    //获取模板文件信息
    public PlmsFileModels getFileModelByType(String type);

    public List<Map> getParentAgentByAgentCode(String agentCode);

}
