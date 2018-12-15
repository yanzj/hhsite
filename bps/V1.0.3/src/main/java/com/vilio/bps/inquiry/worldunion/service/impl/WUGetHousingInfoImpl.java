package com.vilio.bps.inquiry.worldunion.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.pojo.SearchBean;
import com.vilio.bps.inquiry.worldunion.pojo.WUConstruction;
import com.vilio.bps.inquiry.worldunion.service.WUGetHousingInfo;
import com.vilio.bps.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/12/0012.
 */
@Service
public class WUGetHousingInfoImpl implements WUGetHousingInfo {

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.worldunion.service.impl.WUGetHousingInfoImpl.class);

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    public List<SearchBean> getConstruction(String cityId, String name) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        //请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        apiUrl += WUConstants.WU_API_GETCONSTRUCTION;
        apiUrl += "/" + cityId + "/" + name;

        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        } catch (Exception e) {
            logger.error("获取楼盘列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(SearchBean.class, result);
    }

    public List<WUConstruction> getConstrutionViewInfoById(String conId) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        //请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        apiUrl += WUConstants.WU_API_GETCONSTRUTIONVIEWINFOBYID;
        apiUrl += "/" + conId;

        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        } catch (Exception e) {
            logger.error("获取楼盘信息出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(WUConstruction.class, result);
    }

    public List<SearchBean> getBuild(String conId) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        //请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        apiUrl += WUConstants.WU_API_GETBUILD;
        apiUrl += "/" + conId;

        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        } catch (Exception e) {
            logger.error("获取楼栋列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(SearchBean.class, result);
    }

    public List<SearchBean> getHouse(String buildId) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        //请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        apiUrl += WUConstants.WU_API_GETHOUSE;
        apiUrl += "/" + buildId;

        Object result = null;
        try {
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            //TODO 判断世联的返回码
        } catch (Exception e) {
            logger.error("获取房号列表出现异常：", e);
            throw e;
        }
        return CommonUtil.convertJSONToList(SearchBean.class, result);
    }


    private List<SearchBean> convertJSONToSearchBean(Object jsonObject) {
        List<SearchBean> searchBeanList = new ArrayList<SearchBean>();
        if (jsonObject instanceof JSONObject) {// if json is a Map
            JSONObject jsonObj = (JSONObject) jsonObject;
            try {
                CommonUtil.dealEmpty2NullOfJson(jsonObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SearchBean searchBean = new SearchBean();
            searchBean.setId(jsonObj.getString(WUFields.WU_AUP_ID));
            searchBean.setName(jsonObj.getString(WUFields.WU_A_NAME));
            searchBean.setAliases(jsonObj.getString(WUFields.WU_A_ALIASES));
            searchBeanList.add(searchBean);
            return searchBeanList;

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
                SearchBean searchBean = new SearchBean();
                searchBean.setId(jsonObj.getString(WUFields.WU_AUP_ID));
                searchBean.setName(jsonObj.getString(WUFields.WU_A_NAME));
                searchBean.setAliases(jsonObj.getString(WUFields.WU_A_ALIASES));
                searchBeanList.add(searchBean);
            }
            return searchBeanList;

        } else {// if json is just a raw element

            // TODO: do something here
            // return jsonData;
            return null;
        }
    }

    private List<WUConstruction> convertJSONToConstruction(Object jsonObject) {
        List<WUConstruction> wuConstructionList = new ArrayList<WUConstruction>();
        if (jsonObject instanceof JSONObject) {// if json is a Map
            JSONObject jsonObj = (JSONObject) jsonObject;
            WUConstruction wuConstruction = new WUConstruction();
            wuConstruction.setConstructionId(jsonObj.getString(WUFields.WU_A_CONSTRUCTIONID));
            wuConstruction.setConstructionName(jsonObj.getString(WUFields.WU_A_CONSTRUCTIONNAME));
            wuConstruction.setRegionName(jsonObj.getString(WUFields.WU_A_REGIONNAME));
            wuConstruction.setAreaName(jsonObj.getString(WUFields.WU_A_AREANAME));
            wuConstruction.setAreaLine(jsonObj.getString(WUFields.WU_A_AREALINE));
            wuConstruction.setDoorpLate(jsonObj.getString(WUFields.WU_A_DOORPLATE));
            wuConstruction.setEndDate(jsonObj.getString(WUFields.WU_A_ENDDATE));
            wuConstruction.setSaleName(jsonObj.getString(WUFields.WU_A_SALENAME));
            wuConstruction.setProvinceName(jsonObj.getString(WUFields.WU_A_PROVINCENAME));
            wuConstructionList.add(wuConstruction);
            return wuConstructionList;

        } else if (jsonObject instanceof JSONArray) {// if json is an Array

            JSONArray jsonArray = (JSONArray) jsonObject;
            int length = jsonArray.size();
            for (int i = 0; i < length; ++i) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                WUConstruction wuConstruction = new WUConstruction();
                wuConstruction.setConstructionId(jsonObj.getString(WUFields.WU_A_CONSTRUCTIONID));
                wuConstruction.setConstructionName(jsonObj.getString(WUFields.WU_A_CONSTRUCTIONNAME));
                wuConstruction.setRegionName(jsonObj.getString(WUFields.WU_A_REGIONNAME));
                wuConstruction.setAreaName(jsonObj.getString(WUFields.WU_A_AREANAME));
                wuConstruction.setAreaLine(jsonObj.getString(WUFields.WU_A_AREALINE));
                wuConstruction.setDoorpLate(jsonObj.getString(WUFields.WU_A_DOORPLATE));
                wuConstruction.setEndDate(jsonObj.getString(WUFields.WU_A_ENDDATE));
                wuConstruction.setSaleName(jsonObj.getString(WUFields.WU_A_SALENAME));
                wuConstruction.setProvinceName(jsonObj.getString(WUFields.WU_A_PROVINCENAME));
                wuConstructionList.add(wuConstruction);
            }
            return wuConstructionList;

        } else {// if json is just a raw element

            // TODO: do something here
            // return jsonData;
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        new WUGetHousingInfoImpl().getConstruction("4","锦秋");
//        new WUGetHousingInfoImpl().getConstrutionViewInfoById("10978");
//        new WUGetHousingInfoImpl().getBuild("10978");
//        new WUGetHousingInfoImpl().getHouse("440748");
    }
}
