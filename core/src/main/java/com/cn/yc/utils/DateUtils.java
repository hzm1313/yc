package com.cn.yc.utils;


import org.apache.http.util.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DT167 on 2018/3/20.
 */
public class DateUtils {
    public static Long getDateUnix(String dateStr) {
        Date date = new Date(dateStr);
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Long.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0L;
        }
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

}
