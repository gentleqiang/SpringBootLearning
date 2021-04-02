package com.gentleman.server.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author 一粒尘埃
 * @date 2021/1/4/22:30
 */
public class TimeUtil {

    private static final String YYYY_MM_DD = "yyyyMMdd";

    private static final String HH_MM_SS = "hhmmss";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyyMMddhhmmss";

    public static String toDate(Date date){
        return DateFormatUtils.format(date,YYYY_MM_DD);
    }

    public static String toTime(Date date){
        return DateFormatUtils.format(date,HH_MM_SS);
    }

    public static String toDateTime(Date date){
        return DateFormatUtils.format(date,YYYY_MM_DD_HH_MM_SS);
    }

}
