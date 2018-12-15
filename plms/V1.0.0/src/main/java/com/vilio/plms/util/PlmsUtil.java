package com.vilio.plms.util;

import com.vilio.plms.glob.Fields;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangxf on 2017/6/15.
 */
public class PlmsUtil {

    private static final Logger logger = Logger.getLogger(PlmsUtil.class);

    /**
     * 通用返回方法
     *
     * @param request
     * @param response
     * @param result
     */
    public static void returnData(HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> result) {
        PrintWriter pw = null;
        String respMessage = null;
        Map<String, Object> head = (Map<String, Object>) result.get(Fields.PARAM_MESSAGE_HEAD);
        respMessage = JsonUtil.objectToJson(result);
        logger.info(respMessage);
        try {
            if (respMessage != null) {
                pw = response.getWriter();
                pw.print(respMessage);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("返回信息失败！");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    /**
     * 计算还款日
     *
     * @param date
     * @param addMonth
     */
    public static Date getDueDate(Date date, int addMonth) {
        //获取日期中年、月、日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        logger.info("Current Date: " + calendar.getTime());
        logger.info("Year: " + year);
        logger.info("Month: " + month);
        logger.info("Day: " + day);

        //现将日忽略，日设置为1号，算出addMonth+1个月的一号日期，减一天以后，就会得到addMonth月后的最后一天，跟day判断大小
        calendar.set(Calendar.DATE, 1);//设置日为1号
        calendar.add(Calendar.MONTH, addMonth + 1);//加addMonth+1个月
        calendar.add(Calendar.DATE, -1);//日期减一天，获取addMonth个月后的最后一天的日期
        //获取日
        int addMonthEndDay = calendar.get(Calendar.DATE);
        //原来获取的日减一天与addMonth月后的最后一天获取的日作比较
        if ((day - 1) < addMonthEndDay) {
            calendar.set(Calendar.DATE, day - 1);
        }
        logger.info("Deal Current Date: " + calendar.getTime());
        return calendar.getTime();
    }




    public static void main(String[] args) {
        //System.out.println(DateUtil.convert(getDueDate(DateUtil.parseDate("20170301"), 1), "yyyy-MM-dd"));

        System.out.println(JsonUtil.jsonToList("[{\"toUser\":\"1457002698@qq.com\"},{\"toUser\":\"632364386@qq.com\"}]"));
    }


}
