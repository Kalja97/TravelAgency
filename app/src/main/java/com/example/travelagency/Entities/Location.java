package com.example.travelagency.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "locations", primaryKeys = {"countryName"})
public class Location implements Comparable {

    /*@PrimaryKey(autoGenerate = true)
    private Long locationid;*/
    @NonNull
    private String countryName;

    private int inhabitants;
    private String description;
    private String language;

    public Location(@NonNull String countryName, int inhabitants, String description, String language){

        this.countryName = countryName;
        this.inhabitants = inhabitants;
        this.description = description;
        this.language = language;
    }

    @Ignore
    public Location() {

    }

   /* public Long getLocationid() {
        return locationid;
    }

    public void setLocationid(Long locationid) {
        this.locationid = locationid;
    }
*/
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Location)) return false;
        Location o = (Location) obj;
        return o.getCountryName().equals(this.getCountryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName);
    }

    @Override
    public String toString() {
        return countryName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}
