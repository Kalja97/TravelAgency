package com.example.travelagency.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.Entities.TripF;

public class TripLiveData extends LiveData<TripF> {
    private static final String TAG = "AccountLiveData";

    /*
    private final DatabaseReference reference;
    private final String countryName;
    private final TripLiveData.MyValueEventListener listener = new TripLiveData.MyValueEventListener();

    public AccountLiveData(DatabaseReference ref) {
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
            TripF entity = dataSnapshot.getValue(AccountEntity.class);
            entity.setId(dataSnapshot.getKey());
            entity.setCountryName(countryName);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
    */
}
