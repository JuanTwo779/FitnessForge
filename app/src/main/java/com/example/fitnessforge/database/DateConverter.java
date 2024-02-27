package com.example.fitnessforge.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {


    //converts Long dates back to Date object
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    //converts Date objects to long before storing in local DB
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
