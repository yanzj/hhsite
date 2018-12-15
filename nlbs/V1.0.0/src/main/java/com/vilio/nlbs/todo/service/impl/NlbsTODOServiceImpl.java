package com.vilio.nlbs.todo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.commonMapper.dao.NlbsPendingUserDistributorMapper;
import com.vilio.nlbs.commonMapper.dao.NlbsTodoMapper;
import com.vilio.nlbs.todo.service.NlbsTODOService;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/20/0020.
 */
@Service
public class NlbsTODOServiceImpl implements NlbsTODOService {

    @Resource
    NlbsPendingUserDistributorMapper nlbsPendingUserDistributorMapper;

    @Resource
    NlbsTodoMapper nlbsTodoMapper;


    /**
     * 根据配置，查找待处理人列表，并生成待处理人
     * @param serialNo
     * @param userNo
     * @param address
     * @return
     * @throws Exception
     */
    @Override
    public int insertTodoTask(String serialNo, String userNo, String address) throws Exception {

        if(StringUtils.isBlank(userNo)){
            return 0;
        }
        List<Map<String, Object>> userList = nlbsPendingUserDistributorMapper.getUserListByUserNo(userNo);

        if(userList != null && userList.size() > 0){
            Map paramMap = new HashMap();
            paramMap.put(Fields.PARAM_SERIAL_NO, serialNo);
            paramMap.put(Fields.PARAM_CONTENT, "房产“" + address + "”待评估");
            paramMap.put(Fields.PARAM_TODO_TYPE, Constants.TODO_TYPE_TOINQUIRY);
            paramMap.put(Fields.PARAM_USER_LIST, userList);

            return nlbsTodoMapper.getInsertPrmMap(paramMap);
        }

        return 0;
    }

    /**
     * 删除待办任务
     * @param serialNo
     * @param userNo
     * @param isExcept 是否是删除除userNo以外的用户的任务
     * @return
     */
    @Override
    public int deleteTask(String serialNo, String userNo, boolean isExcept) throws Exception {
        Map paramMap = new HashMap();
        if(isExcept){
            paramMap.put(Fields.PARAM_EXCEPT_USER_NO, userNo);
        } else {
            paramMap.put(Fields.PARAM_USER_NO, userNo);
        }
        paramMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        return nlbsTodoMapper.deleteTask(paramMap);
    }

    /**
     * 待办任务列表初始化
     * @return
     * @throws Exception
     */
    @Override
    public Map initTodoTaskList() throws Exception {
        Map returnMap = new HashMap();
        List<Map<String, Object>> todoTypeList = nlbsTodoMapper.getTodoType();

        returnMap.put(Fields.PARAM_TODO_TASK_LIST, todoTypeList == null ? new ArrayList<Map<String, Object>>() : todoTypeList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取待办任务类型成功！");
        return returnMap;
    }

    /**
     * 获取待办任务列表
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map getTodoTaskList(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        List<Map> todoTaskList = new ArrayList<Map>();
        Integer pageNo = null != paramMap.get(Fields.PARAM_PAGE_NO) ? new Integer(paramMap.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != paramMap.get(Fields.PARAM_PAGE_SIZE) ? new Integer(paramMap.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;

        //Step 1 获取分页查询结果
        PageHelper.startPage(pageNo, pageSize);
        todoTaskList = nlbsTodoMapper.getTodoListWithSelective(paramMap);

        PageInfo pageInfo = new PageInfo(todoTaskList);

        returnMap.put(Fields.PARAM_TODO_TASK_LIST,todoTaskList);
        returnMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        returnMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        returnMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取待办任务列表成功！");

        return returnMap;
    }
}
