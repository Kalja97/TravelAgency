package com.example.travelagency.Repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.async.CreateTrip;
import com.example.travelagency.async.DeleteTrip;
import com.example.travelagency.async.UpdateTrip;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;

public class TripRepository {
    private static TripRepository instance;

    private TripRepository() {

    }

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


    public LiveData<Trip> getTrip(final String countryName, Application application) {
        return ((BaseApp) application).getDatabase().tripDao().getByLocation(countryName);
    }

    public LiveData<List<Trip>> getTrips(Application application) {
        return ((BaseApp) application).getDatabase().tripDao().getAllTrips();
    }

    public void insert(final Trip trip, OnAsyncEventListener callback,
                       Application application) {
        new CreateTrip(application, callback).execute(trip);
    }

    public void update(final Trip trip, OnAsyncEventListener callback,
                       Application application) {
        new UpdateTrip(application, callback).execute(trip);
    }

    public void delete(final Trip trip, OnAsyncEventListener callback,
                       Application application) {
        new DeleteTrip(application, callback).execute(trip);
    }

}
