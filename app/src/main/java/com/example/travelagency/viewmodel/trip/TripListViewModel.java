package com.example.travelagency.viewmodel.trip;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.Repository.TripRepository;
import com.example.travelagency.util.OnAsyncEventListener;

import java.util.List;

public class TripListViewModel extends AndroidViewModel {

    private TripRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Trip>> observableTrips;

    public TripListViewModel(@NonNull Application application,
                             final String countryName, TripRepository tripRepository) {
        super(application);

        repository = tripRepository;

        observableTrips = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTrips.setValue(null);

        LiveData<List<Trip>> trips = repository.gettripsByCountry(countryName);

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
        private final TripRepository tripRepository;

        public Factory(@NonNull Application application, String countryName) {
            this.application = application;
            this.countryName = countryName;
            tripRepository = TripRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripListViewModel(application, countryName, tripRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Trip>> getTrips() {
        return observableTrips;
    }

    /*
    public void deleteLocation(Trip trip) {
        repository.delete(trip, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(Exception e) {}
        });
    }
    */
}

