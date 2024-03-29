package com.example.travelagency.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "trips",
        foreignKeys =
        @ForeignKey(
                entity = Location.class,
                parentColumns = "countryName",
                childColumns = "countryName",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"countryName"}
                )}
)

public class Trip {

    @PrimaryKey(autoGenerate = true)
    private Long id;

//    private Long fk;
    private String countryName;
    private String tripname;
    private String duration;
    private String date;
    private String price;
    private String description;
    private String imageUrl;
    private float rating;

    public Trip(String countryName, String tripname, String duration, String date, String price, String description, String imageUrl, float rating){
        this.countryName = countryName;
        this.tripname = tripname;
        this. duration = duration;
        this.date = date;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
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
        if (!(obj instanceof Trip)) return false;
        Trip o = (Trip) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return tripname + "  (" + duration + ")";
    }
}
