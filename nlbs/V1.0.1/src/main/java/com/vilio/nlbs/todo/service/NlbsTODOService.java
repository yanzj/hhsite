package com.vilio.nlbs.todo.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/17/0017.
 */
public interface NlbsTODOService {

    public int insertTodoTask(String serialNo, String userNo, String address) throws Exception;

    public int deleteTask(String serialNo, String userNo, boolean isExcept) throws Exception;

    public Map initTodoTaskList() throws Exception;

    public Map getTodoTaskList(Map paramMap) throws Exception;

}
