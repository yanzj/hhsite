package com.vilio.um.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.BeanUtil;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.um.dao.UmUserDao;
import com.vilio.um.pojo.UmUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100005<br>
 * 功能：查询用户上下级关系业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100005 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100005.class);

    @Resource
    private UmUserDao umUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验查询类型必填
        if (!GlobDict.user_tree_all.getKey().equals(body.get("type")) && !GlobDict.user_tree_subordinate.getKey().equals(body.get("type"))
                && !GlobDict.user_tree_super.getKey().equals(body.get("type"))
                && !GlobDict.user_tree_super_subordinate.getKey().equals(body.get("type"))
                && !GlobDict.user_info.getKey().equals(body.get("type"))) {
            throw new ErrorException("9996", "");
        }
        //校验用户编号
        if (JudgeUtil.isNull(body.get("type")) && !body.get("type").toString().equals(GlobDict.user_tree_all.getKey())) {
            //如果不为全部，则查询全部用户上下级信息
            if (!JudgeUtil.isNull(body.get("userId")) || body.get("userId").toString().length() > 40) {
                throw new ErrorException("9996", "");
            }
        }
    }

    /**
     * 查询用户树形结构业务主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (GlobDict.user_info.getKey().equals(body.get("type"))) {
            //查询当前用户
            UmUser userParam = new UmUser();
            userParam.setUserId(body.get("userId").toString());
            UmUser user = umUserDao.queryUser(userParam);
            resultMap.putAll(BeanUtil.transBean2Map(user));
        } else {
            List<UmUser> users = new ArrayList<UmUser>();
            //查询数据库所有用户
            List<UmUser> usersAll = umUserDao.queryUserList(new UmUser());
            if (GlobDict.user_tree_all.getKey().equals(body.get("type"))) {
                //查询所有用户上下及关系
                users = userListToTree(usersAll);
            } else {
                //找到当前节点
                UmUser user = new UmUser();
                for (UmUser userTmp : usersAll) {
                    //找到当前节点
                    if (body.get("userId").toString().equals(userTmp.getUserId())) {
                        user = userTmp;
                    }
                }
                if (GlobDict.user_tree_super.getKey().equals(body.get("type"))) {
                    //查找当前用户上级子孙节点
                    //开始递归查找所有上级节点和上级上级的节点
                    List<UmUser> userSuper = new ArrayList<UmUser>();
                    userSuperList(usersAll, user.getSuperUserId(), userSuper);
                    userSuper.add(user);
                    users = userListToTree(userSuper);
                } else if (GlobDict.user_tree_subordinate.getKey().equals(body.get("type"))) {
                    //查询用户下级信息
                    //开始递归查找所有下级节点和下级下级的节点
                    user.setChildUsers(getChild(user.getUserId(), usersAll));
                    users.add(user);
                } else if (GlobDict.user_tree_super_subordinate.getKey().equals(body.get("type"))) {
                    List<UmUser> userSuperSub = new ArrayList<UmUser>();
                    //开始递归查找所有上级节点和上级上级的节点
                    List<UmUser> userSuper = new ArrayList<UmUser>();
                    userSuperList(usersAll, user.getSuperUserId(), userSuper);
                    userSuper.add(user);
                    //查找下级节点和下下级节点
                    List<UmUser> userSub = new ArrayList<UmUser>();
                    userSubList(usersAll, user.getUserId(), userSub);
                    //把上下级节点增加到userSuperSub里面
                    userSuperSub.addAll(userSuper);
                    userSuperSub.addAll(userSub);
                    users = userListToTree(userSuperSub);
                }

            }
            resultMap.put("users", users);
        }


    }

    /**
     * 查找下级节点和下下级节点
     *
     * @param usersAll
     * @param userId
     * @param userSub
     */
    private void userSubList(List<UmUser> usersAll, String userId, List<UmUser> userSub) {
        for (UmUser userTmp : usersAll) {
            if (userId.equals(userTmp.getSuperUserId())) {
                userSub.add(userTmp);
                userSubList(usersAll, userTmp.getUserId(), userSub);
            }
        }
    }


    /**
     * 查找当前用户上级子孙节点
     *
     * @param usersAll
     * @param superId
     */
    private void userSuperList(List<UmUser> usersAll, String superId, List<UmUser> userSuper) {
        for (UmUser userTmp : usersAll) {
            if (userTmp.getUserId().equals(superId)) {
                userSuper.add(userTmp);
                if (userTmp.getSuperUserId() != null && !"".equals(userTmp.getSuperUserId())) {
                    //有上级节点
                    userSuperList(usersAll, userTmp.getSuperUserId(), userSuper);
                }
                break;
            }
        }
    }


    /**
     * 数组形式用户改成树形结构
     *
     * @param rootUser
     * @return
     */
    public static List<UmUser> userListToTree(List<UmUser> rootUser) {

        List<UmUser> userList = new ArrayList<UmUser>();
        // 先找到所有的一级用户
        for (int i = 0; i < rootUser.size(); i++) {
            // 一级用户没有superId
            if (rootUser.get(i).getSuperUserId() == null || "".equals(rootUser.get(i).getSuperUserId())) {
                userList.add(rootUser.get(i));
            }
        }
        // 为一级用户设置子用户，getChild是递归调用的
        for (UmUser user : userList) {
            user.setChildUsers(getChild(user.getUserId(), rootUser));
        }
        return userList;
    }

    /**
     * 递归查找子用户
     *
     * @param id       当前用户id
     * @param rootUser 要查找的列表
     * @return
     */
    private static List<UmUser> getChild(String id, List<UmUser> rootUser) {
        // 子用户
        List<UmUser> childList = new ArrayList<>();
        for (UmUser user : rootUser) {
            // 遍历所有节点，将父用户id与传过来的id比较
            if (user.getSuperUserId() != null && !"".equals(user.getSuperUserId())) {
                if (user.getSuperUserId().equals(id)) {
                    childList.add(user);
                }
            }
        }
        // 把子用户的子用户再循环一遍
        for (UmUser user : childList) {
            //有上级id的子用户还有子用户
            if (user.getSuperUserId() != null && !"".equals(user.getSuperUserId())) {
                // 递归
                user.setChildUsers(getChild(user.getUserId(), rootUser));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
