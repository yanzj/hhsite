package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import com.vilio.pcfs.util.CalendarUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 类名： Pcfs000001<br>
 * 功能：获取验证码接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月4日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000001 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000001.class);

    @Resource
    private VerifyDao verifyDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //手机号
        if (!JudgeUtil.isNull(body.get("mobileNo")) || body.get("mobileNo").toString().length() > 18 || !JudgeUtil.isMobile(body.get("mobileNo").toString())) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "手机号错误");
        }
        //业务类型
        if (!JudgeUtil.isNull(body.get("transType")) || body.get("transType").toString().length() > 2) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "交易类型错误");
        }
        //业务类型必须支持
        if (!GlobDict.sms_login_type.getKey().equals(body.get("transType")) && !GlobDict.sms_borrow_type.getKey().equals(body.get("transType"))
                && !GlobDict.sms_login_pwd_type.getKey().equals(body.get("transType"))
                && !GlobDict.sms_trans_pwd_type.getKey().equals(body.get("transType"))
                && !GlobDict.sms_authentication.getKey().equals(body.get("transType"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "交易类型错误");
        }
    }


    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //发送um判断手机号是否存在与我们的用户表中，如果不存在，则不能发送验证码
        Map sendUmParam = new HashMap();
        sendUmParam.put("mobileNo", ObjectUtils.toString(body.get("mobileNo")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umChechMobile);
        //发送um进行验证
        PcfsUtil.sendUMRetJudge(sendUmParam);
        //判断同一类型，同一手机，最近一次发送时间是否小于60秒
        Map checkVerifySecond = new HashMap();
        checkVerifySecond.put("verifyType", body.get("transType"));
        checkVerifySecond.put("mobileNo", body.get("mobileNo"));
        checkVerifySecond.put("smsSecondTimeOut", GlobParam.smsSecondTimeOut);
        int checkCount = verifyDao.checkVerifySecond(checkVerifySecond);
        if (checkCount > 0) {
            throw new ErrorException(ReturnCode.SMS_FREQUENTLY_FAIL, "");
        }

        //将当前手机号，当前类型的所有有效的验证码更改为无效
        Map updateParam = new HashMap();
        updateParam.putAll(body);
        updateParam.put("newStatus", GlobDict.verify_invalid.getKey());
        updateParam.put("oldStatus", GlobDict.verify_effective.getKey());
        updateParam.put("succStatus", GlobDict.verify_succ.getKey());
        int ret = verifyDao.updateVerifyStatus(updateParam);
        if (ret < 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        Verify verify = new Verify();
        //根据不同业务类型，设置短信信息
        if (GlobDict.sms_login_type.getKey().equals(body.get("transType"))) {
            //登录验证码
            verify.setVerifyCode(getRandNum(GlobParam.smsLoginVerifyNum));
            //有效时间
            verify.setEffectiveTime(GlobParam.smsLoginVerifyTimeout);
            //登录模板
            verify.setTemplateNo(GlobParam.smsLoginTemplateNo);
        } else if (GlobDict.sms_borrow_type.getKey().equals(body.get("transType"))) {
            //借款验证码
            verify.setVerifyCode(getRandNum(GlobParam.smsBorrowNum));
            //有效时间
            verify.setEffectiveTime(GlobParam.smsBorrowTimeout);
            //登录模板
            verify.setTemplateNo(GlobParam.smsBorrowTemplateNo);
        } else if (GlobDict.sms_login_pwd_type.getKey().equals(body.get("transType"))) {
            //修改密码验证码
            verify.setVerifyCode(getRandNum(GlobParam.smsUpdatePwdNum));
            //有效时间
            verify.setEffectiveTime(GlobParam.smsUpdatePwdTimeout);
            //登录模板
            verify.setTemplateNo(GlobParam.smsUpdatePwdTemplateNo);
        } else if (GlobDict.sms_trans_pwd_type.getKey().equals(body.get("transType"))) {
            //重置密码
            verify.setVerifyCode(getRandNum(GlobParam.smsUpdateTransPwdNum));
            //有效时间
            verify.setEffectiveTime(GlobParam.smsUpdateTransPwdTimeout);
            //登录模板
            verify.setTemplateNo(GlobParam.smsUpdateTransPwdTemplateNo);
        } else if (GlobDict.sms_authentication.getKey().equals(body.get("transType"))) {
            //身份验证验证码
            verify.setVerifyCode(getRandNum(GlobParam.smsAuthenticationNum));
            //有效时间
            verify.setEffectiveTime(GlobParam.smsAuthenticationTimeout);
            //登录模板
            verify.setTemplateNo(GlobParam.smsAuthenticationTemplateNo);
        } else {
            throw new ErrorException(ReturnCode.SMS_NO_BUSI_TYPE, "");
        }
        verify.setVerifyType(body.get("transType").toString());
        //生成流水号
        String seq = commonService.getSeq("SERNO", 8);
        String verifySerno = CalendarUtil.getNowTime("yyyyMMddHHmmss") + seq;
        verify.setVerifySerno(verifySerno);
        verify.setMobileNo(body.get("mobileNo").toString());
        verify.setSignNo(GlobParam.smsSignNo);
        //默认验证码有效
        verify.setStatus(GlobDict.verify_effective.getKey());
        //初始化入库
        ret = verifyDao.insertVerify(verify);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //入库成功拼装发送短信报文
        Map sendParam = new HashMap();
        sendParam.put("mobile", verify.getMobileNo());
        sendParam.put("requestNo", verify.getVerifySerno());
        Map templateParam = new HashMap();
        templateParam.put("code", verify.getVerifyCode());
        templateParam.put("time", verify.getEffectiveTime());
        sendParam.put("templateParam", templateParam);
        sendParam.put("signNo", verify.getSignNo());
        sendParam.put("templateCode", verify.getTemplateNo());
        PcfsUtil.sendSms(head, sendParam);
    }


    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            if (GlobParam.baffleSwitch.equals(GlobDict.baffle_switch_valid.getKey())) {
                c = '0';
            }
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

}
