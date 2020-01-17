package com.skalyter.mytraveljournal;

import android.app.Application;

import com.skalyter.mytraveljournal.database.AppDatabase;
import com.skalyter.mytraveljournal.database.AppExecutors;

public class MyTravelJournalApp extends Application {

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(MyTravelJournalApp.this, appExecutors);
    }
}
