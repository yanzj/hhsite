package com.vilio.plms.service.query;

import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.ContractDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.pojo.AccountDetail;
import com.vilio.plms.pojo.Contract;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/7/26.
 */
@Service("queryService")
public class QueryService {

    @Resource
    QueryDao queryDao;
    @Resource
    AccountDetailDao accountDetailDao;
    @Resource
    ContractDao contractDao;

    /**
     * 获取贷款详情-不包含还款计划、放款记录等关联信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryLoanInfo(Map paramMap) throws Exception {
        Map rtnMap = queryDao.queryLoanBaseInfo(paramMap);

        if( rtnMap == null) {
            rtnMap = new HashMap();
        }

        List houseList = queryDao.queryHousInfoByContractCode(paramMap);
        rtnMap.put("houseList",houseList);

        return rtnMap;
    }

    /**
     * 根据contractCode获取还款计划信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryRepaymentSchedule(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        //首先获取还款计划状体
        String contractCode = paramMap.get("contractCode").toString();
        AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(contractCode);
        rtnMap.put("repaymentScheduleStatus","这个地方需要等表结构改好");

        //再获取还款计划列表
        List repaymentList = queryDao.queryRepaymentScheduleListByContractCode(paramMap);
        rtnMap.put("repaymentList",repaymentList);

        return rtnMap == null ? new HashMap() : rtnMap;
    }

    /**
     * 根据contractCode获取放款记录信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryPaidInfo(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        List dataList = queryDao.queryPaidInfoListByContractCode(paramMap);

        for(int i=0; dataList!=null && i<dataList.size(); i++){
            Map map = (Map)dataList.get(i);
            String paidInfoCode = (String)map.get("paidInfoCode");
            List fileList = queryDao.queryFileListForPaidInfo(paidInfoCode);
            map.put("fileList",fileList);
        }

        rtnMap.put("paidList",dataList);

        return rtnMap == null ? new HashMap() : rtnMap;
    }

    /**
     * 根据contractCode获取公证抵押信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryNotarialAndMortgage(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        String contractCode = paramMap.get("contractCode").toString();

        //签约公证信息
        Map signMap = queryDao.querySignNotarialByContractCode(contractCode);
        if(null == signMap) signMap = new HashMap();
        rtnMap.putAll(signMap);

        //签约抵押信息
        Map registrationMap = queryDao.queryRegistrationByContractCode(contractCode);
        if(null == registrationMap) registrationMap = new HashMap();
        rtnMap.putAll(registrationMap);

        //签约公证材料
        paramMap.put("type", GlobDict.pigeonhole_type_sign.getKey());
        List fileList = queryDao.queryFileListForPigeonhole(paramMap);
        rtnMap.put("signFileList",fileList);

        //担保材料
        paramMap.put("type", GlobDict.pigeonhole_type_guarantee.getKey());
        fileList = queryDao.queryFileListForPigeonhole(paramMap);
        rtnMap.put("guaranteeFileList",fileList);

        //保险材料
        paramMap.put("type", GlobDict.pigeonhole_type_insurance.getKey());
        fileList = queryDao.queryFileListForPigeonhole(paramMap);
        rtnMap.put("insuranceFileList",fileList);

        //抵押材料
        paramMap.put("type", GlobDict.pigeonhole_type_mortgage.getKey());
        fileList = queryDao.queryFileListForPigeonhole(paramMap);
        rtnMap.put("mortgageFileList",fileList);

        //产调查询材料
        paramMap.put("type", GlobDict.pigeonhole_type_investigation.getKey());
        fileList = queryDao.queryFileListForPigeonhole(paramMap);
        rtnMap.put("investigationFileList",fileList);

        return rtnMap == null ? new HashMap() : rtnMap;
    }

    /**
     * 根据contractCode获取审批信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryApprovalInfo(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        //获取审批基本信息
        rtnMap = queryDao.queryApprovalInfo(paramMap);
        if(null == rtnMap){
            rtnMap = new HashMap();
        }

        //通过合同编码获取合同基本信息
        String contractCode = paramMap.get("contractCode").toString();
        Contract paramContract = new Contract();
        paramContract.setCode(contractCode);
        Contract contract = contractDao.qryContract(paramContract);

        //获取借款人信息列表,并拼接借款人姓名
        List customerList = queryDao.queryCustomerList(contract.getApplyCode());
        StringBuffer customerName = new StringBuffer("");
        for(int i=0; customerList!=null && i<customerList.size(); i++){
            Map customerMap = (Map)customerList.get(i);
            customerName.append(customerMap.get("name"));
            if(i < customerList.size() - 1){
                customerName.append("、");
            }
        }
        rtnMap.put("customerName",customerName.toString());

        //查询抵押物列表
        List mortgageList = new ArrayList();
        rtnMap.put("mortgageList",mortgageList);

        return rtnMap == null ? new HashMap() : rtnMap;
    }

    /**
     * 获取抵押物信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public List queryMortgageList(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        String contractCode = paramMap.get("contractCode").toString();

        List dataList = queryDao.queryHouseList(contractCode);

        for(int i=0; dataList!=null && i<dataList.size(); i++){
            Map map = (Map)dataList.get(i);
            String houseCode = (String)map.get("code");

            //获取产调基本信息
            Map propertyInvestigation = queryDao.queryLatestPropertyInvestigation(houseCode);

            //获取产调详细列表
            if(null != propertyInvestigation){
                String investigationCode = (String)propertyInvestigation.get("code");
                List investDetailList = queryDao.queryInvestDetail(investigationCode);
                propertyInvestigation.put("investDetailList",investDetailList);
            }

            map.put("propertyInvestigation",propertyInvestigation);

            //获取户口信息列表
            List householdRegistrationList = queryDao.queryHouseholdRegistration(houseCode);
            map.put("householdRegistrationList",householdRegistrationList);
        }

        return dataList;
    }



    /**
     * 获取抵押物信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public List queryCreditInvestigationList(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        String contractCode = paramMap.get("contractCode").toString();

        List dataList = queryDao.queryCreditInvestigationListByContractCode(contractCode);

        for(int i=0; dataList!=null && i<dataList.size(); i++){
            Map map = (Map)dataList.get(i);
            String creditInvestigationCode = (String)map.get("code");

            //未结清贷款信息汇总
            Map loanUnsettle = queryDao.queryLoanUnsettledByCreditCode(creditInvestigationCode);
            map.put("loanUnsettle",loanUnsettle);

            //未销户贷记卡信息汇总
            Map unClosingCard = queryDao.queryUnClosingCardByCreditCode(creditInvestigationCode);
            map.put("unClosingCard",unClosingCard);

            //贷款信息列表
            List loanedInfoList = queryDao.queryLoanedInfoListByCreditCode(creditInvestigationCode);
            map.put("loanedInfoList",loanedInfoList);

            //贷记卡信息列表
            List creaditCardList = queryDao.queryCreaditCardListByCreditCode(creditInvestigationCode);
            map.put("creaditCardList",creaditCardList);

            //担保信息列表
            List guaranteeInfoList = queryDao.queryGuaranteeInfoListByCreditCode(creditInvestigationCode);
            map.put("guaranteeInfoList",guaranteeInfoList);

            //查询记录
            Map queryRecord = queryDao.queryQueryRecordByCreditCode(creditInvestigationCode);
            map.put("queryRecord",queryRecord);
        }

        return dataList;
    }

    /**
     * 根据contractCode获取进件详情信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public Map queryApplyInfo(Map paramMap) throws Exception {
        Map rtnMap = new HashMap();

        String contractCode = paramMap.get("contractCode").toString();

        //通过合同编码获取合同基本信息
        Contract paramContract = new Contract();
        paramContract.setCode(contractCode);
        Contract contract = contractDao.qryContract(paramContract);

        //获取借款人列表
        List customerList = queryDao.queryCustomerList(contract.getApplyCode());
        rtnMap.put("customerList",customerList);

        //获取抵押物列表
        List mortgageList = queryMortgageList(paramMap);
        rtnMap.put("mortgageList",mortgageList);

        //备用房列表
        List spareHouseList = queryDao.querySpareHouseListByContractCode(contractCode);
        rtnMap.put("spareHouseList",spareHouseList);

        //放款账户信息
        List accountList = queryDao.queryAccountListByContractCode(contractCode);
        rtnMap.put("accountList",accountList);

        //征信信息
        List creditInvestigationList = queryCreditInvestigationList(paramMap);
        rtnMap.put("creditInvestigationList",creditInvestigationList);

        //司法信息
        List judicialInfoList = queryDao.queryJudicialInfoListByContractCode(contractCode);
        rtnMap.put("judicialInfoList",judicialInfoList);

        //案件概况
        Map caseInfo = queryDao.queryCaseInfoByContractCode(contractCode);
        rtnMap.put("caseInfo",caseInfo);

        return rtnMap == null ? new HashMap() : rtnMap;
    }

    /**
     * 根据contractCode获取进件材料信息
     * 入参：contractCode 借款合同数据唯一标识
     */
    public List queryMaterialInfo(Map paramMap) throws Exception {

        String contractCode = paramMap.get("contractCode").toString();

        List dataList = queryDao.queryMaterialInfoListByContractCode(contractCode);

        for(int i=0; dataList!=null && i<dataList.size(); i++){
            Map map = (Map)dataList.get(i);

            String materialTypeCode = (String)map.get("materialTypeCode");

            //查询当前类型下的文件列表
            Map param = new HashMap();
            param.put("materialTypeCode",materialTypeCode);
            param.put("contractCode",contractCode);
            List fileList = queryDao.queryFileListByContractCodeAndFileType(param);

            map.put("fileList",fileList);

        }

        return dataList;
    }

}
