package com.skalyter.mytraveljournal.model;

import android.net.Uri;

import java.util.Calendar;

public class Trip {
    private Long id;
    private String name;
    private String destination;
    private Double price;
    private Calendar startDate;
    private Calendar endDate;
    private Float rating;
    private boolean favorite;
    private Uri imageUri;
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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImage(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

}
