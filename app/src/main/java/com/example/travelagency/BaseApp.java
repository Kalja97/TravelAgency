package com.example.travelagency;

import android.app.Application;

import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.Repository.TripRepository;

public class BaseApp extends Application {

    public LocationRepository getLocationRepository() {
        return LocationRepository.getInstance();
    }

    public TripRepository getTripRepository() {
        return TripRepository.getInstance();
    }
}