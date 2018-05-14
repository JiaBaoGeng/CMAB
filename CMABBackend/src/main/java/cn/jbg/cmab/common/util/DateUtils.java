package cn.jbg.cmab.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jbg on 2018/3/27.
 */

public class DateUtils {
    private static Logger log = Logger.getLogger(DateUtils.class.getName());

    public volatile static Date CURRENT_TIME = null;

    // 系统默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // 系统默认日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 8位日期格式
    public static final String DATE_FORMAT_8 = "yyyyMMdd";
    // 14位日期时间格式
    public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

    public static void setCurrentTime(Date currentTime) {
        DateUtils.CURRENT_TIME = currentTime;
    }

    public static void reset() {
        DateUtils.CURRENT_TIME = null;
    }

    public static Date getCurrentTime() {
//		if (CURRENT_TIME != null) {
//			return CURRENT_TIME;
//		}
        return new Date();
    }

    public static Timestamp getTimeStamp(Date date) {
        if (date == null) {
            return null;
        }
        else {
            return new Timestamp(date.getTime());
        }
    }
    public static Timestamp getTimeStamp(String strdate) {
        if (strdate == null) {
            return null;
        }
        else {
            return new Timestamp(getFormated8Date(strdate).getTime());
        }

    }
    public static java.sql.Date getDate(Date date) {
        if (date == null) {
            return null;
        }
        else {
            return new java.sql.Date(date.getTime());
        }
    }

    /**
     * 将Date转换成统一的日期格式文本。
     *
     * @return
     */
    public static String getFormatedDate(Date date) {
        if (null == date)
            return "";

        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
        return dateFormator.format(new java.sql.Date(date.getTime()));
    }

    public static Date getFormatedDate(String text) {
        if (null == text)
            return null;

        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
            return dateFormator.parse(text);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while switch data format " + text, e);
        }

        return null;
    }
    public static Date getFormated8Date(String text) {
        if (null == text)
            return null;

        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT_8);
            return dateFormator.parse(text);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while switch data format " + text, e);
        }

        return null;
    }
    /**
     * 将Date转换成统一的日期时间格式文本。
     *
     * @return
     */
    public static String getFormatedDateTime(Date date) {
        if (null == date)
            return "";

        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormator.format(new java.sql.Date(date.getTime()));
    }

    public static Date getFormatedDateTime(String text) {
        if (null == text)
            return null;

        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(
                    DATE_TIME_FORMAT);
            return dateFormator.parse(text);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while switch datatime format " + text,
                    e);
        }

        return null;
    }

    /**
     * 将Date转换成统一的日期时间格式文本。
     *
     * @return
     */
    public static String getFormatedDateTime(Timestamp stamp) {
        if (null == stamp)
            return "";

        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormator.format(new java.sql.Date(stamp.getTime()));
    }

    /**
     * 将Date转换成统一的日期格式文本。 格式：yyyy-mm-dd
     *
     * @return
     * @author suns
     */
    public static String getFormatedDate(Timestamp stamp) {
        if (null == stamp)
            return "";

        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
        return dateFormator.format(new java.sql.Date(stamp.getTime()));
    }

    public static String getFormatedDateTime14(Date date) {
        if (null == date)
            return "";

        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT_14);
        return dateFormator.format(new java.sql.Date(date.getTime()));
    }

    public static Date getFormatedDateTime14(String text) {
        if (null == text)
            return null;

        try {
            SimpleDateFormat dateFormator = new SimpleDateFormat(
                    DATE_TIME_FORMAT_14);
            return dateFormator.parse(text);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while switch datatime format " + text,
                    e);
        }

        return null;
    }

    /*public static void main(String[] args) {
        String str="20140405";
        System.out.println(new java.sql.Date(getFormated8Date(str).getTime()));
    }*/
}

