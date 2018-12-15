package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 类名： C200011<br>
 * 功能：忘记登录密码修改<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200011 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200011.class);

    @Resource
    private CustomUserDao customUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //手机号校验
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 15);
        if (!JudgeUtil.isMobile(ObjectUtils.toString(body.get("mobileNo")))) {
            throw new ErrorException("9998", "手机号校验失败");
        }
        //新的登录密码
        checkField(ObjectUtils.toString(body.get("password")), "密码", null, 50);
    }

    /**
     * 交易密码验证接口
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        CustomUser customUserParam = new CustomUser();
        customUserParam.setMobile(ObjectUtils.toString(body.get("mobileNo")));
        customUserParam.setPassword(ObjectUtils.toString(body.get("password")));
        //登录密码错误次数归零，并且解锁
        customUserParam.setLoginError("0");
        customUserParam.setHashLock(GlobDict.un_hash_lock.getKey());
        int ret = customUserDao.forgetUpdatePassword(customUserParam);
        if (ret < 0) {
            throw new ErrorException("0010", "");
        }
    }

}
