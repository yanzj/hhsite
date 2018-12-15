package com.vilio.plms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/7/27.
 * 贷后系统查询DAO
 */
public interface QueryDao {


    //贷款业务列表查询
    public List qryLoanList(Map map);

    //还款计划列表查询
    public List queryRepaymentScheduleList(Map map);

    //根据还款计划code查找还款计划信息
    public Map queryRepaymentScheduleInfoByCode(String repaymentScheduleCode);
    //根据还款计划code查找还款计划明细
    public List queryRepaymentScheduleDetailsByCode(String repaymentScheduleCode);

    /**
     * 获取贷款详情-不包含还款计划、放款记录等关联信息
     */
    public Map queryLoanBaseInfo(Map paramMap);

    public List queryHousInfoByContractCode(Map paramMap);

    /**
     * 根据contractCode获取还款计划列表
     * 入参：contractCode 借款合同数据唯一标识
     */
    public List queryRepaymentScheduleListByContractCode(Map paramMap);

    /**
     * 根据contractCode获取放款记录列表
     * 入参：contractCode 借款合同数据唯一标识
     */
    public List queryPaidInfoListByContractCode(Map paramMap);

    /**
     * 获取放款记录列表
     * 入参：列表筛选条件（城市代码 渠道代码 借款人 放款日期起 放款日期止）
     ）
     * @param paramMap
     * @return
     */
    public List queryPaidInfoList(Map paramMap);

    /**
     * 根据放款记录唯一标识获取放款记录详情
     * 入参：paidInfoCode 放款记录数据唯一标识
     */
    public Map queryPaidInfoByPaidInfoCode(Map paramMap);

    /**
     * 查询借款凭证列表
     */
    public List queryFileListForPaidInfo(String paidInfoCode);

    /**
     * 根据合同编码和材料类型查询归档文件列表
     */
    public List queryFileListForPigeonhole(Map paramMap);

    /**
     * 根据合同代码查询签约公证信息
     */
    public Map querySignNotarialByContractCode(String contractCode);

    /**
     * 根据合同代码查询抵押信息
     */
    public Map queryRegistrationByContractCode(String contractCode);

    /**
     * 根据contractCode获取审批信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryApprovalInfo(Map paramMap);

    /**
     * 获取进件申请的借款人列表
     */
    public List queryCustomerList(String applyCode);

    /**
     * 获取合同代码的抵押物列表
     */
    public List queryHouseList(String contractCode);

    /**
     * 获取抵押物最新产调信息
     */
    public Map queryLatestPropertyInvestigation(String houseCode);

    /**
     * 获取抵押物产调明细信息
     */
    public List queryInvestDetail(String investigationCode);

    /**
     * 获取户口信息列表
     */
    public List queryHouseholdRegistration(String houseCode);

    /**
     * 根据合同号获取备用房信息列表
     */
    public List querySpareHouseListByContractCode(String contractCode);

    /**
     * 根据合同号获取账户信息列表
     */
    public List queryAccountListByContractCode(String contractCode);

    /**
     * 根据合同号获取征信信息列表
     */
    public List queryCreditInvestigationListByContractCode(String contractCode);

    /**
     * 根据征信编号获取未结清贷款信息汇总
     */
    public Map queryLoanUnsettledByCreditCode(String creditCode);

    /**
     * 根据征信编号获取未销户贷记卡信息汇总
     */
    public Map queryUnClosingCardByCreditCode(String creditCode);

    /**
     * 根据征信编号获取贷款信息列表
     */
    public List queryLoanedInfoListByCreditCode(String creditCode);

    /**
     * 根据征信编号获取贷记卡信息列表
     */
    public List queryCreaditCardListByCreditCode(String creditCode);

    /**
     * 根据征信编号获取贷记卡信息列表
     */
    public List queryGuaranteeInfoListByCreditCode(String creditCode);

    /**
     * 根据征信编号获取未销户贷记卡信息汇总
     */
    public Map queryQueryRecordByCreditCode(String creditCode);

    /**
     * 根据合同编号获取司法信息列表
     */
    public List queryJudicialInfoListByContractCode(String creditCode);

    /**
     * 根据合同编号获取案件概况
     */
    public Map queryCaseInfoByContractCode(String creditCode);

    /**
     * 根据合同编号获取进件材料分类列表
     */
    public List queryMaterialInfoListByContractCode(String contractCode);

    /**
     * 根据合同编号和文件类型获取进件材料列表
     */
    public List queryFileListByContractCodeAndFileType(Map paramMap);

    /**
     * 根据合同编号获取资金入账信息列表
     */
    public List queryReceiptsRecordListByContractCode(Map paramMap);
    /**
     * 获取资金入账信息列表
     */
    public List queryReceiptsRecordList(Map paramMap);
    /**
     * @Description: 根据资金入账唯一标识查询资金入账记录
     * @param:
     * @return: 
     * @Author: liuzhu.feng
     * @Date: 2017/8/14/0014
     */
    public Map queryReceiptsRecordByReceiptsCode(Map paramMap);

    /**
     * 根据资金入账代码查询入账凭证列表
     */
    public List queryFileListByReceiptsRecordCode(String receiptsRecordCode);

    /**
     * 根据合同编号获取扣款信息列表
     */
    public List queryRepaymentDetailListByContractCode(Map paramMap);
    /**
     * @Description: 查询扣款记录列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/8/14/0014
     */
    public List queryRepaymentDetailList(Map paramMap);

    /**
     * @Description: 扣款记录详情查询
     * @param:
     * @return: 
     * @Author: liuzhu.feng
     * @Date: 2017/8/15/0015
     */
    public Map queryRepaymentDetailByRepaymentCode(Map paramMap);

    public List queryOverDueRepaymentScheduleList(Map paramMap);

    public Map queryOverDueRepaymentScheduleDetail(String repaymentScheduleCode);

    public List queryAccountDetailList(Map paramMap);

    public Map queryAccountDetail(String repaymentScheduleCode);

}
