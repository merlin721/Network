package com.soyoung.component_base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间格式化工具类
 */

public class TimeFormatUtils {

  public static String simpleTime(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = format.format(new Date());
    return nowTime;
  }

  public static String getYMDTime(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String nowTime = null;
    try {
      nowTime = format.format(format.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return nowTime;
  }

  public static String transTime(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = format.format(new Date());
    return String.valueOf(DateDistance.getDistanceTime(date, nowTime));
  }

  public static String getCurrentTime() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = format.format(new Date());
    return nowTime;
  }

  public static String getCustomTime(String type) {
    SimpleDateFormat format = new SimpleDateFormat(type);
    return format.format(new Date());
  }

  /**
   * 格式化时间
   */
  public static String getTabUserZhiboTime(int seconds) {
    String format = "mm,ss";
    if (seconds >= 60 * 60) {
      format = "HH,mm,ss";
    }
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    String timeStr = formatter.format(seconds * 1000);
    return timeStr;
  }

  /**
   * 格式化时间
   */
  public static String secondToHMS(int seconds) {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    String timeStr = formatter.format(seconds * 1000);
    return timeStr;
  }

  public static String generateTime(long position) {
    if (position == 0) {
      return "00:00";
    } else {
      int totalSeconds = (int) (position / 1000);

      int seconds = totalSeconds % 60;
      int minutes = (totalSeconds / 60) % 60;
      int hours = totalSeconds / 3600;

      if (hours > 0) {
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
            seconds).toString();
      } else {
        return String.format(Locale.US, "%02d:%02d", minutes, seconds)
            .toString();
      }
    }
  }

  /**
   * 格式化时间为 MM—DD 格式
   */
  public static String FormatDateForMMDD(String str1) {
    try {
      //时间戳转化为Sting或Date
      SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat s2 = new SimpleDateFormat("MM-dd");
      Date tempDate = null;
      String outTime = null;
      tempDate = s1.parse(str1);
      outTime = s2.format(tempDate);
      return outTime;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // yyyy-MM-dd to  yyyy年MM月dd日
  public static String day2Chinese(String day) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatNew = new SimpleDateFormat("yyyy年MM月dd日");
    try {
      Date date = format.parse(day);
      return formatNew.format(date);
    } catch (Exception e) {
      e.printStackTrace();
      return day;
    }
  }

  public static String day2ChineseNew(String day) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatNew = new SimpleDateFormat("yyyy/MM/dd");
    try {
      Date date = format.parse(day);
      return formatNew.format(date);
    } catch (Exception e) {
      e.printStackTrace();
      return day;
    }
  }

  public static String chinese2English(String day) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = format.parse(day);
      return formatNew.format(date);
    } catch (Exception e) {
      e.printStackTrace();
      return day;
    }
  }

  public static String getShowDay(String day) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat showFormat = new SimpleDateFormat("yyyy.MM.dd");
    try {
      Date date = format.parse(day);
      return showFormat.format(date);
    } catch (Exception e) {
      return day;
    }
  }

  public static String getYear(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatNew = new SimpleDateFormat("yyyy年");
    try {
      Date tempDate = format.parse(date);
      return formatNew.format(tempDate);
    } catch (Exception e) {
      e.printStackTrace();
      return date;
    }
  }

  public static String getMonthDay(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatNew = new SimpleDateFormat("MM月dd日");
    try {
      Date tempDate = format.parse(date);
      return formatNew.format(tempDate);
    } catch (Exception e) {
      e.printStackTrace();
      return date;
    }
  }

  // long转换为Date类型
  // currentTime要转换的long类型的时间
  // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
  public static Date longToDate(long currentTime, String formatType)
      throws ParseException {
    Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
    String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
    Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
    return date;
  }

  // string类型转换为date类型
  // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
  // HH时mm分ss秒，
  // strTime的时间格式必须要与formatType的时间格式相同
  public static Date stringToDate(String strTime, String formatType)
      throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(formatType);
    Date date = null;
    date = formatter.parse(strTime);
    return date;
  }

  public static String dateToString(Date data, String formatType) {
    return new SimpleDateFormat(formatType).format(data);
  }

  // date类型转换为long类型
  // date要转换的date类型的时间
  public static long dateToLong(Date date) {
    return date.getTime();
  }

  /**
   * "20150806"转化为毫秒
   */
  public static long getLong(String data) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = sdf.parse(data);
      return date.getTime();
    } catch (Exception e) {
      e.printStackTrace();
      return 0l;
    }
  }

  /**
   * "20150806"转化为毫秒
   */
  public static long getLongS(String data) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = sdf.parse(data);
      return date.getTime() / 1000l;
    } catch (Exception e) {
      e.printStackTrace();
      return 0l;
    }
  }

  public static String c2S(Calendar calendar) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr = sdf.format(calendar.getTime());
    return dateStr;
  }

  public static Calendar s2c(String str) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    Date date = null;
    try {
      date = sdf.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    calendar.setTime(date);
    return calendar;
  }
}
