package com.vilio.nlbs.plms.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 */
public interface PlmsService {

    Map plms300009(Map paramMap) throws Exception; //贷款业务查询初始化接口

    Map plms300010(Map paramMap) throws Exception; //贷款业务查询

    Map plms300011(Map paramMap) throws Exception; //贷款业务详情查询

    Map plms300012(Map paramMap) throws Exception; //还款计划查询初始化接口

    Map plms300014(Map paramMap) throws Exception; //还款计划查看详情

    Map plms300013(Map paramMap) throws Exception; //还款计划查询

    Map plms300029(Map paramMap) throws Exception; //贷款业务查询-还款记录

    Map plms300034(Map paramMap) throws Exception; //贷款业务查询-放款记录

    Map plms300035(Map paramMap) throws Exception; //贷款业务查询-还款计划

    Map plms300049(Map paramMap) throws Exception; //逾期记录查询初始化

    Map plms300050(Map paramMap) throws Exception; //逾期记录列表查询

    Map plms300060(Map paramMap) throws Exception; //查看逾期记录详细

}