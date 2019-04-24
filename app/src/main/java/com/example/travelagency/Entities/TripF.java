package com.example.travelagency.Entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TripF {

    private String id;
    private String countryName;
    private String tripName;
    private String duration;
    private String date;
    private String price;
    private String description;
    private String imageUrl;
    private float rating;

    public TripF(){

    }

    public TripF(String countryName, String tripName, String duration, String date, String price, String description, String imageUrl, float rating){
        this.countryName = countryName;
        this.tripName = tripName;
        this. duration = duration;
        this.date = date;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //@Exclude
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof TripF)) return false;
        TripF o = (TripF) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return tripName + "  (" + duration + ")";
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("countryName", countryName);
        result.put("date", date);
        result.put("description", description);
        result.put("duration", duration);
        result.put("imageUrl", imageUrl);
        result.put("price", price);
        result.put("rating", rating);
        result.put("tripName", tripName);
        return result;

    }

}
