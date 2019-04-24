package com.example.travelagency.viewmodel.location;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.Repository.LocationRepositoryF;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;


public class LocationListViewModelF extends AndroidViewModel {

    private LocationRepositoryF repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<LocationF>> observableLocations;

    public LocationListViewModelF(@NonNull Application application, LocationRepositoryF locationRepository) {
        super(application);

        repository = locationRepository;

        observableLocations = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableLocations.setValue(null);

        LiveData<List<LocationF>> locations = repository.getAllLocations();

        // observe the changes of the entities from the database and forward them
        observableLocations.addSource(locations, observableLocations::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final LocationRepositoryF locationRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            locationRepository = LocationRepositoryF.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocationListViewModelF(application, locationRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<LocationF>> getLocations() {
        return observableLocations;
    }

    public void deleteLocation(LocationF location) {
        repository.delete(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(Exception e) {}
        });
    }
}
