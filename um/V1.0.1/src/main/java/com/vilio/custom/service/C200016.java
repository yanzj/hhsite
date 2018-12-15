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
 * 类名： C200016<br>
 * 功能：验证手机号是否存在用户表中<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月22日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200016 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200016.class);

    @Resource
    private CustomUserDao customUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //手机号
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 18);
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
        int count = customUserDao.checkMobile(customUserParam);
        if (count == 0) {
            throw new ErrorException("0106","");
        }
    }


}
