package com.skalyter.mytraveljournal.database;

import android.provider.BaseColumns;

public interface DatabaseScheme extends BaseColumns {
    String TABLE_USERS = "users";
    String TABLE_TRIPS = "trips";

    String COLUMN_USER_FIRST_NAME = "user_first_name";
    String COLUMN_USER_LAST_NAME = "user_last_name";
    String COLUMN_USER_EMAIL = "user_email";
    String COLUMN_USER_PASSWORD = "user_password";

    String COLUMN_TRIP_NAME = "trip_name";
    String COLUMN_TRIP_DESTINATION = "trip_destination";
    String COLUMN_TRIP_START = "trip_start";
    String COLUMN_TRIP_END = "trip_end";
    String COLUMN_TRIP_PRICE = "trip_price";
    String COLUMN_TRIP_RATING = "trip_rating";
    String COLUMN_TRIP_TYPE = "trip_type";
    String COLUMN_TRIP_FAVORITE = "trip_is_favorite";
    String COLUMN_TRIP_IMAGE_URI = "trip_image_uri";

    String CREATE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + _ID + " INTEGER PRIMARY KEY, "
            + COLUMN_USER_FIRST_NAME + " TEXT, "
            + COLUMN_USER_LAST_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT)";

    String CREATE_TRIPS = "CREATE TABLE " + TABLE_TRIPS + " ("
            + _ID + " INTEGER PRIMARY KEY, "
            + COLUMN_TRIP_NAME  + " TEXT, "
            + COLUMN_TRIP_DESTINATION + " TEXT, "
            + COLUMN_TRIP_START + " INTEGER, "
            + COLUMN_TRIP_END + " INTEGER, "
            + COLUMN_TRIP_PRICE + " INTEGER, "
            + COLUMN_TRIP_RATING + " FLOAT, "
            + COLUMN_TRIP_TYPE + " INTEGER, "
            + COLUMN_TRIP_FAVORITE + " INTEGER, "
            + COLUMN_TRIP_IMAGE_URI + " TEXT)";

    String SELECT_TRIPS = "SELECT * FROM " + TABLE_TRIPS + " ORDER BY "
            + COLUMN_TRIP_START;
    String ALTER_V2 = "ALTER TABLE " + TABLE_TRIPS + " ADD "
            + COLUMN_TRIP_IMAGE_URI + " TEXT";
}
