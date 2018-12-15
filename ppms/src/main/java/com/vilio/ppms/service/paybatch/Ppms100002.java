package com.vilio.ppms.service.paybatch;

import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.service.base.BaseServiceInterface;
import com.vilio.ppms.service.common.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Ppms100002<br>
 * 功能：批量代付交易业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Ppms100002 implements BaseServiceInterface {

    @Resource
    private CommonService commonService;

    @Override
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //返回参数
        return resultMap;
    }


}
