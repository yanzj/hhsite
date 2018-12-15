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
import java.util.Map;

/**
 * 类名： C200007<br>
 * 功能：交易密码验证接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200007 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200007.class);

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
        //判断用户编号是否为空
        checkField(ObjectUtils.toString(body.get("userId")), "用户编号", null, 40);
        //判断交易密码
        checkField(ObjectUtils.toString(body.get("transPassword")), "交易密码", null, 50);
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
        CustomUser customUserParam = new CustomUser();
        customUserParam.setUserId(ObjectUtils.toString(body.get("userId")));
        CustomUser customUser = customUserDao.queryUser(customUserParam);
        if (customUser==null) {
            throw new ErrorException("9004", "");
        }
        //用户是否有效(状态是否存在，用户状态是否有效)
        loginComm.checkUserStatus(customUser.getStatus());
        //判断交易锁是否锁住
        if (GlobDict.trans_hash_lock.getKey().equals(customUser.getTransHashLock())) {
            //交易锁定
            throw new ErrorException("0102", "");
        }
        //判断交易密码是否存在
        if (!JudgeUtil.isNull(customUser.getTransPassword())) {
            throw new ErrorException("0101", "");
        }
        //判断交易密码是否相等
        if (!ObjectUtils.toString(body.get("transPassword")).equals(customUser.getTransPassword())) {
            //交易密码不相等，对交易错误次数进行处理
            dealTransNum(customUser);
        }
        //交易密码输入成功，重置交易密码输入错误次数
        customUser.setTransError("0");
        //清空交易密码错误次数
        int ret = customUserDao.updateTransErrorByUserId(customUser);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //返回成功
    }


    /**
     * 对交易密码错误次数进行处理
     *
     * @param user
     */
    private void dealTransNum(CustomUser user) throws ErrorException {
        //登录次数加1
        String transError = user.getTransError();
        user.setTransError(transError == null || transError == "" ? "1" : String.valueOf(Integer.parseInt(transError) + 1));
        int ret = customUserDao.updateTransErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        if (Integer.parseInt(user.getTransError()) >= GlobParam.customTransError) {
            //用户交易锁定
            user.setTransHashLock(GlobDict.trans_hash_lock.getKey());
            user.setTransLockTime(String.valueOf(System.currentTimeMillis()));
            ret = customUserDao.updateTransHashLockByUserId(user);
            if (ret <= 0) {
                throw new ErrorException("9997", "");
            }
            throw new ErrorException("0103", "交易密码错误，您已输错" + GlobParam.customLoginError + "次，交易关闭。如需帮助，请联系您的客户经理。");
        } else {
            throw new ErrorException("0104", "交易密码错误，您还可以输入" + (GlobParam.customLoginError - Integer.parseInt(user.getTransError())) + "次");
        }
    }
}
