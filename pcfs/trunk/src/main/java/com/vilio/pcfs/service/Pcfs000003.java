package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import com.vilio.pcfs.util.DateUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000003<br>
 * 功能：借款接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000003 extends BaseService {


    @Resource
    private VerifyDao verifyDao;


    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断借款金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("amount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款金额错误");
        }
        //判断抵押物code是否存在
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, 36);
        //判断期数不能为空，切必须大于0
        checkField(ObjectUtils.toString(body.get("borrowPeriod")), "借款期数", null, 2);

        if (Integer.parseInt(ObjectUtils.toString(body.get("borrowPeriod"))) <= 0) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款期数错误");
        }
        //判断验证码不能为空，切必须等于指定值
        checkField(ObjectUtils.toString(body.get("verifyCode")), "验证码", GlobParam.smsBorrowNum, GlobParam.smsBorrowNum);
        //交易密码不能为空
        checkField(ObjectUtils.toString(body.get("transPsd")), "交易密码", null, null);
        //判断手机号是否存在
        checkField(ObjectUtils.toString(body.get("mobileNo")), "手机号", null, 15);
    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //判断验证码是否有效
        //根据手机号和验证码查询出最近一条的验证码流水
        Verify verifyParam = new Verify();
        verifyParam.setMobileNo(ObjectUtils.toString(body.get("mobileNo")));
        verifyParam.setVerifyCode(ObjectUtils.toString(body.get("verifyCode")));
        verifyParam.setVerifyType(GlobDict.sms_borrow_type.getKey());
        Verify verify = verifyDao.queryVerifyByMobileAndVerify(verifyParam);
        if (verify == null) {
            //验证码不存在
            throw new ErrorException(ReturnCode.VREIFY_CODE_ISNULL, "");
        }
        //判断验证码状态
        if (!GlobDict.verify_effective.getKey().equals(verify.getStatus())) {
            //验证码已失效，请重新获取
            throw new ErrorException(ReturnCode.VREIFY_CODE_NULLIFY, "");
        }
        //判断验证码是否过期
        //获取过期时间
        String pastDateTime = DateUtil.getTimeByMinute(verify.getCreateTime(),Integer.parseInt(verify.getEffectiveTime()));
        if (DateUtil.compareDate(DateUtil.getCurrentDateTime(),pastDateTime)==1) {
            //已过期（验证码已失效，重新获取）
            //标记为失败
            updateVerify(body);
            throw new ErrorException(ReturnCode.VREIFY_CODE_NULLIFY, "");
        }

        //调用um判断交易密码是否正确
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID,head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put("transPassword",ObjectUtils.toString(body.get("transPsd")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO,GlobParam.umVerityTransPwd);
        //发送um进行验证
        Map umResult = PcfsUtil.sendUMRetJudge(sendUmParam);
        //验证成功以后发送贷后进行借款申请操作
        //组织数据发送贷后系统
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000002");
        sendParam.put("amount", ObjectUtils.toString(body.get("amount")));
        sendParam.put("contractCode", ObjectUtils.toString(body.get("contractCode")));
        sendParam.put("borrowPeriod", ObjectUtils.toString(body.get("borrowPeriod")));
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);

        //校验通过，把所有交易验证码标记为失效
        updateVerify(body);
    }


    /**
     * 修改当前手机号所有交易验证码为失效
     * @param body
     * @throws ErrorException
     */
    private void updateVerify(Map<String, Object> body) throws ErrorException {
        Map updateParam = new HashMap();
        updateParam.put("newStatus",GlobDict.verify_invalid.getKey());
        updateParam.put("transType",GlobDict.sms_borrow_type.getKey());
        updateParam.put("oldStatus",GlobDict.verify_effective.getKey());
        updateParam.put("mobileNo",ObjectUtils.toString(body.get("mobileNo")));
        int  ret = verifyDao.updateVerifyStatus(updateParam);
        if (ret<0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }


}
