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
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000012<br>
 * 功能：交易密码修改接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000012 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000012.class);

    @Resource
    private VerifyDao verifyDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断原密码是否为空
        checkField(ObjectUtils.toString(body.get("oldPassword")), "原密码", null, 50);
        //判断新密码
        checkField(ObjectUtils.toString(body.get("newPassword")), "新密码", null, 50);
        //重新重写新密码是否和新密码相同
        if (!ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("reNewPassword")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码和确认新密码不相同");
        }
        if (ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("oldPassword")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码不能和原密码相同，请重输");
        }
        //手机号
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 18);
        if (!JudgeUtil.isMobile(body.get("mobileNo").toString())) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "手机号格式错误");
        }
        //验证码
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", null, 10);

    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //进行验证码验证
        Verify verify = checkVerify(ObjectUtils.toString(body.get("mobileNo")), ObjectUtils.toString(body.get("verifyCode")), GlobDict.sms_trans_pwd_type.getKey());
        if (verify.getStatus().equals(GlobDict.verify_effective.getKey())) {

        } else {
            //验证码已被验证过
            //将当前验证码修改为无效
            verify.setStatus(GlobDict.verify_invalid.getKey());
            int ret = verifyDao.updateVerifyStatusById(verify);
            if (ret<=0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
            //返回验证码已失效
            throw new ErrorException(ReturnCode.VREIFY_CODE_NULLIFY, "");
        }

        //直接调用um进行修改密码操作
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID,head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put("oldTransPassword",ObjectUtils.toString(body.get("oldPassword")));
        sendUmParam.put("newTransPassword",ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put("reNewTransPassword",ObjectUtils.toString(body.get("reNewPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umTransUpdatePwdFunctionNo);
        //发送um进行验证
        Map umResult = PcfsUtil.sendUMRetJudge(sendUmParam);

        //验证码验证通过后才标记为无效
        //将当前验证码修改为无效
        verify.setStatus(GlobDict.verify_invalid.getKey());
        int ret = verifyDao.updateVerifyStatusById(verify);
        if (ret<=0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }
}
