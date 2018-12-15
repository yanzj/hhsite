package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
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
 * 类名： C200015<br>
 * 功能：验证用户手机号是否匹配<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月22日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200015 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200015.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private LoginComm loginComm;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //用户编码是否为空
        checkField(ObjectUtils.toString(body.get("userId")), "用户编号", null, 40);
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
        //查询用户信息
        CustomUser qryUserParam = new CustomUser();
        qryUserParam.setUserId(body.get("userId").toString());
        qryUserParam.setSystemId(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        CustomUser user = customUserDao.queryUser(qryUserParam);
        if (null == user) {
            throw new ErrorException("9004", "");
        }
        //验证手机号是否正确
        if (!ObjectUtils.toString(body.get("mobileNo")).equals(user.getMobile())) {
            //如果不一样，则报错
            throw new ErrorException("0105", "");
        }
    }


}
