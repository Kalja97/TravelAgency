package com.example.travelagency.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "locations")
public class Location {

    @PrimaryKey(autoGenerate = true)
    private Long locationid;

    private String countryName;

    private int inhabitants;
    private String description;
    private String language;

    public Location(String countryName, int inhabitants, String description, String language){

        this.countryName = countryName;
        this.inhabitants = inhabitants;
        this.description = description;
        this.language = language;
    }

    public Location() {

    }

    public Long getLocationid() {
        return locationid;
    }

    public void setLocationid(Long locationid) {
        this.locationid = locationid;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(int inhabitants) {
        this.inhabitants = inhabitants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
