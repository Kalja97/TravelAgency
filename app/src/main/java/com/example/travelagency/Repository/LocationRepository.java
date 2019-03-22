package com.example.travelagency.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.async.CreateLocation;
import com.example.travelagency.async.DeleteLocation;
import com.example.travelagency.async.UpdateLocation;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;

public class LocationRepository {

    private static LocationRepository instance;

    public LocationRepository() {

    }

    public static LocationRepository getInstance() {

        if (instance == null) {
            synchronized (LocationRepository.class) {
                if (instance == null) {
                    instance = new LocationRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Location> getLocation(final String countryName, Application application) {
        return ((BaseApp) application).getDatabase().locationDao().getByPK(countryName);
    }

    public LiveData<List<Location>> getLocations(Application application) {
        return ((BaseApp) application).getDatabase().locationDao().getAllLocations();
    }

    public void insert(final Location location, OnAsyncEventListener callback,
                       Application application) {
        new CreateLocation(application, callback).execute(location);
    }

    public void update(final Location location, OnAsyncEventListener callback,
                       Application application) {
        new UpdateLocation(application, callback).execute(location);
    }

    public void delete(final Location location, OnAsyncEventListener callback,
                       Application application) {
        new DeleteLocation(application, callback).execute(location);
    }
}



