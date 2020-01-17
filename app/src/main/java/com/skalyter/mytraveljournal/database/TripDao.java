package com.skalyter.mytraveljournal.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.skalyter.mytraveljournal.model.Trip;

import java.util.List;

@Dao
public interface TripDao {

    @Query("SELECT * FROM trip")
    LiveData<List<Trip>> getAllTrips();

    @Insert
    void insertTrip(Trip trip);

    @Update
    void updateTrip(Trip trip);

    @Delete
    void deleteTrio(Trip trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Trip> trips);
}
