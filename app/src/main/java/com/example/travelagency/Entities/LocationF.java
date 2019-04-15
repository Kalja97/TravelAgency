package com.example.travelagency.Entities;

import android.support.annotation.NonNull;
//import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class LocationF implements Comparable {
    private String countryName;
    private int inhabitants;
    private String description;
    private String language;

    public LocationF(){
    }

    public LocationF(@NonNull String countryName, int inhabitants, String description, String language){
        this.countryName = countryName;
        this.inhabitants = inhabitants;
        this.description = description;
        this.language = language;
    }

    //@Exclude
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
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
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    //@Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("description", description);
        result.put("inhabitants", inhabitants);
        result.put("language", language);
        return result;

    }

}
