package com.soyoung.component_base.util;

import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间相距
 */
public class DateDistance {

  private static final long ONE_MINUTE = 60000L;
  private static final long ONE_HOUR = 3600000L;
  private static final long ONE_DAY = 86400000L;
  private static final long ONE_WEEK = 604800000L;
  private static final String RIGHT_NOW = "刚刚";
  private static final String ONE_SECOND_AGO = "秒前";
  private static final String ONE_MINUTE_AGO = "分钟前";
  private static final String ONE_HOUR_AGO = "小时前";
  private static final String ONE_DAY_AGO = "天前";
  private static final String ONE_MONTH_AGO = "月前";
  private static final String ONE_YEAR_AGO = "年前";

  /**
   * ( SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");Date date = format.parse("2013-11-11 18:35:35");)
   * 传入date
   * 获取几秒前/几分钟前/.....
   */
  public static String getTimeToStrFormat(String time) {

    if (TextUtils.isEmpty(time)) {
      return "";
    }
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
      Date date = format.parse(time);

      long delta = new Date().getTime() - date.getTime();
      if (delta < 1L * ONE_MINUTE) {
        long seconds = toSeconds(delta);
        return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
      }
      if (delta < 45L * ONE_MINUTE) {
        long minutes = toMinutes(delta);
        return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
      }
      if (delta < 24L * ONE_HOUR) {
        long hours = toHours(delta);
        return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
      }
      if (delta < 48L * ONE_HOUR) {
        return "昨天";
      }
      if (delta < 30L * ONE_DAY) {
        long days = toDays(delta);
        return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
      }
      if (delta < 12L * 4L * ONE_WEEK) {
        long months = toMonths(delta);
        return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
      } else {
        long years = toYears(delta);
        return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
      }
    } catch (Exception e) {
      return time;
    }
  }

  /**
   * ( SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");Date date = format.parse("2013-11-11 18:35:35");)
   * 传入date
   * 获取几秒前/几分钟前/.....
   */
  public static String getTimeToStrFormatForPost(String time) {

    if (TextUtils.isEmpty(time)) {
      return "";
    }
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
      Date date = format.parse(time);
      long delta = new Date().getTime() - date.getTime();
      if (delta < 1L * ONE_MINUTE) {
        return RIGHT_NOW;
      }
      if (delta < 45L * ONE_MINUTE) {
        long minutes = toMinutes(delta);
        if (minutes > 5) {
          return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        } else {
          return RIGHT_NOW;
        }
      }
      if (delta < 24L * ONE_HOUR) {
        long hours = toHours(delta);
        return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
      }
      if (delta < 48L * ONE_HOUR) {
        return "昨天";
      }
      if (delta < 30L * ONE_DAY) {
        String timeStr = getMonthAndDay(date);
        if (TextUtils.isEmpty(timeStr)) {
          long days = toDays(delta);
          return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        } else {
          return timeStr;
        }
      }
      if (delta < 12L * 4L * ONE_WEEK) {

        String timeStr = getMonthAndDay(date);

        if (TextUtils.isEmpty(timeStr)) {
          long months = toMonths(delta);
          return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
          return timeStr;
        }
      } else {
        String timeStr = getMonthAndDay(date);
        if (TextUtils.isEmpty(timeStr)) {
          long years = toYears(delta);
          return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        } else {
          return timeStr;
        }
      }
    } catch (Exception e) {
      return time;
    }
  }

  /**
   * new date str
   */
  private static String getMonthAndDay(Date date) {
    //获取具体的年份 月份 和 日期
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    calendar.setTime(date);
    StringBuilder stringBuilder = new StringBuilder();
    try {
      int year = calendar.get(Calendar.YEAR);
      int mouth = calendar.get(Calendar.MONTH) + 1;
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      if (currentYear > year) {
        stringBuilder.append(year);
        stringBuilder.append("年");
      }
      stringBuilder.append(mouth);
      stringBuilder.append("月");
      stringBuilder.append(day);
      stringBuilder.append("日");
    } catch (Exception ex) {
      return "";
    }
    return TextUtils.isEmpty(stringBuilder) ? "" : stringBuilder.toString();
  }

  private static long toSeconds(long date) {
    return date / 1000L;
  }

  private static long toMinutes(long date) {
    return toSeconds(date) / 60L;
  }

  private static long toHours(long date) {
    return toMinutes(date) / 60L;
  }

  private static long toDays(long date) {
    return toHours(date) / 24L;
  }

  private static long toMonths(long date) {
    return toDays(date) / 30L;
  }

  private static long toYears(long date) {
    return toMonths(date) / 365L;
  }

  /**
   * 两个时间之间相差距离多少天
   *
   * @return 相差天数
   */
  public static long getDistanceDays(String str1, String str2) throws Exception {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date one;
    Date two;
    long days = 0;
    try {
      one = df.parse(str1);
      two = df.parse(str2);
      long time1 = one.getTime();
      long time2 = two.getTime();
      long diff;
      if (time1 < time2) {
        diff = time2 - time1;
      } else {
        diff = time1 - time2;
      }
      days = diff / (1000 * 60 * 60 * 24);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return days;
  }

  /**
   * 比较两个日期大小
   *
   * @return (arg1 > arg2) ? 1 : -1
   */
  public static int compareDataTime(String date1, String date2) {
    int result = 0;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date one;
    Date two;
    long days = 0;
    try {
      one = df.parse(date1);
      two = df.parse(date2);
      long time1 = one.getTime();
      long time2 = two.getTime();
      if (time1 < time2) {
        result = -1;
      } else {
        result = 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 两个时间相差距离多少天多少小时多少分多少秒
   *
   * @param str1 时间参数 1 格式：1990-01-01 12:00:00
   * @param str2 时间参数 2 格式：2009-01-01 12:00:00
   * @return long[] 返回值为：{天, 时, 分, 秒}
   */
  public static long[] getDistanceTimes(String str1, String str2) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date one;
    Date two;
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    try {
      one = df.parse(str1);
      two = df.parse(str2);
      long time1 = one.getTime();
      long time2 = two.getTime();
      long diff;
      if (time1 < time2) {
        diff = time2 - time1;
      } else {
        diff = time1 - time2;
      }
      day = diff / (24 * 60 * 60 * 1000);
      hour = (diff / (60 * 60 * 1000) - day * 24);
      min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
      sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    long[] times = { day, hour, min, sec };
    return times;
  }

  /**
   * 两个时间相差距离多少天多少小时多少分多少秒
   *
   * @param str1 时间参数 1 格式：1990-01-01 12:00:00
   * @param str2 时间参数 2 格式：2009-01-01 12:00:00
   * @return String 返回值为：xx天xx小时xx分xx秒
   */
  public static String getDistanceTime(String str1, String str2) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date one;
    Date two;
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    try {
      one = df.parse(str1);
      two = df.parse(str2);
      long time1 = one.getTime();
      long time2 = two.getTime();
      long diff;
      if (time1 < time2) {
        diff = time2 - time1;
      } else {
        diff = time1 - time2;
      }
      day = diff / (24 * 60 * 60 * 1000);
      hour = (diff / (60 * 60 * 1000) - day * 24);
      min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
      sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    } catch (ParseException e) {
      e.printStackTrace();
      return str1;
    }
    if (day > 0) {
      if (day >= 30) {
        return day / 30 + "个月前";
      } else if (day >= 365) {
        return day / 365 + "年前";
      }
      return day + "天前";
    } else if (hour > 0) {
      return hour + "个小时前";
    } else if (min > 0) {
      return min + "分钟前";
    } else if (sec > 0) {
      return sec + "秒前";
    } else {
      return str2;
    }
    //        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
  }

  public static String getStringTime(long time) {
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    day = time / (24 * 60 * 60 * 1000);
    hour = (time / (60 * 60 * 1000) - day * 24);
    min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
    sec = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    return day + "天" + hour + "时" + min + "分" + sec + "秒";
  }

  public static long[] getStringTimes(long time) {
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    day = time / (24 * 60 * 60 * 1000);
    hour = (time / (60 * 60 * 1000) - day * 24);
    min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
    sec = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    long[] times = { day, hour, min, sec };
    return times;
  }

  public static String getStringTime4s(long time) {
    long day = 0;
    long hour = 0;
    long min = 0;
    long sec = 0;
    day = time / (24 * 60 * 60);
    hour = (time / (60 * 60) - day * 24);
    min = ((time / (60)) - day * 24 * 60 - hour * 60);
    sec = (time - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    return day + "天" + hour + "时" + min + "分" + sec + "秒";
  }

  public static String getNextDay(String day, int i) {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      calendar.setTime(format.parse(day));
      calendar.add(Calendar.DAY_OF_YEAR, i);
      return format.format(calendar.getTime());
    } catch (Exception e) {
      e.printStackTrace();
      return day;
    }
  }
}