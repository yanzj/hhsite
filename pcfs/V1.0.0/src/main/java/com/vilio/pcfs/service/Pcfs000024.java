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
 * 类名： Pcfs000024<br>
 * 功能：身份验证接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000024 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000024.class);

    @Resource
    private VerifyDao verifyDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", null, 10);
        checkField(ObjectUtils.toString(body.get("name")), "姓名", null, 10);

        checkField(ObjectUtils.toString(body.get("cardId")), "身份证号", null, 40);
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 40);
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //校验验证码是否正确
        //判断验证码
        Verify verify = checkVerify(ObjectUtils.toString(body.get("mobileNo")), ObjectUtils.toString(body.get("verifyCode")), GlobDict.sms_authentication.getKey());
        if (verify.getStatus().equals(GlobDict.verify_effective.getKey())) {
            //发送贷后系统校验身份证和姓名
            Map sendParam = new HashMap();
            sendParam.put("functionNo", "plms000013");
            sendParam.put("name",ObjectUtils.toString(body.get("name")));
            sendParam.put("idNo",ObjectUtils.toString(body.get("cardId")));
            //调用贷后系统
            Map plmsResultMap = PcfsUtil.sendPLMS(head, sendParam);
            Map resultHead = (Map) plmsResultMap.get("head");
            String returnCode = resultHead.get("returnCode").toString();
            String returnMessage = resultHead.get("returnMessage").toString();
            if (!"0000".equals(returnCode)) {
                //将当前验证码修改为无效
//                verify.setStatus(GlobDict.verify_invalid.getKey());
//                int ret = verifyDao.updateVerifyStatusById(verify);
//                if (ret<=0) {
//                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//                }
                throw new ErrorException(returnCode, returnMessage);
            }
            //贷后系统验证通过，开始验证um系统手机号是否正确
            Map sendUmParam = new HashMap();
            sendUmParam.put(GlobParam.PARAM_USER_ID, head.get(GlobParam.PARAM_USER_ID));
            sendUmParam.put("mobileNo", body.get("mobileNo"));
            sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umVerityUserMobile);
            Map umResultMap = PcfsUtil.sendUM(sendUmParam);
            Map umResultHead = (Map) umResultMap.get("head");
            String umReturnCode = umResultHead.get("returnCode").toString();
            String umReturnMessage = umResultHead.get("returnMessage").toString();
            logger.info("um返回参数："+umReturnMessage);
            if (!"0000".equals(returnCode)) {
                //将当前验证码修改为无效
//                verify.setStatus(GlobDict.verify_invalid.getKey());
//                int ret = verifyDao.updateVerifyStatusById(verify);
//                if (ret<=0) {
//                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//                }
                throw new ErrorException(umReturnCode, umReturnMessage);
            }
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

        resultMap.put("name",ObjectUtils.toString(body.get("name")));

    }
}
