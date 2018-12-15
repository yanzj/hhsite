package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.comm.service.PwdComm;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： U200002<br>
 * 功能：对外用户修改密码业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200002 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200002.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private PwdComm pwdComm;

    @Resource
    private LoginComm loginComm;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        pwdComm.checkParam(body);
    }

    /**
     * 密码修改业务主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
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
        if (!body.get("oldPassword").equals(user.getPassword())) {
            dealLoginNum(user);
        }
        //如果原密码正确，则重置登录密码错误次数
        user.setLoginError("0");
        customUserDao.updateLoginErrorByUserId(user);
        //直接根据用户编号和旧密码直接修改密码（并且重置错误次数）
        Map updateParam = new HashMap();
        updateParam.put("userId", body.get("userId"));
        updateParam.put("oldPassword", body.get("oldPassword"));
        updateParam.put("newPassword", body.get("newPassword"));
        updateParam.put("loginError", "0");
        updateParam.put("status", GlobDict.user_status_valid.getKey());
        int ret = customUserDao.updatePassword(updateParam);
        if (ret <= 0) {
            logger.error("更新密码失败：" + ret);
            throw new ErrorException("0010", "");
        }
        resultMap.putAll(body);
    }

    /**
     * 对密码错误次数进行处理
     *
     * @param user
     */
    private void dealLoginNum(CustomUser user) throws ErrorException {
        //登录次数加1
        String loginError = user.getLoginError();
        user.setLoginError(loginError == null || loginError == "" ? "1" : String.valueOf(Integer.parseInt(loginError) + 1));
        int ret = customUserDao.updateLoginErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        if (Integer.parseInt(user.getLoginError()) >= GlobParam.customLoginError) {
            //用户锁定
            user.setHashLock(GlobDict.hash_lock.getKey());
            user.setLockTime(String.valueOf(System.currentTimeMillis()));
            ret = customUserDao.updateHashLockByUserId(user);
            if (ret <= 0) {
                throw new ErrorException("9997", "");
            }
            throw new ErrorException("9002", "用户名或密码错误，超过" + GlobParam.customLoginError + "次锁定");
        } else {
            throw new ErrorException("9003", "用户名或密码错误，还剩" + (GlobParam.customLoginError - Integer.parseInt(user.getLoginError())) + "次锁定");
        }
    }

}
