package com.skalyter.mytraveljournal.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.skalyter.mytraveljournal.database.DateConverter;
import com.skalyter.mytraveljournal.database.TripTypeConverter;

import java.util.Calendar;
@Entity(tableName = "trip")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "destination")
    private String destination;
    @ColumnInfo(name = "price")
    private Double price;
    @ColumnInfo(name = "start_date")
    @TypeConverters(DateConverter.class)
    private Calendar startDate;
    @ColumnInfo(name = "end_date")
    @TypeConverters(DateConverter.class)
    private Calendar endDate;
    @ColumnInfo(name = "rating")
    private Float rating;
    @ColumnInfo(name = "favorite")
    private boolean favorite;
    @Ignore
    private Bitmap image;
    @ColumnInfo(name = "type")
    @TypeConverters(TripTypeConverter.class)
    private TripType type;

    public Trip() {
    }

    //constructor fara id
    public Trip(String name, String destination, Double price, Calendar startDate, Calendar endDate, Float rating, boolean favorite) {
        this.name = name;
        this.destination = destination;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.favorite = favorite;
    }

    //constructor cu id
    public Trip(Long id, String name, String destination, Double price, Calendar startDate, Calendar endDate, Float rating, boolean favorite) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.favorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

}
