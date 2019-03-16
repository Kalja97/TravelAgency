package com.example.travelagency.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.util.OnAsyncEventListener;

public class CreateLocation extends AsyncTask<Location, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateLocation(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Location... params) {
        try {
            for (Location location : params) {
                //Create Method
                //((BaseApp) application).getDatabase().locationDao()
                 //       .insert(location);
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