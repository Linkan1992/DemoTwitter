package com.demotwitter.linkan.demotwitter.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class UtilFunction {

  private UtilFunction() {
    // This utility class is not publicly instantiable
  }

  public static String getVideoTime(Long videoTimeInMills) {

    String secondDelimeter = "";
    String minuteDelimeter = "";

    if (videoTimeInMills != null && videoTimeInMills > 0) {

      try {

        if (videoTimeInMills / 1000 < 60) {
          return "0" + ":" + String.valueOf(videoTimeInMills / 1000);
        } else if (videoTimeInMills / 1000 >= 60 && videoTimeInMills / 1000 < 60 * 60) {
          long minutes = (videoTimeInMills / 1000) / 60;
          long seconds = (videoTimeInMills / 1000) % 60;
          return String.valueOf(minutes) + ":" + String.valueOf(seconds);
        } else {
          long hours = (videoTimeInMills / 1000) / (60 * 60);
          long minutes = (videoTimeInMills / 1000 / 60) % (60);
          long seconds = (videoTimeInMills / 1000) % (60);

          if (seconds <= 9)
            secondDelimeter = "0";

          if (minutes <= 9)
            minuteDelimeter = "0";

          return String.valueOf(hours) + ":" + minuteDelimeter + String.valueOf(minutes) + ":" + secondDelimeter + String.valueOf(seconds);

        }
      } catch (IllegalArgumentException e) {
        return "";
      }
    }

    return "";

  }


  public static String getTweetCreatedAt(String stringDate) {

    DateTimeFormatter formatter = null;

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

      formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_TIME_FORMAT);
      LocalDateTime dateTime = LocalDateTime.parse(stringDate, formatter);

      LocalDateTime to = LocalDateTime.now();

      LocalDateTime fromTemp = LocalDateTime.from(dateTime);


      try {

        if (fromTemp.until(to, ChronoUnit.SECONDS) < 60) {
          return fromTemp.until(to, ChronoUnit.SECONDS) + "s";
        } else if (fromTemp.until(to, ChronoUnit.MINUTES) < 60) {
          return fromTemp.until(to, ChronoUnit.MINUTES) + "m";
        } else if (fromTemp.until(to, ChronoUnit.HOURS) < 24) {
          return fromTemp.until(to, ChronoUnit.HOURS) + "h";
        } else {
          return fromTemp.until(to, ChronoUnit.DAYS) + "d";
        }
      } catch (IllegalArgumentException e) {
        return "";
      }

    }

    return "";
  }


  public static String getTweetCreatedAt(String createdAt, DateTime now) {
    if (createdAt == null) {
      return "";
    }

    org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern(AppConstants.DATE_TIME_FORMAT);
    try {
      DateTime created = dtf.parseDateTime(createdAt);

      if (Seconds.secondsBetween(created, now).getSeconds() < 60) {
        return Seconds.secondsBetween(created, now).getSeconds() + "s";
      } else if (Minutes.minutesBetween(created, now).getMinutes() < 60) {
        return Minutes.minutesBetween(created, now).getMinutes() + "m";
      } else if (Hours.hoursBetween(created, now).getHours() < 24) {
        return Hours.hoursBetween(created, now).getHours() + "h";
      } else {
        return Days.daysBetween(created, now).getDays() + "d";
      }
    } catch (IllegalArgumentException e) {
      return "";
    }
  }

}
