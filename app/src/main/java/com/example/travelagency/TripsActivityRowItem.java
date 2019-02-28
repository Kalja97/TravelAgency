package com.example.travelagency;

public class TripsActivityRowItem {

    private String trip_name;
    private String quickDescription;

    public TripsActivityRowItem(String trip_name, String quickDescription) {

        this.trip_name = trip_name;
        this.quickDescription = quickDescription;

    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getQuickDescription_title() {
        return quickDescription;
    }

    public void setQuickDescription(String quickDescription) {
        this.quickDescription = quickDescription;
    }
}
