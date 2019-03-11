package com.example.travelagency.Adapters;

public class Country {

    private String countryname, language, year, description;
    private int pic, rank, inhabitant;

    public Country(String countryname, String language, String description, int inhabitant, int rank, int pic){

        this.countryname = countryname;
        this.language = language;
        this.description = description;
        this.inhabitant = inhabitant;
        this.rank = rank;
        this.pic = pic;

    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInhabitant() {
        return inhabitant;
    }

    public void setInhabitant(int inhabitant) {
        this.inhabitant = inhabitant;
    }
}
