package com.vilio.nlbs.todo.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/17/0017.
 */
public interface NlbsTODOService {

    public int insertInquiryTodoTask(String serialNo, String userNo, String address) throws Exception;

    public int insertApplyTodoTask(String serialNo, String userNo, String customerName) throws Exception;

    public int deleteTask(String serialNo, String userNo, boolean isExcept) throws Exception;

    public Map initTodoTaskList() throws Exception;

    public Map getTodoTaskList(Map paramMap) throws Exception;

}
