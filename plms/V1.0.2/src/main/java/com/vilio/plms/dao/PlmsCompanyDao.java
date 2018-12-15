package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsCompany;

/**
 * Created by dell on 2017/8/17.
 */
public interface PlmsCompanyDao {
    int saveCompanyInfo(PlmsCompany company);

    PlmsCompany getCompanyByBmsCode(PlmsCompany company);
}
