package com.example.travelagency.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.Repository.TripRepository;

import java.util.List;

public class TripListViewModel extends AndroidViewModel {

    private TripRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Trip>> observableLocations;

    public TripListViewModel(@NonNull Application application,
                                 TripRepository tripRepository, String countryName) {
        super(application);

        this.application = application;
        this.repository = tripRepository;

        observableLocations = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableLocations.setValue(null);

        LiveData<List<Trip>> trips = repository.getTrips(countryName, application);

        // observe the changes of the client entity from the database and forward them
        observableLocations.addSource(trips, observableLocations::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String countryName;
        private final TripRepository repository;

        public Factory(@NonNull Application application, String countryName) {
            this.application = application;
            this.countryName = countryName;
            repository = ((BaseApp) application).getTripRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripListViewModel(application, repository, countryName);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<List<Trip>> getTrips() {
        return observableLocations;
    }
}