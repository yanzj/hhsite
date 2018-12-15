package com.vilio.plms.service;

import com.vilio.plms.dao.PlmsRepaymentScheduleDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms000005<br>
 * 功能：查询还款计划详情接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月10日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000005 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000001.class);

    @Resource
    private PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("repaymentCode"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "还款计划流水号参数校验失败");
        }




    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String orderID = body.get("repaymentCode").toString();
        Map conditionMap = new HashMap();
        conditionMap.put("repaymentCode", orderID);
        List<Map> list = plmsRepaymentScheduleDao.queryRepaymentDetailInfo(conditionMap);

        //还款日期
        String repaymentDate = null;
        //应还总额
        BigDecimal totalAmount = new BigDecimal(0);
        //应还本金总额
        BigDecimal totalPrincipal = new BigDecimal(0);
        //应还利息总额
        BigDecimal totalInterest = new BigDecimal(0);
        //应还罚息总额
        BigDecimal totalPenalty  = new BigDecimal(0);
        //应还服务费
        BigDecimal totalServiceFee = new BigDecimal(0);
        //应还服务费违约金
        BigDecimal totalServiceFeePenalty  = new BigDecimal(0);
        //保证金
        BigDecimal totalBail  = new BigDecimal(0);
        //保证金违约金
        BigDecimal totalBailPenalty  = new BigDecimal(0);
        if(null != list && list.size() > 0){
            repaymentDate = (String) list.get(0).get("repaymentDate");
            for(Map m :list){
                totalAmount = totalAmount.add((BigDecimal) m.get("repaymentAmount"));
                totalPrincipal = totalPrincipal.add((BigDecimal) m.get("repaymentPrincipal"));
                totalInterest = totalInterest.add((BigDecimal) m.get("repaymentInterest"));
                if(null != m.get("repaymentOverdue")){
                    totalPenalty = totalPenalty.add((BigDecimal) m.get("repaymentOverdue"));
                }
                if(null != m.get("serviceFee")){
                    totalServiceFee = totalServiceFee.add((BigDecimal) m.get("serviceFee"));
                }
                if(null != m.get("serviceFeePenalty")){
                    totalServiceFeePenalty = totalServiceFeePenalty.add((BigDecimal) m.get("serviceFeePenalty"));
                }
                if(null != m.get("bail")){
                    totalBail = totalBail.add((BigDecimal) m.get("bail"));
                }
                if(null != m.get("bailPenalty")){
                    totalBailPenalty = totalBailPenalty.add((BigDecimal) m.get("bailPenalty"));
                }
            }
        }


        resultMap.put("repaymentDate", repaymentDate);
        resultMap.put("totalAmount", totalAmount);
        resultMap.put("totalPrincipal", totalPrincipal);
        resultMap.put("totalInterest", totalInterest);
        resultMap.put("totalPenalty", totalPenalty);
        resultMap.put("totalServiceFee", totalServiceFee);
        resultMap.put("totalServiceFeePenalty", totalServiceFeePenalty);
        resultMap.put("totalBail", totalBail);
        resultMap.put("bailPenalty", totalBailPenalty);

        resultMap.put("repaymentDetailInfoList", list);
    }













    }
