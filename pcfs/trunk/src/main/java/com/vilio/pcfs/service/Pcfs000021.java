package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Pcfs000021<br>
 * 功能：验证码验证接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000021 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000021.class);

    @Resource
    private VerifyDao verifyDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //验证手机号是否为空
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 15);
        //验证码是否为空
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", null, 10);
        //验证码类型
        checkField(ObjectUtils.toString(body.get("verifyType")), "验证码类型", null, 2);
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //判断验证码
        Verify verify = checkVerify(ObjectUtils.toString(body.get("mobileNo")), ObjectUtils.toString(body.get("verifyCode")), ObjectUtils.toString(body.get("verifyType")));
        if (verify.getStatus().equals(GlobDict.verify_effective.getKey())) {
            //验证通过
            //将当前验证码变成验证通过
            verify.setStatus(GlobDict.verify_succ.getKey());
            int ret = verifyDao.updateVerifyStatusById(verify);
            if (ret<=0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
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
    }


}
