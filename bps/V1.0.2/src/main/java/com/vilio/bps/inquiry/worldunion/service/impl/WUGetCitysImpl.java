package com.vilio.bps.inquiry.worldunion.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.pojo.WUCity;
import com.vilio.bps.inquiry.worldunion.service.WUGetCitys;
import com.vilio.bps.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dell on 2017/5/11/0011.
 */
@Service
public class WUGetCitysImpl implements WUGetCitys {

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.worldunion.service.impl.WUGetCitysImpl.class);

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    /**
     * 获取所有省份信息
     * @return
     * @throws Exception
     */
    public List<WUCity> getProvinces() throws Exception {
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_GETPROVINCES;
        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        }catch (Exception e){
            logger.error("获取省份列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(WUCity.class, result);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<WUCity> getCitys() throws Exception {
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_GETCITYS;
        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        }catch (Exception e){
            logger.error("获取城市列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(WUCity.class, result);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<WUCity> getAreas() throws Exception {
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_GETAREAS;
        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        }catch (Exception e){
            logger.error("获取行政区域列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(WUCity.class, result);
    }

    /**
     * 根据城市ID获取行政区域列表
     * @return
     * @throws Exception
     */
    public List<WUCity> getAreasByCityCode(String cityCode) throws Exception {
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_GETAREAS;
        apiUrl += "/" + cityCode;
        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        }catch (Exception e){
            logger.error("获取行政区域列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(WUCity.class, result);
    }


    private List<WUCity> convertJSONToCityList(Object jsonObject){
        List<WUCity> wuCityList = new ArrayList<WUCity>();
        if (jsonObject instanceof JSONObject) {// if json is a Map
            JSONObject jsonObj = (JSONObject) jsonObject;
            try {
                CommonUtil.dealEmpty2NullOfJson(jsonObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WUCity wuCity = new WUCity();
            wuCity.setId(jsonObj.getString(WUFields.WU_A_ID));
            wuCity.setName(jsonObj.getString(WUFields.WU_A_NAME));
            wuCity.setgBCode(jsonObj.getString(WUFields.WU_GBCODE));
            wuCity.setpId(jsonObj.getString(WUFields.WU_PID));
            wuCity.setType(jsonObj.getString(WUFields.WU_TYPE));
            wuCityList.add(wuCity);
            return wuCityList;

        } else if (jsonObject instanceof JSONArray) {// if json is an Array

            JSONArray jsonArray = (JSONArray) jsonObject;
            int length = jsonArray.size();
            for (int i = 0; i < length; ++i) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                try {
                    CommonUtil.dealEmpty2NullOfJson(jsonObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WUCity wuCity = new WUCity();
                wuCity.setId(jsonObj.getString(WUFields.WU_A_ID));
                wuCity.setName(jsonObj.getString(WUFields.WU_A_NAME));
                wuCity.setgBCode(jsonObj.getString(WUFields.WU_GBCODE));
                wuCity.setpId(jsonObj.getString(WUFields.WU_PID));//用int获取出现问题，暂不需要该字段
                wuCity.setType(jsonObj.getString(WUFields.WU_TYPE));
                wuCityList.add(wuCity);
            }
            return wuCityList;

        } else {// if json is just a raw element

            // TODO: do something here
            // return jsonData;
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        new WUGetCitysImpl().getCitys();
    }
}
