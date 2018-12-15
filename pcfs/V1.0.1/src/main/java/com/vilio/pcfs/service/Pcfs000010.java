package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.LoginInfoDao;
import com.vilio.pcfs.dao.MessagesDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.LoginInfo;
import com.vilio.pcfs.util.BeanUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类名： Pcfs000010<br>
 * 功能：登录接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000010 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000010.class);

    @Resource
    private LoginComn loginComn;

    @Resource
    private MessagesDao messagesDao;

    @Resource
    private LoginInfoDao loginInfoDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //手机号
        if (!JudgeUtil.isNull(body.get("mobileNo")) || body.get("mobileNo").toString().length() > 18 || !JudgeUtil.isMobile(body.get("mobileNo").toString())) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "手机号格式错误");
        }
        //登录密码
        if (!JudgeUtil.isNull(body.get("loginPsd")) || body.get("loginPsd").toString().length() > 50) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "密码格式错误");
        }
    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //发送um系统验密
        Map sendUmParam = new HashMap();
        sendUmParam.put("loginType", GlobDict.login_type_mobile.getKey());
        sendUmParam.put("mobile", body.get("mobileNo"));
        sendUmParam.put("password", body.get("loginPsd"));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umLoginFunctionNo);
        Map umResult = PcfsUtil.sendUMRetJudge(sendUmParam);
        Map umResultBody = (Map) umResult.get("body");
        Map userInfo = (Map) umResultBody.get("userInfo");

        if (GlobDict.first_login.getKey().equals(ObjectUtils.toString(userInfo.get("firstLogin")))) {
            //如果是首次登陆，则调用更改首次登录标识
            Map updateFirstLogin = new HashMap();
            updateFirstLogin.put(GlobParam.PARAM_USER_ID, userInfo.get("userId"));
            updateFirstLogin.put("firstLogin", GlobDict.un_first_login.getKey());
            updateFirstLogin.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umUpdateFirstLoginFunctionNo);
            PcfsUtil.sendUMRetJudge(updateFirstLogin);
        }
        //查询数据库中的device_token是否存在，并且不等于当前userId的。表示有其他人在此设备上登录，则修改之前的用户为登出状态
        userInfo.put("deviceToken", head.get(GlobParam.PARAM_DEVICE_TOKEN).toString());
        int ret = loginInfoDao.updateUserLoginStatusByDeviceTokenAndUserId(userInfo);
        if (ret < 0) {
            throw new ErrorException("SYS9997", "");
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(ObjectUtils.toString(userInfo.get("userId")));
        loginInfo.setUserName(ObjectUtils.toString(userInfo.get("userName")));
        loginInfo.setSystemTimestamp(ObjectUtils.toString(System.currentTimeMillis()));
        loginInfo.setMobile(ObjectUtils.toString(userInfo.get("mobile")));
        loginInfo.setEmail(ObjectUtils.toString(userInfo.get("email")));
        loginInfo.setFirstLogin(ObjectUtils.toString(userInfo.get("firstLogin")));
        loginInfo.setFullName(ObjectUtils.toString(userInfo.get("fullName")));
        loginInfo.setSex(ObjectUtils.toString(userInfo.get("sex")));
        loginInfo.setBirthday(ObjectUtils.toString(userInfo.get("birthday")));
        loginInfo.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        loginInfo.setNick(ObjectUtils.toString(userInfo.get("nick")));
        loginInfo.setHeadImg(ObjectUtils.toString(userInfo.get("headImg")));
        loginInfo.setBirthAddr(ObjectUtils.toString(userInfo.get("birthAddr")));
        //判断交易密码是否为空
        if (!JudgeUtil.isNull(userInfo.get("transPassword"))) {
            //交易密码为空
            loginInfo.setTransPwdFlag(GlobDict.trans_pwd_unexist.getKey());
        } else {
            //交易密码存在
            loginInfo.setTransPwdFlag(GlobDict.trans_pwd_exist.getKey());
        }
        loginInfo.setOverdueSet(ObjectUtils.toString(userInfo.get("overdueSet")));
        loginInfo.setPaymentSet(ObjectUtils.toString(userInfo.get("paymentSet")));
        loginInfo.setDisplayDetailSet(ObjectUtils.toString(userInfo.get("displayDetailSet")));
        loginInfo.setDeviceToken(ObjectUtils.toString(head.get(GlobParam.PARAM_DEVICE_TOKEN)));
        loginInfo.setChannel(ObjectUtils.toString(head.get(GlobParam.PARAM_CHANNEL)));
        //设置ssion
        loginComn.setSession(loginInfo);
        //查询未读条数
        Map param = new HashMap();
        param.put("readFlag", GlobDict.unread.getKey());
        param.put("userId", loginInfo.getUserId());
        Integer unreadNo = messagesDao.queryMessagesCountByReadFlag(param);
        resultMap.put("unreadNo", unreadNo == null ? "0" : String.valueOf(unreadNo));
        //返回值设置
        resultMap.putAll(BeanUtil.transBean2Map(loginInfo));
    }


}
