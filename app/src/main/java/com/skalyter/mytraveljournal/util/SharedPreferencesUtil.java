package com.skalyter.mytraveljournal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static final String APP_KEY="my_travel_journal_key";

    //save a String value by key
    public static void setStringValueInSharedPreferences(Context context,
                                                         String key, String value){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //get a String value by key
    public static String getStringValueFromSharedPreferences(Context context, String key){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    //delete a value by key
    public static void deleteValueFromSharedPreferences(Context context, String key){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    //delete all values
    public static void deleteAllValuesFromSharedPreferences(Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
