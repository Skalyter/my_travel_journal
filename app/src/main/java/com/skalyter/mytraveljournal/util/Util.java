package com.skalyter.mytraveljournal.util;

import java.util.Calendar;

public class Util {

    public static Calendar getCalendarFromString(String string) {
        String[] values = string.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(values[2]),
                Integer.parseInt(values[1]), Integer.parseInt(values[0]));
        return calendar;
    }

    public static String getStringFromCalendar(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }
}
