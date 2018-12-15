package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.service.BaseService;
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
 * 类名： C200012<br>
 * 功能：通过用户编号直接修改密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200012 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200012.class);

    @Resource
    private CustomUserDao customUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //用户编号校验
        checkField(ObjectUtils.toString(body.get("userId")), "用户编号", null, 40);
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
        customUserParam.setUserId(ObjectUtils.toString(body.get("userId")));
        customUserParam.setPassword(ObjectUtils.toString(body.get("password")));
        int ret = customUserDao.updatePasswordByUserId(customUserParam);
        if (ret < 0) {
            throw new ErrorException("0010", "");
        }
    }

}
