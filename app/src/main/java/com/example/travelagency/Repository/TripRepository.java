package com.example.travelagency.Repository;

import android.arch.lifecycle.LiveData;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.Firebase.TripListLiveData;
import com.example.travelagency.Firebase.TripLiveData;
import com.example.travelagency.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TripRepository {
    private static final String TAG = "TripRepository";

    private static TripRepository instance;

    public TripRepository() {
    }

    //Constructor
    public static TripRepository getInstance() {
        if (instance == null) {
            synchronized (TripRepository.class) {
                if (instance == null) {
                    instance = new TripRepository();
                }
            }
        }
        return instance;
    }

    //Query: get a trip
    public LiveData<Trip> getTrip(final String countryName, final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName)
                .child("trips")
                .child(id);
        return new TripLiveData(reference);
    }

    //Query: get all trips of a location
    public LiveData<List<Trip>> gettripsByCountry(final String countryName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName)
                .child("trips");
        return new TripListLiveData(reference, countryName);
    }

    //Query: insert a trip
    public void insert(final Trip trip, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("countries").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(trip.getCountryName())
                .child("trips")
                .child(id)
                .setValue(trip, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    //Query: update a trip
    public void update(final Trip trip, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(trip.getCountryName())
                .child("trips")
                .child(trip.getId())
                .updateChildren(trip.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    //Query: delete a trip
    public void delete(final Trip trip, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(trip.getCountryName())
                .child("trips")
                .child(trip.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
