package com.example.travelagency.Repository;

import android.app.Application;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.async.CreateLocation;
import com.example.travelagency.async.DeleteLocation;
import com.example.travelagency.async.UpdateLocation;
import com.example.travelagency.util.OnAsyncEventListener;

public class LocationRepository {

    private static LocationRepository instance;

    private LocationRepository() {

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



