package com.example.travelagency.Repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.firebase.LocationListLiveData;
import com.example.travelagency.firebase.LocationLiveData;
import com.example.travelagency.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LocationRepositoryF {
    private static final String TAG = "ClientRepository";

    private static LocationRepositoryF instance;

    public LocationRepositoryF() {
    }

    public static LocationRepositoryF getInstance() {
        if (instance == null) {
            synchronized (TripRepositoryF.class) {
                if (instance == null) {
                    instance = new LocationRepositoryF();
                }
            }
        }
        return instance;
    }

    public LiveData<LocationF> getLocation(final String countryName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName);
        return new LocationLiveData(reference);
    }

    public LiveData<List<LocationF>> getAllLocations() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries");
        return new LocationListLiveData(reference);
    }

    public void insert(final LocationF location, final OnAsyncEventListener callback) {
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

    public void update(final LocationF location, final OnAsyncEventListener callback) {
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

    public void delete(final LocationF location, OnAsyncEventListener callback) {
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