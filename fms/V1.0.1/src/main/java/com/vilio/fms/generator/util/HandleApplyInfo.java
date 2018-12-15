package com.vilio.fms.generator.util;

import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Constants;
import com.vilio.fms.util.Fields;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 处理进件的参数
 * @param:
 * @return:
 * @Author: liuzhu.feng
 * @Date: 2017/8/1/0001
 */
public class HandleApplyInfo {

    private final static String NUMBER_CN_SUFFIX = "CN";//金额小写转大写，会在同级同名参数的参数名加后缀
    private final static String FIELD_STR_SUFFIX = "Str";//列表类某字段需要串联，则在列表同级添加对应字段加
    private final static String SYMBOL_SPLIT = "_";//分隔符，顿号

    private static Logger logger = Logger.getLogger(HandleApplyInfo.class);

    /**
     * 根据进件信息、合同抵押方式、以及合同生成顺位处理进件参数
     *
     * @param applyInfoMap
     * @param mortgageType
     * @return
     */
    public static List<Map> doHandle(Map applyInfoMap, boolean relateToMortgageType, String mortgageType) {
        if (applyInfoMap == null) {
            return null;
        }
        String combindFileName = "";
        List<Map> returnApplyList = new ArrayList<>();
        Map returnApplyInfo = CommonUtil.clone((HashMap) applyInfoMap);//参数保护
        //申请金额
        Object loanAmount = returnApplyInfo.get(Fields.PARAM_LOAN_AMOUNT);
        returnApplyInfo.put(Fields.PARAM_LOAN_AMOUNT + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(loanAmount));
        //借款期限
        Object loanPeriodName = returnApplyInfo.get(Fields.PARAM_LOAN_PERIOD_NAME);
        //意向金
        Object intentionMoney = returnApplyInfo.get(Fields.PARAM_INTENTION_MONEY);
        returnApplyInfo.put(Fields.PARAM_INTENTION_MONEY + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(intentionMoney));

        //产权人列表
        List<String> propertyOwnerList = (List<String>) returnApplyInfo.get("propertyOwnerCode");
        if(propertyOwnerList == null){
            propertyOwnerList = new ArrayList<>();
        }

        //人员信息列表 && 借款人信息列表
        List<Map> bmsPeopleList = (List<Map>) returnApplyInfo.get("loanPeopleList");
        String loaners = "";//定义所有借款人，用顿号分隔
        if (bmsPeopleList != null) {
            for (Map bmsPeopleMap : bmsPeopleList) {
                boolean mainLoanner = Boolean.valueOf(bmsPeopleMap.get("mainLoanner") == null ? "false" : bmsPeopleMap.get("mainLoanner").toString());
                boolean isBorrower = Boolean.valueOf(bmsPeopleMap.get("loaners") == null ? "false" : bmsPeopleMap.get("loaners").toString());
                String loaner = bmsPeopleMap.get(Fields.PARAM_NAME) == null ? "" : bmsPeopleMap.get(Fields.PARAM_NAME).toString();
                String peopleId = bmsPeopleMap.get("id") == null ? "-1" : bmsPeopleMap.get("id").toString();
                String certificateNumber = bmsPeopleMap.get("certificateNumber") == null ? "" : bmsPeopleMap.get("certificateNumber").toString();

                //添加主借款人信息
                if (mainLoanner) {
                    returnApplyInfo.put("customerAddress", bmsPeopleMap.get("familyAddress"));
                }
                //年收入
                Object annualIncome = returnApplyInfo.get("annualIncome");
                bmsPeopleMap.put("annualIncome" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(annualIncome));
                //是借款人
                if (isBorrower) {
                    loaners = StringUtils.isBlank(loaner) ? loaners : loaners + loaner + SYMBOL_SPLIT;
                }
                //判断是否是产权人
                bmsPeopleMap.put("propertyOwner", propertyOwnerList.contains(peopleId));
                //判断是否未成年
                bmsPeopleMap.put("grownUp", isGrownUp(certificateNumber));
            }
        }
        loaners = StringUtils.isBlank(loaners) ? loaners : loaners.substring(0, loaners.length() - 1);
        BigDecimal loanAmountBig = new BigDecimal(loanAmount == null ? "0" : loanAmount.toString());
        combindFileName = loaners + "_" + (loanAmountBig.divide(new BigDecimal("10000"), 0).longValue()) + "万_" + (loanPeriodName == null ? "0" : loanPeriodName) + "个月";
        returnApplyInfo.put("combindFileName", combindFileName);
        returnApplyInfo.put("loaners" + FIELD_STR_SUFFIX, loaners);

        //风控信息
        Map bmsLoanRiskInfo = (Map) returnApplyInfo.get("loanRiskInfo");
        if(bmsLoanRiskInfo != null){
            //批复总额度
            Object totalApprovalAmount = bmsLoanRiskInfo.get("totalApprovalAmount");
            bmsLoanRiskInfo.put("totalApprovalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalApprovalAmount));

            //担保总额度
            Object guaranteeAmount = bmsLoanRiskInfo.get("guaranteeAmount");
            bmsLoanRiskInfo.put("guaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));

            //担保费
            Object guaranteeFee = bmsLoanRiskInfo.get("guaranteeFee");
            bmsLoanRiskInfo.put("guaranteeFee" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeFee));
            //保证金金额
            Object depositAmount = bmsLoanRiskInfo.get("depositAmount");
            bmsLoanRiskInfo.put("depositAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(depositAmount));
        }


        //抵押物信息列表
        List<Map> bmsMortgageList = (List<Map>) returnApplyInfo.get("loanPawnList");
        BigDecimal totalGuaranteeAmount = new BigDecimal(BigInteger.ZERO);//总担保金额
        BigDecimal totalApprovalAmount = new BigDecimal(BigInteger.ZERO);//总批复金额
        BigDecimal guaranteeSecureAmount = new BigDecimal(BigInteger.ZERO);//担保保险总额
        double approvalLimit = 0;//总批复期限
        if (bmsMortgageList != null) {
            for (Map bmsMortgageMap : bmsMortgageList) {
                //评估价
                Object valuation = bmsMortgageMap.get("valuation");
                bmsMortgageMap.put("valuation" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(valuation));
                //一抵余额
                Object mortgageFirstBalance = bmsMortgageMap.get("mortgageFirstBalance");
                bmsMortgageMap.put("mortgageFirstBalance" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageFirstBalance));
                //一抵金额
                Object mortgageFirstAmount = bmsMortgageMap.get("mortgageFirstAmount");
                bmsMortgageMap.put("mortgageFirstAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageFirstAmount));
                //二抵金额
                Object mortgageSecondAmount = bmsMortgageMap.get("mortgageSecondAmount");
                bmsMortgageMap.put("mortgageSecondAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageSecondAmount));
                //批复金额
                Object approvalAmount = bmsMortgageMap.get("approvalAmount");
                approvalAmount = approvalAmount == null ? "0" : approvalAmount.toString();
                totalApprovalAmount = totalApprovalAmount.add(new BigDecimal(approvalAmount.toString()));
                bmsMortgageMap.put("approvalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(approvalAmount));
                //担保额度
                Object guaranteeAmount = bmsMortgageMap.get("guaranteeAmount");
                guaranteeAmount = guaranteeAmount == null ? "0" : guaranteeAmount.toString();
                totalGuaranteeAmount = totalGuaranteeAmount.add(new BigDecimal(guaranteeAmount.toString()));
                bmsMortgageMap.put("guaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));
                //合同抵押物价值
                Object contractPawnPrice = bmsMortgageMap.get("contractPawnPrice");
                bmsMortgageMap.put("contractPawnPrice" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(contractPawnPrice));
                //抵押权债权总额
                Object pawnMortgageAmount = bmsMortgageMap.get("pawnMortgageAmount");
                bmsMortgageMap.put("pawnMortgageAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(pawnMortgageAmount));

                // 年化利率
                Object interestRate = bmsMortgageMap.get("interestRate");
                bmsMortgageMap.put("interestRate", interestRate == null ? 0 : interestRate);
                // 资方产品利率
                Object backendInterestRate = bmsMortgageMap.get("backendInterestRate");
                bmsMortgageMap.put("backendInterestRate", backendInterestRate == null ? 0 : backendInterestRate);

                //息差=资方产品利率-年化利率
                returnApplyInfo.put("interestSpread", (interestRate == null ? 0 : Double.parseDouble(interestRate.toString())) - (backendInterestRate == null ? 0 : Double.parseDouble(backendInterestRate.toString())));


                //批复期限
                logger.info("批复期限的类型是：" + bmsMortgageMap.get("approvalLimit").getClass());
                approvalLimit = bmsMortgageMap.get("approvalLimit") == null ? 0 : Double.parseDouble(bmsMortgageMap.get("approvalLimit").toString());

                //分抵分押
                if (relateToMortgageType && Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType)) {
                    //克隆抵押物
                    Map returnApplyInfoClone = CommonUtil.clone((HashMap)returnApplyInfo);
                    List<Map> cloneMortgageList = new ArrayList<>();
                    returnApplyInfoClone.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST));
                    returnApplyInfoClone.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND));
                    returnApplyInfoClone.put("totalGuaranteeAmount", guaranteeAmount);//担保额度
                    returnApplyInfoClone.put("totalGuaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));//担保额度
                    returnApplyInfoClone.put("totalApprovalAmount", approvalAmount);//批复金额
                    returnApplyInfoClone.put("totalApprovalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(approvalAmount));//批复金额
                    //担保费+保险费合计：担保、保险费合计标准为：借款期限6个月（含）以内为借款金额的【0.56】%；借款期限6个月以上12个月（含）以内为【1.12】%
                    if(approvalLimit <= 6){
                        guaranteeSecureAmount = new BigDecimal(approvalAmount.toString()).multiply(new BigDecimal("0.0056"));
                    } else if(approvalLimit > 6 && approvalLimit <= 12){
                        guaranteeSecureAmount = new BigDecimal(approvalAmount.toString()).multiply(new BigDecimal("0.0112"));
                    }
                    returnApplyInfoClone.put("guaranteeSecureAmount", guaranteeSecureAmount == null ? 0 : guaranteeSecureAmount);
                    returnApplyInfoClone.put("guaranteeSecureAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeSecureAmount));

                    cloneMortgageList.add(bmsMortgageMap);
                    //覆盖原来的抵押物列表为只有一个的
                    returnApplyInfoClone.put("loanPawnList", cloneMortgageList);
                    returnApplyList.add(returnApplyInfoClone);
                }
            }

            //联抵联押，不需要处理抵押物
            if (!(relateToMortgageType && Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType))) {
                returnApplyList.add(returnApplyInfo);
            }
            //添加总的担保额度
            returnApplyInfo.put("totalGuaranteeAmount", totalGuaranteeAmount);
            returnApplyInfo.put("totalGuaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalGuaranteeAmount));
            //添加总的批复额度
            returnApplyInfo.put("totalApprovalAmount", totalApprovalAmount);
            returnApplyInfo.put("totalApprovalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalApprovalAmount));

            //担保费+保险费合计：担保、保险费合计标准为：借款期限6个月（含）以内为借款金额的【0.56】%；借款期限6个月以上12个月（含）以内为【1.12】%
            if(approvalLimit <= 6){
                guaranteeSecureAmount = totalApprovalAmount.multiply(new BigDecimal("0.0056"));
            } else if(approvalLimit > 6 && approvalLimit <= 12){
                guaranteeSecureAmount = totalApprovalAmount.multiply(new BigDecimal("0.0112"));
            }
            returnApplyInfo.put("guaranteeSecureAmount", guaranteeSecureAmount == null ? 0 : guaranteeSecureAmount);
            returnApplyInfo.put("guaranteeSecureAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeSecureAmount));
        }

        logger.info("\n\n---------------------------------------------------------打印处理后的参数列表-------开始-----------------------------------------------------------");
        logger.info(returnApplyList);
        logger.info("---------------------------------------------------------打印处理后的参数列表-------结束-----------------------------------------------------------\n\n");

        return returnApplyList;
    }


    /**
     * 根据借款人切分参数
     *
     * @param applyInfoMap
     * @return
     */
    public static List<Map> doHandleWithCustomer(Map applyInfoMap) {
        if (applyInfoMap == null) {
            return null;
        }
        String combindFileName = "";
        List<Map> returnApplyList = new ArrayList<>();
        Map returnApplyInfo = CommonUtil.clone((HashMap) applyInfoMap);//参数保护
        //申请金额
        Object loanAmount = returnApplyInfo.get(Fields.PARAM_LOAN_AMOUNT);
        returnApplyInfo.put(Fields.PARAM_LOAN_AMOUNT + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(loanAmount));
        //借款期限
        Object loanPeriodName = returnApplyInfo.get(Fields.PARAM_LOAN_PERIOD_NAME);
        //意向金
        Object intentionMoney = returnApplyInfo.get(Fields.PARAM_INTENTION_MONEY);
        returnApplyInfo.put(Fields.PARAM_INTENTION_MONEY + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(intentionMoney));

        //风控信息
        Map bmsLoanRiskInfo = (Map) returnApplyInfo.get("loanRiskInfo");
        if(bmsLoanRiskInfo != null){
            //批复总额度
            Object totalApprovalAmount = bmsLoanRiskInfo.get("totalApprovalAmount");
            bmsLoanRiskInfo.put("totalApprovalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalApprovalAmount));

            //担保总额度
            Object guaranteeAmount = bmsLoanRiskInfo.get("guaranteeAmount");
            bmsLoanRiskInfo.put("guaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));

            //担保费
            Object guaranteeFee = bmsLoanRiskInfo.get("guaranteeFee");
            bmsLoanRiskInfo.put("guaranteeFee" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeFee));
            //保证金金额
            Object depositAmount = bmsLoanRiskInfo.get("depositAmount");
            bmsLoanRiskInfo.put("depositAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(depositAmount));
        }


        //抵押物信息列表
        List<Map> bmsMortgageList = (List<Map>) returnApplyInfo.get("loanPawnList");
        BigDecimal totalGuaranteeAmount = new BigDecimal(BigInteger.ZERO);//总担保金额
        BigDecimal totalApprovalAmount = new BigDecimal(BigInteger.ZERO);//总批复金额
        BigDecimal guaranteeSecureAmount = new BigDecimal(BigInteger.ZERO);//担保保险总额
        double approvalLimit = 0;//总批复期限
        if (bmsMortgageList != null) {
            for (Map bmsMortgageMap : bmsMortgageList) {
                //评估价
                Object valuation = bmsMortgageMap.get("valuation");
                bmsMortgageMap.put("valuation" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(valuation));
                //一抵余额
                Object mortgageFirstBalance = bmsMortgageMap.get("mortgageFirstBalance");
                bmsMortgageMap.put("mortgageFirstBalance" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageFirstBalance));
                //一抵金额
                Object mortgageFirstAmount = bmsMortgageMap.get("mortgageFirstAmount");
                bmsMortgageMap.put("mortgageFirstAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageFirstAmount));
                //二抵金额
                Object mortgageSecondAmount = bmsMortgageMap.get("mortgageSecondAmount");
                bmsMortgageMap.put("mortgageSecondAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(mortgageSecondAmount));
                //批复金额
                Object approvalAmount = bmsMortgageMap.get("approvalAmount");
                approvalAmount = approvalAmount == null ? "0" : approvalAmount.toString();
                totalApprovalAmount = totalApprovalAmount.add(new BigDecimal(approvalAmount.toString()));
                bmsMortgageMap.put("approvalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(approvalAmount));
                //担保额度
                Object guaranteeAmount = bmsMortgageMap.get("guaranteeAmount");
                guaranteeAmount = guaranteeAmount == null ? "0" : guaranteeAmount.toString();
                totalGuaranteeAmount = totalGuaranteeAmount.add(new BigDecimal(guaranteeAmount.toString()));
                bmsMortgageMap.put("guaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));
                //合同抵押物价值
                Object contractPawnPrice = bmsMortgageMap.get("contractPawnPrice");
                bmsMortgageMap.put("contractPawnPrice" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(contractPawnPrice));
                //抵押权债权总额
                Object pawnMortgageAmount = bmsMortgageMap.get("pawnMortgageAmount");
                bmsMortgageMap.put("pawnMortgageAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(pawnMortgageAmount));

                // 年化利率
                Object interestRate = bmsMortgageMap.get("interestRate");
                bmsMortgageMap.put("interestRate", interestRate == null ? 0 : interestRate);
                // 资方产品利率
                Object backendInterestRate = bmsMortgageMap.get("backendInterestRate");
                bmsMortgageMap.put("backendInterestRate", backendInterestRate == null ? 0 : backendInterestRate);

                //息差=资方产品利率-年化利率
                returnApplyInfo.put("interestSpread", (interestRate == null ? 0 : Double.parseDouble(interestRate.toString())) - (backendInterestRate == null ? 0 : Double.parseDouble(backendInterestRate.toString())));

                //批复期限
                logger.info("批复期限的类型是：" + bmsMortgageMap.get("approvalLimit").getClass());
                approvalLimit = bmsMortgageMap.get("approvalLimit") == null ? 0 : Double.parseDouble(bmsMortgageMap.get("approvalLimit").toString());

            }
            //添加总的担保额度
            returnApplyInfo.put("totalGuaranteeAmount", totalGuaranteeAmount);
            returnApplyInfo.put("totalGuaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalGuaranteeAmount));
            //添加总的批复额度
            returnApplyInfo.put("totalApprovalAmount", totalApprovalAmount);
            returnApplyInfo.put("totalApprovalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(totalApprovalAmount));
            //担保费+保险费合计：担保、保险费合计标准为：借款期限6个月（含）以内为借款金额的【0.56】%；借款期限6个月以上12个月（含）以内为【1.12】%
            if(approvalLimit <= 6){
                guaranteeSecureAmount = totalApprovalAmount.multiply(new BigDecimal("0.0056"));
            } else if(approvalLimit > 6 && approvalLimit <= 12){
                guaranteeSecureAmount = totalApprovalAmount.multiply(new BigDecimal("0.0112"));
            }
            returnApplyInfo.put("guaranteeSecureAmount", guaranteeSecureAmount == null ? 0 : guaranteeSecureAmount);
            returnApplyInfo.put("guaranteeSecureAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeSecureAmount));
        }

        //人员信息列表 && 借款人信息列表
        List<Map> bmsPeopleList = (List<Map>) returnApplyInfo.get("loanPeopleList");
        String loaners = "";//定义所有借款人，用顿号分隔
        if (bmsPeopleList != null) {
            for (Map bmsPeopleMap : bmsPeopleList) {
                String mainLoanner = bmsPeopleMap.get("mainLoanner") == null ? "" : bmsPeopleMap.get("mainLoanner").toString();
                String isBorrower = bmsPeopleMap.get("loaners") == null ? "" : bmsPeopleMap.get("loaners").toString();
                String loaner = bmsPeopleMap.get(Fields.PARAM_NAME) == null ? "" : bmsPeopleMap.get(Fields.PARAM_NAME).toString();

                //添加主借款人信息
                if ("1".equals(mainLoanner)) {
                    returnApplyInfo.put("customerAddress", bmsPeopleMap.get("familyAddress"));
                }
                //年收入
                Object annualIncome = returnApplyInfo.get("annualIncome");
                bmsPeopleMap.put("annualIncome" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(annualIncome));
                //是借款人
                if ("1".equals(isBorrower)) {
                    loaners = StringUtils.isBlank(loaner) ? loaners : loaners + loaner + SYMBOL_SPLIT;
                }

                //参数切分 克隆借款人信息
                Map returnApplyInfoClone = CommonUtil.clone((HashMap)returnApplyInfo);
                List<Map> clonePeopleList = new ArrayList<>();
                clonePeopleList.add(bmsPeopleMap);
                //覆盖原来的抵押物列表为只有一个的
                returnApplyInfoClone.put("loanPeopleList", clonePeopleList);
                returnApplyList.add(returnApplyInfoClone);
            }
        }
        loaners = StringUtils.isBlank(loaners) ? loaners : loaners.substring(0, loaners.length() - 1);
        BigDecimal loanAmountBig = new BigDecimal(loanAmount == null ? "0" : loanAmount.toString());
        combindFileName = loaners + "_" + (loanAmountBig.divide(new BigDecimal("10000"), 0).longValue()) + "万_" + (loanPeriodName == null ? "0" : loanPeriodName) + "个月";
        returnApplyInfo.put("combindFileName", combindFileName);
        returnApplyInfo.put("loaners" + FIELD_STR_SUFFIX, loaners);

        logger.info("\n\n---------------------------------------------------------打印处理后的参数列表-------开始-----------------------------------------------------------");
        logger.info(returnApplyList);
        logger.info("---------------------------------------------------------打印处理后的参数列表-------结束-----------------------------------------------------------\n\n");

        return returnApplyList;
    }


    public static boolean isGrownUp(String idNo){
        try {
            if (StringUtils.isNotBlank(idNo)) {
                int year = Integer.parseInt(idNo.substring(6, 10));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date update = sdf.parse(String.valueOf(year + 18) + idNo.substring(10, 14));
                Date today = new Date();
                return today.after(update);
            } else {
                return true;
            }
        } catch (Exception e){

        }
        return true;
    }


    public static void main(String[] args) {

        System.out.println(isGrownUp("xxxxxx19990829xxxx"));
    }
}
