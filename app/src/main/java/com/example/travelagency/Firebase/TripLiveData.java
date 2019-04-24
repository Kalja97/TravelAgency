package com.example.travelagency.Firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.travelagency.Entities.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TripLiveData extends LiveData<Trip> {
    private static final String TAG = "TripLiveData";


    private final DatabaseReference reference;
    private final String countryName;
    private final TripLiveData.MyValueEventListener listener = new TripLiveData.MyValueEventListener();

    public TripLiveData(DatabaseReference ref) {
        reference = ref;
        countryName = ref.getParent().getParent().getKey();
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
            Trip entity = dataSnapshot.getValue(Trip.class);
            if (entity != null) {
                entity.setId(dataSnapshot.getKey());
                entity.setCountryName(countryName);
                setValue(entity);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}
