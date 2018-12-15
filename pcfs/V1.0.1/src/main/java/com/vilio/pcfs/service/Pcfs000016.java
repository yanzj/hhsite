package com.vilio.pcfs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.pcfs.dao.PushMessageDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Pcfs000016<br>
 * 功能：接收消息推送接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月3日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000016 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000025.class);
    @Resource
    private PushMessageDao pushMessageDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (body.get("pageNo") == null || !JudgeUtil.isNull(body.get("pageNo")) ){
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }


        if (body.get("pageSize") == null || !JudgeUtil.isNull(body.get("pageSize")) ){
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }

    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        String userId = head.get("userId").toString();
        //入参封装
        Map requestMap = new HashMap();

        Integer pageNo = Integer.valueOf(body.get("pageNo").toString());
        Integer pageSize = Integer.valueOf(body.get("pageSize").toString());
        PageHelper.startPage(pageNo.intValue(), pageSize.intValue());

        List queryResult = pushMessageDao.queryUserMessagesListInfo(userId);
        if (queryResult == null ){
            queryResult = new ArrayList();
        }
        //查询当前页数
        PageInfo pageInfo = new PageInfo(queryResult);
        resultMap.put("messageList",queryResult);
        resultMap.put("totalPage", Integer.valueOf(pageInfo.getPages()));//总页数
        resultMap.put("total", Long.valueOf(pageInfo.getTotal()));//总条数
        resultMap.put("currentPage", Integer.valueOf(pageInfo.getPageNum()));//当前页
        //查询未读条数

        String unreadCount = pushMessageDao.queryUserUnreadMessageNum(userId);
        resultMap.put("unreadNo",unreadCount);


    }
}
