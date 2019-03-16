package com.example.travelagency.viewmodel;


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

public class TripViewModel  extends AndroidViewModel {

    private Application application;
    private TripRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Trip> observableAccount;

        public TripViewModel(@NonNull Application application,
                            final Long accountId, TripRepository accountRepository) {
        super(application);

        this.application = application;

        repository = accountRepository;

        observableAccount = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableAccount.setValue(null);

        //LiveData<Trip> account = repository.getAccount(accountId, application);

        // observe the changes of the account entity from the database and forward them
        //
            //
            //
            //
            //
            //
            //
            // observableAccount.addSource(account, observableAccount::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long accountId;

        private final TripRepository repository;

        public Factory(@NonNull Application application, Long accountId, TripRepository repository) {
            this.application = application;
            this.accountId = accountId;
            //repository = ((BaseApp) application).getAccountRepository();

            // wenn das mit BaseApp geht oben das verwenden
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TripViewModel(application, accountId, repository);
        }
    }

    /**
     * Expose the LiveData AccountEntity query so the UI can observe it.
     */
    public LiveData<Trip> getAccount() {
        return observableAccount;
    }

    public void createTript(Trip account, OnAsyncEventListener callback) {
        repository.insert(account, callback, application);
    }

    public void updateTrip(Trip account, OnAsyncEventListener callback) {
        repository.update(account, callback, application);
    }

    public void deleteTrip(Trip account, OnAsyncEventListener callback) {
        repository.delete(account, callback, application);
    }
}