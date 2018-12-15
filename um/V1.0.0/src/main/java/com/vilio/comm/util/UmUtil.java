package com.vilio.comm.util;

import com.vilio.comm.glob.GlobParam;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wangxf on 2017/6/15.
 */
public class UmUtil {

    private static final Logger logger = Logger.getLogger(UmUtil.class);


    /**
     * 通用返回方法
     *
     * @param request
     * @param response
     * @param result
     */
    public static void returnData(HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> result) {
        PrintWriter pw = null;
        String respMessage = null;
        Map<String, Object> head = (Map<String, Object>) result.get(GlobParam.PARAM_MESSAGE_HEAD);
        respMessage = JsonUtil.objectToJson(result);
        logger.info("返回的参数为："+respMessage);
        try {
            if (respMessage != null) {
                pw = response.getWriter();
                pw.print(respMessage);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("返回信息失败！");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     *数组形式菜单改成树形结构
     * @param rootMenu
     * @return
     */
    public static List<Map<String,Object>> menuListToTree(List<Map<String,Object>> rootMenu) {

        List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单没有superId
            if (rootMenu.get(i).get("superId")==null||"".equals(rootMenu.get(i).get("superId"))) {
                menuList.add(rootMenu.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Map<String,Object> menu : menuList) {
            menu.put("childMenus",getChild(menu.get("menuId").toString(), rootMenu));
        }
        return menuList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private static List<Map<String,Object>> getChild(String id, List<Map<String,Object>> rootMenu) {
        // 子菜单
        List<Map<String,Object>> childList = new ArrayList<>();
        for (Map<String,Object> menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.get("superId")!=null&&!"".equals(menu.get("superId").toString())){
                if (menu.get("superId").equals(id)){
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Map<String,Object> menu : childList) {
            //有上级id的子菜单还有子菜单
            if (menu.get("superId")!=null&&!"".equals(menu.get("superId").toString())) {
                // 递归
                menu.put("childMenus",getChild(menu.get("menuId").toString(),rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    public static void main(String[] args) {
        System.out.println(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
    }
}
