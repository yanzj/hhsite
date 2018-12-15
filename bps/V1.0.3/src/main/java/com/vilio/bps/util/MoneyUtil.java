package com.vilio.bps.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhangjx
 * @version 1.0
 * @date 2014年1月14日
 * Description: 金额格式转换类
 */
public class MoneyUtil {

    private MoneyUtil() {
    }

    public static String convertFen2Yuan(String fen) {
        BigDecimal fenAmt = new BigDecimal(fen);
        BigDecimal yuanAmt = fenAmt.divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN);
        return yuanAmt.toString();
    }
    public static String convertFen2Yuan3Dec(String fen) {
        BigDecimal fenAmt = new BigDecimal(fen);
        BigDecimal yuanAmt = fenAmt.divide(new BigDecimal(100)).setScale(3, RoundingMode.DOWN);
        return yuanAmt.toString();
    }

    public static String convertYuan2Fen(String yuan) {
        BigDecimal yuanAmt = new BigDecimal(yuan);
        BigDecimal fenAmt = yuanAmt.multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);
        return fenAmt.toString();
    }

    public static String convertFen2Yuan4Dec(String fen) {
        BigDecimal fenAmt = new BigDecimal(fen);
        BigDecimal yuanAmt = fenAmt.divide(new BigDecimal(100)).setScale(4, RoundingMode.DOWN);
        return yuanAmt.toString();
    }

    public static BigDecimal convertBdFen2Yuan2Dec(BigDecimal fen){
        if(fen == null)
            return fen;
        BigDecimal yuan = fen.divide(new BigDecimal("100")).setScale(2, RoundingMode.DOWN);
        return yuan;
    }

    public static BigDecimal convertBdFen2Yuan2Dec2(BigDecimal fen){
        if(fen == null)
            return new BigDecimal("0");
        BigDecimal yuan = fen.divide(new BigDecimal("100")).setScale(2, RoundingMode.DOWN);
        return yuan;
    }

}
