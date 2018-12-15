package com.vilio.ppms.service.collection;

import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.service.base.BaseServiceInterface;
import com.vilio.ppms.service.common.CommonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Ppms100001<br>
 * 功能：代收交易业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Ppms100001 implements BaseServiceInterface {

    private static final Logger logger = Logger.getLogger(Ppms100001.class);

    @Resource
    private CommonService commonService;

    @Override
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("test",commonService.getSeq("SERNO",6));




        //返回参数
        return resultMap;
    }

}
