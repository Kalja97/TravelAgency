package com.example.travelagency.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;

public class LocationListViewModel extends AndroidViewModel {

        private LocationRepository repository;

        private Application application;

        // MediatorLiveData can observe other LiveData objects and react on their emissions.
        private final MediatorLiveData<Location> observableClient;

        public LocationListViewModel(@NonNull Application application,
                               final String countryName, LocationRepository locationRepository) {
            super(application);

            this.application = application;

            repository = locationRepository;

            observableClient = new MediatorLiveData<>();
            // set by default null, until we get data from the database.
            observableClient.setValue(null);

            LiveData<Location> location = repository.getLocation(countryName, application);

            // observe the changes of the client entity from the database and forward them
            observableClient.addSource(location, observableClient::setValue);
        }

        /**
         * A creator is used to inject the account id into the ViewModel
         */
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
                return (T) new LocationListViewModel(application, countryName, repository);
            }
        }

        /**
         * Expose the LiveData ClientEntity query so the UI can observe it.
         */
        public LiveData<Location> getLocation() {
            return observableClient;
        }

        public void createClient(Location location, OnAsyncEventListener callback) {
            repository.insert(location, callback, application);
        }

        public void updateClient(Location location, OnAsyncEventListener callback) {
            repository.update(location, callback, application);
        }

        public void deleteClient(Location location, OnAsyncEventListener callback) {
            repository.delete(location, callback, application);

        }
}
