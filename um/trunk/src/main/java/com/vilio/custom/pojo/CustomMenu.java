package com.vilio.custom.pojo;

import com.vilio.comm.pojo.Menu;

import java.util.List;

/**
 * 类名： CustomMenu<br>
 * 功能：菜单对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class CustomMenu extends Menu{

    public List<CustomMenu> childMenus;

    public List<CustomMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<CustomMenu> childMenus) {
        this.childMenus = childMenus;
    }

    public CustomMenu() {
    }

    public CustomMenu(Integer id, String menuId, String menuName, String superId, String status, String menuDesc, String url, String systemId, String createTime, List<CustomMenu> childMenus) {
        super(id, menuId, menuName, superId, status, menuDesc, url, systemId, createTime);
        this.childMenus = childMenus;
    }
}
