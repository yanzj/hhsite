package com.vilio.plms.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @date 2013-4-10 上午9:58:11
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String TIME_PATTERN = "HHmmss";
    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_PATTERN2 = "yyyy-MM-dd";
    public static final String SHORT_DATE_PATTERN = "yyMMdd";
    public static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_TIME_PATTERN2 = "yyyyMMdd HH:mm:ss";
    public static final String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_TIME_PATTERN = "yyMMddHHmmss";
    public static final String DATE_TIME_PATTERN4 = "yyyy.MM.dd";

    private static FastDateFormat timeFormat = FastDateFormat.getInstance(TIME_PATTERN);
    private static FastDateFormat dateFormat = FastDateFormat.getInstance(DATE_PATTERN);
    private static FastDateFormat dateFormat2 = FastDateFormat.getInstance(DATE_PATTERN2);
    private static FastDateFormat shortDateFormat = FastDateFormat.getInstance(SHORT_DATE_PATTERN);
    private static FastDateFormat dateTimeFormat = FastDateFormat.getInstance(DATE_TIME_PATTERN);
    private static FastDateFormat dateTimeFormat2 = FastDateFormat.getInstance(DATE_TIME_PATTERN3);
    private static FastDateFormat shortDateTimeFormat = FastDateFormat.getInstance(SHORT_DATE_TIME_PATTERN);


    private DateUtil() {
    }


    /**
     * 将yyyymmdd格式转换为yyyy-MM-dd HH:mm:ss
     */
    public static String toTimePattern3(String date) {
        return convert(date, DATE_TIME_PATTERN, DATE_TIME_PATTERN3);
    }

    /**
     * @param date        待转换的日期
     * @param datePattern 待转换的日期格式
     * @return
     */
    public static String convert(String date, String datePattern) {
        return convert(date, datePattern, DATE_TIME_PATTERN);
    }

    /**
     * @param date        待转换的日期
     * @param datePattern 转换的日期格式
     * @return
     */
    public static String convert(Date date, String datePattern) {
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        return format.format(date);
    }

    /**
     * @param date        待转换的日期
     * @param datePattern 待转换的日期格式
     * @param outPattern  输出日期的格式
     * @return
     */
    public static String convert(String date, String datePattern, String outPattern) {
        try {
            Date sdf = new SimpleDateFormat(datePattern).parse(date);
            return formatDateTime(sdf, outPattern);
        } catch (ParseException e) {
            logger.error("日期转换出错！", e);
        }
        return "";
    }

    /**
     * HHmmss
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return timeFormat.format(calendar);
    }

    /**
     * yyMMdd
     */
    public static String getCurrentShortDate() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return shortDateFormat.format(calendar);
    }

    /**
     * yyMMddHHmmss
     */
    public static String getCurrentShortDateTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return shortDateTimeFormat.format(calendar);
    }

    /**
     * yyyyMMdd
     */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return dateFormat.format(calendar);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return dateTimeFormat.format(calendar);
    }


    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime2() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return dateTimeFormat2.format(calendar);
    }

    /**
     * yyyyMMdd
     */
    public static Date parseDate(String source) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyy-MM-dd
     */
    public static Date parseDate2(String source) {
        try {
            return new SimpleDateFormat(DATE_PATTERN2).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yyyyMMddHHmmss
     */
    public static Date parseDateTime(String source) {
        if (StringUtils.isNotEmpty(source)) {
            source = StringUtils.rightPad(source, 14, '0');
            try {
                return new SimpleDateFormat(DATE_TIME_PATTERN).parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseDateTime2(String source) {
        if (StringUtils.isNotEmpty(source)) {
            source = StringUtils.rightPad(source, 14, '0');
            try {
                return new SimpleDateFormat(DATE_TIME_PATTERN2).parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseDateTime3(String source) {
        if (StringUtils.isNotEmpty(source)) {
            source = StringUtils.rightPad(source, 14, '0');
            try {
                return new SimpleDateFormat(DATE_TIME_PATTERN3).parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseDateTimeForPattern(String source, String pattern) {
        if (StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(pattern)) {
            try {
                return new SimpleDateFormat(pattern).parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * HHmmss
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return timeFormat.format(date);
    }

    /**
     * yyyyMMdd
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return dateFormat.format(date);
    }

    /**
     * yyyy-MM-dd
     */
    public static String formatDate2(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return dateFormat2.format(date);
    }

    /**
     * yyyyMMddHHmmss
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return dateTimeFormat.format(date);
    }

    /**
     * 自定义日期类型的格式化,如果自定义型为空或null,
     * 按默认类型yyyyMMddHHmmss格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateTime(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isEmpty(pattern)) {
            return formatDateTime(date);
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 获取昨天
     *
     * @return yyyyMMdd
     */
    public static String getYesterday() {
        return getYesterday(DATE_PATTERN);
    }

    /**
     * 获取昨天
     *
     * @return yyyy-MM-dd
     */
    public static String getYesterdayPattern2() {
        return getYesterday(DATE_PATTERN2);
    }

    /**
     * 获取当前日期的前一天
     *
     * @param pattern
     * @return 当前日期的前一天
     */
    public static String getYesterday(String pattern) {
        if (null == pattern || "".equals(pattern)) {
            return StringUtils.EMPTY;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.DATE, -1);
        return FastDateFormat.getInstance(pattern).format(calendar);
    }

    /**
     * 获取当前日期的后一天
     *
     * @param pattern
     * @return 当前日期的前一天
     */
    public static String getTomorrow(String pattern) {
        if (null == pattern || "".equals(pattern)) {
            return StringUtils.EMPTY;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.DATE, 1);
        return FastDateFormat.getInstance(pattern).format(calendar);
    }

    /**
     * 获取明天
     *
     * @return yyyyMMdd
     */
    public static String getTomorrow() {
        return getTomorrow(DATE_PATTERN);
    }

    /**
     * 获取明天
     *
     * @return yyyy-MM-dd
     */
    public static String getTomorrowPattern2() {
        return getTomorrow(DATE_PATTERN2);
    }

    /**
     * 获取本月第一天
     *
     * @return yyyyMMdd
     */
    public static String getFirstDayForCurrentMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.DATE, 1);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calendar.getTime());
    }

    /**
     * 获取本月最后一天
     *
     * @return yyyyMMdd
     */
    public static String getLastDayForCurrentMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calendar.getTime());
    }

    /**
     * 获取传入参照日期获取该日期后 n月 的最后一天
     *
     * @param date   参照日期
     * @param number 相对月份数
     * @return yyyyMMdd
     */
    public static String getLastDayForSetMonth(Date date, int number) {
        if (null == date || number < 0) {
            return null;
        }
        String strDate = DateUtil.formatDate(date);
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));
        month = month + number;
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE,  1);
        calendar.add(Calendar.DATE, -1);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calendar.getTime());
    }

    /**
     * 获取传入参照日期获取该日期后 n月 后的该天，如果要获取月份没有当前天数，则去月份的最后一天（例：获取2017年8月30号后相对6个月的日期，由于2018年2月只有28天，所以就取2018年2月28号）
     *
     * @param date   参照日期
     * @param number 相对月份数
     * @return yyyyMMdd
     */
    public static String getDayForSetMonth(Date date, int number) {
        if (null == date || number < 1) {
            return null;
        }
        String strDate = DateUtil.formatDate(date);
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));
        int day = Integer.parseInt(strDate.substring(6, 8));
        String lastDay = getLastDayForSetMonth(date, number);
        if (day > Integer.parseInt(lastDay.substring(6, 8))) {
            //传入天大于要获取月份的最后一天，取最后一天
            return lastDay;
        } else {
            month = month + number;
            Calendar calendar = Calendar.getInstance(Locale.CHINESE);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DATE, day);
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            return format.format(calendar.getTime());
        }

    }

    /**
     * 在当前日期后则返回true
     *
     * @param DATE1
     * @return
     */
    public static boolean compare_date(String DATE1) {


        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 得到某一时间之前多少小时的时间
     *
     * @param currentDate
     * @param beforeHours
     * @param pattern
     * @return
     */
    public static String getDateBefore(Date currentDate, int beforeHours, String pattern) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, -beforeHours);
        return FastDateFormat.getInstance(pattern).format(calendar);
    }

    /**
     * 得到某一时间之前多少小时的时间
     *
     * @param currentDate
     * @param beforeHours
     * @return
     */
    public static String getDateBefore(Date currentDate, int beforeHours) {
        return getDateBefore(currentDate, beforeHours, DATE_TIME_PATTERN);
    }

    /**
     * 获取某一时间前几小时的时间yyyyMMddHHmmss
     */
    public static String getDateBefore(Calendar calendar, int hour) {
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        return FastDateFormat.getInstance(DATE_TIME_PATTERN).format(calendar);
    }

    /**
     * 得到某一时间之前多少小时的时间
     *
     * @param currentDate
     * @param beforeHours
     * @return
     */
    public static Date getDateTimeBefore(Date currentDate, int beforeHours) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, -beforeHours);
        return calendar.getTime();
    }

    /**
     * 获取1个月前的日期yyyyMMddHHmmss
     */
    public static String getDateBeforeOneMonth() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.MONTH, -1);
        return FastDateFormat.getInstance(DATE_TIME_PATTERN).format(calendar);
    }

    /**
     * 返回多少天前或者多少天后
     * yyyyMMdd
     *
     * @param days
     * @return
     */
    public static String getDateDistanceNow(int days) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.DATE, days);
        return FastDateFormat.getInstance(DATE_PATTERN).format(calendar);
    }

    /**
     * 返回多少天前或者多少天后
     * @param inputDate 传入参照日期
     * @param days 多少天之后
     * @return
     */
    public static Date getDateDistanceInputDate(Date inputDate, int days) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 给定时间与当前时间比较
     * yyyyMMddHHmmss
     *
     * @param sDate
     * @return 大于或等于当前时间返回true其他返回false
     */
    public static boolean laterThanNow(String sDate) {
        if (StringUtils.isEmpty(sDate) || sDate.length() != 14) return false;
        Calendar calendar0 = Calendar.getInstance(Locale.CHINESE);
        calendar0.set(Integer.parseInt(sDate.substring(0, 4)), Integer.parseInt(sDate.substring(4, 6)) - 1, Integer.parseInt(sDate.substring(6, 8)), Integer.parseInt(sDate.substring(8, 10)), Integer.parseInt(sDate.substring(10, 12)), Integer.parseInt(sDate.substring(10, 12)));
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return calendar0.before(calendar);
    }


    /**
     * 时间与当前时间作比较
     *
     * @return
     */
    public static boolean dateThanNow(String dateStr) throws ParseException {
        java.util.Date nowdate = new java.util.Date();
        SimpleDateFormat sdf = null;
        if (dateStr.length() == 8) {
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        } else if (dateStr.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        } else if (dateStr.length() == 14) {
            sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        } else if (dateStr.length() == 19) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }
        Date date = sdf.parse(dateStr);
        boolean flag = date.before(nowdate);
        return flag;
    }


    /**
     * 返回年份
     * <p/>
     * 日期
     *
     * @return 返回年份
     */
    public static int getYear() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回月份
     * <p/>
     * 日期
     *
     * @return 返回月份
     */
    public static int getMonth() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日份
     * <p/>
     * 日期
     *
     * @return 返回日份
     */
    public static int getDay() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     * <p/>
     * 日期
     *
     * @return 返回小时
     */
    public static int getHour() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     * <p/>
     * 日期
     *
     * @return 返回分钟
     */
    public static int getMinute() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     * <p/>
     * 日期
     *
     * @return 返回秒钟
     */
    public static int getSecond() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     * <p/>
     * 日期
     *
     * @return 返回毫秒
     */
    public static long getMillis() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.getTimeInMillis();
    }

    /**
     * 是否成年
     * 判断是否满18周岁生日为当天的属于满18周岁
     *
     * @param sDate
     * @return 大于等于18返回true参数不符合规则返回false
     */
    public static boolean isAdult(String sDate) {
        if (StringUtils.isEmpty(sDate) || sDate.length() != 8) return false;
        Calendar calendar0 = Calendar.getInstance(Locale.CHINESE);
        calendar0.set(Integer.parseInt(sDate.substring(0, 4)), Integer.parseInt(sDate.substring(4, 6)) - 1, Integer.parseInt(sDate.substring(6, 8)));
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        return calendar0.before(calendar);
    }

    public static int getWeek(String date) {
        Calendar calendar0 = Calendar.getInstance(Locale.CHINESE);
        calendar0.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
        return calendar0.get(Calendar.DAY_OF_WEEK);

    }

    /**
     * 得到 @param date 的前一天
     *
     * @param date
     * @return
     */
    public static String getDayBefore(String date) {
        Calendar calendar0 = Calendar.getInstance(Locale.CHINESE);
        calendar0.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
        calendar0.add(Calendar.DAY_OF_MONTH, -1);
        return FastDateFormat.getInstance(DATE_PATTERN).format(calendar0);
    }

    /**
     * 得到 @param date 的后一天
     *
     * @param date
     * @return
     */
    public static String getNextDay(String date) {
        Calendar calendar0 = Calendar.getInstance(Locale.CHINESE);
        calendar0.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));
        calendar0.add(Calendar.DAY_OF_MONTH, 1);
        return FastDateFormat.getInstance(DATE_PATTERN).format(calendar0);
    }

    /**
     * 当前日期与toDate做比对，得出相差的天数，如果是同一天则返回0
     *
     * @param toDate yyyyMMdd
     * @return
     */
    public static int dateCompare(String toDate) {
        int days = 0;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String nowDay = df.format(new Date());
        try {
            Date from = df.parse(nowDay);
            Date to = df.parse(toDate);
            days = (int) (to.getTime() - from.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            logger.error("日期转换出现异常");
        }
        return days;
    }

    /**
     * 获取日期的前一天
     *
     * @param
     * @return 日期的前一天
     */
    public static String getFileYesterday(String source) {

        Date date = parseDate(source);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
    }

    /**
     * 日期获取多少天之后的日期  yyyy-MM-dd
     *
     * @param
     * @return 日期的前一天
     */
    public static String getDaysDate2(String source, int days) {
        Date date = parseDate2(source);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

    /**
     * 日期获取多少天之后的日期  yyyyMMdd
     *
     * @param
     * @return 日期的前一天
     */
    public static String getDaysDate(String source, String days) {
        Date date = parseDate(source);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, Integer.parseInt(days));
        return (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());
    }


    //日期格式相差天数
    public static long differDays(String endDate, String startDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(endDate);
            date2 = sdf.parse(startDate);
        } catch (Exception e) {
            throw e;
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        long gap = (cal1.getTimeInMillis() - cal2.getTimeInMillis()) / (1000l * 3600 * 24);
        return gap;
    }

    public static long differDays2(String endDate, String startDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(endDate);
            date2 = sdf.parse(startDate);
        } catch (Exception e) {
            throw e;
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double ONE_DAY_MILLIS = 1000l * 3600 * 24;
        /*long gap = ( (ONE_DAY_MILLIS + cal1.getTimeInMillis()) - cal2.getTimeInMillis()) / ONE_DAY_MILLIS;*/
        double gap = Math.ceil((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_DAY_MILLIS);
        return (long) gap;
    }


    public static long differDays3(String endDate, String startDate, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(endDate);
            date2 = sdf.parse(startDate);
        } catch (Exception e) {
            throw e;
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double ONE_DAY_MILLIS = 1000l * 3600 * 24;
        /*long gap = ( (ONE_DAY_MILLIS + cal1.getTimeInMillis()) - cal2.getTimeInMillis()) / ONE_DAY_MILLIS;*/
        double gap = Math.ceil((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_DAY_MILLIS);
        return (long) gap;
    }


    //日期格式相差毫秒数
    public static long differMillis(String endDate, String startDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(endDate);
            date2 = sdf.parse(startDate);
        } catch (Exception e) {
            throw e;
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        long gap = (cal1.getTimeInMillis() - cal2.getTimeInMillis());
        return gap;
    }

    //日期格式相差小时数
    public static double differHours(Date endDate, Date startDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date1 = endDate;
        Date date2 = startDate;

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double ONE_HOUR_MILLIS = 1000l * 3600;
        double gap = Math.ceil((cal1.getTimeInMillis() - cal2.getTimeInMillis()) / ONE_HOUR_MILLIS);
        return gap;
    }

    /**
     * @param date 待转换的日期
     * @return
     */
    public static String convert(String date) {
        return convert(date, DATE_TIME_PATTERN, DATE_TIME_PATTERN4);
    }

    /**
     * startDate与endDate做比对，得出相差的天数，如果是同一天则返回0
     * 如果startDate 小于 endDate ，返回正数，如果大于，返回负数
     *
     * @return
     */
    public static int dateCompare(String startDate, String endDate) throws ParseException {
        int days = 0;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date from = df.parse(endDate.substring(0, 8));
            Date to = df.parse(startDate.substring(0, 8));
            days = (int) ((from.getTime() - to.getTime())
                    / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
            logger.error("日期转换出现异常");
            throw e;
        }
        return days;
    }

    /**
     * startDate与endDate做比对，得出相差的天数，如果是同一天则返回0
     * 如果startDate 小于 endDate ，返回正数，如果大于，返回负数
     *
     * @return
     */
    public static int dateCompare(String startDate, String endDate, String pattern) throws ParseException {
        int days = 0;

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            if ("yyyyMMdd".equals(pattern)) {
                Date from = df.parse(endDate.substring(0, 8));
                Date to = df.parse(startDate.substring(0, 8));
                days = (int) ((from.getTime() - to.getTime())
                        / (24 * 60 * 60 * 1000));
            } else if ("yyyy-MM-dd".equals(pattern)) {
                Date from = df.parse(endDate.substring(0, 10));
                Date to = df.parse(startDate.substring(0, 10));
                days = (int) ((from.getTime() - to.getTime())
                        / (24 * 60 * 60 * 1000));
            } else {
                throw new ParseException("不支持的日期类型", 0);
            }
        } catch (Exception e) {
            logger.error("日期转换出现异常");
            throw e;
        }
        return days;
    }


    /**
     * 返回周几
     * <p/>
     * 日期
     *
     * @return 返回周几  周日返回1 周一返回2 周二返回3 周三返回4 周四返回5 周五返回6 周六返回7
     */
    public static int getXingqi() {
        Calendar c = Calendar.getInstance(Locale.CHINESE);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * YYYY-MM-DD
     */
    public static String getCurrentDatePattern2() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return FastDateFormat.getInstance(DATE_PATTERN2).format(calendar);
    }

    /**
     * YYYY-MM-DD HH24:MI:SS
     *
     * @return
     */
    public static String getCurrentDateTimePattern2() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        return FastDateFormat.getInstance(DATE_TIME_PATTERN3).format(calendar);
    }


    /**
     * 获取n个月后的日期yyyyMMdd（num可负数）
     */
    public static String getDateAfterNumMonth(String date, int num) throws ParseException {
        SimpleDateFormat sdf = null;
        if (date.length() == 8) {
            sdf = new SimpleDateFormat("yyyyMMdd");
        } else if (date.length() == 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } else if (date.length() == 14) {
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (date.length() == 19) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date now = sdf.parse(date);
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, num);
        return FastDateFormat.getInstance(DATE_PATTERN).format(calendar);
    }


    /**
     * date与当前时间做比对，得出相差的天数，如果是同一天则返回0
     * 如果date 小于 当前日期 ，返回正数，如果大于，返回负数
     *
     * @return
     */
    public static int dateCompareNow(String date) throws ParseException {
        int days = 0;
        SimpleDateFormat df = null;
        if (date.length() == 8) {
            df = new SimpleDateFormat("yyyyMMdd");
        } else if (date.length() == 10) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        } else if (date.length() == 14) {
            df = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (date.length() == 19) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            Date from = new Date();
            Date to = df.parse(date);
            days = (int) ((from.getTime() - to.getTime())
                    / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
            logger.error("日期转换出现异常");
            throw e;
        }
        return days;
    }


    /**
     * 日期格式化
     * a. 一个小时之内，显示XX分钟前；
     * b. 10个小时之内，X小时<=接收时间 <= Y小时，显示X个小时前；
     * c. 超过10个小时的显示具体时间：
     * a) 接收时间与当前时间同一天，显示：今天 hh:mm:ss
     * b) 接收时间为当前时间前一天，显示：昨天 hh:mm:ss
     * c) 接收时间为当前时间前两天，显示：前天 hh:mm:ss
     * d) 其它情况显示：yyyy-mm-dd hh:mm:ss
     *
     * @param sourceDate
     * @return
     */
    public static String formatDateForDisplay(Date sourceDate) {
        if (sourceDate == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN3);
        String sourceDayTime = sdf.format(sourceDate);
        String sourceTime = sourceDayTime.substring(11);

        Date currentDate = new Date();
        long second = (currentDate.getTime() - sourceDate.getTime()) / 1000;

        if (second < 3600) {
            return second / 60 + "分钟前";
        }
        if (second >= 3600 && second < 36000) {
            return second / 3600 + "小时前";
        }
        if (second >= 36000) {
            // 如果是今天
            if (isComparTodayWithDays(sourceDate, 0)) {
                return "今天 " + sourceTime;
            }

            //如果是昨天
            if (isComparTodayWithDays(sourceDate, -1)) {
                return "昨天 " + sourceTime;
            }
            //如果是前天
            if (isComparTodayWithDays(sourceDate, -2)) {
                return "前天 " + sourceTime;
            }
        }

        return sourceDayTime;
    }

    /**
     * 是否是N天，相比于今天
     *
     * @param sourceDate
     * @param days
     * @return
     */
    public static boolean isComparTodayWithDays(Date sourceDate, int days) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - days);
        Date today = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN2);

        return sdf.format(today).equals(
                sdf.format(sourceDate));
    }

    /**
     * 获取传入日期当月的天数
     * @param date
     * @return
     */
    public static int getDaysByYearMonth(Date date) {
        String strDate = DateUtil.formatDate(date);
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));

        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxDate = calendar.get(Calendar.DATE);
        return maxDate;
    }
        /**
         * 获取传入日期当月的固定日
         * @param date   传入日期
         * @param number 固定日
         * @return yyyyMMdd
         */
    public static String getFixedDay(Date date, int number) {
        int monthDays = getDaysByYearMonth(date);
        if(monthDays < number){
            return null;
        }
        String strDate = DateUtil.formatDate(date);
        int year = Integer.parseInt(strDate.substring(0, 4));
        int month = Integer.parseInt(strDate.substring(4, 6));

        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, number);
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calendar.getTime());

    }

    /**
     * 获取两个日期间所有月份（含日期所在月份）
     * @param startDate 开始日期
     * @param endDate 戒指日期
     * @return
     */
    public static List<Date> getMonthsBetweenTwoDate(Date startDate, Date endDate){
        Calendar start = Calendar.getInstance(Locale.CHINESE);
        Calendar end = Calendar.getInstance(Locale.CHINESE);

        start.setTime(startDate);
        start.set(Calendar.DATE, 1);

        end.setTime(endDate);
        end.set(Calendar.DATE, 2);

        List<Date> result = new ArrayList();

        Calendar curr = start;
        while (curr.before(end)) {
            result.add(curr.getTime());
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }
    public static void main(String[] args) throws Exception {
//        String a = "20151019000000";
//        String b = "20151123000000";
//        String c = "20151123100000";
//
//        Date d1 = DateUtil.parseDateTime(b);
//        Date d2 = DateUtil.parseDateTime(c);
//        double x = DateUtil.differHours(d2, d1);
//        long y = DateUtil.differDays2(c, a);
//
//
//        System.out.println("X:" +x + ";y:" + y);

//        BigDecimal a=BigDecimal.valueOf(1.1);
//        BigDecimal b=BigDecimal.valueOf(1.000);
//        System.out.println(a.compareTo(b));

//        System.out.println(Long.parseLong("0"));

//        System.out.println(getDateAfterNumMonth("2017-07-15",-2));

//        System.out.println(dateCompareNow("20170715"));

//        System.out.println(getDateAfterNumMonth("20170331",-1));

//        System.out.println(DateUtil.dateThanNow("20170727"));
//        System.out.println(getDaysDate("20170727", "4"));

        //System.out.println(getDateDistanceNow(3));

        //System.out.println(DateUtil.getDaysDate2("2017-08-25", 1));
//        System.out.println(getDateAfterNumMonth("20170131",1));

        System.out.println(getDateDistanceInputDate(parseDate("20170228"),3));
    }
}
