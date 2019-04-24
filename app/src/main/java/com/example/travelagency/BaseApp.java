package com.example.travelagency;

import android.app.Application;

import com.example.travelagency.Repository.LocationRepositoryF;
import com.example.travelagency.Repository.TripRepositoryF;

public class BaseApp extends Application {

    public LocationRepositoryF getLocationRepository() {
        return LocationRepositoryF.getInstance();
    }

    public TripRepositoryF getTripRepository() {
        return TripRepositoryF.getInstance();
    }
}