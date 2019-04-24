package com.example.travelagency.Firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.travelagency.Entities.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripListLiveData extends LiveData<List<Trip>> {

    private static final String TAG = "TripListLiveData";

    private final DatabaseReference reference;
    private final String countryName;
    private final MyValueEventListener listener = new MyValueEventListener();

    public TripListLiveData(DatabaseReference ref, String countryName) {
        reference = ref;
        this.countryName = countryName;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toTrips(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Trip> toTrips(DataSnapshot snapshot) {
        List<Trip> trips = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Trip entity = childSnapshot.getValue(Trip.class);
            entity.setId(childSnapshot.getKey());
            entity.setCountryName(countryName);
            trips.add(entity);
        }
        return trips;
    }

}