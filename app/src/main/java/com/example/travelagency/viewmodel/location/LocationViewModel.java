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

public class LocationViewModel extends AndroidViewModel {

    private static final String TAG = "LocationViewModel";

    private LocationRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Location> mObservableLocation;

    public LocationViewModel(@NonNull Application application,
                             final String countryName, LocationRepository locationRepository) {
        super(application);

        repository = locationRepository;
        mObservableLocation = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableLocation.setValue(null);

        LiveData<Location> account = repository.getLocation(countryName);

        // observe the changes of the client entity from the database and forward them
        mObservableLocation.addSource(account, mObservableLocation::setValue);
    }

    //Factory of the location view model
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String countryName;
        private final LocationRepository repository;

        public Factory(@NonNull Application application, String countryName) {
            this.application = application;
            this.countryName = countryName;
            repository = ((BaseApp) application).getLocationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocationViewModel(application, countryName, repository);
        }
    }

    //get a location
    public LiveData<Location> getLocation() {
        return mObservableLocation;
    }

    //create location
    public void createLocation(Location location, OnAsyncEventListener callback) {
        LocationRepository.getInstance().insert(location, callback);
    }

    //update location
    public void updateLocation(Location location, OnAsyncEventListener callback) {
        LocationRepository.getInstance().update(location, callback);
    }

    //delete location
    public void deleteLocation(Location location, OnAsyncEventListener callback) {
        LocationRepository.getInstance().delete(location, callback);
    }
}
