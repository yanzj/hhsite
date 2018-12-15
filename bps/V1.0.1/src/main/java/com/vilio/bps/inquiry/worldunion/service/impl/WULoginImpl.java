package com.vilio.bps.inquiry.worldunion.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.service.WULogin;
import com.vilio.bps.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/11/0011.
 */
@Service("wULoginImpl")
public class WULoginImpl implements WULogin {

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.worldunion.service.impl.WULoginImpl.class);

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    public Map<String, Object> login() throws Exception {
        Map paramMap = new HashMap();
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        String userName = configInfo.getwUUsername();
        String password = configInfo.getwUPassword();

        String passwordMD5 = Md5Utils.MD5Encode(password, "UTF-8", false);
        paramMap.put(WUFields.WU_USERNAME, userName);
        //TODO 替换为MD5加密后的密码
        paramMap.put(WUFields.WU_PASSWORD, password);
        JSONObject result = null;
        try {
            result = HttpRequestUtils.httpPost(apiUrl + WUConstants.WU_API_LOGIN, paramMap, null, false);
            //TODO 判断世联的返回码
            if(result.getBoolean(WUFields.WU_SUCCESS) && !StringUtils.isBlank(result.getString(WUFields.WU_TOKEN))){
                WUConstants.WU_API_LOGIN_TOKEN = result.getString(WUFields.WU_TOKEN);
                result.put(Fields.PARAM_WU_TOKEN, WUConstants.WU_API_LOGIN_TOKEN);
                logger.info(WUConstants.WU_API_LOGIN_TOKEN);
            }
        }catch (Exception e){
            logger.error("获取登录token出现异常：", e);
            throw e;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        new WULoginImpl().login();
    }

}
