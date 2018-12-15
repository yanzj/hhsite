package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000006<br>
 * 功能：还款记录查询接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月5日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000006 extends BaseService {
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("pageNo")) ){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前请求页数不能为空");
        }
        if ( !JudgeUtil.isNull(body.get("pageSize"))  ){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前页请求个数参数不能为空");
        }

        if ( !JudgeUtil.isNull(body.get("status"))  ){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "订单类型不能为空");
        }
        //判断订单类型是否合法

    }

    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //组织向贷后系统请求数据
        String page = (String) body.get("pageNo");
        String pageSize = (String) body.get("pageSize");
        String status = (String) body.get("status");
        Map sendParam = new HashMap();
        sendParam.put("functionNo", "plms000006");
        sendParam.put("pageNo",page);
        sendParam.put("pageSize",pageSize);
        sendParam.put("status",status);
        //调用贷后系统
        Map plmsRet = PcfsUtil.sendPLMSRetJudge(head, sendParam);
        //返回上游系统贷后信息
        resultMap.putAll(plmsRet);
    }


}
