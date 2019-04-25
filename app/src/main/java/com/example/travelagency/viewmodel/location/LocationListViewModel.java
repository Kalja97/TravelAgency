package com.example.travelagency.viewmodel.location;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;


public class LocationListViewModel extends AndroidViewModel {

    private static final String TAG = "LocationListViewModel";

    private LocationRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Location>> observableLocations;

    public LocationListViewModel(@NonNull Application application, LocationRepository locationRepository) {
        super(application);

        repository = locationRepository;

        observableLocations = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableLocations.setValue(null);

        LiveData<List<Location>> locations = repository.getAllLocations();

        // observe the changes of the entities from the database and forward them
        observableLocations.addSource(locations, observableLocations::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final LocationRepository locationRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            locationRepository = ((BaseApp) application).getLocationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocationListViewModel(application, locationRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Location>> getLocations() {
        return observableLocations;
    }

    /*
    public void deleteLocation(Location location) {
        repository.delete(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(Exception e) {}
        });
    }
    */
}
