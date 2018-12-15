package com.vilio.um.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.BeanUtil;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.um.dao.UmGroupDao;
import com.vilio.um.pojo.UmGroup;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100004<br>
 * 功能：查询机构信息业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100004 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100004.class);

    @Resource
    private UmGroupDao umGroupDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验非必填字段
        //校验查询类型必填
        if (!GlobDict.group_tree_all.getKey().equals(body.get("type")) && !GlobDict.group_tree_subordinate.getKey().equals(body.get("type"))
                && !GlobDict.group_tree_super.getKey().equals(body.get("type"))
                && !GlobDict.group_tree_super_subordinate.getKey().equals(body.get("type"))
                && !GlobDict.group_info.getKey().equals(body.get("type"))) {
            throw new ErrorException("9996", "");
        }
        //校验机构编号
        if (JudgeUtil.isNull(body.get("type")) && !body.get("type").toString().equals(GlobDict.group_tree_all.getKey())) {
            //如果不为全部，则查询全部机构上下级信息
            if (!JudgeUtil.isNull(body.get("groupId")) || body.get("groupId").toString().length() > 40) {
                throw new ErrorException("9996", "");
            }
        }
    }

    /**
     * 查询机构业务主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if (GlobDict.group_info.getKey().equals(body.get("type"))) {
            UmGroup groupParam = new UmGroup();
            groupParam.setGroupId(body.get("groupId").toString());
            UmGroup group = umGroupDao.queryGroup(groupParam);
            resultMap.putAll(BeanUtil.transBean2Map(group));
        } else {
            List<UmGroup> groups = new ArrayList<UmGroup>();
            //查询数据库所有机构
            List<UmGroup> groupAll = umGroupDao.queryGroupList();
            if (GlobDict.group_tree_all.getKey().equals(body.get("type"))) {
                groups = groupListToTree(groupAll);
            } else {
                //找到当前节点
                UmGroup group = new UmGroup();
                for (UmGroup groupTmp : groupAll) {
                    //找到当前节点
                    if (body.get("groupId").toString().equals(groupTmp.getGroupId())) {
                        group = groupTmp;
                    }
                }
                if (GlobDict.group_tree_super.getKey().equals(body.get("type"))) {
                    //查找当前用户上级子孙节点
                    //开始递归查找所有上级节点和上级上级的节点
                    List<UmGroup> groupsuper = new ArrayList<UmGroup>();
                    groupSuperList(groupAll, group.getSuperId(), groupsuper);
                    groupsuper.add(group);
                    groups = groupListToTree(groupsuper);
                } else if (GlobDict.group_tree_subordinate.getKey().equals(body.get("type"))) {
                    //查询用户下级信息
                    //开始递归查找所有下级节点和下级下级的节点
                    group.setChildGroups(getChild(group.getGroupId(), groupAll));
                    groups.add(group);
                } else if (GlobDict.group_tree_super_subordinate.getKey().equals(body.get("type"))) {
                    List<UmGroup> groupsuperSub = new ArrayList<UmGroup>();
                    //开始递归查找所有上级节点和上级上级的节点
                    List<UmGroup> groupsuper = new ArrayList<UmGroup>();
                    groupSuperList(groupAll, group.getSuperId(), groupsuper);
                    groupsuper.add(group);
                    //查找下级节点和下下级节点
                    List<UmGroup> groupsub = new ArrayList<UmGroup>();
                    groupSubList(groupAll, group.getGroupId(), groupsub);
                    //把上下级节点增加到groupsuperSub里面
                    groupsuperSub.addAll(groupsuper);
                    groupsuperSub.addAll(groupsub);
                    groups = groupListToTree(groupsuperSub);
                }
            }
            resultMap.put("groups", groups);
        }

    }


    /**
     * 查找下级节点和下下级节点
     *
     * @param groupAll
     * @param groupId
     * @param groupSub
     */
    private void groupSubList(List<UmGroup> groupAll, String groupId, List<UmGroup> groupSub) {
        for (UmGroup groupTmp : groupAll) {
            if (groupId.equals(groupTmp.getSuperId())) {
                groupSub.add(groupTmp);
                groupSubList(groupAll, groupTmp.getGroupId(), groupSub);
            }
        }
    }


    /**
     * 查找当前用户上级子孙节点
     *
     * @param groupAll
     * @param superId
     */
    private void groupSuperList(List<UmGroup> groupAll, String superId, List<UmGroup> groupSuper) {
        for (UmGroup groupTmp : groupAll) {
            if (groupTmp.getGroupId().equals(superId)) {
                groupSuper.add(groupTmp);
                if (groupTmp.getSuperId() != null && !"".equals(groupTmp.getSuperId())) {
                    //有上级节点
                    groupSuperList(groupAll, groupTmp.getSuperId(), groupSuper);
                }
                break;
            }
        }
    }


    /**
     * 数组形式机构改成树形结构
     *
     * @param rootGroup
     * @return
     */
    public static List<UmGroup> groupListToTree(List<UmGroup> rootGroup) {
        List<UmGroup> groupList = new ArrayList<UmGroup>();
        // 先找到所有的一级机构
        for (int i = 0; i < rootGroup.size(); i++) {
            // 一级机构没有superId
            if (rootGroup.get(i).getSuperId() == null || "".equals(rootGroup.get(i).getSuperId())) {
                groupList.add(rootGroup.get(i));
            }
        }
        // 为一级机构设置子机构，getChild是递归调用的
        for (UmGroup group : groupList) {
            group.setChildGroups(getChild(group.getGroupId(), rootGroup));
        }
        return groupList;
    }

    /**
     * 递归查找子机构
     *
     * @param id        当前机构id
     * @param rootGroup 要查找的列表
     * @return
     */
    private static List<UmGroup> getChild(String id, List<UmGroup> rootGroup) {
        // 子机构
        List<UmGroup> childList = new ArrayList<>();
        for (UmGroup group : rootGroup) {
            // 遍历所有节点，将父机构id与传过来的id比较
            if (group.getSuperId() != null && !"".equals(group.getSuperId())) {
                if (group.getSuperId().equals(id)) {
                    childList.add(group);
                }
            }
        }
        // 把子机构的子机构再循环一遍
        for (UmGroup group : childList) {
            //有上级id的子机构还有子机构
            if (group.getSuperId() != null && !"".equals(group.getSuperId())) {
                // 递归
                group.setChildGroups(getChild(group.getGroupId(), rootGroup));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
