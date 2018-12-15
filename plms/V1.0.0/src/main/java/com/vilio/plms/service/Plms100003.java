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
 * 类名： Plms100003<br>
 * 功能：登出<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100003 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100003.class);

    @Resource
    private AppDao appDao;

    @Resource
    private LoginService loginService;

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

        String userNo = (String) body.get(Fields.PARAM_USER_NO);

        Map sessionMap = new HashMap();
        sessionMap.put(Fields.PARAM_USER_NO, userNo);
        loginService.removeSession(sessionMap);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "登出成功");
    }

}
