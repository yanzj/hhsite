package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.dao.BaseTableDao;
import com.vilio.plms.dao.BmsBaseTableDao;
import com.vilio.plms.dynamicdatasource.CustomerContextHolder;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.MessageService;
import com.vilio.plms.service.quartz.BmsSynchronizationBaseDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/8/28.
 */
@Service("bmsSynchronizationBaseDataService")
public class BmsSynchronizationBaseDataServiceImpl implements BmsSynchronizationBaseDataService {
    @Resource
    MessageService messageService;
    @Resource
    CommonService commonService;
    @Resource
    BmsBaseTableDao bmsBaseTableDao;
    @Resource
    BaseTableDao baseTableDao;

    public void execute() throws Exception{
        List<HashMap> bmsCityList = new ArrayList();
        List<HashMap> bmsAreaList = new ArrayList();
        List<HashMap> bmsAgentList = new ArrayList();
        List<HashMap> bmsCompanyList = new ArrayList();
        List<HashMap> bmsDistributeList = new ArrayList();

        synchronized (this) {
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
            try {
                bmsCityList = bmsBaseTableDao.qryBmsCity();
                bmsAreaList = bmsBaseTableDao.qryBmsAreaList();
                bmsAgentList = bmsBaseTableDao.qryBmsAgentList();
                bmsCompanyList = bmsBaseTableDao.qryBmsCompanyList();
                bmsDistributeList = bmsBaseTableDao.qryBmsDistributeList();
                CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_PLMS);
            }catch(Exception e){
                CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_PLMS);
                e.printStackTrace();
            }
        }

//        HashMap testArea = new HashMap();
//        HashMap testCity = new HashMap();
//        HashMap testAgent = new HashMap();
//        HashMap testCompany = new HashMap();
//        HashMap testDistribute = new HashMap();

//        testArea.put("code","10");
//        testArea.put("abbrName","西南");
//        testArea.put("fullName","西南区");
//        testArea.put("orderByNo","1");
//
//        testCity.put("code","10");
//        testCity.put("abbrName","深圳");
//        testCity.put("fullName","深圳市");
//        testCity.put("areaCode","10");
//        testCity.put("orderByNo","1");
//
//        testAgent.put("id","10");
//        testAgent.put("post","job_master");
//        testAgent.put("name","测试1");
//        testAgent.put("cellPhone","13600000000");
//        testAgent.put("email","4324242412@qq.com");
//        testAgent.put("channelId","10");
//
//        testCompany.put("code","10");
//        testCompany.put("name","宏获资产");
//        testCompany.put("type","01");
//        testCompany.put("shortName","宏获");
//        testCompany.put("areaCode","10");
//        testCompany.put("orderByNo","10");
//
//        testDistribute.put("id","10");
//        testDistribute.put("abbrName","宏获资产");
//        testDistribute.put("fullName","01");
//        testDistribute.put("cityNo","10");
//        testDistribute.put("type","0");
//        testDistribute.put("orderByNo","10");
//        bmsAreaList.add(testArea);
//        bmsCityList.add(testCity);
//        bmsAgentList.add(testAgent);
//        bmsCompanyList.add(testCompany);
//        bmsDistributeList.add(testDistribute);


        //同步核心系统区域表
        for (HashMap bmsArea:bmsAreaList){
            String bmsAreaCode = (String)bmsArea.get("code");
            String bmsAreaAbbrName = (String)bmsArea.get("abbrName");
            String bmsAreaFullName = (String)bmsArea.get("fullName");
            Integer bmsAreaOrderByNo = (Integer)bmsArea.get("orderByNo");

            Map area = baseTableDao.qryArea(bmsAreaCode);

            if(area!=null){
                area.put("areaName",bmsAreaFullName);
                baseTableDao.updateArea(area);
            }else{
                area = new HashMap();
                String areaCode = commonService.getUUID();
                area.put("code",areaCode);
                area.put("bmsCode",bmsAreaCode);
                area.put("areaName",bmsAreaFullName);
                area.put("status", GlobDict.valid.getKey());
                baseTableDao.insertArea(area);
            }
        }


        //同步核心系统城市表
        for (HashMap bmsCity:bmsCityList){
            String bmsCityCode = (String)bmsCity.get("code");
            String bmsCityAbbrName = (String)bmsCity.get("abbrName");
            String bmsCityFullName = (String)bmsCity.get("fullName");
            String bmsCityOrderByNo = (String)bmsCity.get("orderByNo");
            String bmsAreaCode = (String)bmsCity.get("areaCode");

            Map city = baseTableDao.qryCity(bmsCityCode);

            if(city!=null){
                city.put("abbrName",bmsCityAbbrName);
                city.put("fullName",bmsCityFullName);
                city.put("orderByNo",bmsCityOrderByNo);
                city.put("areaCode",bmsAreaCode);
                baseTableDao.updateCity(city);
            }else{
                city = new HashMap();
                String cityCode = commonService.getUUID();
                city.put("code",bmsCityCode);
                city.put("bmsCode",bmsCityCode);
                city.put("abbrName",bmsCityAbbrName);
                city.put("fullName",bmsCityFullName);
                city.put("areaCode",bmsAreaCode);
                city.put("status",GlobDict.valid.getKey());
                city.put("orderByNo",bmsCityOrderByNo);
                baseTableDao.insertCity(city);
            }
        }

        //同步核心系统渠道表
        for (HashMap bmsDistribute:bmsDistributeList){
            Integer bmsDistributeCode = (Integer)bmsDistribute.get("id");
            String bmsDistributeCodeString = null;
            if (bmsDistributeCode != null){
                bmsDistributeCodeString = bmsDistributeCode.toString();
            }
            String bmsDistributeAbbrName = (String)bmsDistribute.get("channelShortName");
            String bmsDistributeFullName = (String)bmsDistribute.get("channelName");
            Integer bmsDistributeOrderByNo = (Integer)bmsDistribute.get("sortNumber");
            String bmsCityCode = (String)bmsDistribute.get("city");
            String bmsChannelType = (String)bmsDistribute.get("channelType");

            Map distribute = baseTableDao.qryDistribute(bmsDistributeCodeString);

            if(distribute!=null){
                distribute.put("abbrName",bmsDistributeAbbrName);
                distribute.put("fullName",bmsDistributeFullName);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("cityNo",bmsCityCode);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("type",bmsChannelType);
                baseTableDao.updateDistribute(distribute);
            }else{
                distribute = new HashMap();
                String distributeCode = commonService.getUUID();
                distribute.put("code",distributeCode);
                distribute.put("bmsCode",bmsDistributeCode);
                distribute.put("abbrName",bmsDistributeAbbrName);
                distribute.put("fullName",bmsDistributeFullName);
                distribute.put("cityNo",bmsCityCode);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("status",GlobDict.valid.getKey());
                distribute.put("type",bmsChannelType);
                baseTableDao.insertDistribute(distribute);
            }
        }

        //更新父渠道信息
        for (HashMap bmsDistribute:bmsDistributeList){
            Integer bmsDistributeCode = (Integer)bmsDistribute.get("id");
            String bmsDistributeCodeString = null;
            if (bmsDistributeCode != null){
                bmsDistributeCodeString = bmsDistributeCode.toString();
            }
            Integer bmsParentId = (Integer)bmsDistribute.get("parentId");
            String bmsParentIdString = null;
            if (bmsParentId != null){
                bmsParentIdString = bmsParentId.toString();
            }

            Map distribute = new HashMap();
            Map parentDistribute = new HashMap();

            distribute = baseTableDao.qryDistribute(bmsDistributeCodeString);

            parentDistribute = baseTableDao.qryDistribute(bmsParentIdString);

            if (distribute != null && parentDistribute != null) {

                String code = (String) parentDistribute.get("code");

                distribute.put("parentDistributor", code);

                baseTableDao.updateDistribute(distribute);
            }
        }

        //同步核心系统业务员表
        for (HashMap bmsAgent:bmsAgentList){
            Integer bmsAgentCode = (Integer)bmsAgent.get("id");
            String bmsAgentCodeString = null;
            if (bmsAgentCode != null){
                bmsAgentCodeString = bmsAgentCode.toString();
            }
            String bmsAgentPost = (String)bmsAgent.get("post");
            String bmsAgentName = (String)bmsAgent.get("name");
            String bmsCellPhone = (String)bmsAgent.get("cellphone");
            String bmsEmail = (String)bmsAgent.get("email");
            Integer bmsChannelId = (Integer)bmsAgent.get("channelId");
            String bmsChannelIdString = null;
            if (bmsChannelId != null){
                bmsChannelIdString = bmsChannelId.toString();
            }

            Map agent = baseTableDao.qryAgent(bmsAgentCodeString);
            Map distribute = baseTableDao.qryDistribute(bmsChannelIdString);
            String distributeCode = (String)distribute.get("code");

            if(agent!=null){
                agent.put("titile",bmsAgentPost);
                agent.put("name",bmsAgentName);
                agent.put("mobileNo",bmsCellPhone);
                agent.put("email",bmsEmail);
                agent.put("distributorCode",distributeCode);
                baseTableDao.updateAgent(agent);
            }else{
                agent = new HashMap();
                String agentCode = commonService.getUUID();
                agent.put("code",agentCode);
                agent.put("bmsCode",bmsAgentCode);
                agent.put("titile",bmsAgentPost);
                agent.put("name",bmsAgentName);
                agent.put("mobileNo",bmsCellPhone);
                agent.put("email",bmsEmail);
                agent.put("distributorCode",distributeCode);
                agent.put("status",GlobDict.valid.getKey());
                baseTableDao.insertAgent(agent);

                //distributor_code
            }
        }

        //更新上级用户
        for (HashMap bmsAgent:bmsAgentList){
            Integer bmsAgentCode = (Integer)bmsAgent.get("id");
            String bmsAgentCodeString = null;
            if (bmsAgentCode != null){
                bmsAgentCodeString = bmsAgentCode.toString();
            }
            Integer bmsParentId = (Integer)bmsAgent.get("parentId");
            String bmsParentIdString = null;
            if (bmsParentId != null) {
                bmsParentIdString = bmsParentId.toString();

                Map agent = new HashMap();

                agent = baseTableDao.qryAgent(bmsAgentCodeString);

                Map parentAgent = baseTableDao.qryAgent(bmsParentIdString);

                String code = (String) parentAgent.get("code");
                agent.put("parentUser", code);

                baseTableDao.updateAgent(agent);
            }

        }

        //同步核心系统公司表
        for (HashMap bmsCompany:bmsCompanyList){
            String bmsCompanyCode = (String)bmsCompany.get("code");
            String bmsCompanyType = (String)bmsCompany.get("type");
            String bmsCompanyTypeString = null;
            if (bmsCompanyType != null && GlobDict.bms_synchronize_company_type_insurance.getDesc().equals(bmsCompanyType)){
                bmsCompanyTypeString = GlobDict.bms_synchronize_company_type_insurance.getKey();
            }
            else if (bmsCompanyType != null && GlobDict.bms_synchronize_company_type_guarantee.getDesc().equals(bmsCompanyType)){
                bmsCompanyTypeString = GlobDict.bms_synchronize_company_type_guarantee.getKey();
            }
            else if (bmsCompanyType != null && GlobDict.bms_synchronize_company_type_fundside.getDesc().equals(bmsCompanyType)){
                bmsCompanyTypeString = GlobDict.bms_synchronize_company_type_fundside.getKey();
            }
            String bmsCompanyName = (String)bmsCompany.get("name");
            String bmsCompanyShortName = (String)bmsCompany.get("shortName");
            String bmsCompanyLegal = (String)bmsCompany.get("legal");
            String bmsCompanyEmail = (String)bmsCompany.get("email");
            String bmsCompanyPhone = (String)bmsCompany.get("phone");

            String bmsCompanyAccountName = (String)bmsCompany.get("accountName");
            String bmsCompanyOpenBank = (String)bmsCompany.get("openBank");
            String bmsCompanyBankAccount = (String)bmsCompany.get("bankAccount");
            String bmsCompanyAddress = (String)bmsCompany.get("address");
            String bmsCompanyOutputMaterials = (String)bmsCompany.get("outputMaterials");

            Integer bmsCompanyIsEnabled = (Integer)bmsCompany.get("isEnabled");

            String bmsCompanyIsEnabledString = null;
            if (bmsCompanyIsEnabled != null){
                bmsCompanyIsEnabledString = bmsCompanyIsEnabled.toString();
            }

            Map company = baseTableDao.qryCompany(bmsCompanyCode);

            if(company!=null){
                company.put("companyType",bmsCompanyTypeString);
                company.put("companyName",bmsCompanyName);
                company.put("abbrName",bmsCompanyShortName);
//                company.put("bmsCompanyLegal",bmsCompanyLegal);
//                company.put("bmsCompanyEmail",bmsCompanyEmail);
//                company.put("bmsCompanyPhone",bmsCompanyPhone);
                company.put("status",GlobDict.valid.getKey());

//                company.put("bmsCompanyAccountName",bmsCompanyAccountName);
//                company.put("bmsCompanyOpenBank",bmsCompanyOpenBank);
//                company.put("bmsCompanyBankAccount",bmsCompanyBankAccount);
//                company.put("bmsCompanyAddress",bmsCompanyAddress);
//                company.put("bmsCompanyOutputMaterials",bmsCompanyOutputMaterials);
                baseTableDao.updateCompany(company);
            }else{
                company = new HashMap();
                String companyCode = commonService.getUUID();
                company.put("code",companyCode);
                company.put("bmsCompanyCode",bmsCompanyCode);
                company.put("companyType",bmsCompanyTypeString);
                company.put("companyName",bmsCompanyName);
                company.put("abbrName",bmsCompanyShortName);
                company.put("status",GlobDict.valid.getKey());
//                company.put("bmsCompanyLegal",bmsCompanyLegal);
//                company.put("bmsCompanyEmail",bmsCompanyEmail);
//                company.put("bmsCompanyPhone",bmsCompanyPhone);
//                company.put("bmsCompanyIsEnabled",bmsCompanyIsEnabled);
//
//                company.put("bmsCompanyAccountName",bmsCompanyAccountName);
//                company.put("bmsCompanyOpenBank",bmsCompanyOpenBank);
//                company.put("bmsCompanyBankAccount",bmsCompanyBankAccount);
//                company.put("bmsCompanyAddress",bmsCompanyAddress);
//                company.put("bmsCompanyOutputMaterials",bmsCompanyOutputMaterials);
                baseTableDao.insertCompany(company);
            }
        }
    }
}
