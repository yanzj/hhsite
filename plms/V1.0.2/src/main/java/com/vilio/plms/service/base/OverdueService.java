package com.vilio.plms.service.base;

import com.vilio.plms.exception.ErrorException;

/**
 * Created by martin on 2017/8/25.
 */
public interface OverdueService{
    public void overdue(String contractCode,String executeDate) throws ErrorException;
}
