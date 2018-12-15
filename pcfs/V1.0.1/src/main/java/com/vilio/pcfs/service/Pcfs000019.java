package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000019<br>
 * 功能：利息试算接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000019 extends BaseService {

    public void checkParam(Map<String, Object> body) throws ErrorException {
        String principal = (String) body.get("principal");
        if (!JudgeUtil.isNull(principal)){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "本金不能为空");
        }

    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //组织向贷后系统请求数据
        String principal = (String) body.get("principal");
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000009");
        sendParam.put("principal",principal);
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);
    }



}
