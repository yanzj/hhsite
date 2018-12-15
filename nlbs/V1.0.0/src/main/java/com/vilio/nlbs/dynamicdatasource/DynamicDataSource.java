package com.vilio.nlbs.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by dell on 2017/5/22/0022.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}
