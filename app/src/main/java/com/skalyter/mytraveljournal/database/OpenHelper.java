package com.skalyter.mytraveljournal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.skalyter.mytraveljournal.database.DatabaseScheme.ALTER_V2;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.CREATE_TRIPS;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.CREATE_USERS;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String NAME = "travel_journal_db";

    public OpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TRIPS);
        sqLiteDatabase.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       if(i == 1  && i1 == 2){
           sqLiteDatabase.execSQL(ALTER_V2);
       }
    }


}
