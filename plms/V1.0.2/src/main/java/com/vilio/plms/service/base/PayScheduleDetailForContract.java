package com.vilio.plms.service.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/8/3.
 */
public interface PayScheduleDetailForContract {
    public boolean payContractByBatchCode(String contractCode, Date happenTime, String batchCode,String paymentMethod) throws Exception;

    public boolean payContract(String contractCode, Date happenTime) throws Exception;

    public boolean payContractList(List<Map> contractList, Date happenTime) throws Exception;

}
