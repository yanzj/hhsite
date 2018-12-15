package com.vilio.fms.generator.util;

import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Constants;
import com.vilio.fms.util.Fields;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public class HandleApplyInfo {

    private final static String NUMBER_CN_SUFFIX = "CN";//金额小写转大写，会在同级同名参数的参数名加后缀
    private final static String FIELD_STR_SUFFIX = "Str";//列表类某字段需要串联，则在列表同级添加对应字段加
    private final static String SYMBOL_SPLIT = "、";//分隔符，顿号

    /**
     * 根据进件信息、合同抵押方式、以及合同生成顺位处理进件参数
     *
     * @param applyInfoMap
     * @param mortgageType
     * @return
     */
    public static List<Map> doHandle(Map applyInfoMap, String mortgageType) {
        if (applyInfoMap == null) {
            return null;
        }
        List<Map> returnApplyList = new ArrayList<>();
        Map returnApplyInfo = CommonUtil.clone((HashMap) applyInfoMap);//参数保护
        //申请金额
        Object loanAmount = returnApplyInfo.get(Fields.PARAM_LOAN_AMOUNT);
        returnApplyInfo.put(Fields.PARAM_LOAN_AMOUNT + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(loanAmount));
        //意向金
        Object intentionMoney = returnApplyInfo.get(Fields.PARAM_INTENTION_MONEY);
        returnApplyInfo.put(Fields.PARAM_INTENTION_MONEY + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(intentionMoney));

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
            }
        }
        loaners = StringUtils.isBlank(loaners) ? loaners : loaners.substring(0, loaners.length() - 1);
        returnApplyInfo.put("loaners" + FIELD_STR_SUFFIX, loaners);


        //抵押物信息列表
        List<Map> bmsMortgageList = (List<Map>) returnApplyInfo.get("loanPawnList");
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
                bmsMortgageMap.put("approvalAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(approvalAmount));
                //担保额度
                Object guaranteeAmount = bmsMortgageMap.get("guaranteeAmount");
                bmsMortgageMap.put("guaranteeAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(guaranteeAmount));
                //合同抵押物价值
                Object contractPawnPrice = bmsMortgageMap.get("contractPawnPrice");
                bmsMortgageMap.put("contractPawnPrice" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(contractPawnPrice));
                //抵押权债权总额
                Object pawnMortgageAmount = bmsMortgageMap.get("pawnMortgageAmount");
                bmsMortgageMap.put("pawnMortgageAmount" + NUMBER_CN_SUFFIX, NumberToCN.number2CNMontrayUnit(pawnMortgageAmount));



                //分抵分押
                if (Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType)) {
                    //克隆抵押物
                    Map returnApplyInfoClone = CommonUtil.clone((HashMap)returnApplyInfo);
                    List<Map> cloneMortgageList = new ArrayList<>();
                    returnApplyInfoClone.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST));
                    returnApplyInfoClone.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND));
                    cloneMortgageList.add(returnApplyInfoClone);
                    returnApplyList.add(returnApplyInfoClone);
                }
            }
            //联抵联押，不需要处理抵押物
            if (Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType)) {
                returnApplyList.add(returnApplyInfo);
            }
        }
        return returnApplyList;
    }


    public static void main(String[] args) {


        Map<String, String> mapAA = new HashMap<>();

        mapAA.put("A", "A");

        mapAA.put("AA", "AA");

        mapAA.put("AAA", "AAA");

        System.out.println(mapAA);


        Map<String, String> mapBB = new HashMap<>();

        //把mapAA的元素复制到mapBB中

        mapBB.putAll(mapAA);

        mapBB.put("AA", "B");
        mapBB.put("B", "B");

        //打印出的mapAA应该不受影响

        System.out.println(mapAA);

        //打印出的mapBB应该多了元素B

        System.out.println(mapBB);

    }
}
