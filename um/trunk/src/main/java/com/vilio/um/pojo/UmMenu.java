package com.vilio.um.pojo;

import com.vilio.comm.pojo.Menu;

import java.util.List;

/**
 * 类名： UmMenu<br>
 * 功能：菜单对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UmMenu extends Menu{

    public List<UmMenu> childMenus;

    public List<UmMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<UmMenu> childMenus) {
        this.childMenus = childMenus;
    }

    public UmMenu() {
    }

    public UmMenu(Integer id, String menuId, String menuName, String superId, String status, String menuDesc, String url, String systemId, String createTime, List<UmMenu> childMenus) {
        super(id, menuId, menuName, superId, status, menuDesc, url, systemId, createTime);
        this.childMenus = childMenus;
    }
}
