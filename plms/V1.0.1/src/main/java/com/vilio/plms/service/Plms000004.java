package com.vilio.plms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.PlmsRepaymentScheduleDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类名： Plms000004<br>
 * 功能：还款计划列表查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月10日<br>
 * 作者： zhux<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000004  extends BaseService {


    private static final Logger logger = Logger.getLogger(Plms000004.class);

    @Resource
    private PlmsRepaymentScheduleDao plmsRepaymentScheduleMapper;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_NO))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "页数参数校验失败");
        }
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_SIZE))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前页请求个数校验失败");
        }
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String userId = head.get(Fields.PARAM_USER_ID).toString();
        //入参封装
        Map requestMap = new HashMap();
        requestMap.put(Fields.PARAM_CONTRACT_CODE , body.get(Fields.PARAM_CONTRACT_CODE));
        requestMap.put(Fields.PARAM_STATUS , GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());

        PageHelper.startPage(Integer.valueOf(body.get(Fields.PARAM_PAGE_NO).toString()), Integer.valueOf(body.get(Fields.PARAM_PAGE_SIZE).toString()));
        List<PlmsRepaymentScheduleBean> scheduleList = plmsRepaymentScheduleMapper.queryNeedRepaymentScheduleForContractCode(requestMap);
        BigDecimal totalAmount = plmsRepaymentScheduleMapper.queryNeedRepaymentScheduleTotalAmountForContractCode(requestMap);

        //回参封装
        List<Map> repaymentList = new ArrayList();
        if(null != scheduleList && scheduleList.size() > 0){
            for(PlmsRepaymentScheduleBean bean: scheduleList){
                Map repaymentMap = new HashedMap();
                repaymentMap.put("repaymentCode",bean.getCode());
                repaymentMap.put("repaymentAmount",bean.getAmount().toString().toString());
                repaymentMap.put("repaymentPrincipal",bean.getPrincipal().toString());
                repaymentMap.put("repaymentInterest",bean.getInterest().toString());
                repaymentMap.put("bail",bean.getBail());
                repaymentMap.put("bailPenalty",bean.getBailPenalty());
                if(null != bean.getOverdue()){
                    repaymentMap.put("repaymentOverdue",bean.getOverdue().toString());
                }else{
                    repaymentMap.put("repaymentOverdue",null);
                }
                Date repaymentDate = bean.getRepaymentDate();
                String stringRepaymentDate = DateUtil.formatDate2(repaymentDate);
                repaymentMap.put(Fields.PARAM_REPAYMENT_DATE,stringRepaymentDate);
                repaymentList.add(repaymentMap);
            }
        }
        resultMap.put("totalAmount", totalAmount);
        resultMap.put(Fields.PARAM_REPAYMENT_LIST, repaymentList);

        PageInfo pageInfo = new PageInfo(scheduleList);
        resultMap.put(Fields.PARAM_TOTAL_PAGE, Integer.valueOf(pageInfo.getPages()));
        resultMap.put(Fields.PARAM_TOTAL, Long.valueOf(pageInfo.getTotal()));
        resultMap.put(Fields.PARAM_CURRENT_PAGE, Integer.valueOf(pageInfo.getPageNum()));

    }
}
