package com.vilio.um.pojo;

import com.vilio.comm.pojo.SubSystem;

/**
 * 类名： SubSystem<br>
 * 功能：子系统对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UmSubSystem extends SubSystem {

    public UmSubSystem() {
    }

    public UmSubSystem(Integer id, String systemId, String systemName, String status, String systemDesc, String createTime) {
        super(id, systemId, systemName, status, systemDesc, createTime);
    }

}
