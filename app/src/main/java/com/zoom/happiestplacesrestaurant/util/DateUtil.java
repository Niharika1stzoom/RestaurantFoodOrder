package com.zoom.happiestplacesrestaurant.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getDisplayReviewDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd LLL");
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }

    public static String getReviewOrderDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd LLL HH:mm");
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }
    public static String getTimeDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }

    public static String getTodayDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }
}
