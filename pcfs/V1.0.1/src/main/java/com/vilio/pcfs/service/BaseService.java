package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VerifyDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.Verify;
import com.vilio.pcfs.util.DateUtil;
import com.vilio.pcfs.util.JudgeUtil;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类名： BaseService<br>
 * 功能：所有service的父类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BaseService {

    private static final Logger logger = Logger.getLogger(BaseService.class);

    @Resource
    private VerifyDao verifyDao;

    @Resource
    public CommonService commonService;

    /**
     * @param params
     * @return
     */
    public Map<String, Object> doMain(Map<String, Object> params) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();
        Map<String, Object> body = new HashMap<String, Object>();
        respMap.put(GlobParam.PARAM_MESSAGE_BODY, params.get(GlobParam.PARAM_MESSAGE_BODY));
        try {
            head.putAll((Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD));
            head.put(GlobParam.PARAM_SYSTEM_TIME,DateUtil.getCurrentDateTime2());
            respMap.put(GlobParam.PARAM_MESSAGE_HEAD, head);
            body = doService(params);
            if (body!=null &&body.size()!=0) {
                JudgeUtil.MapValToString(body);
            }
            respMap.put(GlobParam.PARAM_MESSAGE_BODY, body);
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SUCCESS_CODE));
        } catch (ErrorException e) {
            e.printStackTrace();
            String returnCode = e.getErroCode() == null || "".equals(e.getErroCode()) ? ReturnCode.SYSTEM_EXCEPTION : e.getErroCode();
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, returnCode);
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, e.getMessage() == null || "".equals(e.getMessage())
                    ? GlobParam.ERROR_CODE.get(returnCode) : e.getMessage());
            logger.error("错误码" + head.get(GlobParam.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(GlobParam.PARAM_MESSAGE_ERR_MESG));
        } catch (Exception e) {
            e.printStackTrace();
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            logger.error("错误码" + head.get(GlobParam.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(GlobParam.PARAM_MESSAGE_ERR_MESG));
        }
        return respMap;
    }

    /**
     * 流程方法
     *
     * @param params
     * @return
     * @throws ErrorException
     */
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 参数校验
        checkParam(body);
        //登录业务处理
        busiService(head, body, resultMap);
        //返回参数
        return resultMap;
    }


    /**
     * 参数验证空实现
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException,Exception {
    }



    /**
     * @param field
     * @param name
     * @param needLength
     * @param maxLength
     * @throws ErrorException
     */
    public void checkField(String field, String name, Integer needLength, Integer maxLength) throws ErrorException {
        if (field == null || field.length() == 0) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("缺少%s必要参数,或数据格式不正确", name));
        }
        if (needLength != null) {
            if (field == null || field.length() != needLength) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("%s长度必须为%d", name, needLength));
            }
        }
        if (maxLength != null) {
            if (field == null || field.length() > maxLength) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("%s长度不能大于%d", name, maxLength));
            }
        }
    }


    /**
     * 校验手机号验证码
     * @param mobileNo
     * @param verifyCode
     * @param verifyType
     */
    public Verify checkVerify(String mobileNo, String verifyCode, String verifyType) throws ErrorException, ParseException {
        Verify verifyParam = new Verify();
        verifyParam.setMobileNo(mobileNo);
        verifyParam.setVerifyCode(verifyCode);
        verifyParam.setVerifyType(verifyType);
        Verify verify = verifyDao.queryVerifyByMobileAndVerify(verifyParam);
        if (verify == null) {
            //验证码不存在
            throw new ErrorException(ReturnCode.VREIFY_CODE_ISNULL, "");
        }
        //判断验证码状态
        if (GlobDict.verify_invalid.getKey().equals(verify.getStatus())) {
            //验证码已失效，请重新获取
            throw new ErrorException(ReturnCode.VREIFY_CODE_NULLIFY, "");
        }
        //判断验证码是否过期
        //获取过期时间
        String pastDateTime = DateUtil.getTimeByMinute(verify.getCreateTime(),Integer.parseInt(verify.getEffectiveTime()));
        if (DateUtil.compareDate(DateUtil.getCurrentDateTime(),pastDateTime)==1) {
            //已过期（验证码已失效，重新获取）
            //标记为失效
            throw new ErrorException(ReturnCode.VREIFY_CODE_NULLIFY, "");
        }
        return verify;
    }

    /**
     * 定时任务流程入口
     */
    public void execute() throws Exception {
    }

    /**
     * 生成uuid
     *
     * @return
     * @throws ErrorException
     */
    public String getUUID() {
        return UUID.randomUUID().toString();
    }



}
