package com.vilio.nlbs.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public class CommonUtil {

    public static boolean isEmptyString(Object obj){
        return (null == obj) || "".equals(obj.toString().trim());
    }

    public static String trim(Object obj){
        if(null == obj) {
            return null;
        }
        return obj.toString().trim();
    }

    public static boolean isNullOrSpace(Object obj){
        if(null == obj) {
            return true;
        }
        if("".equals(trim(obj))){
            return true;
        }
        return false;
    }

    public static String dealStringNull(Object obj){
        if(null == obj) {
            return null;
        }
        return obj.toString();
    }

    public static BigDecimal transDouble2BigDecimal(Double obj){
        if(null == obj || "".equals(trim(obj))) {
            return null;
        }
        return new BigDecimal(obj);
    }

    public static Double dealDoubleNull(Object obj){
        if(null == obj || "".equals(trim(obj))) {
            return null;
        }
        if(obj instanceof String ){
            return new Double((String)obj);
        }
        return (Double)obj;
    }

    public static Integer dealIntegerNull(Object obj){
        if(null == obj || "".equals(trim(obj))) {
            return null;
        }
        if(obj instanceof String ){
            return new Integer((String)obj);
        }
        return (Integer) obj;
    }

    public static Date dealDateNull(Object obj){
        if(null == obj) {
            return null;
        }
        return (Date) obj;
    }

    public static String formatDate2String(Date date,String pattern){
        if(null == date ){
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date formatString2Date(String date,String pattern) throws Exception{
        if(null == date ){
            return null;
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(date);
    }

    public static String padding(Integer obj,int length,String pad,String lr) {
        StringBuilder sb = new StringBuilder();
        sb.append(obj);

        if (sb.length() >= length) {
            return sb.toString();
        }
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < length - sb.length(); i++) {
            padding.append(pad);
        }
        if ("l".equals(lr)) {
            return padding.append(sb).toString();
        } else {
            return sb.append(padding).toString();
        }
    }

    public static void dealEmpty2Null(Map dataMap) throws Exception{
        if(null == dataMap){
            return;
        }
        Iterator it = dataMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            String key = entry.getKey();
            Object data = entry.getValue();

            if(data instanceof String){
                if("".equals(data.toString())){
                    data = null;
                    dataMap.put(key,data);
                }
            }else if(data instanceof List){
                for(int i=0; i<((List) data).size();i++){
                    Object tempMap =((List) data).get(i);
                    dealEmpty2Null((Map)tempMap);
                }
            }
        }
    }

    public static void dealEmpty2NullOfJson(JSONObject json) throws Exception{
        if(null == json){
            return;
        }
        Iterator it = json.keys();
        while(it.hasNext()){
            String key = (String) it.next();
            String value = json.getString(key);

            if(null == value || "".equals(value.toString()) || "null".equals(value)){
                value = "";
                json.put(key, value);
            }
        }
    }

    public static Object mergeStringObj(Object main,Object tail){
        if(null != main && null != tail){
            return new StringBuilder(main.toString()).append(tail).toString();
        }
        return main;
    }

    public static String getFileType(String fileSuffix){
        if(null == fileSuffix || "".equals(fileSuffix.trim())){
            return "";
        }
        if("doc".equals(fileSuffix) || "docx".equals(fileSuffix)){
            return "word";
        }
        if("xls".equals(fileSuffix) || "xlsx".equals(fileSuffix)){
            return "excel";
        }
        if("pdf".equals(fileSuffix)){
            return "pdf";
        }
        if("html".equals(fileSuffix) || "htm".equals(fileSuffix) || "mht".equals(fileSuffix)){
            return "html";
        }
        if("jpg".equals(fileSuffix) || "jpeg".equals(fileSuffix) || "png".equals(fileSuffix) || "gif".equals(fileSuffix) || "bmp".equals(fileSuffix)){
            return "img";
        }
        return "img";
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    /**
     * 将 map 转换为实体对象
     * @param type 实体类型
     * @param map  被转数据
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object convertMapToEntity(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        if(null == map || null == type){
            return null;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            //首字母误写为大写的情况考虑
            String uper_case_propertyName_ = propertyName.substring(0,1).toUpperCase() + propertyName.substring(1,propertyName.length());

            if (map.containsKey(propertyName) || map.containsKey(uper_case_propertyName_)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                if(null == value){
                    value = map.get(uper_case_propertyName_);
                }
                try {
                    value = convertVaule(type, propertyName, value);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     *
     * @param jsonObj
     * @return
     */
    public static Map toMap(JSONObject jsonObj){
        if(jsonObj == null){
            return new HashMap();
        }
        Map returnMap = new HashMap();
        Iterator it = jsonObj.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext()) {
            Object key = it.next();
            Object value = jsonObj.get(key);
            if(value instanceof JSONObject){
                value = toMap((JSONObject) value);
            }else if(value instanceof JSONArray){
                List<Map> objectList = new ArrayList<Map>();
                for(Object jo : (JSONArray)value){
                    objectList.add(toMap((JSONObject) jo));
                }
                value = objectList;
            }else if(JSONNull.getInstance().equals(value)){
                value = null;
            }
            returnMap.put(key, value);

        }

        return  returnMap;
    }
    /**
     * 根据实体属性转换数据类型
     * @param type 数据对应的实体类
     * @param propertyName 属性名称
     * @param value  被转换值
     * @return
     */
    public static Object convertVaule(Class type ,String propertyName, Object value) throws NoSuchFieldException, ParseException {
        Field field= type.getDeclaredField(propertyName);
        Class<?> classType = field.getType();

        if(!classType.isPrimitive() && classType.equals(String.class)){
            value = value.toString();
        }else if(classType.equals(Integer.class) || classType.getName().equals("int")){
            if(value instanceof Integer){
                value = (Integer)value;
            }else if(value instanceof String){
                Integer.parseInt((String)value);
            }
        }else if(classType.equals(Long.class) || classType.getName().equals("long")){
            if(value instanceof Long){
                value = (Long)value;
            }else if(value instanceof String){
                Long.parseLong((String)value);
            }
        }else if(classType.equals(Double.class) || classType.getName().equals("double")){
            if(value instanceof Double){
                value = (Double)value;
            }else if(value instanceof String){
                Double.parseDouble((String)value);
            }
        }else if(classType.equals(Float.class) || classType.getName().equals("float")){
            if(value instanceof Float){
                value = (Float)value;
            }else if(value instanceof String){
                Float.parseFloat((String)value);
            }
        }else if(classType.equals(Short.class) || classType.getName().equals("short")){
            if(value instanceof Short){
                value = (Short)value;
            }else if(value instanceof String){
                Short.parseShort((String)value);
            }
        }else if(classType.equals(Byte.class) || classType.getName().equals("byte")){
            if(value instanceof Byte){
                value = (Byte)value;
            }else if(value instanceof String){
                Byte.parseByte((String)value);
            }
        }else if(!classType.isPrimitive() && classType.equals(Character.class)){
            if(value instanceof Character){
                value = (Character)value;
            }
        }else if(classType.equals(Boolean.class) || classType.getName().equals("boolean")){
            if(value instanceof Boolean){
                value = (Character)value;
            }else if(value instanceof String){
                if(value.equals("true")){
                    value = true;
                }else if(value.equals("false")){
                    value = false;
                }
            }
        }else if(classType.getName().equals("char")){
            if(value instanceof Character){
                value = (Character)value;
            }
        }else if(!classType.isPrimitive() && classType.equals(Date.class)){
            if(value instanceof Date){
                value = (Date)value;
            }else if(value instanceof String){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                value = sdf.parse((String)value);
            }
        }

        return value;
    }

    /**
     * jsonObject 转换成相关实体列表
     * @param classObj  对应的实体类型
     * @param jsonObject  数据json
     * @return 转换后的实体列表
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws ParseException
     */
    public static List convertJSONToList(Object classObj, Object jsonObject) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
        List list = new ArrayList();

        JSONArray jsonArray = new JSONArray();
        if((jsonObject instanceof JSONArray)){
            jsonArray = (JSONArray) jsonObject;;
        }else if(jsonObject instanceof JSONObject){
            JSONObject jsonObj = (JSONObject) jsonObject;
            jsonArray.add(jsonObj);
        }

        if(null == jsonArray || jsonArray.size() < 1){
            return null;
        }
        Iterator<JSONObject> it = jsonArray.iterator();
        while(it.hasNext()){
            JSONObject jsonObj = it.next();
            Map map = toMap(jsonObj);
            Object returnObj = convertMapToEntity(classObj.getClass(), map);
            if(null != returnObj){
                list.add(returnObj);
            }
        }

        return list;
    }
    /**
     * 检查必填参数工具类
     * @param paramMap
     * @param requiredFieldList
     * @return
     * @throws HHBizException
     */
    public static boolean checkRequiredFields(Map paramMap, List<String> requiredFieldList) throws HHBizException {
        if(paramMap == null){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "消息体不能为空！");
        }
        if(requiredFieldList == null || requiredFieldList.size() == 0){
            return true;
        }

        for(String s: requiredFieldList){
            Object obj = paramMap.get(s);
            if(null == obj){
                throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "缺少必要参数" + "[" + s +"]");
            }
            if(obj instanceof List){
                continue;
            }
            String field = (String) obj;
            if(StringUtils.isBlank(field)){
                throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "缺少必要参数" + "[" + s +"]");
            }
        }

        return true;
    }

    /**
     * List<Map>排序
     * @param resultList
     * @throws Exception
     */
    public static void listSort(List<Map> resultList, String orderKey, boolean isAsc) throws Exception {
        // resultList是需要排序的list，其内放的是Map
        // 返回的结果集
        Collections.sort(resultList, new Comparator<Map>() {

            public int compare(Map o1, Map o2) {

                //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值
                Object s1 = o1.get(orderKey);
                Object s2 = o2.get(orderKey);

                if(s1 instanceof Integer){
                    if ((Integer) s1 > (Integer) s2) {
                        return isAsc ? 1 : -1;
                    } else {
                        return isAsc ? -1 : 1;
                    }
                }else {
                    String s1Str = s1.toString();
                    String s2Str = s2.toString();
                    return isAsc ? s1Str.compareTo(s2Str) : s2Str.compareTo(s1Str);
                }
            }
        });

    }

    /**
     * 获取一个唯一的字符串，用时间戳+随机数及前后缀组成
     * @param prefStr
     * @param sufStr
     * @return
     */
    public static String getCurrentTimeStr(String prefStr, String sufStr) {
        String currentTime = formatDate2String(new Date(), "yyyyMMddHHmmssSSS");
        int random = (int) (Math.random() * 10000);
        String randomStr = String.format("%04d", random);
        return (prefStr == null ? "" : prefStr) + currentTime + randomStr + (sufStr == null ? "" : sufStr);
    }

    public static void main(String[] args) throws Exception {
        Integer a = null;
        a = (null == a )?0:a + 1;
        System.out.println(a);

        System.out.println(getCurrentTimeStr(null, null));
    }
}
