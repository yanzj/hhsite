package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： C200013<br>
 * 功能：首次设置交易密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200013 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200013.class);

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
        //判断新密码
        checkField(ObjectUtils.toString(body.get("newTransPassword")), "新密码", null, 40);

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
        //用户是否有效(状态是否存在，用户状态是否有效)
        loginComm.checkUserStatus(user.getStatus());
        //判断交易密码是否为空，不为空不能修改
        if (JudgeUtil.isNull(user.getTransPassword())) {
            //首次密码不为空，不能进行设置
            throw new ErrorException("0056", "");
        }
        //重置交易密码
        Map updateParam = new HashMap();
        updateParam.put("userId", body.get("userId"));
        updateParam.put("newTransPassword", body.get("newTransPassword"));
        updateParam.put("transError", "0");
        updateParam.put("status", GlobDict.user_status_valid.getKey());
        int ret = customUserDao.updateResetTransPassword(updateParam);
        if (ret <= 0) {
            logger.error("更新密码失败：" + ret);
            throw new ErrorException("0010", "");
        }
        //修改成功
        logger.info("修改成功");
    }


}
