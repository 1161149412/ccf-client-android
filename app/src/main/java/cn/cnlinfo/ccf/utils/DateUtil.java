package cn.cnlinfo.ccf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ouarea on 2015/12/18.
 */
public class DateUtil {

    public static Date getDate(String date, String format) {
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date, String format) {
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    public static String convert(String date, String sourceFormat, String destFormat) {
        return format(getDate(date, sourceFormat), destFormat);
    }

    //时间戳转化为StingDate
    public static String timestampToDate(long timestamp, String format) {
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Long time = timestamp;
        return simpleDateFormat.format(time);
    }


    //Date或者String转化为时间戳
    public static long dateToTimestamp(String time, String format) {
        if (null == format) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null ? date.getTime() : 0;
    }

    //时间戳转化为StingDate
    public static String timestampToDate(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Long time = timestamp;
        String date = format.format(time);
        return date;
    }

    //Date或者String转化为时间戳
    public static long dateToTimestamp(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateTime = date.getTime();
        return dateTime;
    }

    //截取时间按固定格式显示
    public static String formatTime(String time, String format) {
        if (time == null) {
            return "";
        }
        long timestamp = dateToTimestamp(time);
        Long aLong = timestamp;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        String date = simpleDateFormat.format(aLong);
        return date;
    }
}
