package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.viewmodel.TripViewModel;


public class TripActivity extends AppCompatActivity  {

    private static final String TAG = "TripActivitys";

    private Trip trip;
    private TextView tvCountryName;
    private TextView tvTripName;
    private TextView tvDuration;
    private TextView tvDate;
    private TextView tvprice;
    private TextView tvDescription;

    private TripViewModel vmTrip;


    //To put the symbols to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_trip, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        String tname = "Barcelona";

        initiateView();

        TripViewModel.Factory tripFac = new TripViewModel.Factory(getApplication(), tname);
        vmTrip = ViewModelProviders.of(this, tripFac).get(TripViewModel.class);
        vmTrip.getTrip().observe(this, tripEntity -> {
            if (tripEntity != null) {
                trip = tripEntity;
                updateContent();
            }

        });
    }

    private void initiateView() {
        tvCountryName = findViewById(R.id.country);
        tvTripName = findViewById(R.id.trip);
        tvDuration = findViewById(R.id.duration);
        tvDate = findViewById(R.id.date);
        tvprice = findViewById(R.id.price);
        tvDescription = findViewById(R.id.description);
    }

    private void updateContent() {
        if (trip != null) {
            tvCountryName.setText(trip.getTripname());
            tvTripName.setText(trip.getTripname());
            tvDuration.setText(trip.getDuration());
            tvDate.setText(trip.getDate());
            tvprice.setText(trip.getPrice());
            tvDescription.setText(trip.getDescription());
        }
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, TripActivityEdit.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
