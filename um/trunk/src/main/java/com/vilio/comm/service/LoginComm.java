package com.vilio.comm.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 类名： LoginComm<br>
 * 功能：对内对外用户登录公共方法<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class LoginComm {

    private static final Logger logger = Logger.getLogger(LoginComm.class);

    /**
     * 登录参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断登录类型是否为空
        if (!JudgeUtil.isNull(body.get("loginType"))) {
            throw new ErrorException("9996", "");
        }
        if (GlobDict.login_type_name.getKey().equals(body.get("loginType"))) {
            //用户名密码登录
            if (!JudgeUtil.isNull(body.get("userName"))) {
                throw new ErrorException("9996", "");
            }
        } else if (GlobDict.login_type_mobile.getKey().equals(body.get("loginType"))) {
            //手机号密码登录
            if (!JudgeUtil.isNull(body.get("mobile"))) {
                throw new ErrorException("9996", "");
            }
        } else if (GlobDict.login_type_email.getKey().equals(body.get("loginType"))) {
            //手机号密码登录
            if (!JudgeUtil.isNull(body.get("email"))) {
                throw new ErrorException("9996", "");
            }
        }
        //验证密码
        if (!JudgeUtil.isNull(body.get("password"))) {
            throw new ErrorException("9996", "");
        }
    }

    /**
     * 判断用户状态
     *
     * @param status
     */
    public void checkUserStatus(String status) throws ErrorException {
        if (!JudgeUtil.isNull(status)
                || !GlobDict.user_status_valid.getKey().equals(status)) {
            if (GlobDict.user_status_disable.getKey().equals(status)) {
                throw new ErrorException("0007", "");
            } else if (GlobDict.user_status_delete.getKey().equals(status)) {
                throw new ErrorException("9004", "");
            } else {
                throw new ErrorException("0009", "");
            }
        }
    }


}
