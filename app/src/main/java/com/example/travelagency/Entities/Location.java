package com.example.travelagency.Entities;

public class Location {

    //@PrimaryKey
    public String countryName;


    public String inhabitants;
    public String description;
    public String language;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(String inhabitants) {
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
