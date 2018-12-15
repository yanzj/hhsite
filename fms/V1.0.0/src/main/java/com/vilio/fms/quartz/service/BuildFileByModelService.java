package com.vilio.fms.quartz.service;

import com.vilio.fms.common.service.GenerateService;
import com.vilio.fms.common.service.QuartzService;
import com.vilio.fms.generator.dao.FmsBuildModelDetailsMapper;
import com.vilio.fms.generator.dao.FmsBuildModelRecordsMapper;
import com.vilio.fms.generator.dao.FmsModelBeanMapper;
import com.vilio.fms.generator.pojo.FmsBuildModelDetails;
import com.vilio.fms.generator.pojo.FmsBuildModelRecords;
import com.vilio.fms.generator.pojo.FmsModelBean;
import com.vilio.fms.generator.service.ApplyService;
import com.vilio.fms.generator.util.HandleApplyInfo;
import com.vilio.fms.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
@Service
public class BuildFileByModelService extends QuartzService {

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;

    @Resource
    FmsBuildModelRecordsMapper fmsBuildModelRecordsMapper;

    @Resource
    FmsBuildModelDetailsMapper fmsBuildModelDetailsMapper;

    @Resource
    ApplyService applyService;

    Logger logger = Logger.getLogger(BuildFileByModelService.class);

    @Override
    public void subExcute() throws Exception {
        //Step1 查找状态为初始化的单子
        FmsBuildModelRecords fmsBuildModelRecords = new FmsBuildModelRecords();
        fmsBuildModelRecords.setStatus(Constants.GENERATOR_STATUS_DOING);
        List<FmsBuildModelRecords> fmsBuildModelRecordsList = fmsBuildModelRecordsMapper.queryRecords(fmsBuildModelRecords);

        if (fmsBuildModelRecordsList != null && fmsBuildModelRecordsList.size() > 0) {
            //Step2 遍历每一条申请，逐条处理
            for (FmsBuildModelRecords fbmr : fmsBuildModelRecordsList) {
                String serialNo = fbmr.getSerialNo();
                String businessId = fbmr.getBusinessId();
                String mortgageType = fbmr.getMortgageType();
                String contractStr = fbmr.getModelList();
                Map applyInfoMap = null;
                String applyInfoStr = "";

                try{
                    applyInfoMap = applyService.queryApplyRecord(businessId);
                    if(applyInfoMap != null){
                        //记录下当前业务编号下的业务参数（进件信息）
                        applyInfoStr = JSONObject.fromObject(applyInfoMap).toString();
                        fbmr.setParams(applyInfoStr);
                        fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                    fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                    logger.info("获取进件信息失败，此次轮询失败-------------------\n");
                    return;
                }
                Map paramMap = new HashMap();
                //转换模板代码，如果出现异常，则捕获并把该条申请置为失败
                try {
                    //整理合同模版列表
                    JSONArray contractArray = JSONArray.fromObject(contractStr);
                    List<Map> contractList = (List<Map>) contractArray;
                    //contractList.forEach(System.out::println);
                    if (contractList != null && contractList.size() > 0) {
                        int modelCount = contractList.size();
                        int failCount = 0;
                        //先删除当前序号下的具体文件，防止之前生成一半后异常退出的
                        fmsBuildModelDetailsMapper.deleteDetailsBySerialNo(serialNo);
                        //Step3 遍历一条申请中，所选中的模板文件，按需生成文件并上传到阿里云服务器
                        for (Map contractMap : contractList) {
                            String modelCode = contractMap.get(Fields.PARAM_MODEL_FILE_CODE).toString();
                            List<Map> requiredParamList = new ArrayList<>();
                            Map requiredParam = new HashMap();

                            FmsModelBean fmsModelBean = fmsModelBeanMapper.queryModelByModelCode(modelCode);
                            if (fmsModelBean != null) {
                                String functionNo = fmsModelBean.getServiceFunctionNo();
                                String modelFileCode = fmsModelBean.getModelFileCode();
                                String modelFileName = fmsModelBean.getModelFileName();
                                String modelName = fmsModelBean.getModelName();
                                String fileSuffix = fmsModelBean.getFileSuffix();

                                Map generateMap = new HashMap();
                                generateMap.put(Fields.PARAM_MODEL_FILE_CODE, modelFileCode);
                                generateMap.put(Fields.PARAM_MODEL_FILE_NAME, modelFileName);
                                generateMap.put(Fields.PARAM_MODEL_NAME, modelName);
                                generateMap.put(Fields.PARAM_FILE_SUFFIX, fileSuffix);

                                //判断文件模版是否与抵押方式有关
                                boolean needHanleParam = false;//若为分抵分押，必定为true
                                if("Y".equals(fmsModelBean.getRelateToMortgageType())){
                                    //判断若是分抵分押，则处理参数，并置标志位needHanleParam为true
                                    if(Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType)){
                                        needHanleParam = true;
                                        requiredParamList = HandleApplyInfo.doHandle(applyInfoMap, mortgageType);
                                        if(requiredParamList != null && requiredParamList.size() > 0){
                                            for(Map requiredParamMap : requiredParamList){
                                                String requiredParamStr = JSONObject.fromObject(requiredParamMap).toString();

                                                FmsBuildModelDetails fmsBuildModelDetails = new FmsBuildModelDetails();
                                                String code = CommonUtil.getCurrentTimeStr("FBMD-", "");
                                                fmsBuildModelDetails.setCode(code);
                                                fmsBuildModelDetails.setSerialNo(serialNo);
                                                fmsBuildModelDetails.setModelCode(modelCode);
                                                fmsBuildModelDetails.setRequiredParam(requiredParamStr);
                                                fmsBuildModelDetails.setMortgageType(mortgageType);
                                                fmsBuildModelDetails.setCertificateNumberFirst(requiredParamMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST).toString());
                                                fmsBuildModelDetails.setCertificateNumberSecond(requiredParamMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND).toString());
                                                fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_DOING);
                                                fmsBuildModelDetailsMapper.insertDetails(fmsBuildModelDetails);

                                                try {
                                                    GenerateService generateService = (GenerateService) SpringContextUtil.getBean(functionNo);
                                                    Map generateResultMap = generateService.excute(generateMap, paramMap);
                                                    //把上传到阿里云服务器后返回的文件信息，回写到详情表里
                                                    handleGrenerateResult(fmsBuildModelDetails, generateResultMap);
                                                } catch (Exception e) {
                                                    failCount++;
                                                    fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_FAIL);
                                                    fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                                                    logger.info("生成文件时：模版号为【" + modelCode + "】的模版没有成功生成文件！");
                                                }
                                            }
                                        }
                                    }
                                }
                                //如果是和抵押方式没有关系，且为分抵分押呢？？？？？？如何处理？？？？？？？？？
                                if(!needHanleParam){
                                    FmsBuildModelDetails fmsBuildModelDetails = new FmsBuildModelDetails();
                                    //先行插入具体的文件，待生成成功后，回写文件ID，名称
                                    String code = CommonUtil.getCurrentTimeStr("FBMD-", "");
                                    fmsBuildModelDetails.setCode(code);
                                    fmsBuildModelDetails.setSerialNo(serialNo);
                                    fmsBuildModelDetails.setModelCode(modelCode);
                                    fmsBuildModelDetails.setRequiredParam(applyInfoStr);//此处的参数和记录表中的一样
                                    fmsBuildModelDetails.setMortgageType(mortgageType);
                                    fmsBuildModelDetails.setCertificateNumberFirst("");//此处不需要写入产证编号
                                    fmsBuildModelDetails.setCertificateNumberSecond("");//此处不需要写入产证编号
                                    fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_DOING);
                                    fmsBuildModelDetailsMapper.insertDetails(fmsBuildModelDetails);

                                    try {
                                        GenerateService generateService = (GenerateService) SpringContextUtil.getBean(functionNo);
                                        Map generateResultMap = generateService.excute(generateMap, paramMap);
                                        //把上传到阿里云服务器后返回的文件信息，回写到详情表里
                                        handleGrenerateResult(fmsBuildModelDetails, generateResultMap);
                                    } catch (Exception e) {
                                        failCount++;
                                        fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_FAIL);
                                        fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                                        logger.info("生成文件时：模版号为【" + modelCode + "】的模版没有成功生成文件！");
                                    }
                                }

                            } else {
                                failCount++;
                                logger.info("生成文件时：模版号为【" + modelCode + "】的模版不存在！");
                            }
                        }

                        //检查一条申请中，是否全部生成成功
                        if (failCount > 0 && failCount < modelCount) {//部分失败
                            fbmr.setStatus(Constants.GENERATOR_STATUS_PART_FAIL);
                            fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，部分生成失败！");
                        } else if (failCount == modelCount) {//全部失败
                            fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                            fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，全部生成失败！");
                        } else if (modelCount == 0) { //理论上不存在该情况
                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请无选中模版，无需生成！");
                        } else if (modelCount > 0 && failCount == 0) {
                            fbmr.setStatus(Constants.GENERATOR_STATUS_SUCCESS);
                            fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请已处理，全部生成成功！");
                        }
                    } else {
                        fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                        fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                        logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请未发现待生成模版，全部生成失败！");
                    }
                } catch (Exception e) {
                    fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                    fmsBuildModelRecordsMapper.updateRecordsStatus(fbmr);
                    logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，全部生成失败！");
                }

            }
        } else {
            logger.info("无待生成文件---------------------------------------------------------------");
        }

    }

    @Override
    public String getQuartzDescription() {
        return "通过模板生成文件";
    }

    private synchronized void handleGrenerateResult(FmsBuildModelDetails fmsBuildModelDetails, Map generateResultMap) throws Exception {
        if (generateResultMap != null) {
            Map bodyMap = (Map) generateResultMap.get(Fields.PARAM_MESSAGE_BODY);
            if (bodyMap != null) {
                List<Map> fileList = (List<Map>) bodyMap.get("fileMaps");
                if (fileList != null && fileList.size() > 0) {
                    for (Map map : fileList) {
                        fmsBuildModelDetails.setFileCode(map.get(Fields.PARAM_SERIAL_NO) == null ? "" : map.get(Fields.PARAM_SERIAL_NO).toString());
                        fmsBuildModelDetails.setFileName(map.get(Fields.PARAM_FILE_NAME) == null ? "" : map.get(Fields.PARAM_FILE_NAME).toString());
                        fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_SUCCESS);
                        fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                        logger.info("成功生成文件[" + fmsBuildModelDetails.getFileCode() + "]《" + fmsBuildModelDetails.getFileName() + "》！");
                    }
                }
            }
        } else {
            throw new Exception("上传文件失败");
        }
    }
}
