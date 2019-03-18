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
import com.example.travelagency.util.OnAsyncEventListener;

public class TripViewModel  extends AndroidViewModel {

    private Application application;
    private TripRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Trip> observableTrip;

        public TripViewModel(@NonNull Application application,
                            final String countryname, TripRepository tripRepository) {
        super(application);

        this.application = application;

        repository = tripRepository;

        observableTrip = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTrip.setValue(null);

        LiveData<Trip> trip = repository.getTrip(countryname, application);

        //observe the changes of the account entity from the database and forward them
        observableTrip.addSource(trip, observableTrip::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String countryname;

        private final TripRepository repository;

        public Factory(@NonNull Application application, String countryname, TripRepository repository) {
            this.application = application;
            this.countryname = countryname;
            this.repository = ((BaseApp) application).getTripRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripViewModel(application, countryname, repository);
        }
    }

    /**
     * Expose the LiveData Trip query so the UI can observe it.
     */
    public LiveData<Trip> getTrip() {
        return observableTrip;
    }

    public void createTrip(Trip trip, OnAsyncEventListener callback) {
        repository.insert(trip, callback, application);
    }

    public void updateTrip(Trip trip, OnAsyncEventListener callback) {
        repository.update(trip, callback, application);
    }

    public void deleteTrip(Trip trip, OnAsyncEventListener callback) {
        repository.delete(trip, callback, application);
    }
}