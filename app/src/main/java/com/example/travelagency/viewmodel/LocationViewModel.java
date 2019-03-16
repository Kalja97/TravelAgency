package com.example.travelagency.viewmodel;

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

    private LocationRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Location> observableLocation;

    public LocationViewModel(@NonNull Application application,
                           final String clientId, LocationRepository locationRepository) {

        super(application);

        this.application = application;

        repository = locationRepository;

        observableLocation = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableLocation.setValue(null);

        //LiveData<ClientEntity> client = repository.getClient(clientId, application);

        // observe the changes of the client entity from the database and forward them
        //observableLocation.addSource(client, observableLocation::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String clientId;

        private final LocationRepository repository;

        public Factory(@NonNull Application application, String clientId, LocationRepository repository) {
            this.application = application;
            this.clientId = clientId;
            //repository = ((BaseApp) application).getClientRepository();
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            //return (T) new TripViewModel(application, clientId, repository);
            return null;
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<Location> getClient() {
        return observableLocation;
    }

    public void createLocation(Location location, OnAsyncEventListener callback) {
        repository.insert(location, callback, application);
    }

    public void updateLocation(Location location, OnAsyncEventListener callback) {
        repository.update(location, callback, application);
    }

    public void deleteLocation(Location location, OnAsyncEventListener callback) {
        repository.delete(location, callback, application);
    }
}