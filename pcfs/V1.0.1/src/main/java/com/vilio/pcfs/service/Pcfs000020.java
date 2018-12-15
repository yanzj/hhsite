package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000020<br>
 * 功能：用户合同信息查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月23日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000020 extends BaseService {

    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, 36);
    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //组织向贷后系统请求数据
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000010");
        sendParam.put("contractCode",body.get("contractCode"));
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);
    }



}
