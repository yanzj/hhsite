package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.util.PcfsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000002<br>
 * 功能：账户信息查询接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000002 extends BaseService {

    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //组织发送贷后后台数据
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000001");
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        resultMap.putAll(plmsRet);
    }


}
