package com.example.travelagency.viewmodel.location;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.util.OnAsyncEventListener;

public class LocationViewModel extends AndroidViewModel {

    private static final String TAG = "AccountViewModel";

    private LocationRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Location> mObservableClient;

    public LocationViewModel(@NonNull Application application,
                             final String countryName, LocationRepository locationRepository) {
        super(application);

        repository = locationRepository;
        mObservableClient = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableClient.setValue(null);

        LiveData<Location> account = repository.getLocation(countryName);

        // observe the changes of the client entity from the database and forward them
        mObservableClient.addSource(account, mObservableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String countryName;
        private final LocationRepository repository;

        public Factory(@NonNull Application application, String countryName, LocationRepository repository) {
            this.application = application;
            this.countryName = countryName;
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocationViewModel(application, countryName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<Location> getLocation() {
        return mObservableClient;
    }

    public void createLocation(Location location, OnAsyncEventListener callback) {
        LocationRepository.getInstance().insert(location, callback);
    }

    public void updateLocation(Location location, OnAsyncEventListener callback) {
        //((BaseApp) getApplication()).getLocationRepository().update(location, callback);
        LocationRepository.getInstance().update(location, callback);
    }

    public void deleteLocation(Location location, OnAsyncEventListener callback) {
        //((BaseApp) getApplication()).getLocationRepository().delete(location, callback);
        LocationRepository.getInstance().delete(location, callback);
    }
}
