package com.vilio.pcfs.service;


import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000017<br>
 * 功能：逾期列表接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月8日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000017 extends BaseService{

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {

        //判读当前分页数据是否合法
        if (!JudgeUtil.isNull(body.get("pageNo"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前请求页数不能为空");
        }
        if (!JudgeUtil.isNull(body.get("pageSize"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前页请求个数参数不能为空");
        }

        if (!JudgeUtil.isNull(body.get("overdueType")) || body.get("overdueType").toString().length() > 1 ) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "");
        }
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //组织向贷后系统请求数据
        String page = (String) body.get("pageNo");
        String pageSize = (String) body.get("pageSize");
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000017");
        sendParam.put("pageNo", page);
        sendParam.put("pageSize", pageSize);
        sendParam.put("overdueType", body.get("overdueType"));
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);
    }










}
