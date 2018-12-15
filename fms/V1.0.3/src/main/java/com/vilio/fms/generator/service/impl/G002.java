package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.GenerateService;
import com.vilio.fms.commonMapper.dao.FmsFileLoadMapper;
import com.vilio.fms.commonMapper.pojo.FmsFileLoad;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.HHBizException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
@Service("G002")
public class G002 extends GenerateService {

    @Resource
    OssFileService ossFileService;

    @Resource
    FmsFileLoadMapper fmsFileLoadMapper;

    @Resource
    FileUploadService fileUploadService;

    private static Logger logger = Logger.getLogger(G002.class);

    /**
     * 生成文件并上传到阿里云服务器
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map subExcute(Map paramMap, Map rootMap) throws Exception {

        String modelFileCode = paramMap.get(Fields.PARAM_MODEL_FILE_CODE) == null ? "" : paramMap.get(Fields.PARAM_MODEL_FILE_CODE).toString();
        String modelFileName = paramMap.get(Fields.PARAM_MODEL_FILE_NAME) == null ? "" : paramMap.get(Fields.PARAM_MODEL_FILE_NAME).toString();
        String modelName = paramMap.get(Fields.PARAM_MODEL_NAME) == null ? "" : paramMap.get(Fields.PARAM_MODEL_NAME).toString();
        String fileSuffix = paramMap.get(Fields.PARAM_FILE_SUFFIX) == null ? "" : paramMap.get(Fields.PARAM_FILE_SUFFIX).toString();

        //Step1 从阿里云服务器获取模版文件流
        FmsFileLoad fmsFileLoad = fmsFileLoadMapper.queryBySerialNo(modelFileCode);
        if(fmsFileLoad == null){
            logger.info("获取不到模板文件，请检查相关配置项！此次生成失败！！！\n");
            throw new HHBizException("9999", "获取不到模板文件，请检查相关配置项！此次生成失败！");
        }
        String filePath = fmsFileLoad.getFilePath();
        String fileName = fmsFileLoad.getFileName();
        InputStream is = ossFileService.getFile(filePath,fileName);


        //Step2 遍历整个excel，填充替换相关字段值
        String fileRandomName = CommonUtil.getCurrentTimeStr("_", "");
        String uploadFileName = modelName + fileRandomName + "." + fileSuffix;

        HSSFWorkbook workbook = new HSSFWorkbook(is);
        if(workbook != null) {
            int sheetCount = workbook.getNumberOfSheets();
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
                if(sheet == null){
                    continue;
                }
                int rowNum = sheet.getPhysicalNumberOfRows();
                int cellNum = sheet.getRow(0) == null ? 0 : sheet.getRow(0).getPhysicalNumberOfCells();
                for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
                    HSSFRow row = sheet.getRow(rowIndex);
                    if(row == null){
                        continue;
                    }
                    for (short cellIndex = 0; cellIndex < cellNum; cellIndex++) {
                        HSSFCell cell = row.getCell(cellIndex);
                        Object modelValueObj = "";
                        String modelValueStr = "";//该值为文档中的原有值
                        StringBuffer targetValueStr = new StringBuffer();//该值为文档中应该被替换成的值
                        if (cell != null) {
                            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                //System.out.println(cell.getStringCellValue());
                                modelValueObj = cell.getRichStringCellValue();
                            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                                //System.out.println(cell.getNumericCellValue());
                                modelValueObj = cell.getNumericCellValue();
                            }
                            modelValueStr = modelValueObj == null ? "" : modelValueObj.toString().trim();
                            if (getTargetValue(modelValueStr, targetValueStr, rootMap)) {
                                cell.setCellValue(targetValueStr.toString());
                            }
                        }
                    }
                }
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        //Step3 上传文件
        Map uploadFileParamMap = new HashMap();
        MultipartFile[] files = {null};
        MockMultipartFile hhMultipartFile = new MockMultipartFile(uploadFileName, byteArrayOutputStream.toByteArray());
        files[0] = hhMultipartFile;
        uploadFileParamMap.put("files", files);
        uploadFileParamMap.put("filePath", "fms/targetfile/");
        CommonUtil.dealEmpty2Null(uploadFileParamMap);

        return fileUploadService.uploadFile(uploadFileParamMap);
    }

    @Override
    public String getGenerateDescription() {
        return "G002(POI实现EXCEL...)";
    }

    /**
     * @Description: 根据originalValue判断是否需要替换，若需要替换，同时把targetValue更新
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/19/0019
     */
    private synchronized boolean getTargetValue(String originalValue, StringBuffer targetValue, Map rootMap){
        if(StringUtils.isBlank(originalValue)){
            return false;
        }
        if(originalValue.startsWith("${") && originalValue.endsWith("}") && rootMap != null){

            String fieldName = originalValue.substring(2, originalValue.length() - 1);
            /**
             * 此处罗列一下规则：《如有更新，同时更新该注释》
             * customerName：主借款人姓名
             * customerPhone：主借款人电话号码
             * customerCertificateNumber：主借款人证件号码
             * customerWorkUnit：主借款人工作单位
             * customerAddress：主借款人通讯地址
             * customerAge：主借款人年龄
             * totalApprovalAmountCN+totalApprovalAmount：人民币（大写）：批复总额￥（小写）批复总额元
             * loanPawnList-抵押物列表信息相关，用-连接字段
             */
            //主借款人相关信息
            if(fieldName.startsWith("customer")){
                List<Map> peopleList = (List<Map>) rootMap.get("loanPeopleList");
                if(peopleList != null && peopleList.size() > 0){
                    for(Map people : peopleList){
                        boolean mainLoaner = Boolean.valueOf(people.get("mainLoanner") == null ? "false" : people.get("mainLoanner").toString());
                        if(mainLoaner){
                            if("customerName".equals(fieldName)){
                                targetValue.append(people.get("name") == null ? "" : people.get("name").toString());
                            }
                            if("customerPhone".equals(fieldName)){
                                targetValue.append(people.get("cellphone") == null ? "" : people.get("cellphone").toString());
                            }
                            if("customerCertificateNumber".equals(fieldName)){
                                targetValue.append(people.get("certificateNumber") == null ? "" : people.get("certificateNumber").toString());
                            }
                            if("customerWorkUnit".equals(fieldName)){
                                targetValue.append(people.get("workUnit") == null ? "" : people.get("workUnit").toString());
                            }
                            if("customerAddress".equals(fieldName)){
                                targetValue.append(people.get("familyAddress") == null ? "" : people.get("familyAddress").toString());
                            }
                            if("customerAge".equals(fieldName)){
                                targetValue.append(people.get("age") == null ? "" : people.get("age").toString());
                            }
                            break;
                        }
                    }
                }
            }

            if("totalApprovalAmountCN+totalApprovalAmount".equals(fieldName)){
                //人民币（大写）：批复总额￥（小写）批复总额元
                Map loanRiskInfo = (Map) rootMap.get("loanRiskInfo");
                if(loanRiskInfo != null){
                    String totalApprovalAmount = loanRiskInfo.get("totalApprovalAmount") == null ? "0" : loanRiskInfo.get("totalApprovalAmount").toString();
                    double totalApprovalAmountDouble = Double.parseDouble(totalApprovalAmount);
                    NumberFormat numberFormat = NumberFormat.getNumberInstance();
                    numberFormat.setGroupingUsed(false);
                    targetValue.append("人民币（大写）："
                            + (loanRiskInfo.get("totalApprovalAmountCN") == null ? "             " : loanRiskInfo.get("totalApprovalAmountCN").toString())
                    + "￥（小写）"
                    + (numberFormat.format(totalApprovalAmountDouble))
                    + "元");
                    //System.out.println(targetValue);
                }
            }

            if(fieldName.startsWith("loanPawnList-")){
                List<Map> loanPawnList = (List<Map>) rootMap.get("loanPawnList");
                if(loanPawnList != null && loanPawnList.size() > 0){
                    for(int loanPawnIndex = 0; loanPawnIndex < loanPawnList.size(); loanPawnIndex ++){
                        Map loanPawn = loanPawnList.get(loanPawnIndex);
                         if(("loanPawnList-certificateNumber" + loanPawnIndex).equals(fieldName)){
                             targetValue.append(loanPawn.get("certificateNumber") == null ? "" : loanPawn.get("certificateNumber").toString());
                         }
                         if(("loanPawnList-propertyAddress" + loanPawnIndex).equals(fieldName)){
                             targetValue.append(loanPawn.get("propertyAddress") == null ? "" : loanPawn.get("propertyAddress").toString());
                         }

                    }
                }
            }
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        double a = 10000002.01;
        System.out.println(numberFormat.format(a));
    }
}
