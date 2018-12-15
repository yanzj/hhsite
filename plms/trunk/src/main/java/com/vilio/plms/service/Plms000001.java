package com.vilio.plms.service;

import com.vilio.plms.dao.AppDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类名： Plms000001<br>
 * 功能：查询账户信息接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000001 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000001.class);

    @Resource
    private AppDao appDao;

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
        String userId = head.get(Fields.PARAM_USER_ID).toString();
        Map conditionMap = new HashMap();
        conditionMap.put("userId", userId);
        Map accountInfoMap = appDao.queryAccountInfo(conditionMap);
        if (accountInfoMap == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_INFO_FAIL, "");
        }

        String accountCode = (String) accountInfoMap.get("accountCode");
        List accountDetailList = appDao.queryAccountDetail(accountCode);
        Date minDate = null;
        //是否有多套抵押物
        boolean numberFlag = false;
        //是否多套抵押物有不同的授信截止日期
        boolean differentFlag = false;
        //是否含有多套抵押物，切有不同的授信截止日期
        boolean isMultipleSets = false;
        if (accountDetailList.size() > 1) {
            numberFlag = true;
        }
        for (int i = 0; i < accountDetailList.size(); i++) {
            Map accountDetail = (HashMap) accountDetailList.get(i);
            //年化利率乘以100返回上游    wangxf by 20170801
            accountDetail.put("annualizedInterest", MathUtil.strMul2(accountDetail.get("annualizedInterest").toString(), "100", 2));

            String contractCode = (String) accountDetail.get("contractCode");
            String certificateAddress = "";
            List certificateAddressList = (ArrayList) appDao.queryCertificateAddress(contractCode);
            if (certificateAddressList != null) {
                for (int j = 0; j < certificateAddressList.size(); j++) {
                    //这里get(i) 改成  get(j)  wangxf by 20170723
                    String certificateAddressSingle = (String) certificateAddressList.get(j);
                    if ("".equals(certificateAddress)) {
                        certificateAddress = certificateAddressSingle;
                    } else {
                        certificateAddress = certificateAddress + ";" + certificateAddressSingle;
                    }
                }
            }
            accountDetail.put("certificateAddress", certificateAddress);
            Date date = (Date) accountDetail.get("creditEndDate");
            if (minDate == null) {
                minDate = date;
            } else {
                if (!differentFlag && minDate.compareTo(date) != 0) {
                    differentFlag = true;
                }
                if (minDate.after(date)) {
                    minDate = date;
                }
            }

            Map borrowClosedPeriodMap = appDao.queryBorrowClosedPeriodMap(contractCode);
            accountDetail.put("circle", borrowClosedPeriodMap.get("circle"));
            Integer borrowClosedPeriod = (Integer) borrowClosedPeriodMap.get("borrowClosedPeriod");
            borrowClosedPeriod = borrowClosedPeriod * (-1);
            Date principalDate = (Date) accountDetail.get("principalDate");
            if (principalDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(principalDate);
                calendar.add(Calendar.MONTH, borrowClosedPeriod);
                Date loanEndDate = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String loanEndDateString = sdf.format(loanEndDate);
                Date loanByDate = sdf2.parse(loanEndDateString);
                //日期转换  wangxf by 20170723
                accountDetail.put("loanByDate", DateUtil.convert(loanByDate, "yyyy-MM-dd"));
            }

            //日期转换  wangxf by 20170723
            accountDetail.put("creditEndDate", DateUtil.convert(date, "yyyy-MM-dd"));
            accountDetail.put("principalDate", DateUtil.convert((Date) accountDetail.get("principalDate"), "yyyy-MM-dd"));
        }
        if (numberFlag && differentFlag) {
            isMultipleSets = true;
        }
        Map firstRepaymentMap = appDao.queryFirstRepaymentMap(conditionMap);
        Map overdueCountMap = appDao.queryOverdueCountMap(conditionMap);

        if (accountInfoMap != null) {
            resultMap.putAll(accountInfoMap);
        }
        resultMap.put("creditEndDateTotal", minDate == null ? "" : DateUtil.convert(minDate, "yyyy-MM-dd"));
        resultMap.put("isMultipleSets", isMultipleSets);
        resultMap.put("houseList", accountDetailList);
        if (firstRepaymentMap != null) {
            resultMap.putAll(firstRepaymentMap);
        }
        if (overdueCountMap != null) {
            resultMap.putAll(overdueCountMap);
        }
        //resultMap.putAll(borrowClosedPeriodMap);
    }

}
