package com.example.travelagency.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "trips",
        foreignKeys =
        @ForeignKey(
                entity = Location.class,
                parentColumns = "locationid",
                childColumns = "fk",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"tripname"}
                )}
)

public class Trip {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Long fk;
    private String location;
    private String tripname;
    private String duration;
    private String date;
    private String price;
    private String description;

    public Trip(String location, String tripname, String duration, String date, String price, String description){
        this.location = location;
        this.tripname = tripname;
        this. duration = duration;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFk() {
        return fk;
    }

    public void setFk(Long fk) {
        this.fk = fk;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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


    @Override

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Trip)) return false;
        Trip o = (Trip) obj;
        return o.getId().equals(this.getId());
    }
}
