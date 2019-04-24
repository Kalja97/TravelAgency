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

public class TripViewModel extends AndroidViewModel {

    private static final String TAG = "TripViewModel";

    private TripRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Trip> mObservableTrip;

    public TripViewModel(@NonNull Application application, final String countryName, final String id,
                         TripRepository tripRepository) {
        super(application);

        repository = tripRepository;
        mObservableTrip = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableTrip.setValue(null);

        LiveData<Trip> trip = repository.getTrip(countryName, id);

        // observe the changes of the client entity from the database and forward them
        mObservableTrip.addSource(trip, mObservableTrip::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String countryName;
        private final String id;
        private final TripRepository repository;

        public Factory(@NonNull Application application, String countryName, String id, TripRepository repository) {
            this.application = application;
            this.countryName = countryName;
            this.id = id;
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripViewModel(application,countryName, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<Trip> getTrip() {
        return mObservableTrip;
    }

    public void createTrip(Trip trip, OnAsyncEventListener callback) {
        TripRepository.getInstance().insert(trip, callback);
    }

    public void updateTrip(Trip trip, OnAsyncEventListener callback) {
        //((BaseApp) getApplication()).getTripRepository()
                //.update(trip, callback);
        TripRepository.getInstance().update(trip, callback);
    }

    public void deleteTrip(Trip trip, OnAsyncEventListener callback) {
        /*((BaseApp) getApplication()).getTripRepository()
                .delete(trip, callback);*/
        TripRepository.getInstance().delete(trip, callback);
    }
}

