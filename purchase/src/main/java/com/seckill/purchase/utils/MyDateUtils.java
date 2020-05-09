package com.seckill.purchase.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {
    /**
     * 将字符串转换成指定格式的日期
     *
     * @param str        日期字符串.
     * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @return
     */
    public static Date strToDate(final String str, String dateFormat) {
        if (str == null || str.trim().length() == 0) return null;
        try {
            if (dateFormat == null || dateFormat.length() == 0) dateFormat = "yyyy-MM-dd HH:mm:ss";
            DateFormat fmt = new SimpleDateFormat(dateFormat);
            return fmt.parse(str.trim());

        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 将日期转换成指定格式的字符串.
     *
     * @param date       日期
     * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
     * @since 2018/9/19 14:40
     **/
    public static String dateToStr(final Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        try {
            if (dateFormat == null || dateFormat.trim().length() == 0) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            DateFormat fmt = new SimpleDateFormat(dateFormat.trim());
            return fmt.format(date);
        } catch (Exception ex) {
            return "";
        }
    }
}
