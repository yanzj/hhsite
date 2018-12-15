package com.vilio.plms.service.opertaion;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100022<br>
 * 功能：还款计划确认初始化接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100022 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100022.class);

    @Resource
    OperationManagerDao operationManagerDao;

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
        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();
        String paidCode = body.get("paidCode") == null ? "" : body.get("paidCode").toString();

        Integer pageNo = null != body.get(Fields.PARAM_PAGE_NO) ? new Integer(body.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != body.get(Fields.PARAM_PAGE_SIZE) ? new Integer(body.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;

        PageHelper.startPage(pageNo, pageSize);
        List list = operationManagerDao.queryRepaymentScheduleDetailTmpListByPaidCode(paidCode);
        PageInfo pageInfo = new PageInfo(list);

        resultMap.put("repaymentList", list);
        resultMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        resultMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        resultMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
    }

}
