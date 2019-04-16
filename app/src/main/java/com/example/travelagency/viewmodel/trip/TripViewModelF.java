package com.example.travelagency.viewmodel.trip;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.travelagency.BaseAppF;
import com.example.travelagency.Entities.TripF;
import com.example.travelagency.Repository.TripRepositoryF;
import com.example.travelagency.util.OnAsyncEventListener;

public class TripViewModelF extends AndroidViewModel {

    private static final String TAG = "TripViewModel";

    private TripRepositoryF repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<TripF> mObservableTrip;

    public TripViewModelF(@NonNull Application application, final String countryName, final String id,
                              TripRepositoryF tripRepository) {
        super(application);

        repository = tripRepository;
        mObservableTrip = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        mObservableTrip.setValue(null);

        LiveData<TripF> trip = repository.getTrip(countryName, id);

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
        private final TripRepositoryF repository;

        public Factory(@NonNull Application application, String countryName, String id, TripRepositoryF repository) {
            this.application = application;
            this.countryName = countryName;
            this.id = id;
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripViewModelF(application,countryName, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<TripF> getTrip() {
        return mObservableTrip;
    }

    public void createTrip(TripF trip, OnAsyncEventListener callback) {
        TripRepositoryF.getInstance().insert(trip, callback);
    }

    public void updateTrip(TripF trip, OnAsyncEventListener callback) {
        TripRepositoryF.getInstance().update(trip, callback);
    }

    public void deleteTrip(TripF trip, OnAsyncEventListener callback) {
        TripRepositoryF.getInstance().delete(trip, callback);
    }
}

