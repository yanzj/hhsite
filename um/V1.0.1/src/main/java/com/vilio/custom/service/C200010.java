package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.custom.dao.CustomUserDao;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： C200010<br>
 * 功能：首次登录标识修改<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200010 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200010.class);

    @Resource
    private CustomUserDao customUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //用户编码是否为空
        checkField(ObjectUtils.toString(body.get("userId")), "用户编号", null, 40);
        //判断新密码
        checkField(ObjectUtils.toString(body.get("firstLogin")), "首次登录标识", null, 2);
        if (!GlobDict.first_login.getKey().equals(ObjectUtils.toString(body.get("firstLogin")))&&
                !GlobDict.un_first_login.getKey().equals(ObjectUtils.toString(body.get("firstLogin")))) {
            throw new ErrorException("9998", "");
        }
    }

    /**
     * 交易密码验证接口
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        //直接修改首次登录标识
        Map updateParam = new HashMap();
        updateParam.put("firstLogin",ObjectUtils.toString(body.get("firstLogin")));
        updateParam.put("userId",ObjectUtils.toString(body.get("userId")));
        int ret = customUserDao.updateFirstLogin(updateParam);
        if (ret<=0) {
            throw new ErrorException("9997", "");
        }
    }

}