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
                + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public static boolean isBefore(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) return false;
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
                if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                } else if (calendar1.get(Calendar.DAY_OF_MONTH) > calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return false;
                }
                return true;
            } else if (calendar1.get(Calendar.MONTH) > calendar2.get(Calendar.MONTH)) {
                return false;
            }
            return true;
        } else if (calendar1.get(Calendar.YEAR) > calendar2.get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }

    public static boolean isAfter(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) return false;
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
                if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                } else if (calendar1.get(Calendar.DAY_OF_MONTH) < calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return false;
                }
                return true;
            } else if (calendar1.get(Calendar.MONTH) < calendar2.get(Calendar.MONTH)) {
                return false;
            }
            return true;
        } else if (calendar1.get(Calendar.YEAR) < calendar2.get(Calendar.YEAR)) {
            return false;
        }
        return true;
    }
}
