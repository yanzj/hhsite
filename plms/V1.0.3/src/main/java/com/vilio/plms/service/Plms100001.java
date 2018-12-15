package com.vilio.plms.service;

import com.vilio.plms.dao.AppDao;
import com.vilio.plms.dao.LoginInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.LoginService;
import com.vilio.plms.service.base.RemoteUmService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类名： Plms100001<br>
 * 功能：登录接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100001 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100001.class);

    @Resource
    private AppDao appDao;

    @Resource
    private LoginService loginService;

    @Resource
    private RemoteUmService remoteUmService;

    @Resource
    private LoginInfoDao loginInfoDao;

    /**
     * 参数验证
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
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //Step 1 初始化参数及入参整理，整理调用um的参数
        Map umResultMap = new HashMap();
        String clientTimestamp = (String) body.get(Fields.PARAM_CLIENTTIMESTAMP);
        body.put(Fields.PARAM_LOGIN_TYPE, "1");//登录方式，0-用户名密码登录 1-手机密码登录 2-邮箱密码登陆
        body.put(Fields.PARAM_MOBILE, body.get(Fields.PARAM_USER_NAME));//登录方式，0-用户名密码登录 1-手机密码登录 2-邮箱密码登陆
        body.remove(Fields.PARAM_CLIENTTIMESTAMP);
        body.remove(Fields.PARAM_USER_NAME);
        umResultMap = remoteUmService.callService(body, GlobParam.UM_FUNCTION_LOGIN);
        if(umResultMap != null){
            Map headMap = (Map<String, Object>) umResultMap.get(Fields.PARAM_MESSAGE_HEAD);
            Object errCode = headMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            if(ReturnCode.SUCCESS_CODE.equals(errCode)){
                Map bodyMap = (Map<String, Object>) umResultMap.get(Fields.PARAM_MESSAGE_BODY);
                Map userInfo = (Map<String, Object>) bodyMap.get(Fields.PARAM_USER_INFO);
                Map groupInfo = (Map<String, Object>) bodyMap.get(Fields.PARAM_GROUP_INFO);

                //Step 2 更新用户登录session
                Map sessionMap = new HashMap();
                if(userInfo != null){
                    Map user = loginInfoDao.queryUserInfoByUmId(userInfo.get(Fields.PARAM_USER_ID).toString());
                    sessionMap.put(Fields.PARAM_USER_NO, user.get("code"));
                    sessionMap.put(Fields.PARAM_UM_ID, userInfo.get(Fields.PARAM_USER_ID));
                    sessionMap.put("systemTimestamp", System.currentTimeMillis() + "");
                    sessionMap.put(Fields.PARAM_CLIENTTIMESTAMP, clientTimestamp);
                    sessionMap.put(Fields.PARAM_USER_NAME, userInfo.get(Fields.PARAM_USER_NAME));
                    sessionMap.put(Fields.PARAM_FULL_NAME, userInfo.get(Fields.PARAM_FULL_NAME));
                    sessionMap.put(Fields.PARAM_E_MAIL, userInfo.get(Fields.PARAM_E_MAIL));
                    sessionMap.put(Fields.PARAM_MOBILE, userInfo.get(Fields.PARAM_MOBILE));
                    //Step 3 整理出参，按照原接口定义整理
                    resultMap.put(Fields.PARAM_FIRSTLOGIN, userInfo.get(Fields.PARAM_FIRSTLOGIN));
                    resultMap.put(Fields.PARAM_USER_NO, user.get("code"));
                    resultMap.put(Fields.PARAM_UM_ID, userInfo.get(Fields.PARAM_USER_ID));
                    resultMap.put(Fields.PARAM_MOBILE, userInfo.get(Fields.PARAM_MOBILE));
                    resultMap.put(Fields.PARAM_USER_NAME, userInfo.get(Fields.PARAM_USER_NAME));
                    resultMap.put(Fields.PARAM_E_MAIL, userInfo.get(Fields.PARAM_E_MAIL));
                    resultMap.put(Fields.PARAM_FULL_NAME, userInfo.get(Fields.PARAM_FULL_NAME));
                    resultMap.put(Fields.PARAM_STATUS, userInfo.get(Fields.PARAM_STATUS));
                }
                if(groupInfo != null){
                    sessionMap.put(Fields.PARAM_CITY_CODE, groupInfo.get(Fields.PARAM_GROUP_CITY));
                    sessionMap.put(Fields.PARAM_CITY_NAME, groupInfo.get("groupCityAbbrName"));
                    sessionMap.put(Fields.PARAM_GROUP_ID, groupInfo.get(Fields.PARAM_GROUP_ID));
                    sessionMap.put(Fields.PARAM_GROUP_NAME, groupInfo.get(Fields.PARAM_GROUP_NAME));

                    resultMap.put(Fields.PARAM_GROUP_ID, groupInfo.get(Fields.PARAM_GROUP_ID));
                    resultMap.put(Fields.PARAM_GROUP_NAME, groupInfo.get(Fields.PARAM_GROUP_NAME));
                    resultMap.put(Fields.PARAM_CITY_CODE, groupInfo.get(Fields.PARAM_GROUP_CITY));
                    resultMap.put(Fields.PARAM_CITY_NAME, groupInfo.get("groupCityAbbrName"));
                }
                loginService.setSession(sessionMap);
                resultMap.put(Fields.PARAM_MENU_LIST, bodyMap.get(Fields.PARAM_MENUS));

            }else{
                throw new ErrorException(ReturnCode.UM_SYSTEMT_EXCEPTION,
                        null != headMap.get(Fields.PARAM_MESSAGE_ERR_MESG) ? headMap.get(Fields.PARAM_MESSAGE_ERR_MESG).toString() : "");
            }
        }

    }

}
