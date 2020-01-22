package com.skalyter.mytraveljournal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skalyter.mytraveljournal.model.Trip;
import com.skalyter.mytraveljournal.model.TripType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_DESTINATION;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_END;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_FAVORITE;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_IMAGE_URI;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_NAME;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_PRICE;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_RATING;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_START;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_TRIP_TYPE;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.SELECT_TRIPS;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.TABLE_TRIPS;

public class TripDao implements  ITripDao {

    Context context;

    public TripDao(Context context) {
        this.context = context;
    }

    @Override
    public List<Trip> getAllTripsChronologically() {
        List<Trip> trips = new ArrayList<>();
        OpenHelper openHelper = new OpenHelper(context);
        if(openHelper != null){
            SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(SELECT_TRIPS, null);
            try{
                if(cursor != null && cursor.getCount() > 0){
                    while (cursor.moveToNext()){
                        trips.add(cursorToEntity(cursor));
                    }
                }
            } finally {
                if(cursor != null){
                    cursor.close();
                }
                sqLiteDatabase.close();
            }
        }
        return trips;
    }

    @Override
    public long insertTrip(Trip trip) {
        OpenHelper openHelper = new OpenHelper(context);
        long id=-1;
        if(openHelper !=null){
            SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
            try{
                id = sqLiteDatabase.insert(TABLE_TRIPS,
                        null, entityToContentValues(trip));
            } finally {
                sqLiteDatabase.close();
            }
        }
        return id;
    }

    @Override
    public void updateTrip(Trip trip) {
        OpenHelper openHelper = new OpenHelper(context);
        if(openHelper != null){
            SQLiteDatabase sqLiteDatabase =openHelper.getWritableDatabase();
            try {
                sqLiteDatabase.update(TABLE_TRIPS, entityToContentValues(trip),
                        _ID + "=?",
                        new String[]{String.valueOf(trip.getId())});
            } finally {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public void deleteTrio(Trip trip) {
        OpenHelper openHelper = new OpenHelper(context);
        if(openHelper != null){
            SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
            try{
                sqLiteDatabase.delete(TABLE_TRIPS,
                        _ID + "=?",
                        new String[]{String.valueOf(trip.getId())});
            }finally {
                sqLiteDatabase.close();
            }
        }
    }

    @Override
    public Trip getTrip(long id){
        Trip trip = new Trip();
        OpenHelper openHelper = new OpenHelper(context);
        if(openHelper!= null){
            SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_TRIPS,
                    null, _ID + "=?",
                     new String[]{String.valueOf(id)},
                    null, null, null);
            try {
                if(cursor != null && cursor.getCount() > 0){
                    if(cursor.moveToNext()){
                        trip = cursorToEntity(cursor);
                    }
                }
            } finally {
                if(cursor != null){
                    cursor.close();
                }
                sqLiteDatabase.close();
            }
        }
        return  trip;
    }

    @Override
    public List<Trip> insertAll(List<Trip> trips) {
        OpenHelper openHelper = new OpenHelper(context);
        if(openHelper != null){
            SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
            try {
                for(int i = 0; i < trips.size(); i++){
                    trips.get(i).setId(sqLiteDatabase.insert(TABLE_TRIPS,
                            null,
                            entityToContentValues(trips.get(i))));
                }
            } finally {
                sqLiteDatabase.close();
            }
        }
        return trips;
    }

    private ContentValues entityToContentValues(Trip trip){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_NAME, trip.getName());
        contentValues.put(COLUMN_TRIP_DESTINATION, trip.getDestination());
        contentValues.put(COLUMN_TRIP_START, trip.getStartDate().getTimeInMillis());
        contentValues.put(COLUMN_TRIP_END, trip.getStartDate().getTimeInMillis());
        contentValues.put(COLUMN_TRIP_PRICE, trip.getPrice());
        contentValues.put(COLUMN_TRIP_RATING, trip.getRating());
        if(trip.getImageUri() != null) {
            contentValues.put(COLUMN_TRIP_IMAGE_URI, trip.getImageUri().toString());
        }
        if(trip.isFavorite()){
            contentValues.put(COLUMN_TRIP_FAVORITE, 1);
        } else {
            contentValues.put(COLUMN_TRIP_FAVORITE, 0);
        }
        contentValues.put(COLUMN_TRIP_TYPE, trip.getType().getKey());
        return contentValues;
    }
    private Trip cursorToEntity(Cursor cursor){
        Trip trip = new Trip();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        trip.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        trip.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_NAME)));
        trip.setDestination(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_DESTINATION)));
        calendar.setTimeInMillis(
                cursor.getLong(cursor.getColumnIndex(COLUMN_TRIP_START)));
        trip.setStartDate(calendar);
        calendar1.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COLUMN_TRIP_END)));
        trip.setEndDate(calendar1);
        trip.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_TRIP_PRICE)));
        trip.setRating(cursor.getFloat(cursor.getColumnIndex(COLUMN_TRIP_RATING)));
        String uri =cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_IMAGE_URI));
        if(uri != null) trip.setImage(Uri.parse(uri));
        int fav = cursor.getInt(cursor.getColumnIndex(COLUMN_TRIP_FAVORITE));
        if(fav==1){
            trip.setFavorite(true);
        } else {
            trip.setFavorite(false);
        }
        trip.setType(TripType.getType(cursor.getInt(cursor.getColumnIndex(COLUMN_TRIP_TYPE))));
        return trip;
    }
}
