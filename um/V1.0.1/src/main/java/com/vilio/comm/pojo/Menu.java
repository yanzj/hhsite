package com.vilio.comm.pojo;

/**
 * 类名： Menu<br>
 * 功能：菜单对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class Menu {

    public Integer id;
    public String menuId;
    public String menuName;
    public String superId;
    public String status;
    public String menuDesc;
    public String url;
    public String systemId;
    public String createTime;

    public Integer getId() {
        return id;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getSuperId() {
        return superId;
    }

    public String getStatus() {
        return status;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public String getUrl() {
        return url;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getCreateTime() {
        return createTime;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Menu() {
    }

    public Menu(Integer id, String menuId, String menuName, String superId, String status, String menuDesc, String url, String systemId, String createTime) {
        this.id = id;
        this.menuId = menuId;
        this.menuName = menuName;
        this.superId = superId;
        this.status = status;
        this.menuDesc = menuDesc;
        this.url = url;
        this.systemId = systemId;
        this.createTime = createTime;
    }
}
