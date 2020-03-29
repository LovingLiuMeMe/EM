package cn.lovingliu.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author：LovingLiu
 * @Description: 时间的工具类
 * @Date：Created in 2020-03-08
 */
public class DateUtil {
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;

    }
    public static int compare_date(Date DATE1, Date DATE2) {
        try {
            if (DATE1.getTime() > DATE2.getTime()) {
                System.out.println("DATE1 在DATE1前");
                return 1;
            } else if (DATE1.getTime() < DATE2.getTime()) {
                System.out.println("DATE1在DATE1后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;

    }

    public static Date getDaysLater(int day,Date date) {

        return new Date(date.getTime()+day*24*60*60*1000L);

    }
}
