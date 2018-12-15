package com.vilio.plms.service;

import com.vilio.plms.dao.AppDao;
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
 * 类名： Plms100002<br>
 * 功能：第一次登录修改用户密码<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100002 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100002.class);

    @Resource
    private AppDao appDao;

    @Resource
    private RemoteUmService remoteUmService;

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
        Map remoteParamMap = new HashMap();
        remoteParamMap.put(Fields.PARAM_NEW_PASSWORD, body.get(Fields.PARAM_NEW_PASSWORD));
        remoteParamMap.put(Fields.PARAM_RE_NEW_PASSWORD, body.get(Fields.PARAM_NEW_PASSWORD_AGAIN));
        remoteParamMap.put(Fields.PARAM_USER_ID, body.get(Fields.PARAM_USER_NO));
        remoteParamMap.put(Fields.PARAM_OLD_PASSWORD, body.get(Fields.PARAM_PASSWORD));
        umResultMap = remoteUmService.callService(remoteParamMap, GlobParam.UM_FUNCTION_FIRST_CHANGEPWD);
        if(resultMap != null){
            Map headMap = (Map<String, Object>) umResultMap.get(Fields.PARAM_MESSAGE_HEAD);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, headMap.get(Fields.PARAM_MESSAGE_ERR_CODE));
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, headMap.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }
    }

}
