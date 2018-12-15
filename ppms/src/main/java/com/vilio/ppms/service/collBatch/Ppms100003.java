package com.vilio.ppms.service.collBatch;

import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.glob.ReturnCode;
import com.vilio.ppms.service.base.BaseServiceInterface;
import com.vilio.ppms.service.common.CommonService;
import com.vilio.ppms.util.JudgeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Ppms100003<br>
 * 功能：批量代收交易业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Ppms100003 implements BaseServiceInterface {

    private static final Logger logger = Logger.getLogger(Ppms100003.class);

    @Resource
    private CommonService commonService;

    @Override
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 参数校验
        checkParam(body);
        //登录业务处理
        busiService(head, body, resultMap);
        //返回参数
        return resultMap;
    }

    /**
     * 参数校验
     * @param body
     */
    private void checkParam(Map<String, Object> body) throws ErrorException {
        commonService.checkField(ObjectUtils.toString(body.get("chlSerno")), "渠道流水号", null, 40);
        commonService.checkField(ObjectUtils.toString(body.get("totalCount")), "总笔数", null, 8);
        commonService.checkField(ObjectUtils.toString(body.get("totalAmount")), "总金额", null, 15);
        if (JudgeUtil.money_status_success.equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("totalAmount")), 15, 2))){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "金额校验不合法");
        }
        commonService.checkField(ObjectUtils.toString(body.get("reqDate")), "请求日期", null, 8);
        commonService.checkField(ObjectUtils.toString(body.get("reqTime")), "请求时间", null, 6);
        commonService.checkField(ObjectUtils.toString(body.get("batchDetail")), "支付详情列表", null, null);



    }

    /**
     * 业务处理
     * @param head
     * @param body
     * @param resultMap
     */
    private void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) {



    }

}
