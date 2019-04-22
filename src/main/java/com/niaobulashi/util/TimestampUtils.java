package com.niaobulashi.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeStamp工具类，提供TimeStamp与String、Date的转换
 *
 * @author chenssy
 * @date 2016-09-24
 * @since 1.0.0
 */
public class TimestampUtils {

    /**
     * String转换为TimeStamp
     * @param value
     *              待转换的String，格式必须为 yyyy-mm-dd hh:mm:ss[.f...] 这样的格式，中括号表示可选，否则报错
     * @return java.sql.Timestamp
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Timestamp string2Timestamp(String value){
        if(value == null && !"".equals(value.trim())){
            return null;
        }
    	Timestamp ts = new Timestamp(System.currentTimeMillis());
    	ts = Timestamp.valueOf(value);
    	return ts;
    }

    /**
     * 将Timestamp 转换为String类型，format为null则使用默认格式 yyyy-MM-dd HH:mm:ss
     *
     * @param value
     *              待转换的Timestamp
     * @param format
     *              String的格式
     * @return java.lang.String
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static String timestamp2String(Timestamp value,String format){
    	if(null == value){
    		return "";
    	}
    	SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
    	
    	return sdf.format(value);
    }

    /**
     * Date转换为Timestamp
     *
     * @param date
     *              待转换的Date
     * @return java.sql.Timestamp
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Timestamp date2Timestamp(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * Timestamp转换为Date
     *
     * @param time
     *              待转换的Timestamp
     * @return java.util.Date
     *
     * @author chenssy
     * @date 2016-09-24
     * @since v1.0.0
     */
    public static Date timestamp2Date(Timestamp time){
        return time == null ? null : time;
    }

    /**
     * date to string
     * format yyyy-MM-dd
     *
     * @return String
     */

    public static String dateToString(Date d) {
        String dString = "";
        SimpleDateFormat f = new SimpleDateFormat(DateFormatUtils.DATE_FORMAT1);
        if (d != null) {
            if (StringUtils.isNotEmpty(d.toString())) {
                dString = f.format(d);
            }
        }
        return dString;
    }

    /**
     * string to date
     *
     * @return
     */
    public static Date stringToDate(String str, String pattern) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            SimpleDateFormat f = new SimpleDateFormat(pattern);
            Date d = f.parse(str);
            // System.out.print(d);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(string2Timestamp("2019-04-12 15:55:30"));
        System.out.println(dateToString(stringToDate("2019-04-12 15:55:30", "yyyy-MM-dd")));
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1); //天数减1
        Date lastDay = ca.getTime(); //结果
        Date balanceDay = stringToDate("2019-04-12 15:55:30", "yyyy-MM-dd");
        System.out.println("lastDay:" + lastDay);
        System.out.println("balanceDay:" + balanceDay);
        int result=balanceDay.compareTo(lastDay);
        if (result >= 0) {
            System.out.println("balanceDay >= lastDay");
        } else {
            System.out.println("balanceDay < lastDay");
        }
    }
}
