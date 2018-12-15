package com.vilio.bps.util;


import com.vilio.bps.commonMapper.pojo.BpsConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/8.
 * 房价算法表
 */
public class AlgorithmUtil {

    private AlgorithmUtil(){}

    /**
     * 平均值算法
     * @param parameter
     * @return
     */
    public static long arithmeticMeanAlgorithm(Map parameter){
        long price = 0;
        if(null == parameter) return price;
        List<BigDecimal> priceList = (List<BigDecimal>) parameter.get("priceList");

        if(null != priceList && priceList.size() >0){
            BigDecimal basePrice = new BigDecimal("0.0");
            for(BigDecimal bigPrice: priceList){
                basePrice = basePrice.add(bigPrice) ;
            }
            basePrice = basePrice.divide(new BigDecimal(priceList.size()), 2 ,BigDecimal.ROUND_HALF_DOWN);
            price = basePrice.longValue();
        }

        return price;
    }

    /**
     * 浮动百分比算法
     * @param parameter
     * @return
     */
    public static long floatPercentAlgorithm(Map parameter){
        long price = 0;
        if(null == parameter) return price;
        Map<String, BpsConfig> configMap = (Map<String, BpsConfig>) parameter.get("config");
        BigDecimal minPrice = (BigDecimal) parameter.get("minPrice");
        //
        List<BigDecimal> priceList = (List<BigDecimal>) parameter.get(Fields.PARAM_PRICE_LIST);

        if(null != priceList && priceList.size() > 0){
            for(int i = 0; i<priceList.size() ; i++){
                BigDecimal bd = priceList.get(i);
                if(i==0){
                    minPrice = bd;
                }
                if(bd.compareTo(minPrice) == -1){
                    minPrice = bd;
                }
            }
        }

        BpsConfig timeThresholdConfig = configMap.get(Constants.CONFIG__FLOAT_PERCENT);
        String strFloatPercent = timeThresholdConfig.getConfigValue();
        BigDecimal floatPercent = new BigDecimal(strFloatPercent);
        price = minPrice.multiply(floatPercent.add(new BigDecimal("1"))).longValue();

        return price;
    }

    public static long matchAlgorithm(String algorithm, Map parameter){
        if(algorithm.equals("arithmeticMean")){
            return arithmeticMeanAlgorithm(parameter);
        }else if(algorithm.equals("floatPercent")){
            return floatPercentAlgorithm(parameter);
        }
        return 0;
    }
}
