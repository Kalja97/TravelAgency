package com.example.travelagency.firebase;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.travelagency.Entities.LocationF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LocationListLiveData extends LiveData<List<LocationF>> {

    private static final String TAG = "TripListLiveData";



    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public LocationListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toLocations(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<LocationF> toLocations(DataSnapshot snapshot) {
        List<LocationF> locations = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            LocationF entity = childSnapshot.getValue(LocationF.class);
            //entity.setId(childSnapshot.getKey());
            //entity.setCountryName(countryName);
            locations.add(entity);
        }
        return locations;
    }


}