package com.example.travelagency;

import android.app.Application;

import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.Repository.TripRepository;

    public class BaseApp extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
        }

        public AppDatabase getDatabase() {
            return AppDatabase.getInstance(this);
        }

        public LocationRepository getLocationRepository() {
            return LocationRepository.getInstance();
        }

        public TripRepository getTripRepository() {
            return TripRepository.getInstance();
        }
    }

