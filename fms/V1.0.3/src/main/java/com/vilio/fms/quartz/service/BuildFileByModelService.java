package com.vilio.fms.quartz.service;

import com.vilio.fms.common.service.GenerateService;
import com.vilio.fms.common.service.QuartzService;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.generator.dao.*;
import com.vilio.fms.generator.pojo.*;
import com.vilio.fms.generator.service.ApplyService;
import com.vilio.fms.generator.util.HandleApplyInfo;
import com.vilio.fms.remote.service.RemoteMpsService;
import com.vilio.fms.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
@Service
public class BuildFileByModelService implements Runnable, Serializable {

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;

    @Resource
    FmsBuildModelRecordsMapper fmsBuildModelRecordsMapper;

    @Resource
    FmsBuildModelDetailsMapper fmsBuildModelDetailsMapper;

    @Resource
    ApplyService applyService;

    @Resource
    ConfigInfo configInfo;

    @Resource
    FileUploadService fileUploadService;

    @Resource
    RemoteMpsService remoteMpsService;

    @Resource
    FmsSendRecordsMapper fmsSendRecordsMapper;

    @Resource
    FmsSendFilesMapper fmsSendFilesMapper;

    Logger logger = Logger.getLogger(BuildFileByModelService.class);

    private String serialNo = "";

    public void run() {
        //Step1 查找状态为初始化的单子
        FmsBuildModelRecords fmsBuildModelRecords = new FmsBuildModelRecords();
        fmsBuildModelRecords.setStatus(Constants.GENERATOR_STATUS_INIT);
        fmsBuildModelRecords.setSerialNo(this.serialNo);
        List<FmsBuildModelRecords> fmsBuildModelRecordsList = null;
        try {
            fmsBuildModelRecordsList = fmsBuildModelRecordsMapper.queryRecords(fmsBuildModelRecords);


            if (fmsBuildModelRecordsList != null && fmsBuildModelRecordsList.size() > 0) {
                //Step2 遍历每一条申请，逐条处理
                for (FmsBuildModelRecords fbmr : fmsBuildModelRecordsList) {
                    //与外发有关的参数
                    List<Map> outSendFileList = new ArrayList<>();//需要外发的文件列表
                    String emailContent = "";//邮件内容
                    String subject = "";//文件、通知标题
                    String displayName = "";//展示的参数
                    String combindFileName = "";//压缩包文件名
                    List<Map> emailToList = new ArrayList<>();
                    List<Map> emailCcList = new ArrayList<>();

                    //与生成文件有关的参数
                    String serialNo = fbmr.getSerialNo();
                    String businessId = fbmr.getBusinessId();
                    String mortgageType = fbmr.getMortgageType();
                    String contractStr = fbmr.getModelList();
                    String originalOutFileStr = fbmr.getAttachFileList();
                    Map applyInfoMap = null;
                    String applyInfoStr = "";

                    //与业务有关的参数
                    String customerName = "";//主借款人
                    String distributorName = "";//渠道简称

                    try {
                        applyInfoMap = applyService.queryApplyRecord(businessId);
                        customerName = applyInfoMap.get("customerName") == null ? "" : applyInfoMap.get("customerName").toString();
                        if (applyInfoMap != null) {
                            //记录下当前业务编号下的业务参数（进件信息）
                            applyInfoStr = JSONObject.fromObject(applyInfoMap).toString();
                            fbmr.setParams(applyInfoStr);
                            fbmr.setStatus(Constants.GENERATOR_STATUS_DOING);//相当于上锁！！！
                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                        } else {
                            //若由于返回为空导致获取进件信息失败，则直接置失败
                            fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                            fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                            logger.info("获取进件信息失败，本条轮询失败-------------------loanId:" + businessId + "-------------------------------\n");
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //若由于出现异常获取进件信息失败，则等待下次轮询时再次尝试，不再直接置失败
                        logger.info("获取进件信息失败，本条轮询失败-------------------loanId:" + businessId + "-------------------------------\n" + e.getMessage() + "\n");
                        continue;
                    }
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

                                FmsModelBean fmsModelBean = fmsModelBeanMapper.queryModelByModelCode(modelCode);
                                if (fmsModelBean != null) {
                                    String functionNo = fmsModelBean.getServiceFunctionNo();
                                    String modelFileCode = fmsModelBean.getModelFileCode();
                                    String modelFileName = fmsModelBean.getModelFileName();
                                    String modelName = fmsModelBean.getModelName();
                                    String fileSuffix = fmsModelBean.getFileSuffix();
                                    String needOutSend = fmsModelBean.getNeedOutSend();

                                    //此处可能涉及同一个机构多个模板的取舍问题，以及模板的补充问题？？？？？？？？？？？？？？？？
                                    try {
                                        if ("Y".equals(needOutSend)) {
                                            emailToList.clear();
                                            emailCcList.clear();
                                            emailContent = fmsModelBean.getEmailModel() == null ? "" : fmsModelBean.getEmailModel();
                                            subject = customerName + "的材料";
                                            displayName = configInfo.getRiskManagentName();
                                            String[] emailToArray = (fmsModelBean.getMailReceiverAddress() == null ? "" : fmsModelBean.getMailReceiverAddress().toString()).split(";");
                                            String[] emailCcArray = (fmsModelBean.getMailCcAddress() == null ? "" : fmsModelBean.getMailCcAddress().toString()).split(";");
                                            for (String toUser : emailToArray) {
                                                if (StringUtils.isNotBlank(toUser)) {
                                                    Map toUserMap = new HashMap();
                                                    toUserMap.put("toUser", toUser);
                                                    emailToList.add(toUserMap);
                                                }
                                            }

                                            for (String ccUser : emailCcArray) {
                                                if (StringUtils.isNotBlank(ccUser)) {
                                                    Map ccUserMap = new HashMap();
                                                    ccUserMap.put("ccUser", ccUser);
                                                    emailCcList.add(ccUserMap);
                                                }
                                            }

                                        }
                                    } catch (Exception e) {
                                        logger.info("整理模板文件的接收方时出现了异常\n");
                                        e.printStackTrace();
                                    }

                                    Map generateMap = new HashMap();
                                    generateMap.put(Fields.PARAM_MODEL_FILE_CODE, modelFileCode);
                                    generateMap.put(Fields.PARAM_MODEL_FILE_NAME, modelFileName);
                                    generateMap.put(Fields.PARAM_MODEL_NAME, modelName);
                                    generateMap.put(Fields.PARAM_FILE_SUFFIX, fileSuffix);

                                    //判断文件模版是否与抵押方式有关
                                    boolean needHanleParam = false;//若为分抵分押，必定为true
                                    boolean relateToMortgageType = "Y".equals(fmsModelBean.getRelateToMortgageType());//为Y时relateToCustomer必定为N（即二者不可同时为Y）
                                    boolean relateToCustomer = "Y".equals(fmsModelBean.getRelateToCustomer());//为Y时relateToMortgageType必定为N（即二者不可同时为Y）
                                    if (relateToCustomer) {
                                        requiredParamList = HandleApplyInfo.doHandleWithCustomer(applyInfoMap);
                                        needHanleParam = true;
                                        if (requiredParamList != null && requiredParamList.size() > 0) {
                                            modelCount += requiredParamList.size() - 1;
                                            for (Map requiredParamMap : requiredParamList) {
                                                String requiredParamStr = JSONObject.fromObject(requiredParamMap).toString();
                                                combindFileName = requiredParamMap.get("combindFileName") == null ? null : requiredParamMap.get("combindFileName").toString();

                                                FmsBuildModelDetails fmsBuildModelDetails = new FmsBuildModelDetails();
                                                String code = CommonUtil.getCurrentTimeStr("FBMD-", "");
                                                fmsBuildModelDetails.setCode(code);
                                                fmsBuildModelDetails.setSerialNo(serialNo);
                                                fmsBuildModelDetails.setModelCode(modelCode);
                                                fmsBuildModelDetails.setRequiredParam(requiredParamStr);
                                                fmsBuildModelDetails.setMortgageType(mortgageType);
                                                fmsBuildModelDetails.setCertificateNumberFirst("");//此处不需要写入产证编号
                                                fmsBuildModelDetails.setCertificateNumberSecond("");//此处不需要写入产证编号
                                                fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_DOING);
                                                fmsBuildModelDetailsMapper.insertDetails(fmsBuildModelDetails);

                                                try {
                                                    GenerateService generateService = (GenerateService) SpringContextUtil.getBean(functionNo);
                                                    Map generateResultMap = generateService.excute(generateMap, requiredParamMap);
                                                    //把上传到阿里云服务器后返回的文件信息，回写到详情表里
                                                    handleGrenerateResult(fmsBuildModelDetails, generateResultMap, outSendFileList);
                                                } catch (Exception e) {
                                                    failCount++;
                                                    fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_FAIL);
                                                    fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                                                    logger.info("生成文件时：模版号为【" + modelCode + "】的模版没有成功生成文件！出现了异常--ONE--\n");
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            failCount = modelCount;
                                            fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                                            fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请失败，因为参数列表是空的，全部生成失败！--position1");
                                            break;
                                        }
                                    } else {
                                        requiredParamList = HandleApplyInfo.doHandle(applyInfoMap, relateToMortgageType, mortgageType);
                                    }
                                    //判断若是分抵分押且与抵押方式有关，则处理参数，并置标志位needHanleParam为true
                                    if (relateToMortgageType && Constants.MORTGAGE_TYPE_SUB_MORTGAGE.equals(mortgageType)) {
                                        needHanleParam = true;
                                        if (requiredParamList != null && requiredParamList.size() > 0) {
                                            modelCount += requiredParamList.size() - 1;
                                            for (Map requiredParamMap : requiredParamList) {
                                                String requiredParamStr = JSONObject.fromObject(requiredParamMap).toString();
                                                combindFileName = requiredParamMap.get("combindFileName") == null ? null : requiredParamMap.get("combindFileName").toString();

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
                                                    Map generateResultMap = generateService.excute(generateMap, requiredParamMap);
                                                    //把上传到阿里云服务器后返回的文件信息，回写到详情表里
                                                    handleGrenerateResult(fmsBuildModelDetails, generateResultMap, outSendFileList);
                                                } catch (Exception e) {
                                                    failCount++;
                                                    fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_FAIL);
                                                    fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                                                    logger.info("生成文件时：模版号为【" + modelCode + "】的模版没有成功生成文件！出现了异常--ONE--\n");
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else {
                                            failCount = modelCount;
                                            fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                                            fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请失败，因为参数列表是空的，全部生成失败！--position1");
                                            break;
                                        }
                                    }
                                    //如果是和抵押方式没有关系，且为分抵分押-----则只生成一份
                                    if (!relateToCustomer && !needHanleParam && requiredParamList != null && requiredParamList.size() > 0) {
                                        combindFileName = requiredParamList.get(0).get("combindFileName") == null ? null : requiredParamList.get(0).get("combindFileName").toString();

                                        FmsBuildModelDetails fmsBuildModelDetails = new FmsBuildModelDetails();
                                        //先行插入具体的文件，待生成成功后，回写文件ID，名称
                                        String code = CommonUtil.getCurrentTimeStr("FBMD-", "");
                                        fmsBuildModelDetails.setCode(code);
                                        fmsBuildModelDetails.setSerialNo(serialNo);
                                        fmsBuildModelDetails.setModelCode(modelCode);
                                        fmsBuildModelDetails.setRequiredParam(JSONObject.fromObject(requiredParamList.get(0)).toString());
                                        fmsBuildModelDetails.setMortgageType(mortgageType);
                                        fmsBuildModelDetails.setCertificateNumberFirst("");//此处不需要写入产证编号
                                        fmsBuildModelDetails.setCertificateNumberSecond("");//此处不需要写入产证编号
                                        fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_DOING);
                                        fmsBuildModelDetailsMapper.insertDetails(fmsBuildModelDetails);

                                        try {
                                            GenerateService generateService = (GenerateService) SpringContextUtil.getBean(functionNo);
                                            Map generateResultMap = generateService.excute(generateMap, requiredParamList.get(0));
                                            //把上传到阿里云服务器后返回的文件信息，回写到详情表里
                                            handleGrenerateResult(fmsBuildModelDetails, generateResultMap, outSendFileList);
                                        } catch (Exception e) {
                                            failCount++;
                                            fmsBuildModelDetails.setStatus(Constants.GENERATOR_DETAILS_STATUS_FAIL);
                                            fmsBuildModelDetailsMapper.updateDetails(fmsBuildModelDetails);
                                            logger.info("生成文件时：模版号为【" + modelCode + "】的模版没有成功生成文件！出现了异常--TWO--\n");
                                            e.printStackTrace();
                                        }
                                    } else if (requiredParamList == null || requiredParamList.size() == 0) {
                                        failCount = modelCount;
                                        fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                                        fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                        fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                        logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请失败，因为参数列表是空的，全部生成失败！--position2");
                                        break;
                                    }

                                } else {
                                    failCount++;
                                    logger.info("生成文件时：模版号为【" + modelCode + "】的模版不存在！");
                                }
                            }

                            //检查一条申请中，是否全部生成成功
                            if (failCount > 0 && failCount < modelCount) {//部分失败
                                fbmr.setStatus(Constants.GENERATOR_STATUS_PART_FAIL);
                                fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，部分生成失败！");
                            } else if (failCount == modelCount && failCount > 0) {//全部失败
                                fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                                fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，全部生成失败！--position3");
                            } else if (modelCount == 0) { //理论上不存在该情况
                                logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请无选中模版，无需生成！");
                            } else if (modelCount > 0 && failCount == 0) {
                                fbmr.setStatus(Constants.GENERATOR_STATUS_SUCCESS);
                                fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请已处理，全部生成成功！");
                            }
                        } else {
                            fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                            fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                            logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请未发现待生成模版，全部生成失败！");
                        }
                    } catch (Exception e) {
                        fbmr.setStatus(Constants.GENERATOR_STATUS_ALL_FAIL);
                        fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                        fmsBuildModelRecordsMapper.updateRecords(fbmr);
                        logger.info("生成文件时：序列号为【" + fbmr.getSerialNo() + "】的申请出现异常，全部生成失败！--position4--\n");
                        e.printStackTrace();
                    }

                    //如果该笔记录需外发，则先把附件的文件添加进待发送列表中
                    if (Constants.OUT_SEND_STATUS_DOING.equals(fbmr.getSendStatus())) {
                        logger.info("generatorEnd_0：序列号为【" + fbmr.getSerialNo() + "】的申请需要外发！");

                        JSONArray originalOutFileArray = null;
                        if (StringUtils.isBlank(originalOutFileStr)) {
                            originalOutFileArray = new JSONArray();
                        } else {
                            originalOutFileArray = JSONArray.fromObject(originalOutFileStr);
                        }
                        List<Map> originalOutFileList = (List<Map>) originalOutFileArray;
                        if (originalOutFileList != null && originalOutFileList.size() > 0) {
                            for (Map originalOutFileMap : originalOutFileList) {
                                Map outSendMap = new HashMap();
                                outSendMap.put(Fields.PARAM_FILE_ID, originalOutFileMap.get(Fields.PARAM_FILE_ID));
                                outSendMap.put(Fields.PARAM_FILE_NAME, originalOutFileMap.get(Fields.PARAM_FILE_NAME));
                                outSendMap.put(Fields.PARAM_FILE_TYPE, originalOutFileMap.get(Fields.PARAM_FILE_TYPE));
                                outSendFileList.add(outSendMap);
                            }
                        } else {
                            logger.info("generatorEnd_0：序列号为【" + fbmr.getSerialNo() + "】的申请未发现额外需要外发的材料，仅发送刚刚生成的材料！");
                        }

                        Map zipFileMap = null;
                        try {
                            //调用fms本身的服务，上传文件并压缩
                            Map uploadFileParamMap = new HashMap();
                            List<Map> serialNoList = new ArrayList<>();
                            if (outSendFileList != null && outSendFileList.size() > 0) {
                                for (Map outPutFileMap : outSendFileList) {
                                    Map serialNoMap = new HashMap();
                                    serialNoMap.put(Fields.PARAM_SERIAL_NO, outPutFileMap.get(Fields.PARAM_FILE_ID));
                                    serialNoMap.put(Fields.PARAM_FILE_NAME, outPutFileMap.get(Fields.PARAM_FILE_NAME));
                                    serialNoMap.put(Fields.PARAM_FILE_TYPE, outPutFileMap.get(Fields.PARAM_FILE_TYPE));
                                    serialNoList.add(serialNoMap);
                                }
                            }
                            uploadFileParamMap.put("serialNoList", serialNoList);
                            uploadFileParamMap.put("filePath", "fms/targetzipfile/");
                            uploadFileParamMap.put("fileName", combindFileName);
                            CommonUtil.dealEmpty2Null(uploadFileParamMap);
                            zipFileMap = fileUploadService.zipAndUploadFileSerialNoList(uploadFileParamMap);
                        } catch (Exception e) {
                            //回写状态，此处无需回写，会在后面判断zipFileMap时回写
                            //fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                            //fmsBuildModelRecordsMapper.updateRecords(fbmr);
                            logger.info("把文件上传到服务器压缩和上传失败\n");
                            e.printStackTrace();
                        }

                        try {
                            //调用MPS发送
                            if (zipFileMap != null) {
                                String code = CommonUtil.getCurrentTimeStr("FSR-", "");
                                Map bodyZipFileMap = (Map) zipFileMap.get("body");
                                List<Map> sendFileList = new ArrayList<>();

                                if (bodyZipFileMap != null) {
                                    String fileId = bodyZipFileMap.get("serialNo") == null ? "" : bodyZipFileMap.get("serialNo").toString();
                                    String fileName = bodyZipFileMap.get("fileName") == null ? "" : bodyZipFileMap.get("fileName").toString();
                                    emailContent = createContent(emailContent, fileId, fileName);

                                    Map sendFileMap = new HashMap();
                                    sendFileMap.put("urlFile", configInfo.getFmsOuterNetUrl() + bodyZipFileMap.get("url"));
                                    sendFileMap.put("urlFileName", fileName);
                                    sendFileList.add(sendFileMap);

                                    //存储本地库
                                    FmsSendRecords fmsSendRecords = new FmsSendRecords();
                                    fmsSendRecords.setCode(code);
                                    fmsSendRecords.setRecordNo(fbmr.getSerialNo());
                                    fmsSendRecords.setContent(emailContent);
                                    fmsSendRecords.setMailReceiverAddress(JSONArray.fromObject(emailToList).toString());
                                    fmsSendRecords.setMailCcAddress(JSONArray.fromObject(emailCcList).toString());
                                    fmsSendRecords.setSendType("email");
                                    fmsSendRecordsMapper.insertSendRecords(fmsSendRecords);

                                    //存储本地库
                                    FmsSendFiles fmsSendFiles = new FmsSendFiles();
                                    fmsSendFiles.setCode(CommonUtil.getCurrentTimeStr("FSF-", ""));
                                    fmsSendFiles.setSendCode(code);
                                    fmsSendFiles.setFileId(fileId);
                                    fmsSendFilesMapper.insertSendFiles(fmsSendFiles);
                                }
                                Map forMpsParamMap = new HashMap();
                                forMpsParamMap.put("type", "Email");
                                forMpsParamMap.put("subject", subject);
                                forMpsParamMap.put("toUserList", emailToList);
                                forMpsParamMap.put("displayName", displayName);
                                forMpsParamMap.put("requestNo", code);
                                forMpsParamMap.put("userName", configInfo.getRiskManagentEmailUserName());
                                forMpsParamMap.put("content", emailContent);
                                forMpsParamMap.put("senderIdentityId", "fms_system");
                                forMpsParamMap.put("senderName", configInfo.getRiskManagentName());
                                forMpsParamMap.put("password", configInfo.getRiskManagentEmailPassword());
                                forMpsParamMap.put("senderSystem", "fms");
                                forMpsParamMap.put("toCcList", emailCcList);
                                //forMpsParamMap.put("fileList", sendFileList);
                                Map mpsReturnMap = remoteMpsService.sendEmailService(forMpsParamMap);
                                Map mpsHeadMap = (Map) mpsReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
                                if (mpsHeadMap != null) {
                                    Object mpsReturnCode = mpsHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
                                    if (ReturnCode.SUCCESS_CODE.equals(mpsReturnCode)) {
                                        //回写状态
                                        fbmr.setSendStatus(Constants.OUT_SEND_STATUS_SUCCESS);
                                        fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                    } else {
                                        //回写状态
                                        fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                        fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                    }
                                } else {
                                    //回写状态
                                    fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                    fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                }

                            } else {
                                //回写状态
                                fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                                fmsBuildModelRecordsMapper.updateRecords(fbmr);
                                logger.info("sendToMps：序列号为【" + fbmr.getSerialNo() + "】的申请，把文件上传到服务器压缩和上传失败！");
                            }
                        } catch (Exception e) {
                            //回写状态
                            fbmr.setSendStatus(Constants.OUT_SEND_STATUS_FAIL);
                            fmsBuildModelRecordsMapper.updateRecords(fbmr);
                            logger.info("sendToMps 发送邮件时出现了异常。异常信息如下\n");
                            e.printStackTrace();
                        }

                    } else {
                        logger.info("generatorEnd_1：序列号为【" + fbmr.getSerialNo() + "】的申请无需外发！");
                    }

                }
            } else {
                logger.info("无待生成文件---------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void handleGrenerateResult(FmsBuildModelDetails fmsBuildModelDetails, Map generateResultMap, List<Map> outSendFileList) throws Exception {
        if (generateResultMap != null) {
            Map bodyMap = (Map) generateResultMap.get(Fields.PARAM_MESSAGE_BODY);
            if (bodyMap != null) {
                List<Map> fileList = (List<Map>) bodyMap.get("fileMaps");
                if (fileList != null && fileList.size() > 0) {
                    for (Map map : fileList) {
                        Map outSendMap = new HashMap();
                        outSendMap.put(Fields.PARAM_FILE_ID, map.get(Fields.PARAM_SERIAL_NO) == null ? "" : map.get(Fields.PARAM_SERIAL_NO).toString());
                        outSendMap.put(Fields.PARAM_FILE_NAME, map.get(Fields.PARAM_FILE_NAME) == null ? "" : map.get(Fields.PARAM_FILE_NAME).toString());
                        outSendMap.put(Fields.PARAM_FILE_TYPE, "");
                        outSendFileList.add(outSendMap);

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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String createContent(String contentModelStr, String serialNo, String fileName){
        StringBuffer sb = new StringBuffer();
        sb.append(contentModelStr);
        sb.append("<p>");
        sb.append("<a href='");
        String outUrl = configInfo.getFmsOuterNetUrl() + "/nlbs/fileLoad/downloadFile?serialNo=" + serialNo;
        sb.append(outUrl);//文件的下载链接地址
        sb.append("' target='_blank' >");
        sb.append(fileName);//文件名字
        sb.append("</a>");
        sb.append("</p>");
        return sb.toString();
    }
}
