package com.example.travelagency.async.Trip;

import android.app.Application;
import android.os.AsyncTask;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.util.OnAsyncEventListener;

public class DeleteTrip extends AsyncTask<Trip, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    //constructor
    public DeleteTrip(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Trip... params) {
        try {
            for (Trip trip : params) {
                //delete trip
                ((BaseApp) application).getDatabase().tripDao()
                        .delete(trip);
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
