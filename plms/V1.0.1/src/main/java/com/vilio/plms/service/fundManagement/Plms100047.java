package com.vilio.plms.service.fundManagement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.FundManagerDao;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.Plms000015;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RemoteFmsService;
import org.apache.commons.collections.map.HashedMap;
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
 * 类名： Plms100047<br>
 * 功能：还款到账修改提交<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100047 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100047.class);

    @Resource
    FundManagerDao fundManagerDao;
    @Resource
    FundManagerService fundManagerService;
    @Resource
    RemoteFmsService remoteFmsService;

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
        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();

        String receiptsRecordCode = body.get("receiptsRecordCode").toString();

        List fileList = (List)(body.get("fileList"));
        List deleteFileList = (List)(body.get("deleteFileList"));

        //调用文件平台
        List paramFileList = new ArrayList();
        for(int i=0; fileList!=null&&i<fileList.size(); i++){
            String fileCode = (String)(((Map)fileList.get(i)).get("fileCode"));
            Map fileMap = new HashMap();
            fileMap.put("serialNo",fileCode);
            paramFileList.add(fileMap);
        }
        Map map = new HashMap();
        map.put("serialNoList",paramFileList);
        Map result = remoteFmsService.getUrlList(map);

        //获取返回结果
        Map returnBody = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
        List fileMaps = (List)returnBody.get("fileMaps");

        //循环插入凭证记录
        for(int i=0; fileMaps!=null&&i<fileMaps.size(); i++){
            Map fileMap = (Map)fileMaps.get(i);
            String fileCode = (String)fileMap.get("serialNo");
            String uploadTime = (String)fileMap.get("uploadTime");
            String fileName = (String)fileMap.get("fileName");

            Map receiptsVoucher = new HashMap();
            receiptsVoucher.put("fileCode",fileCode);
            receiptsVoucher.put("status", GlobDict.valid.getKey());
            receiptsVoucher.put("receiptsRecordCode",receiptsRecordCode);
            receiptsVoucher.put("fileName",fileName);
            receiptsVoucher.put("uploadTime",uploadTime);
            fundManagerDao.createReceiptsVoucher(receiptsVoucher);
        }

        //循环删除凭证记录
        for(int i=0; deleteFileList!=null && i<deleteFileList.size(); i++){
            String fileCode = (String)(((Map)deleteFileList.get(i)).get("fileCode"));

            Map receiptsVoucher = new HashMap();
            receiptsVoucher.put("fileCode",fileCode);
            receiptsVoucher.put("status", GlobDict.un_valid.getKey());
            receiptsVoucher.put("receiptsRecordCode",receiptsRecordCode);
            fundManagerDao.updateReceiptsVoucherStatus(receiptsVoucher);
        }


    }

}
