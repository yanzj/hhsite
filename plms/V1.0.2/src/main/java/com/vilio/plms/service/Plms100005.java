package com.vilio.plms.service;

import com.vilio.plms.dao.AppDao;
import com.vilio.plms.dao.MessageReceiveDao;
import com.vilio.plms.dao.TodoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.MessageReceive;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.LoginService;
import com.vilio.plms.service.base.MessageService;
import com.vilio.plms.service.base.RemoteUmService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类名： Plms100005<br>
 * 功能：修改接收到的消息，默认修改消息状态为已读，<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100005 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100005.class);

    @Resource
    MessageService messageService;

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
        Map<String, Object> result = messageService.modifyReceiveMessage(body);

        resultMap.putAll(result);
    }

}
