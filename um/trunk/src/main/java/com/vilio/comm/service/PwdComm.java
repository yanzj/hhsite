package com.vilio.comm.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.util.JudgeUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 类名： PwdComm<br>
 * 功能：对内对外用户密码处理公共方法<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class PwdComm {

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验旧密码是否为空
        if (!JudgeUtil.isNull(body.get("oldPassword"))) {
            throw new ErrorException("9996", "");
        }
        //校验新密码是否为空
        if (!JudgeUtil.isNull(body.get("newPassword"))) {
            throw new ErrorException("9996", "");
        }
        //校验重新输入的新密码是否为空
        if (!JudgeUtil.isNull(body.get("reNewPassword"))) {
            throw new ErrorException("9996", "");
        }
        //校验用户编号是否为空
        if (!JudgeUtil.isNull(body.get("userId"))) {
            throw new ErrorException("9996", "");
        }
        //新密码和重写新密码必须相同
        if (!body.get("newPassword").equals(body.get("reNewPassword"))) {
            throw new ErrorException("9008", "");
        }
    }


}
