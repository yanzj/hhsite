package com.vilio.bps.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/3.
 */
public class BpsBusinessUtil {

    private static Logger logger = LoggerFactory.getLogger(BpsBusinessUtil.class);    //日志记录

    /**
     * 解析第三方估价公司返回的数据，并封装为list返回
     * 仅封装key,value,parentKey
     * 部分参数需要后续处理
     * @param jsonData
     * @param parentId
     * @return
     */
    public static List<Map<String, Object>> parseJsonData(Object jsonData, String parentId) {

        List<Map<String, Object>> bpsObjDataList = new ArrayList();
        List<Map<String, Object>> subBpsObjDataList = new ArrayList();
        //入参检查
        if (null == jsonData) {
            return null;
        }

        try {
            if (jsonData instanceof JSONObject) {// if json is a Map

                JSONObject retObj = new JSONObject();
                JSONObject jsonObj = (JSONObject) jsonData;
                Iterator it = jsonObj.keys();
                while (it.hasNext()) {
                    Map<String, Object> returnMap = new HashedMap();
                    String key = (String) it.next();
                    Object value = jsonObj.get(key);
                    // TODO: do something here
                    returnMap.put("parentKey", parentId);
                    returnMap.put("key", key);
                    if(!(value instanceof JSONObject) && !(value instanceof JSONArray)) {
                        returnMap.put("value", value.toString());
                    } else {
                        subBpsObjDataList = parseJsonData(value, key);
                        if(subBpsObjDataList.size() > 0){
                            bpsObjDataList.addAll(subBpsObjDataList);
                        }
                    }
                    //不管value是具体的值还是数组对象，都把该条记录封装
                    bpsObjDataList.add(returnMap);
                }
                return bpsObjDataList;

            } else if (jsonData instanceof JSONArray) {// if json is an Array

                JSONArray retArray = new JSONArray();
                JSONArray jsonArray = (JSONArray) jsonData;
                int length = jsonArray.size();
                for (int i = 0; i < length; ++i) {
                    // TODO: do something here
                    subBpsObjDataList = parseJsonData(((JSONArray) jsonData).get(i), parentId);
                    if(subBpsObjDataList.size() > 0){
                        bpsObjDataList.addAll(subBpsObjDataList);
                    }

                }
                return bpsObjDataList;

            } else {// if json is just a raw element

                // TODO: do something here
                // return jsonData;

            }

        } catch (Exception e) {
            // deal Exception or throw it
        }

        return bpsObjDataList;
    }

//    public static Map<String, String> convertFieldAndValue(){
//
//    }


    public static void main(String[] args) throws Exception {
//        String json = "{\"returnCode\":\"0000\",\"data\":{\"displayNo\":\"1\",\"firstReviewList\":[{\"id\":42,\"userNo\":\"13000000002\",\"mobile\":\"13000000002\",\"password\":\"31ee952907247e3a2f0af52e81d175cd\",\"userName\":\"13000000002\",\"email\":\"test3@vilio.com.cn\",\"fullName\":\"风控初审\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":null,\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":false},{\"id\":43,\"userNo\":\"13000000003\",\"mobile\":\"13000000003\",\"password\":\"31ee952907247e3a2f0af52e81d175cd\",\"userName\":\"13000000003\",\"email\":\"test4@vilio.com.cn\",\"fullName\":\"风控复审\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":null,\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":false},{\"id\":7,\"userNo\":\"15021000149\",\"mobile\":\"15021000149\",\"password\":\"1292a5f81b095ed7104ae9c0459aef77\",\"userName\":\"15021000149\",\"email\":\"tanya.tao@vilio.com.cn\",\"fullName\":\"陶妍\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":\"00000001\",\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":true}],\"code\":\"00000001\",\"cityCode\":\"310000\",\"userNo\":\"13681692659\",\"abbrName\":\"庭睿\",\"fullName\":\"上海庭睿金融服务有限公司\",\"secondReviewList\":[{\"id\":43,\"userNo\":\"13000000003\",\"mobile\":\"13000000003\",\"password\":\"31ee952907247e3a2f0af52e81d175cd\",\"userName\":\"13000000003\",\"email\":\"test4@vilio.com.cn\",\"fullName\":\"风控复审\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":null,\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":false},{\"id\":7,\"userNo\":\"15021000149\",\"mobile\":\"15021000149\",\"password\":\"1292a5f81b095ed7104ae9c0459aef77\",\"userName\":\"15021000149\",\"email\":\"tanya.tao@vilio.com.cn\",\"fullName\":\"陶妍\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":\"00000001\",\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":true}],\"firstCharacterCode\":\"TR\",\"productList\":[{\"id\":1,\"code\":\"1\",\"productCode\":\"00000001\",\"orderByNo\":1,\"fullName\":\"P1\",\"abbrName\":\"P1\"},{\"id\":2,\"code\":\"2\",\"productCode\":\"00000002\",\"orderByNo\":10,\"fullName\":\"P2\",\"abbrName\":\"P2\"},{\"id\":3,\"code\":\"3\",\"productCode\":\"00000003\",\"orderByNo\":20,\"fullName\":\"P3\",\"abbrName\":\"P3\"},{\"id\":4,\"code\":\"4\",\"productCode\":\"00000004\",\"orderByNo\":30,\"fullName\":\"P4\",\"abbrName\":\"P4\"},{\"id\":5,\"code\":\"5\",\"productCode\":\"00000005\",\"orderByNo\":40,\"fullName\":\"P5\",\"abbrName\":\"P5\"},{\"id\":6,\"code\":\"6\",\"productCode\":\"00000006\",\"orderByNo\":50,\"fullName\":\"P6\",\"abbrName\":\"P6\"}],\"distributorManagerList\":[{\"id\":46,\"userNo\":\"13000000006\",\"mobile\":\"13000000006\",\"password\":\"31ee952907247e3a2f0af52e81d175cd\",\"userName\":\"13000000006\",\"email\":\"test7@vilio.com.cn\",\"fullName\":\"渠道经理\",\"cityCode\":\"000000\",\"companyCode\":\"00000001\",\"departmentCode\":null,\"distributorCode\":null,\"internalUser\":true,\"status\":\"0\",\"firstLogin\":false}]}}";
        String json = "{\"returnCode\":\"0000\"}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        List<Map<String, Object>> bpsReceiveDataList = parseJsonData(jsonObject, null);

        for(Map<String, Object> map : bpsReceiveDataList) {
            System.out.println(map.get("key") + "\t" + map.get("value") + "\t" + map.get("parentKey"));
            System.out.println("*******************************************");
        }
    }
}
