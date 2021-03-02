package com.wczx.api.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 */
@Slf4j
public class TimeUtil {

    public static final String DATE_FORMAT_TIME_STAMP = "yyyy-MM-dd HH:mm:ss.SS";

    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_10 = "yyyy-MM-dd";

    public static final String DATE_FORMAT_8 = "yyyyMMdd";

    public static final String TIME_FORMAT_8 = "HH:mm:ss";

    /**
     * 获取指定格式的字符串日期
     *
     * @param format 想要的格式
     */
    public static String getFormatTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * @return yyyyMMddHHmmss
     */
    public static String getTime14() {
        return getFormatTime(DATE_FORMAT_14);
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNormalTime() {
        return getFormatTime(DATE_FORMAT_NORMAL);
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String getTime10() {
        return getFormatTime(DATE_FORMAT_10);
    }

    /**
     * @return yyyyMMdd
     */
    public static String getTime8() {
        return getFormatTime(DATE_FORMAT_8);
    }

    /**
     * 将 日期字符串 转换成 java.util.Date 对象
     *
     * @param format 日期字符串的格式
     * @param date   日期字符串
     * @return 转换后的 java.util.Date 对象, 解析失败会返回 null
     */
    public static Date getFormatDate(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            log.error(String.format("时间工具类 - 格式化字符串为日期出现错误: format[%s], date[%s]", format, date), e);
        }
        return null;
    }

    /**
     * 将 日期字符串 转换成 时间戳
     *
     * @param format 日期字符串的格式
     * @param date   日期字符串
     * @return 转换后的 时间戳, 解析失败会返回 0
     */
    public static long getFormatTimestamp(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date).getTime();
        } catch (ParseException e) {
            log.error(String.format("时间工具类 - 格式化字符串为日期出现错误: format[%s], date[%s]", format, date), e);
        }
        return 0;
    }

    /**
     * 改变日期格式
     *
     * @param srcTime   原时间
     * @param srcFormat 原格式
     * @param format    新格式
     */
    public static String changeTimeFormat(String srcTime, String srcFormat, String format) {
        try {
            SimpleDateFormat srcSdf = new SimpleDateFormat(srcFormat);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(srcSdf.parse(srcTime));
        } catch (ParseException e) {
            log.error(String.format("时间工具类 - 改变日期格式出现错误: srcTime[%s], srcFormat[%s], format[%s]", srcTime, srcFormat, format), e);
        }
        return null;
    }

    /**
     * 获取指定的时间间隔后的时间, 例:
     * <pre>
     *     interval=1, field=Calendar.SECOND 指定时间 time 后一秒的时间
     *     interval=-1, field=Calendar.HOUR 指定时间 time 前一小时的时间
     * </pre>
     *
     * @param time     初始时间
     * @param format   初始时间的格式
     * @param interval 时间间隔
     * @param field    增加的时间类型, 使用 Calendar 类里的 Calendar.SECOND 等常量
     */
    public static String timeInterval(String time, String format, int interval, int field) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(sdf.parse(time));
            calendar.add(field, interval);

            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            log.error(String.format("时间工具类 - 获取指定的时间间隔出现错误: time[%s], format[%s], interval[%d], timeUnit[%d]", time, format, interval, field), e);
        }
        return null;
    }

    /**
     * 判断 srcTime 是否大于 newTime
     *
     * @param srcTime   原时间
     * @param srcFormat 原时间格式
     * @param newTime   新时间
     * @param newFormat 新时间格式
     * @return srcTime 在 newTime 后面时 返回 true
     */
    public static boolean compare(String srcTime, String srcFormat, String newTime, String newFormat) {
        Date srcDate = getFormatDate(srcFormat, srcTime);
        Date newDate = getFormatDate(newFormat, newTime);

        return compare(srcDate, newDate);
    }

    /**
     * 判断 srcDate 是否大于 newDate
     *
     * @param srcDate 原时间
     * @param newDate 新时间
     * @return srcDate 在 newDate 后面时 返回 true
     */
    public static boolean compare(Date srcDate, Date newDate) {
        Calendar srcCal = Calendar.getInstance();
        Calendar newCal = Calendar.getInstance();

        srcCal.setTime(srcDate);
        newCal.setTime(newDate);

        return srcCal.compareTo(newCal) > 0;
    }

    /**
     * 获取指定日期是星期几
     *
     * @param date 指定日期
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获取两时间差（天）
     */
    public static long getTimeDifference(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_8);
            Date fDate = sdf.parse(startTime);
            Date oDate = sdf.parse(endTime);
            return (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
        } catch (Exception e) {
            log.error("时间工具类 - 获取两时间差错误: 开始时间：" + startTime + " 结束时间：" + endTime, e);
        }
        return 0;
    }

    /**
     * 根据业务获取时间段
     *
     * @param startDate 上班时间
     * @param endDate   下班时间
     * @return
     */
    public static String[] getCurrentTimeQuantum(String startDate, String endDate) {
        String[] time = new String[2];
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_8);
            String signDate = new SimpleDateFormat("yyyy-MM-dd 12:00:00").format(date);//已中午为分割线
            long signTimestamp = new SimpleDateFormat(DATE_FORMAT_NORMAL).parse(signDate).getTime();

//            if(Integer.valueOf(startDate.replaceAll(":","").trim()) < Integer.valueOf(endDate.replaceAll(":","").trim())){
//                time[0] = sdf.format(date)+"000000";
//                time[1] = sdf.format(date)+"235959";
//            }else{

            Calendar c = Calendar.getInstance();
            if (System.currentTimeMillis() > signTimestamp) {
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                time[0] = sdf.format(date) + "120000";
                time[1] = sdf.format(c.getTime()) + "120000";
            } else {
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -1);
                time[0] = sdf.format(c.getTime()) + "120000";
                time[1] = sdf.format(date) + "120000";
            }
//            }
        } catch (Exception e) {
            log.error("根据业务获取时间段出现错误!", e);
        }
        return time;
    }

    /**
     * 根据业务获取时间段
     *
     * @return
     */
    public static String[] getCurrentTimeQuantum() {
        String[] time = new String[2];
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_8);
            String signDate = new SimpleDateFormat("yyyy-MM-dd 12:00:00").format(date);//已中午为分割线
            long signTimestamp = new SimpleDateFormat(DATE_FORMAT_NORMAL).parse(signDate).getTime();
            Calendar c = Calendar.getInstance();
            if (System.currentTimeMillis() > signTimestamp) {
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                time[0] = sdf.format(date) + "120000";
                time[1] = sdf.format(c.getTime()) + "120000";
            } else {
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -1);
                time[0] = sdf.format(c.getTime()) + "120000";
                time[1] = sdf.format(date) + "120000";
            }
        } catch (Exception e) {
            log.error("根据业务获取时间段出现错误!", e);
        }
        return time;
    }

    /**
     * 获取当前计算后的日期
     *
     * @param day 天数
     */
    public static Timestamp byDayGetCurrentDate(Date date, int day) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);

        return new Timestamp(c.getTime().getTime());
    }

    /**
     * 计算时间
     *
     * @return
     */
    public static String calculateDayTime(int i) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -i);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyyMMdd").format(time);
    }

    /**
     * 获取月份最初日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_8);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        } catch (Exception e) {
            log.error("根据业务获取时间段出现错误!", e);

        }
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_8);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        } catch (Exception e) {
            log.error("根据业务获取时间段出现错误!", e);

        }
        return dateFormat.format(calendar.getTime());
    }

    public static List<String> findDates(String dStart, String dEnd, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        List<String> dateList = null;
        try {
            Calendar cStart = Calendar.getInstance();
            cStart.setTime(sdf.parse(dStart));
            dateList = new ArrayList<String>();
            //别忘了，把起始日期加上
            dateList.add(dStart);
            // 此日期是否在指定日期之后
            while (sdf.parse(dEnd).after(cStart.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                cStart.add(Calendar.DAY_OF_MONTH, 1);
                dateList.add(sdf.format(cStart.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }


    /**
     * 日期类型转字符串
     *
     * @param format 格式化方式
     * @param date   日期
     * @return
     */
    public static String getFormatDateStr(String format, Date date) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            log.error(String.format("时间工具类 - 格式化日期为日字符串出现错误: format[%s], date[%s]", format, date), e);
        }
        return null;
    }

    /**
     * 根据年月获取该月所有日期，未来时间不获取并且加入跨天
     *
     * @param yearParam
     * @param monthParam
     * @return
     */
    public static List<String> findDatesByMonth(int yearParam, int monthParam) {
        monthParam = monthParam - 1;
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(yearParam, monthParam, 1);
        int year = aCalendar.get(Calendar.YEAR);
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        String date1 = getFormatDateStr("yyyy-MM-dd HH:mm:ss", new Date());
        String date2 = getFormatDateStr("yyyy-MM-dd 12:00:00", new Date());
        String date = date1.compareTo(date2) < 0 ? new SimpleDateFormat("yyyy-MM-dd").format(TimeUtil.byDayGetCurrentDate(new Date(), -1).getTime()) : new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (month < 10 && i < 10) {
                aDate = String.valueOf(year) + "-0" + month + "-0" + i;
            }
            if (month < 10 && i >= 10) {
                aDate = String.valueOf(year) + "-0" + month + "-" + i;
            }
            if (month >= 10 && i < 10) {
                aDate = String.valueOf(year) + "-" + month + "-0" + i;
            }
            if (month >= 10 && i >= 10) {
                aDate = String.valueOf(year) + "-" + month + "-" + i;
            }
            if (aDate.compareTo(date) > 0) {
                break;
            }
            list.add(aDate);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(getMaxMonthDate("20190624"));
        System.out.println(getMinMonthDate("20190624"));

    }

}
