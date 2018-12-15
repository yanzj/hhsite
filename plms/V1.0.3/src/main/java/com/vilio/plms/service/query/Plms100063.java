package com.vilio.plms.service.query;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100063<br>
 * 功能：查看账户明细<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月31日<br>
 * 作者： fengliuzhu<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100063 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100063.class);

    @Resource
    QueryDao queryDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 业务流程实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String contractCode = body.get("contractCode").toString();

        Map map = queryDao.queryAccountDetail(contractCode);

        if (StringUtils.isBlank(contractCode) || map == null || map.isEmpty()) {
            return;
        }

        resultMap.putAll(map);

    }

}
