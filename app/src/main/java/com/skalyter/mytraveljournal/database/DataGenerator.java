package com.skalyter.mytraveljournal.database;

import androidx.annotation.NonNull;

import com.skalyter.mytraveljournal.model.Trip;

import java.util.ArrayList;
import java.util.List;

import static com.skalyter.mytraveljournal.util.Util.getCalendarFromString;

class DataGenerator {
    private static final String[] DESTINATIONS = new String[]{"Abu Dhabbi", "Dubai", "Tenerife", "Paris"};

    private static final List<Trip> trips = new ArrayList<>(DESTINATIONS.length);
    static {
        for (int i=0; i<DESTINATIONS.length; i++) {
            Trip trip = new Trip("Trip to " + DESTINATIONS[i], DESTINATIONS[i], i*100+250.0,
                    getCalendarFromString(i+3+"/"+i+"/"+2020), getCalendarFromString(i+10+"/"+i+"/"+2020),2.5f+i, false);
        }
    }

    @NonNull
    static List<Trip> getTrips(){
        return trips;
    }
}
