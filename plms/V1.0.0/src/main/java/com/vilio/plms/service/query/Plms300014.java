package com.vilio.plms.service.query;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms300014<br>
 * 功能：还款计划查看详情<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月31日<br>
 * 作者： fengliuzhu<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms300014 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms300014.class);

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
        String repaymentScheduleCode = body.get("repaymentScheduleCode").toString();

        //根据还款计划code查找还款计划信息
        Map repaymentScheduleInfo = queryDao.queryRepaymentScheduleInfoByCode(repaymentScheduleCode);

        if (StringUtils.isBlank(repaymentScheduleCode) || repaymentScheduleInfo == null || repaymentScheduleInfo.isEmpty()) {
            return;
        }

        //查找还款计划明细（详情）
        resultMap.putAll(repaymentScheduleInfo);

        Integer pageNo = null != body.get(Fields.PARAM_PAGE_NO) ? new Integer(body.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != body.get(Fields.PARAM_PAGE_SIZE) ? new Integer(body.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;
        //取还款计划列表
        PageHelper.startPage(pageNo, pageSize);
        List repaymentDetailInfoList = queryDao.queryRepaymentScheduleDetailsByCode(repaymentScheduleCode);
        PageInfo pageInfo = new PageInfo(repaymentDetailInfoList);
        resultMap.put("repaymentDetailInfoList", repaymentDetailInfoList);
        resultMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        resultMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        resultMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
    }

}
