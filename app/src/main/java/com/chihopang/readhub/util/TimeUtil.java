package com.chihopang.readhub.util;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
  public static Long getTimeStamp(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return format.parse(date).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public static String countDown(String dateString) {
    if (TextUtils.isEmpty(dateString)) return "";
    Date date;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
      date = format.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
    String interval;
    interval = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).format(date);

    if (TextUtils.isEmpty(dateString)) return interval;
    long currentTimeMillis = System.currentTimeMillis(), dateTimeMillis = date.getTime();
    Calendar currCalendar = Calendar.getInstance();//当前日期零点
    currCalendar.setTimeInMillis(currentTimeMillis);
    currCalendar.set(Calendar.HOUR_OF_DAY, 0);
    currCalendar.set(Calendar.MINUTE, 0);
    currCalendar.set(Calendar.SECOND, 0);
    currCalendar.set(Calendar.MILLISECOND, 0);
    long countdownTime = currentTimeMillis - dateTimeMillis;
    if (countdownTime < 0) return interval;//传入时间早于当前时间
    if (countdownTime / 1000 < 10) {
      interval = "刚刚";
    } else if (countdownTime / 3600000 >= 24) {
      Calendar dateCalendar = Calendar.getInstance();//传入日期零点
      dateCalendar.setTimeInMillis(dateTimeMillis);
      dateCalendar.set(Calendar.HOUR_OF_DAY, 0);
      dateCalendar.set(Calendar.MINUTE, 0);
      dateCalendar.set(Calendar.SECOND, 0);
      dateCalendar.set(Calendar.MILLISECOND, 0);
      int day =
          (int) ((currCalendar.getTimeInMillis() - dateCalendar.getTimeInMillis()) / 86400000);
      if (day == 1) {
        interval = "昨天";
      } else if (day > 1) {
        interval = day + "天前";
      }
    } else if (countdownTime / 3600000 < 24 && countdownTime / 3600000 > 0) {
      int hour = (int) (countdownTime / 3600000);
      interval = hour + "小时前";
    } else if (countdownTime / 60000 < 60 && countdownTime / 60000 > 0) {
      int minute = (int) (countdownTime / 60000);
      interval = minute + "分钟前";
    } else if (countdownTime / 1000 < 60 && countdownTime / 1000 > 0) {
      int second = (int) (countdownTime / 1000);
      interval = second + "秒前";
    }
    return interval;
  }

  public static String getFormatDate(String dateString) {
    if (TextUtils.isEmpty(dateString)) return "";
    Date date;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
      date = format.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
    return new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA).format(date);
  }
}
