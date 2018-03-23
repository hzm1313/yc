package com.cn.yc.utils;


import java.util.Date;

/**
 * Created by DT167 on 2018/3/20.
 */
public class DateUtils {
    public static Long getDateUnix(String dateStr){
        Date date = new Date(dateStr);
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Long.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0l;
        }
    }
}
