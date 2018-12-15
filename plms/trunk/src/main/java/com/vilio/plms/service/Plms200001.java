package com.vilio.plms.service;

import com.vilio.plms.dao.BaseTableDao;
import com.vilio.plms.dao.BmsBaseTableDao;
import com.vilio.plms.dynamicdatasource.CustomerContextHolder;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.MessageService;
import org.apache.log4j.Logger;
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
 * Created by martin on 2017/7/28.
 */
@Service
public class Plms200001 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms200001.class);

    @Resource
    MessageService messageService;
    @Resource
    BmsBaseTableDao bmsBaseTableDao;
    @Resource
    BaseTableDao baseTableDao;

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
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        List<HashMap> bmsCityList = new ArrayList();
        List<HashMap> bmsAreaList = new ArrayList();
        List<HashMap> bmsAgentList = new ArrayList();
        List<HashMap> bmsCompanyList = new ArrayList();
        List<HashMap> bmsDistributeList = new ArrayList();

        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        bmsCityList = bmsBaseTableDao.qryBmsCity();
        bmsAreaList = bmsBaseTableDao.qryBmsAreaList();
        bmsAgentList = bmsBaseTableDao.qryBmsAgentList();
        bmsCompanyList = bmsBaseTableDao.qryBmsCompanyList();
        bmsDistributeList = bmsBaseTableDao.qryBmsDistributeList();
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_PLMS);

        //同步核心系统区域表
        for (HashMap bmsArea:bmsAreaList){
            String bmsAreaCode = (String)bmsArea.get("code");
            String bmsAreaAbbrName = (String)bmsArea.get("abbrName");
            String bmsAreaFullName = (String)bmsArea.get("fullName");
            String bmsAreaOrderByNo = (String)bmsArea.get("orderByNo");

            Map area = new HashMap();

            area = baseTableDao.qryArea(bmsAreaCode);

            if(area!=null){
                area.put("abbrName",bmsAreaAbbrName);
                area.put("fullName",bmsAreaFullName);
                area.put("orderByNo",bmsAreaOrderByNo);
                baseTableDao.updateArea(area);
            }else{
                area.put("bmsCode",bmsAreaCode);
                area.put("abbrName",bmsAreaAbbrName);
                area.put("fullName",bmsAreaFullName);
                area.put("orderByNo",bmsAreaOrderByNo);
                baseTableDao.insertArea(area);
            }
        }


        //同步核心系统城市表
        for (HashMap bmsCity:bmsCityList){
            String bmsCityCode = (String)bmsCity.get("code");
            String bmsCityAbbrName = (String)bmsCity.get("abbrName");
            String bmsCityFullName = (String)bmsCity.get("fullName");
            String bmsCityOrderByNo = (String)bmsCity.get("orderByNo");

            Map city = new HashMap();

            city = baseTableDao.qryCity(bmsCityCode);

            if(city!=null){
                city.put("abbrName",bmsCityAbbrName);
                city.put("fullName",bmsCityFullName);
                city.put("orderByNo",bmsCityOrderByNo);
                baseTableDao.updateCity(city);
            }else{
                city.put("bmsCode",bmsCityCode);
                city.put("abbrName",bmsCityAbbrName);
                city.put("fullName",bmsCityFullName);
                city.put("orderByNo",bmsCityOrderByNo);
                baseTableDao.insertCity(city);
            }
        }

        //同步核心系统渠道表
        for (HashMap bmsDistribute:bmsDistributeList){
            String bmsDistributeCode = (String)bmsDistribute.get("id");
            String bmsDistributeAbbrName = (String)bmsDistribute.get("channelShortName");
            String bmsDistributeFullName = (String)bmsDistribute.get("channelName");
            String bmsDistributeOrderByNo = (String)bmsDistribute.get("sortNumber");
            String bmsCityCode = (String)bmsDistribute.get("city");
            String bmsChannelType = (String)bmsDistribute.get("channelType");


            Map distribute = new HashMap();

            distribute = baseTableDao.qryDistribute(bmsDistributeCode);

            if(distribute!=null){
                distribute.put("abbrName",bmsDistributeAbbrName);
                distribute.put("fullName",bmsDistributeFullName);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("cityNo",bmsCityCode);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("type",bmsChannelType);
                baseTableDao.updateDistribute(distribute);
            }else{
                distribute.put("bmsCode",bmsDistributeCode);
                distribute.put("abbrName",bmsDistributeAbbrName);
                distribute.put("fullName",bmsDistributeFullName);
                distribute.put("cityNo",bmsCityCode);
                distribute.put("orderByNo",bmsDistributeOrderByNo);
                distribute.put("type",bmsChannelType);
                baseTableDao.insertDistribute(distribute);
            }
        }
        //更新父渠道信息
        for (HashMap bmsDistribute:bmsDistributeList){
            String bmsDistributeCode = (String)bmsDistribute.get("id");
            String bmsParentId = (String)bmsDistribute.get("parentId");

            Map distribute = new HashMap();
            Map parentDistribute = new HashMap();

            distribute = baseTableDao.qryDistribute(bmsDistributeCode);

            parentDistribute = baseTableDao.qryDistribute(bmsParentId);

            if (distribute != null && parentDistribute != null) {

                String code = (String) parentDistribute.get("code");

                distribute.put("parentDistributor", code);

                baseTableDao.updateDistribute(distribute);
            }
        }


        //同步核心系统业务员表
        for (HashMap bmsAgent:bmsAgentList){
            String bmsAgentId = (String)bmsAgent.get("id");
            String bmsAgentCode = (String)bmsAgent.get("code");
            String bmsAgentPost = (String)bmsAgent.get("post");
            String bmsAgentName = (String)bmsAgent.get("name");
            String bmsCellPhone = (String)bmsAgent.get("cellPhone");
            String bmsEmail = (String)bmsAgent.get("email");
            String bmsParentId = (String)bmsAgent.get("parentId");
            String isEnabled = (String)bmsAgent.get("isEnabled");

            Map agent = new HashMap();

            agent = baseTableDao.qryAgent(bmsAgentCode);

            if(agent!=null){
                agent.put("titile",bmsAgentPost);
                agent.put("name",bmsAgentName);
                agent.put("mobileNo",bmsCellPhone);
                agent.put("email",bmsEmail);
                agent.put("isEnabled",isEnabled);
                baseTableDao.updateAgent(agent);
            }else{
                agent.put("code",bmsAgentCode);
                agent.put("titile",bmsAgentPost);
                agent.put("name",bmsAgentName);
                agent.put("mobileNo",bmsCellPhone);
                agent.put("email",bmsEmail);
                agent.put("status", GlobDict.valid.getKey());
                baseTableDao.insertAgent(agent);
            }
        }

        //同步核心系统公司表
        for (HashMap bmsCompany:bmsCompanyList){
            String bmsCompanyCode = (String)bmsCompany.get("code");
            String bmsCompanyType = (String)bmsCompany.get("type");
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

            String bmsCompanyIsEnabled = (String)bmsCompany.get("isEnabled");

            Map company = new HashMap();

            company = baseTableDao.qryCompany(bmsCompanyCode);

            if(company!=null){
                company.put("companyType",bmsCompanyType);
                company.put("companyName",bmsCompanyName);
                company.put("abbrName",bmsCompanyShortName);
//                company.put("bmsCompanyLegal",bmsCompanyLegal);
//                company.put("bmsCompanyEmail",bmsCompanyEmail);
//                company.put("bmsCompanyPhone",bmsCompanyPhone);
//                company.put("status",bmsCompanyIsEnabled);

//                company.put("bmsCompanyAccountName",bmsCompanyAccountName);
//                company.put("bmsCompanyOpenBank",bmsCompanyOpenBank);
//                company.put("bmsCompanyBankAccount",bmsCompanyBankAccount);
//                company.put("bmsCompanyAddress",bmsCompanyAddress);
//                company.put("bmsCompanyOutputMaterials",bmsCompanyOutputMaterials);
                baseTableDao.updateCompany(company);
            }else{
                company.put("bmsCompanyCode",bmsCompanyCode);
                company.put("companyType",bmsCompanyType);
                company.put("companyName",bmsCompanyName);
                company.put("abbrName",bmsCompanyShortName);
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
                baseTableDao.insertAgent(company);
            }
        }
    }
}
