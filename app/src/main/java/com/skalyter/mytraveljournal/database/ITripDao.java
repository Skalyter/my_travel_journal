package com.skalyter.mytraveljournal.database;

import com.skalyter.mytraveljournal.model.Trip;

import java.util.List;

public interface ITripDao {

    List<Trip> getAllTripsChronologically();

    long insertTrip(Trip trip);

    void updateTrip(Trip trip);

    void deleteTrio(Trip trip);

    List<Trip> insertAll(List<Trip> trips);

    Trip getTrip(long id);
}
