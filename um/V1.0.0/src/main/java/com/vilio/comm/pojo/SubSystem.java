package com.vilio.comm.pojo;

/**
 * 类名： SubSystem<br>
 * 功能：子系统对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class SubSystem {

    public Integer id;
    public String systemId;
    public String systemName;
    public String status;
    public String systemDesc;
    public String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemDesc() {
        return systemDesc;
    }

    public void setSystemDesc(String systemDesc) {
        this.systemDesc = systemDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public SubSystem() {
    }

    public SubSystem(Integer id, String systemId, String systemName, String status, String systemDesc, String createTime) {
        this.id = id;
        this.systemId = systemId;
        this.systemName = systemName;
        this.status = status;
        this.systemDesc = systemDesc;
        this.createTime = createTime;
    }
}
