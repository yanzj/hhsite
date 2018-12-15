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
 * 类名： Pcfs000023<br>
 * 功能：首次修改交易密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000023 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000023.class);

    @Resource
    private LoginComn loginComn;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("newPassword")), "交易密码", null, 50);

        if (!ObjectUtils.toString(body.get("newPassword")).equals(ObjectUtils.toString(body.get("reNewPassword")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "新密码和确认新密码不相同");
        }
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //获取session，检查交易密码是否存在
        LoginInfo loginInfoParam = new LoginInfo();
        loginInfoParam.setUserId(ObjectUtils.toString(head.get(GlobParam.PARAM_USER_ID)));
        LoginInfo session = loginComn.getSession(loginInfoParam);
        if (GlobDict.trans_pwd_exist.getKey().equals(session.getTransPwdFlag())) {
            //交易密码存在
            throw new ErrorException(ReturnCode.TRANS_PWD_EXIST, "");
        }
        //设置交易密码
        Map sendUmParam = new HashMap();
        sendUmParam.put(GlobParam.PARAM_USER_ID, head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put("newTransPassword", ObjectUtils.toString(body.get("newPassword")));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umFirstUpdateTransPwd);
        PcfsUtil.sendUMRetJudge(sendUmParam);
    }


}
