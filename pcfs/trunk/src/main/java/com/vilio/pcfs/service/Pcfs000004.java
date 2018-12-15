package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.FileUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 类名： Pcfs000004<br>
 * 功能：还款接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000004 extends BaseService {

    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断借款金额
        checkField(ObjectUtils.toString(body.get("amount")), "借款金额", null, 18);
        //判断借款金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("amount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款金额错误");
        }
        //合同编码
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, null);
        //还款凭证
        if (body.get("imageList")==null||((List)body.get("imageList")).size()<1) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "最少上传一张凭证");
        }
    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //上传凭证到fms上
        List<Map<String, Object>> base64List = new ArrayList<Map<String, Object>>();
        for (Object base64Str:(List)body.get("imageList")) {
            Map<String, Object> base64Map = new HashMap<String, Object>();
            base64Map.put("base64str",ObjectUtils.toString(base64Str));
            base64Map.put("fileName",System.currentTimeMillis()+new Random().nextInt(999999)+".jpg");
            base64List.add(base64Map);
        }
        List fmsList = FileUtil.base64BatchUploadFms(base64List);
        List voucherFileList = new ArrayList();
        for (Object fileInfo:fmsList) {
            voucherFileList.add(((Map)fileInfo).get("serialNo"));
        }
        // 组织数据发送贷后系统
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000003");
        sendParam.put("voucherFileList", voucherFileList);
        sendParam.put("amount", ObjectUtils.toString(body.get("amount")));
        sendParam.put("contractCode", ObjectUtils.toString(body.get("contractCode")));
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);
    }


}
