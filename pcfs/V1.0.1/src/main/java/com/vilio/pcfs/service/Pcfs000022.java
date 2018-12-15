package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000022<br>
 * 功能：忘记密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000022 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000022.class);

    @Resource
    private VerifyDao verifyDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断新密码
        checkField(ObjectUtils.toString(body.get("newPassword")), "新密码", null, 50);
        //重新重写新密码是否和新密码相同
        if (!ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("reNewPassword")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码和确认新密码不相同");
        }
        //忘记密码
        //判断手机号是否存在
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 15);
        if (!JudgeUtil.isMobile(ObjectUtils.toString(body.get("mobileNo")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "手机号校验失败");
        }
        //校验验证码
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", null, 6);
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //忘记密码流程
        pwdForgetTrans(head,body);
    }

    /**
     * 忘记密码
     * @param head
     * @param body
     */
    private void pwdForgetTrans(Map<String, Object> head, Map<String, Object> body) throws ErrorException, ParseException {
        //判断手机号验证码是否正确
        Verify verify = checkVerify(ObjectUtils.toString(body.get("mobileNo")),ObjectUtils.toString(body.get("verifyCode")),GlobDict.sms_login_pwd_type.getKey());
        if (!GlobDict.verify_succ.getKey().equals(verify.getStatus())) {
            //将当前验证码修改为无效
//            verify.setStatus(GlobDict.verify_invalid.getKey());
//            int ret = verifyDao.updateVerifyStatusById(verify);
//            if (ret<=0) {
//                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//            }
            //当前验证码没有经过验证码验证接口验证
            throw new ErrorException(ReturnCode.NO_VERIFY, "");
        }
        //验证通过后，发送um修改登录密码
        Map sendUmParam = new HashMap();
        sendUmParam.put("mobileNo",ObjectUtils.toString(body.get("mobileNo")));
        sendUmParam.put("password",ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umforgetUpdatePassword);
        PcfsUtil.sendUMRetJudge(sendUmParam);
        //将当前验证码修改为无效
        verify.setStatus(GlobDict.verify_invalid.getKey());
        int ret = verifyDao.updateVerifyStatusById(verify);
        if (ret<=0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }


}
