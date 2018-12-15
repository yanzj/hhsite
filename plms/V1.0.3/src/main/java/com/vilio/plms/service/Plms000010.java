package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.House;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import com.vilio.plms.util.MathUtil;
import com.vilio.plms.util.PlmsUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms000010<br>
 * 功能：用户抵押物信息信息查询（改造为合同查询）<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000010 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000010.class);

    @Resource
    private HouseDao houseDao;

    @Resource
    private ContractDao contractDao;

    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, 36);
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String contractCode = (String) body.get("contractCode");
        //查询合同详情
        Map qryParam = new HashMap();
        qryParam.put("umId", head.get(Fields.PARAM_USER_ID));
        qryParam.put("contractCode", contractCode);
        Map contractDetail = contractDao.qryContractDetail(qryParam);
        if (contractDetail == null) {
            throw new ErrorException(ReturnCode.CONTRACT_QUERY_FAIL, "");
        }
        //根据合同查询抵押物信息
        //查询抵押物信息
        List<House> houses = houseDao.qryHouseForContract(contractCode);
        String certificateAddress = "";
        if (houses != null) {
            for (int j = 0; j < houses.size(); j++) {
                //这里get(i) 改成  get(j)  wangxf by 20170723
                House house = houses.get(j);
                String certificateAddressSingle = house.getCertificateAddress();
                if ("".equals(certificateAddress)) {
                    certificateAddress = certificateAddressSingle;
                } else {
                    certificateAddress = certificateAddress + ";" + certificateAddressSingle;
                }
            }
        } else {
            throw new ErrorException(ReturnCode.HOUSE_INFO_FAIL, "");
        }
        if (JudgeUtil.isNull(contractDetail.get("principalOverInterest"))) {
            String principalOverInterest = MathUtil.strMul2(contractDetail.get("principalOverInterest").toString(), "100", 2);
            contractDetail.put("principalOverInterest", principalOverInterest);
        }
        if (JudgeUtil.isNull(contractDetail.get("annualizedInterest"))) {
            String annualizedInterest = MathUtil.strMul2(contractDetail.get("annualizedInterest").toString(), "100", 2);
            contractDetail.put("annualizedInterest", annualizedInterest);
        }
        //还款方式转换
        if (JudgeUtil.isNull(contractDetail.get("repaymentMethods"))) {
            if (GlobDict.first_interest.getKey().equals(contractDetail.get("repaymentMethods"))) {
                contractDetail.put("repaymentMethods", GlobDict.first_interest.getDesc());
            } else if (GlobDict.confirm_interest.getKey().equals(contractDetail.get("detailCode"))) {
                contractDetail.put("repaymentMethods", GlobDict.confirm_interest.getDesc());
            } else if (GlobDict.after_interest.getKey().equals(contractDetail.get("repaymentMethods"))) {
                contractDetail.put("repaymentMethods", GlobDict.after_interest.getDesc());
            }
        }
        //查询最高期数(月数)
        //当前时间加上本金归还周期算出一个日期，跟授信截止日期作比较（根据算头算尾参数决定是否减一天），如果大于授信截止日期，则本金归还周期递减1，继续算，直到满足条件
        //最高期数
        String loanCount = contractDetail.get("principalRepaymentPeriod").toString();
        //借款到期日
        String borrowEndDate = "";
        for (int i = Integer.parseInt(contractDetail.get("principalRepaymentPeriod").toString()); i > 0; i--) {
            String date = DateUtil.convert(PlmsUtil.getDueDate(new Date(),i),"yyyy-MM-dd");
            //算出来的日期，和授信日期作比较，如果小于授信日期，则跳出循环
            if (DateUtil.dateCompare(date, contractDetail.get("creditEndDate").toString(), "yyyy-MM-dd") >= 0) {
                //判断放款天数计算规则
                if (GlobDict.paid_days_computational_rule_end.getKey().equals(contractDetail.get("paidDaysComputationalRule"))) {
                    //算头算尾
                } else {
                    //算头不算尾，算出来的日期加一天
                    date = DateUtil.getDaysDate2(date, 1);
                }
                //借款到期日
                borrowEndDate = date;
                loanCount = String.valueOf(i);
                break;
            }
        }
        if ("".equals(borrowEndDate)) {
            //借款到期日错误
            throw new ErrorException(ReturnCode.BORROW_END_DATE_FAIL, "");
        }
        resultMap.put("loanCount",loanCount);
        resultMap.put("borrowEndDate",borrowEndDate);


        resultMap.putAll(contractDetail);
        resultMap.put("certificateAddress", certificateAddress);
        //需确定
        resultMap.put("accountNo", contractDetail.get("accountNo"));

        //获取最近还款日
        String latestUnpaidDate = null;
        String latestUnpaidScheduleCode = null;
        List<Map> upapidDetailList = plmsRepaymentScheduleDetailDao.getUnpaidDetailForContractCode(contractCode);
        if (null != upapidDetailList && upapidDetailList.size() > 0) {
            latestUnpaidDate = (String) upapidDetailList.get(0).get("repaymentDate");
            latestUnpaidScheduleCode = (String) upapidDetailList.get(0).get("code");
        }
        resultMap.put("latestUnpaidDate", latestUnpaidDate);
        resultMap.put("latestUnpaidScheduleCode", latestUnpaidScheduleCode);

        //预期总金额和笔数
        Map parMap = new HashedMap();
        parMap.put("contractCode", contractCode);
        parMap.put("today", DateUtil.getCurrentDate());
        Map overDueMap = plmsRepaymentScheduleDetailDao.statisticsOverDueAmontAndCountForContract(parMap);
        resultMap.put("overdueAmount", ((BigDecimal) overDueMap.get("amount")).setScale(2, RoundingMode.HALF_DOWN));
        resultMap.put("overdueCount", new BigDecimal((Long) overDueMap.get("count")).intValue());


    }
}
