package com.skalyter.mytraveljournal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skalyter.mytraveljournal.model.User;

import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_USER_EMAIL;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_USER_FIRST_NAME;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_USER_LAST_NAME;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.COLUMN_USER_PASSWORD;
import static com.skalyter.mytraveljournal.database.DatabaseScheme.TABLE_USERS;

public class UserDao implements IUserDao {

    Context context;

    public UserDao(Context context) {
        this.context = context;
    }

    @Override
    public User getUser(String email) {
        User user = new User();
        OpenHelper openHelper = new OpenHelper(context);
        if (openHelper != null) {
            SQLiteDatabase sqLiteDatabase = openHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_USERS,
                    null, COLUMN_USER_EMAIL + "=?",
                    new String[]{email}, null, null, null);
            try {
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToNext()) {
                        user = cursorToEntity(cursor);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                sqLiteDatabase.close();
            }
        }
        return user;
    }

    @Override
    public long insertUser(User user) {
        OpenHelper openHelper = new OpenHelper(context);
        long id = -1;
        if (openHelper != null) {
            SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
            try {
                id = sqLiteDatabase.insert(TABLE_USERS,
                        null, entityToContentValues(user));
            } finally {
                sqLiteDatabase.close();
            }
        }
        return id;

    }

    private ContentValues entityToContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_USER_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        return contentValues;
    }

    private User cursorToEntity(Cursor cursor) {
        User user = new User();
        user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
        user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        return user;
    }
}
