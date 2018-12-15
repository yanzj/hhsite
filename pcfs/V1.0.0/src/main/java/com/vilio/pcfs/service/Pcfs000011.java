package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.LoginInfo;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000011<br>
 * 功能：密码修改页面<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000011 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000011.class);

    @Resource
    private LoginComn loginComn;


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
        //判断交易类型
        if (GlobDict.pwd_update_trans.getKey().equals(ObjectUtils.toString(body.get("transType")))) {
            //修改密码
            //判断原密码是否为空
            checkField(ObjectUtils.toString(body.get("oldPassword")), "原密码", null, 50);
            if (ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("oldPassword")))) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码不能和原密码相同，请重输");
            }
        } else if (GlobDict.pwd_first_trans.getKey().equals(ObjectUtils.toString(body.get("transType")))) {
            //首次登录修改密码
        }else{
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "交易类型错误");
        }
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        if (GlobDict.pwd_update_trans.getKey().equals(ObjectUtils.toString(body.get("transType")))) {
            //修改密码
            pwdUpdateTrans(head,body);
        } else if (GlobDict.pwd_first_trans.getKey().equals(ObjectUtils.toString(body.get("transType")))) {
            //首次登录修改密码
            pwdFirstTrans(head,body);
        }

    }

    /**
     * 首次登录修改密码
     * @param head
     * @param body
     */
    private void pwdFirstTrans(Map<String, Object> head, Map<String, Object> body) throws ErrorException {
        //判断session中是否首次登录
        LoginInfo loginInfoParam = new LoginInfo();
        loginInfoParam.setUserId(ObjectUtils.toString(head.get(GlobParam.PARAM_USER_ID)));
        LoginInfo session = loginComn.getSession(loginInfoParam);
        if (!GlobDict.first_login.getKey().equals(session.getFirstLogin())) {
            //不是首次登录
            throw new ErrorException(ReturnCode.UN_FIRST_LOGIN, "");
        }
        //判断如果是首次登录，直接修改登录密码
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID,ObjectUtils.toString(head.get(GlobParam.PARAM_USER_ID)));
        sendUmParam.put("password",ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umUpdatePasswordByUserId);
        PcfsUtil.sendUMRetJudge(sendUmParam);
    }

    /**
     * 修改密码流程
     * @param head
     * @param body
     * @throws ErrorException
     */
    private void pwdUpdateTrans(Map<String, Object> head, Map<String, Object> body) throws ErrorException {
        //直接调用um进行修改密码操作
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID,head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put("oldPassword",ObjectUtils.toString(body.get("oldPassword")));
        sendUmParam.put("newPassword",ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put("reNewPassword",ObjectUtils.toString(body.get("reNewPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umUpdatePwdFunctionNo);
        //发送um进行验证
        PcfsUtil.sendUMRetJudge(sendUmParam);
    }


}
