package com.vilio.plms.service.fundManagement;

import com.vilio.plms.dao.FundManagerDao;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.service.Plms000001;
import com.vilio.plms.service.Plms000015;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RemoteFmsService;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.ExcelUtil;
import com.vilio.plms.util.FileUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Created by xiezhilei on 2017/8/9.
 */
@Service
public class FundManagerService {

    @Resource
    FundManagerDao fundManagerDao;
    @Resource
    RemoteFmsService remoteFmsService;
    @Resource
    CommonService commonService;
    @Resource
    Plms000015 plms000015;
    @Autowired
    ThreadPoolTaskExecutor poolTaskExecutor;
    @Autowired
    ReceiptsRecordUploadFileDealTask receiptsRecordUploadFileDealTask;

    private static final Logger logger = Logger.getLogger(FundManagerService.class);

    /**
     * 根据合同编码，创建资金入账数据
     * @param paramMap
     * @return
     * @throws Exception
     */
    public void createRepaymentScheduleDetailTmpAndControlData(Map paramMap) throws Exception {

    }

    /**
     * 批量上载接口
     * @param paramMap
     * @return
     * @throws Exception
     */
    public void uploadReceiptsRecord(Map paramMap) throws Exception {
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String fileCode = paramMap.get("fileCode") == null ? "" : paramMap.get("fileCode").toString();

        //调用文件平台
        List fileList = new ArrayList();
        Map fileMap = new HashMap();
        fileMap.put("serialNo",fileCode);
        fileList.add(fileMap);
        Map map = new HashMap();
        map.put("serialNoList",fileList);
        Map result = remoteFmsService.getUrlList(map);

        //获取返回结果
        Map body = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
        List fileMaps = (List)body.get("fileMaps");
        fileMap = (Map)fileMaps.get(0);
        String fileName = (String)fileMap.get("fileName");
        String uploadTime = (String)fileMap.get("uploadTime");

        //保存到上载表
        Map uploadMap = new HashMap();
        String code = commonService.getUUID();
        uploadMap.put("code",code);
        uploadMap.put("uploadUser",userNo);
        uploadMap.put("uploadTime", new Date());
        uploadMap.put("status", GlobDict.receipts_record_upload_status_checking.getKey());
        uploadMap.put("fileCode",fileCode);
        uploadMap.put("fileName",fileName);
        uploadMap.put("errorFileCode",null);
        uploadMap.put("errorFileName",null);
        uploadMap.put("errorFileCreatedTime",null);
        fundManagerDao.insertReceiptsRecordUpload(uploadMap);

        //将任务交给Spring的线程任务执行器处理
        logger.info(Thread.currentThread().getName() + "*******************");
        receiptsRecordUploadFileDealTask.setCode(code);
        receiptsRecordUploadFileDealTask.setFileCode(fileCode);
        receiptsRecordUploadFileDealTask.setFileName(fileName);
        receiptsRecordUploadFileDealTask.setUserNo(userNo);
        poolTaskExecutor.submit(receiptsRecordUploadFileDealTask);
    }

    public boolean dealFile(String code,String fileCode,String fileName,String userId) throws Exception {

        InputStream in = remoteFmsService.getFile(fileCode);

        ExcelUtil excelUtil = new ExcelUtil();
        //读取模板
        Workbook wbModule = excelUtil.getTempWorkbook(in);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(0);

        //校验头部
        if(!isValidHeader(excelUtil,wsheet)){
            createErrorFile(code,wbModule,excelUtil,wsheet,fileName+"文件模板错误");
            return false;
        }

        List datalist = getDataList(excelUtil,wsheet);
        if(!checkDataList(excelUtil,wsheet,datalist,userId)){
            createErrorFile(code,wbModule,excelUtil,wsheet,fileName+"文件模板错误");
            return false;
        }

        //写入数据库
        dealDataList(datalist,userId);

        return true;
    }

    /**
     *
     * @param excelUtil
     * @param wsheet
     * @return
     * @throws Exception
     */
    public boolean isValidHeader(ExcelUtil excelUtil, Sheet wsheet) throws Exception {
        //校验第A到第G列的前两行，若单元格内容与模板不相同，校验不通过

        //校验第一行标题
        Object value = excelUtil.getCellValue(wsheet,0,0);
        if( null == value){
            return false;
        }
        if(!"资金到账登记表".equals(value.toString().trim())){
            return false;
        }

        //校验第二行标题列
        value = excelUtil.getCellValue(wsheet,1,0);
        if( null == value){
            return false;
        }
        if(!"借款合同编号".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,1);
        if( null == value){
            return false;
        }
        if(!"主借款人".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,2);
        if( null == value){
            return false;
        }
        if(!"收款账户".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,3);
        if( null == value){
            return false;
        }
        if(!"到账日期".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,4);
        if( null == value){
            return false;
        }
        if(!"到账金额（元）".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,5);
        if( null == value){
            return false;
        }
        if(!"资金来源".equals(value.toString().trim())){
            return false;
        }

        value = excelUtil.getCellValue(wsheet,1,6);
        if( null == value){
            return false;
        }
        if(!"备注".equals(value.toString().trim())){
            return false;
        }

        return true;
    }

    /**
     * 模板错误
     * @param excelUtil
     * @param wsheet
     * @throws Exception
     */
    public void createErrorFile(String code, Workbook wbModule, ExcelUtil excelUtil, Sheet wsheet,String fileName) throws Exception {
        String targetFilePath= GlobParam.downloadTemp + commonService.getUUID() + ".xlsx";

        File file = new File(targetFilePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        OutputStream os = new FileOutputStream(file);
        wbModule.write(os);
        os.flush();
        os.close();

        Map map = FileUtil.uploadFms(targetFilePath);

        Map errorFile = new HashMap();
        errorFile.put("code",code);
        errorFile.put("errorFileCode",map.get("serialNo"));
        errorFile.put("errorFileName",fileName+".xlsx");
        errorFile.put("errorFileCreatedTime",new Date());
        errorFile.put("status",GlobDict.receipts_record_upload_status_fail.getKey());
        fundManagerDao.updateReceiptsRecordUpload(errorFile);

    }

    /**
     * 读取模板数据列表
     * @param excelUtil
     * @param wsheet
     * @return
     * @throws Exception
     */
    public List getDataList(ExcelUtil excelUtil, Sheet wsheet) throws Exception{
        int row = 2;
        List dataList = new ArrayList();
        while(row >=2 ){
            Map map = new HashMap();
            Object contractNo = excelUtil.getCellValue(wsheet,row,0);
            Object customerNmae = excelUtil.getCellValue(wsheet,row,1);
            Object acountNo = excelUtil.getCellValue(wsheet,row,2);
            Object receiptsDate = excelUtil.getCellValue(wsheet,row,3);
            Object receiptsAmount = excelUtil.getCellValue(wsheet,row,4);
            Object fundSource = excelUtil.getCellValue(wsheet,row,5);
            Object remark = excelUtil.getCellValue(wsheet,row,6);
            if(CommonUtil.isNullOrSpace(contractNo) && CommonUtil.isNullOrSpace(customerNmae) && CommonUtil.isNullOrSpace(acountNo) &&
                    CommonUtil.isNullOrSpace(receiptsDate) &&  CommonUtil.isNullOrSpace(receiptsAmount) &&  CommonUtil.isNullOrSpace(fundSource) &&
                    CommonUtil.isNullOrSpace(remark)){//如果当前行数据全部为空，则不再循环
                break;
            }

            map.put("contractNo",contractNo);
            map.put("customerNmae",customerNmae);
            map.put("acountNo",acountNo);
            map.put("receiptsDate",receiptsDate);
            map.put("receiptsAmount",receiptsAmount);
            map.put("fundSource",fundSource);
            map.put("remark",remark);

            dataList.add(map);
            row++;
        }

        return dataList;
    }

    /**
     * 检测数据，提供报错文件
     * @param excelUtil
     * @param wsheet
     * @param dataList
     * @return
     * @throws Exception
     */
    public boolean checkDataList(ExcelUtil excelUtil, Sheet wsheet, List dataList, String userId) throws Exception{
        boolean valid = true;
        for(int i=0; i<dataList.size(); i++){
            StringBuffer error = new StringBuffer("");
            Map map = (Map)dataList.get(i);

            //借款合同编号
            Object contractNo = map.get("contractNo");

            //获取合同相关的信息
            Map param = new HashMap();
            param.put("contractNo",contractNo);
            param.put("userId",userId);
            Map contractInfo = fundManagerDao.checkContractNoForUser(param);

            boolean isContractNoValid = true;
            if(null == contractNo){
                error.append("借款合同编号不能为空；");
                isContractNoValid = false;
            }else if( null == contractInfo){
                error.append("借款合同编号错误；");
                isContractNoValid = false;
            }

            //主借款人
            Object customerNmae = map.get("customerNmae");
            if(null == customerNmae){
                error.append("主借款人不能为空；");
            }else if(isContractNoValid){
                if(!CommonUtil.trim(customerNmae).equals(contractInfo.get("name"))){
                    error.append("主借款人姓名与借款合同编号不匹配；");
                }
            }

            //收款账户
            Object acountNo = map.get("acountNo");
            if(null == acountNo){
                error.append("收款方不能为空；");
            }else if(!CommonUtil.trim(acountNo).equals("资方还款账户")
                    && !CommonUtil.trim(acountNo).equals("宏获还款账户")
                    && !CommonUtil.trim(acountNo).equals("宏获保证金账户")){
                error.append("收款账户错误；");
            }

            //到账日期
            Object receiptsDate = map.get("receiptsDate");
            if(null == receiptsDate){
                error.append("到账日期不能为空；");
            }else{

                if(!CommonUtil.isValidDate(CommonUtil.trim(receiptsDate),"yyyy-MM-dd")){
                    error.append("到账日期格式错误；");
                }

                if(isContractNoValid) {
                    String creditStartDate = (String) contractInfo.get("creditStartDate");
                    if (DateUtil.dateCompare(receiptsDate.toString(), creditStartDate, "yyyy-MM-dd") > 0) {
                        error.append("到账日期不能早于授信起始日期；");
                    }
                }
            }

            //到账金额
            Object receiptsAmount = map.get("receiptsAmount");
            if(null == receiptsAmount){
                error.append("到账金额不能为空；");
            }else {
                if(!CommonUtil.isNumber(receiptsAmount.toString())){
                    error.append("到账金额格式错误；");
                }
            }

            //资金来源
            Object fundSource = map.get("fundSource");
            if(null == fundSource){
                error.append("资金来源不能为空；");
            }else if(!CommonUtil.trim(fundSource).equals("客户")
                    && !CommonUtil.trim(fundSource).equals("宏获")
                    && !CommonUtil.trim(fundSource).equals("担保")){
                error.append("资金来源错误；");
            }

            //备注
            Object remark =  map.get("remark");

            //如果有报错，则将报错写入第8列
            if(!"".equals(error.toString())){
                valid = false;
                XSSFWorkbook workbook = new XSSFWorkbook();
                Font font = workbook.createFont();
                font.setColor(HSSFColor.RED.index);
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFont(font);
                excelUtil.setCellValue(wsheet,i+2,7,error.toString(),cellStyle);
            }
        }

        return valid;
    }

    /**
     * 处理数据
     * @param dataList
     * @param userId
     * @throws Exception
     */
    public void dealDataList(List dataList, String userId) throws Exception{

        for(int i=0; i<dataList.size(); i++){
            Map map = (Map)dataList.get(i);

            //借款合同编号
            Object contractNo = map.get("contractNo");

            //获取合同相关的信息
            Map param = new HashMap();
            param.put("contractNo",contractNo);
            param.put("userId",userId);
            Map contractInfo = fundManagerDao.checkContractNoForUser(param);


            //主借款人
            Object customerNmae = map.get("customerNmae");

            //收款账户
            Object acountNo = map.get("acountNo");
            if(CommonUtil.trim(acountNo).equals("资方还款账户")){
                acountNo = GlobDict.receipts_record_account_type_fund_side.getKey();
            }else if(CommonUtil.trim(acountNo).equals("宏获还款账户")){
                acountNo = GlobDict.receipts_record_account_type_honghuo.getKey();
            }else if(CommonUtil.trim(acountNo).equals("宏获保证金账户")){
                acountNo = GlobDict.receipts_record_account_type_honghuo_bail.getKey();
            }

            //到账日期
            Object receiptsDate = map.get("receiptsDate");

            //到账金额
            Object receiptsAmount = map.get("receiptsAmount");

            //资金来源
            Object fundSource = map.get("fundSource");
            if(CommonUtil.trim(fundSource).equals("客户")){
                fundSource = GlobDict.receipts_record_fund_source_customer.getKey();
            }else if(CommonUtil.trim(fundSource).equals("宏获")){
                fundSource = GlobDict.receipts_record_fund_source_honghuo.getKey();
            }else if(CommonUtil.trim(fundSource).equals("担保")){
                fundSource = GlobDict.receipts_record_fund_source_danbao.getKey();
            }

            Map paramMap = new HashMap();
            String code = commonService.getUUID();
            paramMap.put("code",code);
            paramMap.put("contractCode",contractInfo.get("contractCode"));
            paramMap.put("receiptsDate",receiptsDate);
            paramMap.put("receiptsAmount",receiptsAmount);
            paramMap.put("accountType",acountNo);
            paramMap.put("remark",map.get("remark"));
            paramMap.put("fundSource",fundSource);
            paramMap.put("operateUser",userId);

            plms000015.busiService(new HashedMap(),paramMap,new HashedMap());
        }
    }

}
