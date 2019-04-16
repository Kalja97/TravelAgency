package com.example.travelagency.Repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.travelagency.Entities.TripF;
import com.example.travelagency.firebase.TripListLiveData;
import com.example.travelagency.firebase.TripLiveData;
import com.example.travelagency.util.OnAsyncEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class TripRepositoryF {
    private static TripRepositoryF instance;

    public static TripRepositoryF getInstance() {
        if (instance == null) {
            synchronized (TripRepositoryF.class) {
                if (instance == null) {
                    instance = new TripRepositoryF();
                }
            }
        }
        return instance;
    }


    public LiveData<TripF> getTrip(final String tripId, final String countryName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName)
                .child("trips")
                .child(tripId);
        return new TripLiveData(reference);
    }

    public LiveData<List<TripF>> gettripsByCountry(final String countryName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(countryName)
                .child("trips");
        return new TripListLiveData(reference, countryName);
    }

    public void insert(final TripF trip, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("countries").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(id)
                .setValue(trip, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TripF trip, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(trip.getCountryName())
                .updateChildren(trip.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TripF trip, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("countries")
                .child(trip.getCountryName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
