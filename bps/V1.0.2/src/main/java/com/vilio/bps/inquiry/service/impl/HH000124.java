package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.worldunion.service.impl.WULoginImpl;
import com.vilio.bps.util.Fields;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 重新获取WU   Token
 */
@Service
public class HH000124 extends BaseService {
    Logger logger = Logger.getLogger(HH000124.class);

    @Resource
    CommonService commonService;
    @Resource
    WULoginImpl wULoginImpl;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        Map responseMap = null;
        try {
            String token = null;
            responseMap = wULoginImpl.login();
            if(null != responseMap){
                token = (String) responseMap.get(Fields.PARAM_WU_TOKEN);
            }
            logger.info("重新获取世联token=" + token);
        } catch (Exception e) {
            logger.info("重新获取世联token异常：" + e);
        }

        return returnMap;
    }


    @Override
    public String getInterfaceDescription() {
        return "重新获取世联Token";
    }
}
