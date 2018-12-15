package com.vilio.bps.inquiry.shchengshi.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.shchengshi.pojo.SCProject;
import com.vilio.bps.inquiry.shchengshi.pojo.SCUnit;
import com.vilio.bps.inquiry.shchengshi.service.SCGetHousingInfo;
import com.vilio.bps.inquiry.shchengshi.util.SCConstants;
import com.vilio.bps.util.AppraisalCompanys;
import com.vilio.bps.util.CommonUtil;
import com.vilio.bps.util.ConfigInfo;
import com.vilio.bps.util.HttpRequestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2017/6/6/0006.
 */
@Service
public class SCGetHousingInfoImpl implements SCGetHousingInfo {

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.shchengshi.service.impl.SCGetHousingInfoImpl.class);

    /**
     * 小区名称匹配
     * @param key 关键字（小区名称或者地址）
     * @return
     * @throws Exception
     */
    public List<SCProject> getProjectList(String key) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.SH_CHENGSHI.getCode());

        //请求参数
        apiUrl += SCConstants.SC_API_GET_PROJECT_LIST;
        if(key != null) {
            apiUrl += "?key="+ key;
            Object result = null;
            try {
                result = HttpRequestUtils.httpGet(apiUrl, null);
                //TODO 判断城市的返回码
            } catch (Exception e) {
                logger.error("获取小区列表出现异常：", e);
                result = "";
            }
            return CommonUtil.convertJSONToList(SCProject.class, result);
        }
        return null;
    }

    /**
     * 楼栋号模糊匹配
     * @param projectId 楼栋号（必填）
     * @param key 关键字（楼栋号）
     * @return
     * @throws Exception
     */
    public List<SCUnit> getUnitList(String projectId, String key) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.SH_CHENGSHI.getCode());

        //请求参数
        apiUrl += SCConstants.SC_API_GET_UNIT_LIST;
        if(projectId != null && key != null) {
            apiUrl += "?projectId="+ projectId + "&key="+ key;
            Object result = null;
            try {
                result = HttpRequestUtils.httpGet(apiUrl, null);
                //TODO 判断城市的返回码
            } catch (Exception e) {
                logger.error("获取楼栋列表出现异常：", e);
                throw e;
            }
            return CommonUtil.convertJSONToList(SCUnit.class, result);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        new SCGetHousingInfoImpl().getProjectList("锦秋");
        new SCGetHousingInfoImpl().getUnitList("11564","1号");
    }
}
