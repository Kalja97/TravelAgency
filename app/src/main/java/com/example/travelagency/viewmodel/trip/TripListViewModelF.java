package com.example.travelagency.viewmodel.trip;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.Entities.TripF;
import com.example.travelagency.Repository.TripRepositoryF;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;

public class TripListViewModelF extends AndroidViewModel {

    private TripRepositoryF repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TripF>> observableTrips;

    public TripListViewModelF(@NonNull Application application,
                              final String countryName, TripRepositoryF tripRepository) {
        super(application);

        repository = tripRepository;

        observableTrips = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTrips.setValue(null);

        LiveData<List<TripF>> trips = repository.gettripsByCountry(countryName);

        // observe the changes of the entities from the database and forward them
        observableTrips.addSource(trips, observableTrips::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String countryName;
        private final TripRepositoryF tripRepository;

        public Factory(@NonNull Application application, String countryName) {
            this.application = application;
            this.countryName = countryName;
            tripRepository = TripRepositoryF.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripListViewModelF(application, countryName, tripRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<TripF>> getTrips() {
        return observableTrips;
    }

    public void deleteLocation(TripF trip) {
        repository.delete(trip, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(Exception e) {}
        });
    }
}

