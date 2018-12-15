package com.vilio.nlbs.dynamicdatasource;

import org.apache.commons.lang.StringUtils;

/**
 * Created by dell on 2017/5/22/0022.
 */
public class CustomerContextHolder {

    public static final String DATA_SOURCE_NLBS = "nlbsDataSource";
    public static final String DATA_SOURCE_BMS = "bmsDataSource";

    //用ThreadLocal来设置当前线程使用哪个dataSource
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        String dataSource = contextHolder.get();
        if (StringUtils.isEmpty(dataSource)) {
            return DATA_SOURCE_NLBS;
        }else {
            return dataSource;
        }
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }


}
