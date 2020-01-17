package com.skalyter.mytraveljournal.database;

import androidx.room.TypeConverter;

import com.skalyter.mytraveljournal.model.TripType;

public class TripTypeConverter {
    @TypeConverter
    public static TripType toTripType(int key){
        switch (key){
            case 1:
                return TripType.CITYBREAK;
            case 2:
                return TripType.MOUNTAIN;
            case 3:
                return TripType.SEASIDE;
                default:
                    throw new IllegalArgumentException("Not a valid trip type");
        }
    }

    @TypeConverter
    public static int toInt(TripType tripType){
        return tripType.getKey();
    }
}
