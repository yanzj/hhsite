package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000013<br>
 * 功能：重置交易密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000013 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000013.class);

    @Resource
    private VerifyDao verifyDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", null, 10);
        checkField(ObjectUtils.toString(body.get("newPassword")), "新密码", null, 40);
        checkField(ObjectUtils.toString(body.get("reNewPassword")), "确认新密码", null, 40);
        if (!ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("reNewPassword")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码和确认新密码不相同");
        }
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 15);
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //判断手机号验证码是否正确
        Verify verify = checkVerify(ObjectUtils.toString(body.get("mobileNo")), ObjectUtils.toString(body.get("verifyCode")), GlobDict.sms_authentication.getKey());
        if (!GlobDict.verify_succ.getKey().equals(verify.getStatus())) {
            //当前验证码没有经过验证码验证接口验证
            throw new ErrorException(ReturnCode.NO_VERIFY, "");
        }
        //验证通过后，发送um修改登录密码
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID, head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put("newTransPassword", ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umUpdateTransPwdByMobile);
        PcfsUtil.sendUMRetJudge(sendUmParam);
        //将当前验证码修改为无效
        verify.setStatus(GlobDict.verify_invalid.getKey());
        int ret = verifyDao.updateVerifyStatusById(verify);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }
}
