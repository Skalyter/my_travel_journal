package com.skalyter.mytraveljournal.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.skalyter.mytraveljournal.model.Trip;
import com.skalyter.mytraveljournal.model.User;

import java.util.List;

@Database(
        entities = {User.class, Trip.class},
        version = 1,
        exportSchema = true
)
@TypeConverters({DateConverter.class, TripTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String TAG = "AppDatabase";

    @VisibleForTesting
    public static final String DB_NAME = "my_travel_app.db";
    private static volatile AppDatabase instance;
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = create(context, executors);
                }
            }
        }
        return instance;
    }

    private static void insertData(final AppDatabase db,
                                   final List<Trip> trips) {
        db.runInTransaction(() -> {
            Log.d(TAG, "insertData: started transaction for inserting dummy data in DB!");
            db.tripDao().insertAll(trips);
        });
    }

    private static void addDelay() {
        try {
            // Sleep for 5 seconds
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
    }

    private static AppDatabase create(@NonNull final Context context, @NonNull final AppExecutors executors) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase
                                    .getInstance(context.getApplicationContext(),
                                            executors);

                            // Pre-populate DB with dummy data
                            insertData(database, DataGenerator.getTrips());
                            // notify that the database was created and it's ready
                            // to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                // Create migration strategy if needed
                // .addMigrations(MIGRATION_1_2)
                .build();
        db.updateDatabaseCreated(context.getApplicationContext());
        return db;
    }

    public abstract TripDao tripDao();

    private void updateDatabaseCreated(@NonNull final Context context) {
        if (context.getDatabasePath(DB_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> isDatabaseCreated() {
        // Useful to check when we need to fetch data from server
        return isDatabaseCreated;
    }
}
