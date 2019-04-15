package com.example.travelagency.viewmodel.location;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.BaseAppF;
import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.Repository.LocationRepositoryF;
import com.example.travelagency.util.OnAsyncEventListener;

public class LocationViewModelF extends AndroidViewModel {

    private static final String TAG = "AccountViewModel";

    private LocationRepositoryF repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<LocationF> mObservableClient;

    public LocationViewModelF(@NonNull Application application,
                           final String countryName, LocationRepositoryF locationRepository) {
        super(application);

        repository = locationRepository;
        mObservableClient = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableClient.setValue(null);

        LiveData<LocationF> account = repository.getLocation(countryName);

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
        private final LocationRepositoryF repository;

        public Factory(@NonNull Application application, String countryName) {
            this.application = application;
            this.countryName = countryName;
            repository = ((BaseAppF) application).getLocationRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new LocationViewModelF(application, countryName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<LocationF> getClient() {
        return mObservableClient;
    }

    public void updateClient(LocationF location, OnAsyncEventListener callback) {
        ((BaseAppF) getApplication()).getLocationRepository()
                .update(location, callback);
    }

    public void deleteClient(LocationF location, OnAsyncEventListener callback) {
        ((BaseAppF) getApplication()).getLocationRepository()
                .delete(location, callback);
    }
}
