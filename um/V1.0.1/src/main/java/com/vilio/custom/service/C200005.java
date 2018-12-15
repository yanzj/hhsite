package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.service.BaseService;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： U200005<br>
 * 功能：对外用户批量查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200005 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200004.class);

    @Resource
    private CustomUserDao customUserDao;



    /**
     * 用户批量查询业务主流程处理
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //批量查询用户
        CustomUser qryUserParam = new CustomUser();
        qryUserParam.setUserId(body.get("userId") == null ? null : body.get("userId").toString());
        qryUserParam.setMobile(body.get("mobile") == null ? null : body.get("mobile").toString());
        qryUserParam.setEmail(body.get("email") == null ? null : body.get("email").toString());
        qryUserParam.setUserName(body.get("userName") == null ? null : body.get("userName").toString());
        qryUserParam.setStatus(body.get("status") == null ? null : body.get("status").toString());
        qryUserParam.setFirstLogin(body.get("firstLogin") == null ? null : body.get("firstLogin").toString());
        qryUserParam.setFullName(body.get("fullName") == null ? null : body.get("fullName").toString());
        qryUserParam.setSex(body.get("sex") == null ? null : body.get("sex").toString());
        qryUserParam.setBirthday(body.get("birthday") == null ? null : body.get("birthday").toString());
        qryUserParam.setBirthAddr(body.get("birthAddr") == null ? null : body.get("birthAddr").toString());
        qryUserParam.setNick(body.get("nick") == null ? null : body.get("nick").toString());
        qryUserParam.setHashLock(body.get("hashLock") == null ? null : body.get("hashLock").toString());
        qryUserParam.setHashLock(body.get("systemId") == null ? null : body.get("systemId").toString());
        List<CustomUser> customUsers = customUserDao.batchQryUser(qryUserParam);
        resultMap.put("users", customUsers);
    }

}
