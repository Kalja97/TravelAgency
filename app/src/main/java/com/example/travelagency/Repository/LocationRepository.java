package com.example.travelagency.Repository;

import android.arch.lifecycle.LiveData;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.Firebase.LocationListLiveData;
import com.example.travelagency.Firebase.LocationLiveData;
import com.example.travelagency.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LocationRepository {
    private static final String TAG = "ClientRepository";

    private static LocationRepository instance;

    public LocationRepository() {
    }

    public static LocationRepository getInstance() {
        if (instance == null) {
            synchronized (TripRepository.class) {
                if (instance == null) {
                    instance = new LocationRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Location> getLocation(final String countryName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName);
        return new LocationLiveData(reference);
    }

    public LiveData<List<Location>> getAllLocations() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries");
        return new LocationListLiveData(reference);
    }

    public void insert(final Location location, final OnAsyncEventListener callback) {
        String id = location.getCountryName();
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(id)
                .setValue(location, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Location location, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(location.getCountryName())
                .updateChildren(location.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Location location, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(location.getCountryName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}