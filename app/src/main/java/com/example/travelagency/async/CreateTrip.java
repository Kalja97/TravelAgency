package com.example.travelagency.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.util.OnAsyncEventListener;

public class CreateTrip extends AsyncTask<Trip, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateTrip(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Trip... params) {
        try {
            for (Trip trip : params) {
                ((BaseApp) application).getDatabase().tripDao()
                        .insert(trip);
            }
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}