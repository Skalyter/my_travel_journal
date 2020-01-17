package com.skalyter.mytraveljournal.database;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class DateConverter {

    @TypeConverter
    public static Calendar toCalendar(Long timestamp){
        Calendar calendar = Calendar.getInstance();
        if(timestamp!=null){
            calendar.setTimeInMillis(timestamp);
        }
        return calendar;
    }

    @TypeConverter
    public static Long toTimestamp(Calendar calendar){
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
