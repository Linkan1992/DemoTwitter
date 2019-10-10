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

    public static String getLikeCount(Integer favoriteCount) {
        String like, likeInK;
        if (favoriteCount >= 1000) {
            likeInK = String.valueOf(favoriteCount / 1000);
            like = String.valueOf(favoriteCount / 100);

            if ((favoriteCount / 100) % 100 == 0)
                return likeInK + "." + like + "K";
            else
                return likeInK + "," + like;
        } else
            return String.valueOf(favoriteCount);

    }


    public static String getReTweetCount(Integer reTweetCount) {
        String reTweet, reTweetInK;
        if (reTweetCount >= 1000) {
            reTweetInK = String.valueOf(reTweetCount / 1000);
            reTweet = String.valueOf(reTweetCount / 100);

            return reTweetInK + "," + reTweet;
       }
/*      else if (reTweetCount > 100) {
            reTweetInK = String.valueOf(reTweetCount / 100);
            reTweet = String.valueOf(reTweetCount / 10);

            return reTweetInK + "," + reTweet;

        } else*/
            return String.valueOf(reTweetCount);

    }

}
