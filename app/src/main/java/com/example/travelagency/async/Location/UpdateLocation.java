package com.example.travelagency.async.Location;

import android.app.Application;
import android.os.AsyncTask;

import com.example.travelagency.BaseApp;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.util.OnAsyncEventListener;

public class UpdateLocation extends AsyncTask<Location, Void, Void> {

    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;



    public UpdateLocation(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(Location... params) {
        try {
            for (Location location : params) {
                ((BaseApp) application).getDatabase().locationDao()
                        .update(location);
            }
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}