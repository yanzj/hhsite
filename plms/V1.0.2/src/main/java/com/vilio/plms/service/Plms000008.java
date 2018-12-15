package com.vilio.plms.service;

import com.vilio.plms.dao.TransitOrderDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Plms000008<br>
 * 功能：在途订单查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月10日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000008 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000008.class);

    @Resource
    private TransitOrderDao transitOrderDao;


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
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        Map param = new HashMap();
        param.put("borrowType", GlobDict.transit_order_borrow.getKey());
        param.put("paidType", GlobDict.transit_order_paid.getKey());
        Map transitOrder = transitOrderDao.queryTransitOrder(param);
//        transitOrder.put("amount", ObjectUtils.toString(transitOrder.get("amount")));
        resultMap.putAll(transitOrder);
    }
}
