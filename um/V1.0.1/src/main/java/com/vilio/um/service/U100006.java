package com.vilio.um.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.um.dao.UmUserDao;
import com.vilio.um.pojo.UmUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100006<br>
 * 功能：根据机构查询所有用户信息<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月30日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100006 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100006.class);

    @Resource
    private UmUserDao umUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("groupId"))) {
            throw new ErrorException("9998", "");
        }
    }

    /**
     * 根据机构查询所有用户信息主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //根据机构查询用户信息
        UmUser userParam = new UmUser();
        userParam.setGroupId(body.get("groupId").toString());
        List<UmUser> users = umUserDao.queryUserList(userParam);
        resultMap.put("users",users);
    }

}
